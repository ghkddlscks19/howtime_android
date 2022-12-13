package com.example.register.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;
import com.example.register.RetrofitAPI;
import com.example.register.activity.ReportCreateActivity;
import com.example.register.domain.ReportReceivedDTO;
import com.example.register.recyclerview.ReportAdapter;
import com.example.register.recyclerview.ReportData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Report extends Fragment {

    private View view;
    private ArrayList<ReportData> arrayList;
    private ReportAdapter reportAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    RetrofitAPI retrofitAPI;
    FloatingActionButton add;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.145";
    private final String BASEURL = FRIP+":9090/report/";

    private void init() {
        add = view.findViewById(R.id.add);
        //recyclerView = (RecyclerView) findViewById(R.id.rv);
        //arrayList = new ArrayList<>();
        //bottomNavigationView = findViewById(R.id.bottomNavi);
        //btnMain = findViewById(R.id.btnMain);
    }

    private void getReport() {
        Call<List<ReportReceivedDTO>> call = retrofitAPI.getReport();

        call.enqueue(new Callback<List<ReportReceivedDTO>>() {
            @Override
            public void onResponse(Call<List<ReportReceivedDTO>> call, Response<List<ReportReceivedDTO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!@");
                    return;
                }
                Log.e("Response", "성공!!!!!!!!");
                List<ReportReceivedDTO> board = response.body();
                for(ReportReceivedDTO post : board) {
                    arrayList.add(new ReportData(post.getTitle(), post.getModifyDate(), post.getMemberId().getNickname(), post.getId()));
                }
                reportAdapter = new ReportAdapter(arrayList);
                recyclerView.setAdapter(reportAdapter);
                linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);

                recyclerView.setLayoutManager(linearLayoutManager);


            }
            @Override
            public void onFailure(Call<List<ReportReceivedDTO>> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.report_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        reportAdapter = new ReportAdapter(arrayList);
        recyclerView.setAdapter(reportAdapter);

        //레트로핏 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        init();

        getReport();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("신고 글 작성", "click!!!!!!!!!!");
                Intent intent = new Intent(getActivity(), ReportCreateActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
