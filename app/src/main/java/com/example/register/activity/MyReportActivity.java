package com.example.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;
import com.example.register.RetrofitAPI;
import com.example.register.domain.BoardReceivedDTO;
import com.example.register.domain.Member;
import com.example.register.domain.ReportReceivedDTO;
import com.example.register.recyclerview.MainAdapter;
import com.example.register.recyclerview.MainData;
import com.example.register.recyclerview.ReportAdapter;
import com.example.register.recyclerview.ReportData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyReportActivity extends AppCompatActivity {

    private ArrayList<ReportData> arrayList;
    private ReportAdapter myReportAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    RetrofitAPI retrofitAPI;
    Button btnMain;
    ToggleButton btnMyToggle;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.145";
    private final String BASEURL = FRIP+":9090/report/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myreport);
        getSupportActionBar().setTitle("내 신고 글 보기");

        // 레트로핏 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        init();

        getMyReport(Member.getInstance().getStudentNum());

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyReportActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnMyToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent intent = new Intent(MyReportActivity.this, MyBoardActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void init(){
        btnMain = (Button) findViewById(R.id.btnMain);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        arrayList = new ArrayList<>();
        bottomNavigationView = findViewById(R.id.bottomNavi);
        btnMyToggle = (ToggleButton) findViewById(R.id.btnMyToggle);
    }

    private void getMyReport(String memberId) {
        Call<List<ReportReceivedDTO>> call = retrofitAPI.getMyReport(memberId);


        call.enqueue(new Callback<List<ReportReceivedDTO>>() {
            @Override
            public void onResponse(Call<List<ReportReceivedDTO>> call, Response<List<ReportReceivedDTO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!");
                    return;
                }
                Log.e("Response", "성공!!!!!!!!");
                List<ReportReceivedDTO> board = response.body();
                for(ReportReceivedDTO post : board) {
                    arrayList.add(new ReportData(post.getTitle(), post.getModifyDate(), post.getMemberId().getNickname(), post.getId()));
                }
                myReportAdapter = new ReportAdapter(arrayList);
                recyclerView.setAdapter(myReportAdapter);
                linearLayoutManager = new LinearLayoutManager(MyReportActivity.this);
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

}
