package com.example.register;

import com.example.register.domain.AnswerDTO;
import com.example.register.domain.AnswerReceivedDTO;
import com.example.register.domain.BoardDTO;
import com.example.register.domain.BoardReceivedDTO;
import com.example.register.domain.Member;
import com.example.register.domain.MemberDTO;
import com.example.register.domain.ReportDTO;
import com.example.register.domain.ReportReceivedDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    // 회원가입
    @POST("create/")
    Call<MemberDTO> createMember(@Body MemberDTO memberDTO);

    // 로그인 한 멤버 정보
    @GET("check/student/{studentnum}")
    Call<List<MemberDTO>> loginMember(@Path("studentnum") String studentNum);

    // 학번 중복확인
    @GET("check/student/")
    Call<Boolean> checkStudentNum(@Query("studentNum") String studentNum);

    // 이메일 중복확인
    @GET("check/email/")
    Call<Boolean> checkEmail(@Query("email") String email);

    // 닉네임 중복확인
    @GET("check/nickname/")
    Call<Boolean> checkNickname(@Query("nickname") String nickname);

    // 로그인 정보 맞는지 확인
    @POST("login/")
    Call<Boolean> checkLogin(@Query("studentNum") String studentNum, @Query("password") String password);

    // 게시글 작성
    @POST("create/")
    Call<BoardDTO> createBoard(@Body BoardDTO boardDTO);

    // 게시글 전부 조회
    @GET("create/all/")
    Call<List<BoardReceivedDTO>> getBoard();

    // 특정 게시물 정보 받아오기
    @GET("create/{boardid}")
    Call<BoardReceivedDTO> getClickBoard(@Path("boardid") int boardId);

    // 댓글 작성
    @POST("create/")
    Call<AnswerDTO> createAnswer(@Body AnswerDTO answerDTO);

    // 댓글 전체 받아오기
    @GET("create/{boardid}")
    Call<AnswerReceivedDTO> getAnswer(@Path("boardid") int boardId);

    // 글 수정
    @PATCH("update/")
    Call<BoardDTO> updateBoard(@Body BoardDTO boardDTO);

    // 내 글 보기
    @GET("create/my/{memberid}")
    Call<List<BoardReceivedDTO>> getMyBoard(@Path("memberid") String memberId);

    // 글 삭제하기
    @DELETE("delete/{boardid}")
    Call<Void> deleteBoard(@Path("boardid") int boardId);

    // 신고 글 생성
    @POST("create/")
    Call<ReportDTO> createReport(@Body ReportDTO reportDTO);

    // 신고 글 전체 불러오기
    @GET("create/all/")
    Call<List<ReportReceivedDTO>> getReport();

    // 클릭한 신고 글 정보 받아오기
    @GET("create/{boardid}")
    Call<ReportReceivedDTO> getClickReport(@Path("boardid") int boardId);

    // 신고 글 수정
    @PATCH("update/")
    Call<ReportDTO> updateReport(@Body ReportDTO reportDTO);

    // 신고 글 삭제
    @DELETE("delete/{boardid}")
    Call<Void> deleteReport(@Path("boardid") int boardId);

    // 내 신고 글 보기
    @GET("create/my/{memberid}")
    Call<List<ReportReceivedDTO>> getMyReport(@Path("memberid") String memberId);
}
