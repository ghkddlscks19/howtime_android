package com.example.register.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.register.R;
import com.example.register.RetrofitAPI;
import com.example.register.domain.BoardReceivedDTO;
import com.example.register.recyclerview.MainAdapter;
import com.example.register.recyclerview.MainData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchDetailActivity extends AppCompatActivity {
    private final String MYIP = "http://192.168.3.43";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.145";
    private final String BASEURL = FRIP + ":9090/board/";
    private String keyword;
    private RetrofitAPI retrofitAPI;
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ImageButton btnBack;
    private Button btnSearch;
    private EditText editKeyword;
    private boolean search = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail);

        init();
        Intent getKeywordIntent = getIntent();
        keyword = getKeywordIntent.getStringExtra("keyword");
        Log.e("keyword", keyword);

        //레트로핏 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        searchBoard();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                searchBoardInDetail();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SearchDetailActivity.this , MainActivity.class);
                startActivity(intent);
            }
        });

    }



    private void init() {
        btnBack = findViewById(R.id.btnBack);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        arrayList = new ArrayList<>();
        btnSearch=findViewById(R.id.btnSearch);
        editKeyword=findViewById(R.id.editSearch);

    }


    private void searchBoardInDetail() {

        Call<List<BoardReceivedDTO>> call = retrofitAPI.searchBoard(editKeyword.getText().toString());
        Log.e("인", "안에서 버튼클릭");
        Log.e("인", editKeyword.getText().toString());

        call.enqueue(new Callback<List<BoardReceivedDTO>>() {
            @Override
            public void onResponse(Call<List<BoardReceivedDTO>> call, Response<List<BoardReceivedDTO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!@@@!!");
                    return;
                }
                Log.e("Response", "성공!!!!!!!!");
                List<BoardReceivedDTO> board = response.body();
                for (BoardReceivedDTO post : board) {
                    arrayList.add(new MainData(post.getHashtag(), post.getTitle(), post.getCreateDate(), post.getMemberId().getNickname(), post.getId()));
                }
                mainAdapter = new MainAdapter(arrayList);
                recyclerView.setAdapter(mainAdapter);
                linearLayoutManager = new LinearLayoutManager(SearchDetailActivity.this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);

                recyclerView.setLayoutManager(linearLayoutManager);

                if(arrayList.isEmpty()){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(SearchDetailActivity.this);
                    dlg.setTitle("검색정보"); //제목
                    dlg.setMessage("검색한 내용의 결과가 없습니다."); // 메시지
//                버튼 클릭시 동작
                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dlg.show();
                }
            }

            @Override
            public void onFailure(Call<List<BoardReceivedDTO>> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }

    private void searchBoard() {
        Call<List<BoardReceivedDTO>> call = retrofitAPI.searchBoard(keyword);


        call.enqueue(new Callback<List<BoardReceivedDTO>>() {
            @Override
            public void onResponse(Call<List<BoardReceivedDTO>> call, Response<List<BoardReceivedDTO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!");
                    return;
                }
                Log.e("Response", "성공!!!!!!!!");
                List<BoardReceivedDTO> board = response.body();
                for (BoardReceivedDTO post : board) {
                    arrayList.add(new MainData(post.getHashtag(), post.getTitle(), post.getCreateDate(), post.getMemberId().getNickname(), post.getId()));
                }
                mainAdapter = new MainAdapter(arrayList);
                recyclerView.setAdapter(mainAdapter);
                linearLayoutManager = new LinearLayoutManager(SearchDetailActivity.this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);

                recyclerView.setLayoutManager(linearLayoutManager);

                if(arrayList.isEmpty()){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(SearchDetailActivity.this);
                    dlg.setTitle("검색정보"); //제목
                    dlg.setMessage("검색한 내용의 결과가 없습니다."); // 메시지
//                버튼 클릭시 동작
                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dlg.show();
                    search = false;
                }

            }

            @Override
            public void onFailure(Call<List<BoardReceivedDTO>> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }
}
