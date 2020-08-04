package com.dasom.khuhelper;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ChargingStationParser extends AsyncTask<Void, Void, Void> {

    private String queryURL;

    private ArrayList<ChargingStation> csList;
    private ChargingStationParserCallBack cspCallBack;

    private int eventType;
    private String tag;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;

    public ChargingStationParser(ChargingStationParserCallBack cspCallBack) {
        this.cspCallBack = cspCallBack;
        csList = new ArrayList<>();
        queryURL = "http://open.ev.or.kr:8080/openapi/services/rest/EvChargerService?"//요청 URL
                +"serviceKey=" + ServiceKey.key;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(queryURL); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            xpp.next();
            eventType = xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("Parser", "파싱 시작");
                        break;

                    case XmlPullParser.START_TAG: // 태그 내용 chargingStation에 저장
                        tag = xpp.getName(); //태그 이름 얻어오기
                        csList.add(setChargingStation());
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        Log.d("Parser", "END_TAG");
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            System.err.println("There was an error: " + e.getCause() + " : " + e.getMessage());
        }

        Log.d("Parser", "파싱 끝...");

        return null;
    }

    @Override
    protected void onPostExecute(Void vd) {
        super.onPostExecute(vd);
        cspCallBack.onSuccess(csList);
    }

    private ChargingStation setChargingStation() {
        ChargingStation tmpStation = new ChargingStation();
        boolean isEndTag = false;

        try {
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    default:
                        isEndTag = false;
                        break;

                    case XmlPullParser.START_TAG:
                        isEndTag = false;
                        tag = xpp.getName(); //태그 이름 얻어오기

                        if(tag.equals("item")) {
                        }
                        else if(tag.equals("statId")){
                            xpp.next();
                            tmpStation.setStatId(xpp.getText());
                        }
                        else if(tag.equals("statNm")){
                            xpp.next();
                            tmpStation.setStatNm(xpp.getText());
                        }
                        else if(tag.equals("chgerType")){
                            xpp.next();
                            tmpStation.setChgerType(Integer.parseInt(xpp.getText()));
                        }
                        else if(tag.equals("addrDoro")){
                            xpp.next();
                            tmpStation.setAddrDoro(xpp.getText());
                        }
                        else if(tag.equals("lat")){
                            xpp.next();
                            tmpStation.setLat(Float.parseFloat(xpp.getText()));
                        }
                        else if(tag.equals("lng")){
                            xpp.next();
                            tmpStation.setLng(Float.parseFloat(xpp.getText()));
                        }
                        else if(tag.equals("useTime")){
                            xpp.next();
                            tmpStation.setUseTime(xpp.getText());
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        if (isEndTag) return tmpStation;
                        isEndTag = true;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            System.err.println("There was an error: " + e.getCause() + " : " + e.getMessage());
        }

        return tmpStation;
    }
}