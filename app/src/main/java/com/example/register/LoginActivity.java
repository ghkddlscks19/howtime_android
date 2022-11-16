package com.example.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    private final String RESTIP = "http://192.168.0.6";
    private final String BASEURL = RESTIP+":9090/member/";
    private RetrofitAPI retrofitAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editId = (EditText) findViewById(R.id.editId);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

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
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("로그인", "실패ㅠㅠㅠ");
                t.printStackTrace();
            }
        });
    }
}
