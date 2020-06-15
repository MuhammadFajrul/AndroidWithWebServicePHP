package com.example.androidwithwebservicephp.model;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

public class Mahasiswa {
    private String nama, stb, jk;
    private JSONObject jsonObject;
    private  Mahasiswa mhs;


    public Mahasiswa(String stb, String nama, String jk) {
        this.nama = nama;
        this.stb = stb;
        this.jk = jk;
    }


    public String getStb() {
        return stb;
    }

    public String getNama() {
        return nama;
    }

    public String getJk() {
        return jk;
    }

    public  JSONObject tojson() throws JSONException {
        jsonObject = new JSONObject();
        jsonObject.put("stb",stb);
        jsonObject.put("nama",nama);
        jsonObject.put("jenis_kelamin",jk);
        return  jsonObject;

    }

    public Mahasiswa jsontoMahasiswa(JSONObject jsonObject) throws JSONException {
        mhs = new Mahasiswa(jsonObject.getString("stb"),
                            jsonObject.getString("nama"),
                            jsonObject.getString("jenis_kelamin"));
        return  mhs;
    }
}
