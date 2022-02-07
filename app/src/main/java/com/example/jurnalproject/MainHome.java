package com.example.jurnalproject;

//import android.support.v7.app.AppCompatActivity;
import  androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainHome extends AppCompatActivity {
    private Button btCreateDB;
    private Button btViewDB;
    Button btnExit;
    Button btnLogOut;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLogOut = findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainHome.this, MainActivity.class));
        });

        btCreateDB = (Button) findViewById(R.id.bt_createdata);
        btViewDB = (Button) findViewById(R.id.bt_viewdata);

        btCreateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kelas yang akan dijalankan ketika tombol Create/Insert Data diklik
                startActivity(FirebaseDBCreateActivity.getActIntent(MainHome.this));
            }
        });

        btViewDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kode untuk tutorial selanjutnya
                startActivity(FirebaseDBReadActivity.getActIntent(MainHome.this));
            }
        });



//        btnExit = (Button) findViewById(R.id.btnExit);
//
//        btnExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //perintah untuk mengakhiri aplikasi
//                finish();
//            }
//        });

    }
}