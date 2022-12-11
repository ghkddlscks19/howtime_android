package com.example.register.activity;

import static android.widget.Toast.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.register.R;
import com.example.register.RetrofitAPI;
import com.example.register.domain.BoardDTO;
import com.example.register.domain.BoardReceivedDTO;
import com.example.register.domain.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardDetailActivity extends AppCompatActivity {
    TextView txtMemberId, txtTitle, txtContent, txtPrice, txtHashtag1, txtHashtag2, txtRequirement;
    ImageButton btnBack, btnMenu;
    Button btnMyBoard;
    String createDate, modifyDate;
    int boardId;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.145";
    private final String BASEURL = FRIP+":9090/board/";
    private RetrofitAPI retrofitAPI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_detail);
        getSupportActionBar().setTitle("글 세부보기");
        init();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);


        getClickBoard(boardId);

        // 뒤로가기 버튼 눌렀을때
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 메뉴 클릭
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);

                getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getItemId() == R.id.btnEdit) {
                            // 수정 클릭
                            BoardDTO boardDTO = new BoardDTO(boardId,txtTitle.getText().toString(), txtContent.getText().toString(), "#"+txtHashtag1.getText().toString()+"#"+txtHashtag2.getText().toString()
                                    , Integer.parseInt(txtPrice.getText().toString()), createDate, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")), txtRequirement.getText().toString(), Member.getInstance().getStudentNum());
                            Intent intent = new Intent(BoardDetailActivity.this, BoardUpdateActivity.class);
                            intent.putExtra("boardId", String.valueOf(boardId));
                            intent.putExtra("boardDTO", boardDTO);
                            startActivity(intent);

                        } else if (menuItem.getItemId() == R.id.btnDelete) {
                            // 삭제 클릭
                            AlertDialog.Builder builder = new AlertDialog.Builder(BoardDetailActivity.this);
                            builder.setTitle("글삭제").setMessage("글을 삭제하시겠습니까?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    deleteBoard();
                                    Intent intent = new Intent(BoardDetailActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    makeText(getApplicationContext(), "Cancel Click", LENGTH_SHORT).show();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    private void init(){
        txtMemberId = (TextView) findViewById(R.id.txtMemberId);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtHashtag1 = (TextView) findViewById(R.id.txtHashtag1);
        txtHashtag2 = (TextView) findViewById(R.id.txtHashtag2);
        txtRequirement = (TextView) findViewById(R.id.txtRequirement);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnMyBoard = (Button) findViewById(R.id.btnMyBoard);
        Intent boardIdIntent = getIntent();
        boardId = Integer.parseInt(boardIdIntent.getStringExtra("boardId"));
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
    }

    // 게시글 클릭했을때
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

                if(Member.getInstance().getStudentNum().equals(board.getMemberId().getStudentNum())){
                    btnMenu.setVisibility(View.VISIBLE);
                }

                txtMemberId.setText(board.getMemberId().getNickname());
                txtTitle.setText(board.getTitle());
                txtContent.setText(board.getContent());
                String[] hashtag = board.getHashtag().split("#");
                String hashtag1 = hashtag[1];
                String hashtag2 = hashtag[2];
                txtHashtag1.setText(hashtag1);
                txtHashtag2.setText(hashtag2);
                txtPrice.setText(String.valueOf(board.getPrice()));
                txtRequirement.setText(board.getRequirement());
                createDate = board.getCreateDate();
                modifyDate = board.getModifyDate();

            }
            @Override
            public void onFailure(Call<BoardReceivedDTO> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }

    // 글삭제
    private void deleteBoard(){
        Call<Void> call = retrofitAPI.deleteBoard(boardId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e("글상세보기", "성공!!!!!!!!!!!!!");
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!");
                    return;
                }

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }
}
