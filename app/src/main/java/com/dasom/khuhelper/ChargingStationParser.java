package com.dasom.khuhelper;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ChargingStationParser extends AsyncTask<String, Integer, String> {

    private String queryURL = "http://open.ev.or.kr:8080/openapi/services/rest/EvChargerService?"//요청 URL
            +"serviceKey=" + ServiceKey.key;

    ArrayList<ChargingStation> csList = new ArrayList<>();

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url= new URL(queryURL);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            ChargingStation chargingStation = new ChargingStation();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("Parser", "파싱 시작");
                        chargingStation = new ChargingStation(); // 얘 위치가 어디가 적합할지 생각해보기
                        break;

                    case XmlPullParser.START_TAG: // 태그 내용 chargingStation에 저장
                        tag = xpp.getName(); //태그 이름 얻어오기

                        if(tag.equals("item")) ; // 첫번째 검색결과
                        else if(tag.equals("statId")){
                            xpp.next();
                            chargingStation.setStatId(Integer.parseInt(xpp.getText()));
                        }
                        else if(tag.equals("statNm")){
                            xpp.next();
                            chargingStation.setStatNm(xpp.getText());
                        }
                        else if(tag.equals("chgerType")){
                            xpp.next();
                            chargingStation.setChgerType(Integer.parseInt(xpp.getText()));
                        }
                        else if(tag.equals("addrDoro")){
                            xpp.next();
                            chargingStation.setAddrDoro(xpp.getText());
                        }
                        else if(tag.equals("lat")){
                            xpp.next();
                            chargingStation.setLat(Integer.parseInt(xpp.getText()));
                        }
                        else if(tag.equals("lng")){
                            xpp.next();
                            chargingStation.setLng(Integer.parseInt(xpp.getText()));
                        }
                        else if(tag.equals("useTime")){
                            xpp.next();
                            chargingStation.setUseTime(xpp.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        csList.add(chargingStation); // 리스트에 추가
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            System.err.println("There was an error: " + e.getCause() + " : " + e.getMessage());
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        Log.d("Parser", "파싱 끝...");

        return null;
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
    }
}