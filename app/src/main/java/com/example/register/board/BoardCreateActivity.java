package com.example.register.board;

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

import com.example.register.MainActivity;
import com.example.register.R;
import com.example.register.RetrofitAPI;
import com.example.register.member.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardCreateActivity extends AppCompatActivity {
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.21";
    private final String BASEURL = FRIP+":9090/board/";
    private RetrofitAPI retrofitAPI;

    EditText editTitle, editContent, editCondition, editHashtag1, editHashtag2, editHashtag3, editHashtag4, editPrice;
    Button btnWrite;
    ImageButton btnBack;
    String sendStudentNum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardcreate);
        init();

        //레트로핏 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);


        //글쓰기 버튼 눌렀을 때
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("글쓰기버튼", "click!!");
                createBoard();
                Intent intent = new Intent(BoardCreateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //뒤로가기 버튼 눌렀을 때
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("뒤로가기버튼", "click!!");
                Intent intent = new Intent(BoardCreateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        editTitle = (EditText) findViewById(R.id.editTitle);
        editContent = (EditText) findViewById(R.id.editContent);
        editCondition = (EditText) findViewById(R.id.editCondition);
        editHashtag1 = (EditText) findViewById(R.id.editHashtag1);
        editHashtag2 = (EditText) findViewById(R.id.editHashtag2);
        editPrice = (EditText) findViewById(R.id.editPrice);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnWrite = (Button) findViewById(R.id.btnWrite);
    }

    private void createBoard() {
        BoardDTO boardDTO = new BoardDTO(editTitle.getText().toString(), editContent.getText().toString(),
                "#"+editHashtag1.getText().toString()+"#"+editHashtag2.getText().toString(),
                Integer.parseInt(editPrice.getText().toString()), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")), editCondition.getText().toString(), Member.getInstance().getStudentNum());

        Call<BoardDTO> call = retrofitAPI.createBoard(boardDTO);

        call.enqueue(new Callback<BoardDTO>() {
            @Override
            public void onResponse(Call<BoardDTO> call, Response<BoardDTO> response) {
                Log.e("글쓰기", "글쓰기성공!!");
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT);
                    return;
                } else {
                    boardDTO.setTitle(response.body().getTitle());
                    boardDTO.setContent(response.body().getContent());
                    boardDTO.setHashtag(response.body().getHashtag());
                    boardDTO.setPrice(response.body().getPrice());
                    boardDTO.setCreateDate(response.body().getCreateDate());
                    boardDTO.setModifyDate(response.body().getModifyDate());
                    boardDTO.setRequirement(response.body().getRequirement());
                    boardDTO.setMemberid(response.body().getMemberid());
                    Intent intent = new Intent(BoardCreateActivity.this, MainActivity.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onFailure(Call<BoardDTO> call, Throwable t) {
                Log.e("글쓰기", "글쓰기실패ㅠㅠ");
                t.printStackTrace();
            }
        });

    }


}
