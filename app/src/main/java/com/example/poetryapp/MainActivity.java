package com.example.poetryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.poetryapp.Adapter.PoetryAdapter;
import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.Models.PoetryModel;
import com.example.poetryapp.Response.GetPoetryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PoetryAdapter poetryAdapter;
//    List<PoetryModel> poetryModelList = new ArrayList<>();
    ApiInterface apiInterface;
    AppCompatButton addPoetry;

    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        poetryModelList.add(new PoetryModel(1,"This is Poem that the legend is writing \nbut no one will Understand","Mirza Galib","01-May-2021"));
//        poetryModelList.add(new PoetryModel(1,"This is Poem that the legend is writing \nbut no one will Understand","Mirza Galib","01-May-2021"));
//        poetryModelList.add(new PoetryModel(1,"This is Poem that the legend is writing \nbut no one will Understand","Mirza Galib","01-May-2021"));
//        poetryModelList.add(new PoetryModel(1,"This is Poem that the legend is writing \nbut no one will Understand","Mirza Galib","01-May-2021"));
//        poetryModelList.add(new PoetryModel(1,"This is Poem that the legend is writing \nbut no one will Understand","Mirza Galib","01-May-2021"));
//        poetryModelList.add(new PoetryModel(1,"This is Poem that the legend is writing \nbut no one will Understand","Mirza Galib","01-May-2021"));
//
        initialization();
//        setadapter(poetryModelList);
        getdata();

        addPoetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Add Poetry Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AddPoetry.class);
                startActivity(intent);
            }
        });


        // Refreshing with SwipeRefreshLayout
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();

                refreshLayout.setRefreshing(false);
            }
        });

    }

    private void initialization() {
        recyclerView = findViewById(R.id.poetry_recyclerView);
        Retrofit retrofit = ApiClient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);
        addPoetry = findViewById(R.id.add_poetry);

        refreshLayout = findViewById(R.id.refreshLayout);
    }

    private void setadapter(List<PoetryModel> poetryModels) {
        poetryAdapter = new PoetryAdapter(this, poetryModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(poetryAdapter);
    }

    private void getdata() {
        Log.e("TAG","Working");
        apiInterface.getpoetry().enqueue(new Callback<GetPoetryResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPoetryResponse> call,@NonNull Response<GetPoetryResponse> response) {
                try {
                    if(response!=null) {
                        if(response.body().getStatus().equals("1")) {
                            setadapter(response.body().getData());
                            Log.e("TAG","try if Working");
                        }
                        else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("TAG","try else Working");
                        }
                    }
                }
                catch (Exception e) {
                    Log.e("exp",e.getLocalizedMessage());
                    Log.e("TAG","catch Working");
                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());
                Log.e("TAG","Failure Working");
            }
        });
    }

}