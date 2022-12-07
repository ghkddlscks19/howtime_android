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
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardDetailActivity extends AppCompatActivity {
    TextView txtId, txtTitle, txtContent, txtPrice, txtHashtag1, txtHashtag2, txtRequirement;
    ImageButton btnBack;
    int boardId;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.145";
    private final String BASEURL = RESTIP+":9090/board/";
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

        Intent boardIdIntent = getIntent();
        boardId = Integer.parseInt(boardIdIntent.getStringExtra("boardId"));
        getClickBoard(boardId);

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
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtHashtag1 = (TextView) findViewById(R.id.txtHashtag1);
        txtHashtag2 = (TextView) findViewById(R.id.txtHashtag2);
        txtRequirement = (TextView) findViewById(R.id.txtRequirement);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }

    private void getClickBoard(int boardId) {
        Call<BoardReceivedDTO> call = retrofitAPI.getClickBoard(boardId);

        call.enqueue(new Callback<BoardReceivedDTO>() {
            @Override
            public void onResponse(Call<BoardReceivedDTO> call, Response<BoardReceivedDTO> response) {
                Log.e("글상세보기", "성공!!!!!!!!!!!!!");
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!");
                    return;
                }

                BoardReceivedDTO board = response.body();

                txtId.setText(board.getMemberId().getNickname());
                txtTitle.setText(board.getTitle());
                txtContent.setText(board.getContent());
                String[] hashtag = board.getHashtag().split("#");
                String hashtag1 = hashtag[1];
                String hashtag2 = hashtag[2];
                txtHashtag1.setText(hashtag1);
                txtHashtag2.setText(hashtag2);
                txtPrice.setText(String.valueOf(board.getPrice()));
                txtRequirement.setText(board.getRequirement());

            }
            @Override
            public void onFailure(Call<BoardReceivedDTO> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }
}
