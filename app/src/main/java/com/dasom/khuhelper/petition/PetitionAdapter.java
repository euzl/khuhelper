package com.dasom.khuhelper.petition;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dasom.khuhelper.R;
import com.dasom.khuhelper.admin.PetitionReplyActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetitionAdapter extends RecyclerView.Adapter<PetitionAdapter.PetitionViewHolder> {

    private Context context;
    private ArrayList<Petition> petitionArrayList;

    public PetitionAdapter(Context context, ArrayList<Petition> petitionArrayList) {
        this.context = context;
        this.petitionArrayList = petitionArrayList;
    }

    /**
     * 뷰홀더 생성 및 셋팅
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public PetitionAdapter.PetitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.petition_item, parent, false); // 뷰 생성
        PetitionViewHolder viewHolder = new PetitionViewHolder(view); // 윗줄에서 생성된 뷰를 뷰홀더로
        return viewHolder;
    }

    /**
     * position에 해당되는 데이터를 뷰홀더에 넣는 과정
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull PetitionAdapter.PetitionViewHolder holder, final int position) {
        final Petition petition = petitionArrayList.get(position);

        holder.titleTextView.setText(petition.getTitle());
        holder.placeTextView.setText("(" + petition.getCsName() + ")");

        if (petition.isCheck()) {
            holder.titleTextView.setTextColor(holder.itemView.getResources().getColor(R.color.blue));
        }

        // TODO: 04/09/2020 사용자가 확인할 때는 다른 액티비티로 이동하거나 다이얼로그가 떠야 한다. 따라서 리스너를 사용하던가 어댑터를 또 만들던가... 해야된다.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PetitionReplyActivity.class);
                intent.putExtra("petition", petition);
                context.startActivity(intent);
            }
        });
    }

    /**
     * 리사이클러뷰 안에 들어갈 뷰 홀더의 개수
     * (리턴값이 뷰 홀더의 개수와 맞지 않으면 리사이클러뷰가 뜨지 않는다.)
     * @return
     */
    @Override
    public int getItemCount() {
        if (petitionArrayList != null) {
            return petitionArrayList.size();
        }
        return 0;
    }

    public class PetitionViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView placeTextView;
        View itemView;

        public PetitionViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;

            titleTextView = itemView.findViewById(R.id.tv_petition_title);
            placeTextView = itemView.findViewById(R.id.tv_petition_place);
        }
    }
}