package com.example.androidwithwebservicephp.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidwithwebservicephp.interfacea.RestCallbckMhs;
import com.example.androidwithwebservicephp.model.Mahasiswa;

import static com.example.androidwithwebservicephp.model.Staticmembers.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestHelper {
    private Context context;
    private RequestQueue requestQueue;
    private final int REQ_METHOD = Request.Method.POST;
    private final String URL = "http://192.168.43.168/PraktikumAndoidPhp/";
    private ArrayList<Mahasiswa> arrayListmhs = new ArrayList<>();


    public RestHelper (Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void insertData(JSONObject jsonObject){
        JsonObjectRequest jsonObjectRequest;
        Response.Listener<JSONObject>jsonObjectListener;
        Response.ErrorListener errorListener;

        jsonObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("hasil")==1){
                        tampilPesan(context,"DATA TERSIMPAN....");
                    }else{
                        tampilPesan(context,"DATA GAGAL TERSIMPAN....");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tampilPesan(context,error.toString()+"din 50");
            }
        };

        jsonObjectRequest = new JsonObjectRequest(
                REQ_METHOD,
                URL+"insertData.php",
                jsonObject,
                jsonObjectListener,
                errorListener);

        requestQueue.add(jsonObjectRequest);
    }

    public void getDataMhs(final RestCallbckMhs restCallbckMhs){
        arrayListmhs.clear();

        JsonArrayRequest jsonArrayRequest;
        Response.Listener<JSONArray> jsonArrayListener;
        Response.ErrorListener errorListener;

        jsonArrayListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0;i<response.length();i++){
                    try {
                        arrayListmhs.add(new Mahasiswa(response.getJSONObject(i).getString("stb"),
                                                    response.getJSONObject(i).getString("nama"),
                                                    response.getJSONObject(i).getString("jenis_kelamin"))
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    restCallbckMhs.RequestDataMahasiswasukses(arrayListmhs);
                }
            }
        };

        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tampilPesan(context,error.toString());
            }
        };
        jsonArrayRequest = new JsonArrayRequest(
                REQ_METHOD,URL+"TampilData.php",null,jsonArrayListener,errorListener
        );
        requestQueue.add(jsonArrayRequest);
    }

    public void editData(String stb, Mahasiswa mhs){
        JsonObjectRequest jsonObjectRequest;
        JSONObject jsonObject;
        Response.Listener<JSONObject> jsonObjectListener;
        Response.ErrorListener errorListener;

        jsonObject = new JSONObject();
        try {
            jsonObject.put("stblama",stb);
            jsonObject.put("stb",mhs.getStb());
            jsonObject.put("nama",mhs.getNama());
            jsonObject.put("jenis_kelamin",mhs.getJk());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("hasil")==1){
                        tampilPesan(context,"DATA TERSIMPAN....");
                    }else{
                        tampilPesan(context,"DATA GAGAL TERSIMPAN....");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tampilPesan(context,error.toString());
            }
        };

        jsonObjectRequest = new JsonObjectRequest(
                REQ_METHOD,
                URL+"editData.php",
                jsonObject,
                jsonObjectListener,
                errorListener
        );
        requestQueue.add(jsonObjectRequest);
    }
    public void hapusData(String stb, final RestCallbckMhs restCallbckMhs){
        JsonObjectRequest jsonObjectRequest;
        JSONObject jsonObject;
        Response.Listener<JSONObject> jsonObjectListener;
        Response.ErrorListener errorListener;

        jsonObject = new JSONObject();
        try {
            jsonObject.put("stb",stb);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("hasil")==1){
                        tampilPesan(context,"BERHASIL MENGAPUS...");
                    }else{
                        tampilPesan(context,"DATA GAGAL TERHAPUS....");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getDataMhs(restCallbckMhs);
            }
        };

        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tampilPesan(context,error.toString());
            }
        };

        jsonObjectRequest = new JsonObjectRequest(
                REQ_METHOD,
                URL+"hapusData.php",
                jsonObject,
                jsonObjectListener,
                errorListener
        );
        requestQueue.add(jsonObjectRequest);
    }
}
