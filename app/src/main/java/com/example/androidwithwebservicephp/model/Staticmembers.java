package com.example.androidwithwebservicephp.model;

import android.content.Context;
import android.widget.Toast;

public class Staticmembers {

    public static void tampilPesan(Context context,String teks){
        Toast.makeText(context,teks,Toast.LENGTH_LONG).show();
    }
}
