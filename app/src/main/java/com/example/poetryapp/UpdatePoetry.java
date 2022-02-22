package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.Response.DeleteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePoetry extends AppCompatActivity {
    
    EditText poetryData;
    AppCompatButton updateBtn;
    int poetryId;
    String poetryDataString;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poetry);
        initialization();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p_data = poetryData.getText().toString();
                if(p_data.equals("")) {
                    poetryData.setError("Field Is Empty");
                } else {
                    callapi(p_data, poetryId+"");
                    Toast.makeText(UpdatePoetry.this, "Call Api", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialization() {
        poetryData = findViewById(R.id.update_poetry_data_edittext);
        updateBtn = findViewById(R.id.update_data_btn);

        poetryId = getIntent().getIntExtra("p_id",0);
        poetryDataString = getIntent().getStringExtra("p_data");
        poetryData.setText(poetryDataString);

        Retrofit retrofit = ApiClient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void callapi(String pData, String pid) {
        apiInterface.updatepoetry(pData, pid).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try {
                    if(response.body().getStatus().equals("1")) {
                        Toast.makeText(UpdatePoetry.this, "Data Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdatePoetry.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("UpdateExp", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }
}