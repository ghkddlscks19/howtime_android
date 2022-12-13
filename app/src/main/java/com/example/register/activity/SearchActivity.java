package com.example.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.register.R;
import com.example.register.RetrofitAPI;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private final String MYIP = "http://192.168.3.43";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.145";
    private final String BASEURL = FRIP + ":9090/board/";
    private RetrofitAPI retrofitAPI;
    private Button btnSearch;
    private EditText editSearch;
    private ImageButton btnBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        init();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchDetailActivity.class);
                intent.putExtra("keyword", editSearch.getText().toString());
                startActivity(intent);
                overridePendingTransition(0, 0); //인텐트 애니메이션 없애기

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
            }
        });

    }

    private void init() {
        btnSearch= (Button) findViewById(R.id.buttonsearch);
        editSearch= (EditText) findViewById(R.id.editsearch);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

}