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

public class AddPoetry extends AppCompatActivity {

    EditText poetName, poetryData;
    AppCompatButton submit;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);
        initialization();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetryDataString = poetryData.getText().toString();
                String poetNameString = poetName.getText().toString();

                if(poetryDataString.equals("")) {
                    poetryData.setError("Field is Empty");
                }else {
                    if(poetNameString.equals("")) {
                        poetName.setError("Field Is Empty");
                    } else {
                        Toast.makeText(AddPoetry.this, "Call Api", Toast.LENGTH_SHORT).show();
                        callapi(poetryDataString, poetNameString);
                    }
                }
            }
        });
    }

    private void initialization() {
        poetName = findViewById(R.id.add_poet_name_edittext);
        poetryData = findViewById(R.id.add_poetry_data_edittext);
        submit = findViewById(R.id.submit_data_btn);
        Retrofit retrofit = ApiClient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void callapi(String poetryData, String poetName) {

        apiInterface.addpoetry(poetryData, poetName).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try {
                    if(response.body().getStatus().equals("1")) {
                        Toast.makeText(AddPoetry.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddPoetry.this, "Not Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                    
                }
                catch (Exception e) {
                    Log.e("AddExp", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });

    }
}