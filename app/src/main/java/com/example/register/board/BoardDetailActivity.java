package com.example.register.board;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.register.MainActivity;
import com.example.register.R;
import com.example.register.RetrofitAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardDetailActivity extends AppCompatActivity {
    TextView txtId, txtTitle, txtContent, txtHashtag1, txtHashtag2, txtCondition;
    ImageButton btnBack;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.21";
    private final String BASEURL = FRIP+":9090/board/";
    private RetrofitAPI retrofitAPI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_detail);
        init();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init(){
        txtId = (TextView) findViewById(R.id.txtId);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);
        txtHashtag1 = (TextView) findViewById(R.id.txtHashtag1);
        txtHashtag2 = (TextView) findViewById(R.id.txtHashtag2);
        txtCondition = (TextView) findViewById(R.id.txtCondition);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

    private void getClickBoard(int id) {
        Call<List<BoardReceivedDTO>> call = retrofitAPI.getClickBoard(id);


        call.enqueue(new Callback<List<BoardReceivedDTO>>() {
            @Override
            public void onResponse(Call<List<BoardReceivedDTO>> call, Response<List<BoardReceivedDTO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!");
                    return;
                }
                List<BoardReceivedDTO> board = response.body();
                for(BoardReceivedDTO post : board) {
                    txtId.setText(post.getMemberId().getNickname());
                    txtTitle.setText(post.getTitle());
                    txtContent.setText(post.getContent());
                    txtHashtag1.setText(post.getHashtag());
                    txtCondition.setText(post.getRequirement());
                }

            }
            @Override
            public void onFailure(Call<List<BoardReceivedDTO>> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }
}
