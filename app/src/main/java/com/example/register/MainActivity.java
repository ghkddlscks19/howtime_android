package com.example.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.board.Board;
import com.example.register.board.BoardCreateActivity;
import com.example.register.board.BoardDTO;
import com.example.register.board.MainAdapter;
import com.example.register.board.MainData;
import com.example.register.member.LoginActivity;
import com.example.register.member.Member;
import com.example.register.member.MemberDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static String c = "";
    RetrofitAPI retrofitAPI;
    FloatingActionButton add;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.21";
    private final String BASEURL = FRIP+":9090/board/";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //레트로핏 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        init();
        getBoard();



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BoardCreateActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        add = findViewById(R.id.add);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        arrayList = new ArrayList<>();
    }

    private String getMember(String studentNum) {
        Call<List<MemberDTO>> call = retrofitAPI.getMember(studentNum);
        call.enqueue(new Callback<List<MemberDTO>>() {

            @Override
            public void onResponse(Call<List<MemberDTO>> call, Response<List<MemberDTO>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<MemberDTO> member = response.body();

                for(MemberDTO post : member){
                    c = post.getNickname();
                }

            }
            @Override
            public void onFailure(Call<List<MemberDTO>> call, Throwable t) {

            }
        });
        return c;
    }

    private void getBoard() {
        Call<List<BoardDTO>> call = retrofitAPI.getBoard();


        call.enqueue(new Callback<List<BoardDTO>>() {
            @Override
            public void onResponse(Call<List<BoardDTO>> call, Response<List<BoardDTO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!");
                    return;
                }
                List<BoardDTO> board = response.body();
                for(BoardDTO post : board) {
                    arrayList.add(new MainData(post.getHashtag(), post.getTitle(), post.getModifyDate(), getMember(post.getMemberid())));
                }
                mainAdapter = new MainAdapter(arrayList);
                recyclerView.setAdapter(mainAdapter);
                linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
            @Override
            public void onFailure(Call<List<BoardDTO>> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }



}
