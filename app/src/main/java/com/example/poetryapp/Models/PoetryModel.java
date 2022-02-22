package com.example.poetryapp.Models;

public class PoetryModel {

    int id;
    String poetry_data, poet_name, data_tiime;

    public PoetryModel(int id, String poetry_data, String poet_name, String data_tiime) {
        this.id = id;
        this.poetry_data = poetry_data;
        this.poet_name = poet_name;
        this.data_tiime = data_tiime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoetry_data() {
        return poetry_data;
    }

    public void setPoetry_data(String poetry_data) {
        this.poetry_data = poetry_data;
    }

    public String getPoet_name() {
        return poet_name;
    }

    public void setPoet_name(String poet_name) {
        this.poet_name = poet_name;
    }

    public String getData_tiime() {
        return data_tiime;
    }

    public void setData_tiime(String data_tiime) {
        this.data_tiime = data_tiime;
    }
}
