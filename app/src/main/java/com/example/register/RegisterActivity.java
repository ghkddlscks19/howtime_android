package com.example.register;

import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterActivity extends AppCompatActivity {

    EditText studentNum, regPassword, checkPassword,
            editName, editNickname, editEmail, editKey;
    Button checkStudentNum, sendEmail, certKey, reg, checkEmail;
    RadioGroup radioGroup;
    TextView textViewResult;
    RadioButton radioMan, radioWoman;
    String genderResult;
    TextView text;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String BASEURL = MYIP+":9090/member/";
    private RetrofitAPI retrofitAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        studentNum = (EditText) findViewById(R.id.studentNum);
        regPassword = (EditText) findViewById(R.id.regPassword);
        editName = (EditText) findViewById(R.id.editName);
        editNickname = (EditText) findViewById(R.id.editNickname);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editKey = (EditText) findViewById(R.id.editKey);
        checkStudentNum = (Button) findViewById(R.id.checkStudentNum);
        sendEmail = (Button) findViewById(R.id.sendEmail);
        certKey = (Button) findViewById(R.id.certKey);
        reg = (Button) findViewById(R.id.reg);
        checkEmail = (Button) findViewById(R.id.checkEmail);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioMan = (RadioButton) findViewById(R.id.man);
        radioWoman = (RadioButton) findViewById(R.id.woman);
        text = (TextView) findViewById(R.id.text);

        checkStudentNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ID중복확인버튼", "click!!");
                checkStudentNum();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.man) genderResult = radioMan.getText().toString();
                else if(checkedId == R.id.woman) genderResult = radioWoman.getText().toString();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("가입하기", "click!!");
                createMember();
            }
        });


    }
    private void checkStudentNum() {
        Call<Boolean> call = retrofitAPI.checkStudentNum(studentNum.getText().toString());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.e("ID중복확인", "성공!!");
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "실패", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!response.body()){
                    Toast.makeText(getBaseContext(), "이미 가입한 ID입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "성공", Toast.LENGTH_SHORT).show();
//                    studentNum.setFocusable(false);
//                    checkStudentNum.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("ID중복확인", "실패");
                t.printStackTrace();
            }
        });
    }

    private void createMember() {
        MemberDTO memberDTO = new MemberDTO(studentNum.getText().toString(), regPassword.getText().toString(),
                editName.getText().toString(), editNickname.getText().toString(),
                genderResult, editEmail.getText().toString());

        Call<MemberDTO> call = retrofitAPI.createMember(memberDTO);

        call.enqueue(new Callback<MemberDTO>() {
            @Override
            public void onResponse(Call<MemberDTO> call, Response<MemberDTO> response) {
                Log.e("회원가입", "회원가입성공!!");
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT);
                    return;
                }
                Member.setInstance(response.body().getStudentNum(), response.body().getPassword(), response.body().getName(),
                        response.body().getNickname(), response.body().getGender(), response.body().getEmail());

            }

            @Override
            public void onFailure(Call<MemberDTO> call, Throwable t) {
                Log.e("회원가입", "회원가입실패ㅠㅠ");
               t.printStackTrace();
            }
        });

    }
}

