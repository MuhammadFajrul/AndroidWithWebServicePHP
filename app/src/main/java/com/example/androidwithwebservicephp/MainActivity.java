package com.example.androidwithwebservicephp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidwithwebservicephp.controller.RestHelper;
import com.example.androidwithwebservicephp.model.Mahasiswa;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private EditText nama,stb,jk;
    private Button simpan,tampil;
    private RestHelper restHelper;
    private Mahasiswa mhs;
    private Intent intentdata;
    private Toolbar toolbarmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarmain = findViewById(R.id.toolbar1);
        toolbarmain.setTitle("TugasWebService");
        setSupportActionBar(toolbarmain);
        intentdata=null;
        restHelper = new RestHelper(this);

        stb = findViewById(R.id.editTextstambuk);
        nama = findViewById(R.id.editTextNama);
        jk = findViewById(R.id.editTextTextPersonName);

        simpan = findViewById( R.id.btn_simpan);
        tampil = findViewById( R.id.btn_tampil);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mhs = new Mahasiswa(stb.getText().toString(),nama.getText().toString(),
                        jk.getText().toString());
                try {
                    if (intentdata==null)
                    restHelper.insertData(mhs.tojson());
                    else
                        restHelper.editData(intentdata.getStringExtra("stb"), new Mahasiswa(
                                stb.getText().toString(),nama.getText().toString(),
                                jk.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentdata = null;
                Intent intent = new Intent(MainActivity.this,TampilDataActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1){
            intentdata=data;
            stb.setText(data.getStringExtra("stb"));
            nama.setText(data.getStringExtra("nama"));
            jk.setText(data.getStringExtra("jk"));
        }
    }
}