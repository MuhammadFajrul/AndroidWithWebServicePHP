package com.example.androidwithwebservicephp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.androidwithwebservicephp.controller.RestHelper;
import com.example.androidwithwebservicephp.interfacea.RestCallbckMhs;
import com.example.androidwithwebservicephp.model.Mahasiswa;

import java.util.ArrayList;

public class TampilDataActivity extends AppCompatActivity {
    private TableLayout tb;
    private TableRow tr;
    private Button btn_update,btn_hapus;
    private TextView col1,col2,col3;
    private RestHelper  restHelper;
    private String nama,stb,jk;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);
        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("Lihat Daftar");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        restHelper = new RestHelper(this);
        btn_update = findViewById(R.id.btn_edit);
        btn_hapus = findViewById(R.id.btn_hapus);
        tb = findViewById(R.id.tb_mhs);
        tampildata();

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restHelper.hapusData(stb, new RestCallbckMhs() {
                    @Override
                    public void RequestDataMahasiswasukses(ArrayList<Mahasiswa> arrayList) {
                        TampiltbMhs(arrayList);
                    }
                });

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_editclick();
            }
        });
    }
    private void tampildata(){
        restHelper.getDataMhs(new RestCallbckMhs() {
            @Override
            public void RequestDataMahasiswasukses(ArrayList<Mahasiswa> arrayList) {
                TampiltbMhs(arrayList);
            }
        });
    }

    private  void  TampiltbMhs(ArrayList<Mahasiswa> arrayList){
        tb.removeAllViews();

        tr = new TableRow(this);
        col1 = new TextView(this);
        col2 = new TextView(this);
        col3 = new TextView(this);

        col1.setText("Stambuk");
        col2.setText("Nama");
        col3.setText("Jenis Kelamin");

        col1.setWidth(300);
        col2.setWidth(400);
        col3.setWidth(300);
        tr.addView(col1);
        tr.addView(col2);
        tr.addView(col3);

        tb.addView(tr);

        for (final Mahasiswa mhs: arrayList){
            tr = new TableRow(this);
            col1 = new TextView(this);
            col2 = new TextView(this);
            col3 = new TextView(this);

            col1.setText(mhs.getStb());
            col2.setText(mhs.getNama());
            col3.setText(mhs.getJk());

            tr.addView(col1);
            tr.addView(col2);
            tr.addView(col3);

            tb.addView(tr);

            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i =0;i<tb.getChildCount();i++){
                        stb = mhs.getStb();
                        nama = mhs.getNama();
                        jk = mhs.getJk();
                        if (tb.getChildAt(i)==v){
                            tb.getChildAt(i).setBackgroundColor(Color.LTGRAY);
                        }else{
                            tb.getChildAt(i).setBackgroundColor(Color.WHITE);
                        }

                    }
                }
            });
        }
    }
    public void btn_editclick(){
        Intent intent = new Intent();
        intent.putExtra("stb",stb);
        intent.putExtra("nama",nama);
        intent.putExtra("jk",jk);
        setResult(1,intent);
        finish();
    }
}