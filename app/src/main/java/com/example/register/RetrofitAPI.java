package com.example.register;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("create/")
    Call<MemberDTO> createMember(@Body MemberDTO memberDTO);

    @GET("check/student/")
    Call<Boolean> checkStudentNum(@Query("studentNum") String studentNum);

    @GET("check/email/")
    Call<Boolean> checkEmail(@Query("email") String email);

    @GET("check/nickname/")
    Call<Boolean> checkNickname(@Query("nickname") String nickname);

    @POST("login/")
    Call<Boolean> checkLogin(@Query("studentNum") String studentNum, @Query("password") String password);
}
