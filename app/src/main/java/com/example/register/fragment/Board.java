package com.example.register.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;
import com.example.register.RetrofitAPI;
import com.example.register.domain.BoardReceivedDTO;
import com.example.register.domain.Member;
import com.example.register.recyclerview.MainAdapter;
import com.example.register.recyclerview.MainData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Board extends Fragment {

    private View view;
    private ArrayList<MainData> arrayList;
    private MainAdapter myBoardAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RetrofitAPI retrofitAPI;
    private final String MYIP = "http://192.168.2.28";
    private final String FRIP = "http://192.168.3.134";
    private final String RESTIP = "http://172.16.153.145";
    private final String BASEURL = FRIP+":9090/board/";


    private void getMyBoard(String memberId) {
        Call<List<BoardReceivedDTO>> call = retrofitAPI.getMyBoard(memberId);


        call.enqueue(new Callback<List<BoardReceivedDTO>>() {
            @Override
            public void onResponse(Call<java.util.List<BoardReceivedDTO>> call, Response<List<BoardReceivedDTO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response", "실패!!!!!!!!");
                    return;
                }
                Log.e("Response", "성공!!!!!!!!");
                java.util.List<BoardReceivedDTO> board = response.body();
                for(BoardReceivedDTO post : board) {
                    arrayList.add(new MainData(post.getHashtag(), post.getTitle(), post.getModifyDate(), post.getMemberId().getNickname(), post.getId()));
                }
                myBoardAdapter = new MainAdapter(arrayList);
                recyclerView.setAdapter(myBoardAdapter);
                linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);

                recyclerView.setLayoutManager(linearLayoutManager);


            }
            @Override
            public void onFailure(Call<java.util.List<BoardReceivedDTO>> call, Throwable t) {
                Log.e("Response", "실패!!!!!!!!");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.myboard, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        //linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        myBoardAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(myBoardAdapter);

        // 레트로핏 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        getMyBoard(Member.getInstance().getStudentNum());

        return view;
    }

}
