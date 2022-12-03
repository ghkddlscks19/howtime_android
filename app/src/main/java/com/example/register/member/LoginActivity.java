package com.example.register.member;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.register.MainActivity;
import com.example.register.R;
import com.example.register.RetrofitAPI;
import com.example.register.board.BoardCreateActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText editId, editPassword;
    Button btnLogin, btnRegister;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.21";
    private final String BASEURL = FRIP+":9090/member/";
    private RetrofitAPI retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();

        //레트로핏 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("로그인버튼", "click!!");
                checkLogin();

            }
        });
    }

    private void init() {
        editId = (EditText) findViewById(R.id.editId);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }

    //로그인 체크
    private void checkLogin() {
        Call<Boolean> call = retrofitAPI.checkLogin(editId.getText().toString(), editPassword.getText().toString());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.e("로그인", "성공!!");
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "실패", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!response.body()){
                    Toast.makeText(getBaseContext(), "ID나 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    loginMember();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("로그인", "실패ㅠㅠㅠ");
                t.printStackTrace();
            }
        });
    }

    //로그인 멤버 정보 저장
    private void loginMember() {
        Call<List<MemberDTO>> call = retrofitAPI.loginMember(editId.getText().toString());
        call.enqueue(new Callback<List<MemberDTO>>() {
            @Override
            public void onResponse(Call<List<MemberDTO>> call, Response<List<MemberDTO>> response) {
                Log.e("로그인멤버정보저장", "성공!!");
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "실패", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getBaseContext(), "로그인 멤버 정보저장 성공", Toast.LENGTH_SHORT).show();
                    List<MemberDTO> member = response.body();
                    for (MemberDTO post : member) {
                        Member.setInstance(post.getStudentNum(), post.getPassword(), post.getName(), post.getNickname(), post.getGender(), post.getEmail());
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<List<MemberDTO>> call, Throwable t) {
                Log.e("로그인멤버정보저장", "실패ㅠㅠㅠ");
                t.printStackTrace();
            }
        });
    }
}
