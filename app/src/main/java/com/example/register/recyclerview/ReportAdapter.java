package com.example.register.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;
import com.example.register.activity.ReportDetailActivity;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.CustomViewHolder>{

    private ArrayList<ReportData> arrayList;

    public ReportAdapter(ArrayList<ReportData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ReportAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    //데이터를 가져오는 문장? & 리스트뷰를 클릭하면 실행되는 동작도 여기서 구현
    public void onBindViewHolder(@NonNull ReportAdapter.CustomViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.date.setText(arrayList.get(position).getDate());
        holder.nname.setText(arrayList.get(position).getNname());
        holder.id.setText(String.valueOf(arrayList.get(position).getId()));

        //리스크뷰를 클릭했을때 토스트를 띄움(잘 작동하는지 테스트하기 위함)
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                String id = String.valueOf(holder.id.getText());
                Intent intent = new Intent(context, ReportDetailActivity.class);
                intent.putExtra("boardId", id);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView date;
        protected TextView nname;
        protected TextView id;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = (TextView) itemView.findViewById(R.id.boardId);
            this.title = (TextView) itemView.findViewById(R.id.Title);
            this.date = (TextView) itemView.findViewById(R.id.Date);
            this.nname = (TextView) itemView.findViewById(R.id.Nname);
        }
    }

}
