package com.example.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.board.BoardCreateActivity;
import com.example.register.board.BoardDTO;
import com.example.register.board.BoardReceivedDTO;
import com.example.register.board.MainAdapter;
import com.example.register.board.MainData;
import com.example.register.frag.Frag1;
import com.example.register.frag.Frag2;
import com.example.register.frag.Frag3;
import com.example.register.frag.Frag4;
import com.example.register.member.Member;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

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
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private Frag4 frag4;
    private Button btnMyBoard;
    RetrofitAPI retrofitAPI;
    FloatingActionButton add;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.145";
    private final String BASEURL = RESTIP+":9090/board/";


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

        btnMyBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        setFrag(0);
                        break;
                    case R.id.post:
                        setFrag(1);
                        break;
                    case R.id.chat:
                        setFrag(2);
                        break;
                    case R.id.report:
                        setFrag(3);
                        break;
                }

                return true;
            }
        });
        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();
        frag4 = new Frag4();
        setFrag(0);

    }



    private void init() {
        add = findViewById(R.id.add);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        arrayList = new ArrayList<>();
        bottomNavigationView = findViewById(R.id.bottomNavi);
        btnMyBoard = (Button) findViewById(R.id.btnMyBoard);
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
                    arrayList.add(new MainData(post.getHashtag(), post.getTitle(), post.getCreateDate(), post.getMemberId().getNickname(), post.getId()));
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

    private void setFrag(int n){      //프래그먼트 교체가 일어나는 실행문
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, frag4);
                ft.commit();
                break;
        }
    }



}
