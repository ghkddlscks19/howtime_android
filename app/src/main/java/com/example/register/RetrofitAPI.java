package com.example.register;

import com.example.register.board.BoardDTO;
import com.example.register.board.BoardReceivedDTO;
import com.example.register.member.MemberDTO;

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

    @GET("check/student/{studentnum}")
    Call<List<MemberDTO>> loginMember(@Path("studentnum") String studentNum);

    @GET("check/student/")
    Call<Boolean> checkStudentNum(@Query("studentNum") String studentNum);

    @GET("check/email/")
    Call<Boolean> checkEmail(@Query("email") String email);

    @GET("check/nickname/")
    Call<Boolean> checkNickname(@Query("nickname") String nickname);

    @POST("login/")
    Call<Boolean> checkLogin(@Query("studentNum") String studentNum, @Query("password") String password);

    @POST("create/")
    Call<BoardDTO> createBoard(@Body BoardDTO boardDTO);

    @GET("create/all/")
    Call<List<BoardReceivedDTO>> getBoard();

    @GET("create/{id}")
    Call<List<BoardReceivedDTO>> getClickBoard(@Path("id") int id);


}
