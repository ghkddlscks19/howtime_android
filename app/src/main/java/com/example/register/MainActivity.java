package com.example.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.board.BoardCreateActivity;
import com.example.register.board.BoardDTO;
import com.example.register.board.BoardReceivedDTO;
import com.example.register.board.MainAdapter;
import com.example.register.board.MainData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
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


    private void getBoard() {
        Call<List<BoardReceivedDTO>> call = retrofitAPI.getBoard();


        call.enqueue(new Callback<List<BoardReceivedDTO>>() {
            @Override
            public void onResponse(Call<List<BoardReceivedDTO>> call, Response<List<BoardReceivedDTO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!");
                    return;
                }
                Log.e("Response", "성공!!!!!!!!");
                List<BoardReceivedDTO> board = response.body();
                for(BoardReceivedDTO post : board) {
                    arrayList.add(new MainData(post.getHashtag(), post.getTitle(), post.getCreateDate(), post.getMemberId().getNickname()));
                }
                mainAdapter = new MainAdapter(arrayList);
                recyclerView.setAdapter(mainAdapter);
                linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);

                recyclerView.setLayoutManager(linearLayoutManager);


            }
            @Override
            public void onFailure(Call<List<BoardReceivedDTO>> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }



}
