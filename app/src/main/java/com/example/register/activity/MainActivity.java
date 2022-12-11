package com.example.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;
import com.example.register.RetrofitAPI;
import com.example.register.domain.BoardReceivedDTO;
import com.example.register.recyclerview.MainAdapter;
import com.example.register.recyclerview.MainData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
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
    private Button btnMyBoard, btnReportMain;
    RetrofitAPI retrofitAPI;
    FloatingActionButton add;
    private MyBoardActivity myBoardActivity;
    private ReportMainActivity reportMainActivity;
    private MainActivity mainActivity;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.145";
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


        // 글 생성 버튼
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BoardCreateActivity.class);
                startActivity(intent);
            }
        });

        // 내 글 보기 버튼
        btnMyBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("내글보기", "click!!!!!!!!!!");
                Intent intent = new Intent(MainActivity.this, MyBoardActivity.class);
                startActivity(intent);
            }
        });

        // 신고 글 전체보기 버튼
        btnReportMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("신고 글 전체보기", "click!!!!!!!!!!");
                Intent intent = new Intent(MainActivity.this, ReportMainActivity.class);
                startActivity(intent);
            }
        });

//        // 네비게이션 바 설정
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.home:
//                        setFrag(0);
//                        break;
//                    case R.id.writing:
//                        setFrag(1);
//                        break;
//                    case R.id.report:
//                        setFrag(2);
//                        break;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });



    }



    private void init() {
        add = findViewById(R.id.add);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        arrayList = new ArrayList<>();
        bottomNavigationView = findViewById(R.id.bottomNavi);
        btnMyBoard = (Button) findViewById(R.id.btnMyBoard);
        btnReportMain = (Button) findViewById(R.id.btnReportMain);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavi);
        mainActivity = new MainActivity();
        myBoardActivity = new MyBoardActivity();
        reportMainActivity = new ReportMainActivity();
//        setFrag(0);
    }


    private void getBoard() {
        Call<List<BoardReceivedDTO>> call = retrofitAPI.getBoard();

        call.enqueue(new Callback<List<BoardReceivedDTO>>() {
            @Override
            public void onResponse(Call<List<BoardReceivedDTO>> call, Response<List<BoardReceivedDTO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!@");
                    return;
                }
                Log.e("Response", "성공!!!!!!!!");
                List<BoardReceivedDTO> board = response.body();
                for(BoardReceivedDTO post : board) {
                    arrayList.add(new MainData(post.getHashtag(), post.getTitle(), post.getModifyDate(), post.getMemberId().getNickname(), post.getId()));
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

//    // 프레그먼트 교체
//    private void setFrag(int n){      //프래그먼트 교체가 일어나는 실행문
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        switch (n) {
//            case 0:
//                ft.replace(R.id.main_frame, mainActivity);
//                ft.commit();
//                break;
//            case 1:
//                ft.replace(R.id.main_frame, myBoardActivity);
//                ft.commit();
//                break;
//            case 2:
//                ft.replace(R.id.main_frame, reportMainActivity);
//                ft.commit();
//                break;
//        }
//    }
}
