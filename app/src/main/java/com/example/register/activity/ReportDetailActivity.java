package com.example.register.activity;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.register.R;
import com.example.register.RetrofitAPI;
import com.example.register.domain.BoardDTO;
import com.example.register.domain.BoardReceivedDTO;
import com.example.register.domain.Member;
import com.example.register.domain.ReportDTO;
import com.example.register.domain.ReportReceivedDTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportDetailActivity extends AppCompatActivity {
    private String TAG_HOME = "home_fragment";
    private String TAG_MYWRITE = "mywrite_fragment";
    private String TAG_REPORT = "report_fragment";
    private String TAG_MYREPORT = "myreport_fragment";
    private BottomNavigationView bottomNavigationView;
    private TextView txtMemberId, txtTitle, txtContent, txtAttackerNickname;
    private ImageButton btnBack, btnMenu;
    private String createDate, modifyDate;
    private int boardId;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.145";
    private final String BASEURL = FRIP+":9090/report/";
    private RetrofitAPI retrofitAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_detail);
        getSupportActionBar().setTitle("시간어때");

        // 레트로핏 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        init();

        getClickReport(boardId);

        // 뒤로가기 버튼 눌렀을때
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportDetailActivity.this, MainActivity.class);
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
                            ReportDTO reportDTO = new ReportDTO(boardId, txtTitle.getText().toString(), txtAttackerNickname.getText().toString()
                                    , createDate, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")), txtContent.getText().toString(), Member.getInstance().getStudentNum());
                            Intent intent = new Intent(ReportDetailActivity.this, ReportUpdateActivity.class);
                            intent.putExtra("boardId", String.valueOf(boardId));
                            intent.putExtra("reportDTO", reportDTO);
                            startActivity(intent);

                        } else if (menuItem.getItemId() == R.id.btnDelete) {
                            // 삭제 클릭
                            AlertDialog.Builder builder = new AlertDialog.Builder(ReportDetailActivity.this);
                            builder.setTitle("글삭제").setMessage("글을 삭제하시겠습니까?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    deleteReport();
                                    Intent intent = new Intent(ReportDetailActivity.this, MainActivity.class);
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

    private void init() {
        txtMemberId = (TextView) findViewById(R.id.txtMemberId);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);
        txtAttackerNickname = (TextView) findViewById(R.id.txtAttackerNickname);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        bottomNavigationView = findViewById(R.id.bottomNavi);
        Intent boardIdIntent = getIntent();
        boardId = Integer.parseInt(boardIdIntent.getStringExtra("boardId"));

    }

    // 게시글 클릭했을때
    private void getClickReport(int boardId) {
        Call<ReportReceivedDTO> call = retrofitAPI.getClickReport(boardId);

        call.enqueue(new Callback<ReportReceivedDTO>() {
            @Override
            public void onResponse(Call<ReportReceivedDTO> call, Response<ReportReceivedDTO> response) {
                Log.e("글상세보기", "성공!!!!!!!!!!!!!");
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!");
                    return;
                }

                ReportReceivedDTO board = response.body();

                if(Member.getInstance().getStudentNum().equals(board.getMemberId().getStudentNum())){
                    btnMenu.setVisibility(View.VISIBLE);
                }

                txtMemberId.setText(board.getMemberId().getNickname());
                txtTitle.setText(board.getTitle());
                txtAttackerNickname.setText(board.getAttackerNickname());
                txtContent.setText(board.getContent());
                createDate = board.getCreateDate();
                modifyDate = board.getModifyDate();
            }
            @Override
            public void onFailure(Call<ReportReceivedDTO> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }

    // 글삭제
    private void deleteReport(){
        Call<Void> call = retrofitAPI.deleteReport(boardId);

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
