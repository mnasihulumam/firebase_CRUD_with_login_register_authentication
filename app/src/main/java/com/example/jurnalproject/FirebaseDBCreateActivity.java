package com.example.jurnalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDBCreateActivity extends AppCompatActivity {

    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;

    // variable fields EditText dan Button
    private Button btSubmit;
    private EditText etJudul;
    private EditText etTahun;
    private EditText etPengarang;
    private EditText etLink;

    private TextView etToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_create);

        // inisialisasi fields EditText dan Button
        etJudul = (EditText) findViewById(R.id.et_judul);
        etTahun = (EditText) findViewById(R.id.et_tahun);
        etPengarang = (EditText) findViewById(R.id.et_pengarang);
        etLink = (EditText) findViewById(R.id.et_link);
        etToolbar = (TextView) findViewById(R.id.et_toolbar);
        btSubmit = (Button) findViewById(R.id.bt_submit);

        // mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        // Update

        final Jurnal jurnal = (Jurnal) getIntent().getSerializableExtra("data");
        if (jurnal != null) {
            etJudul.setText(jurnal.getJudul());
            etTahun.setText(jurnal.getTahun());
            etPengarang.setText(jurnal.getPengarang());
            etLink.setText(jurnal.getLink());
            etToolbar.setText("Edit Data");
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jurnal.setJudul(etJudul.getText().toString());
                    jurnal.setTahun(etTahun.getText().toString());
                    jurnal.setPengarang(etPengarang.getText().toString());
                    jurnal.setLink(etLink.getText().toString());

                    updateJurnal(jurnal);
                }
            });
        }
        else {
            // kode yang dipanggil ketika tombol Submit diklik
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isEmpty(etJudul.getText().toString()) && !isEmpty(etTahun.getText().toString()) && !isEmpty(etPengarang.getText().toString()) && !isEmpty(etLink.getText().toString()))
                        submitJurnal(new Jurnal(etJudul.getText().toString(), etTahun.getText().toString(), etPengarang.getText().toString(), etLink.getText().toString()));
                    else
                        Snackbar.make(findViewById(R.id.bt_submit), "Data jurnal tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            etJudul.getWindowToken(), 0);
                }
            });
        }
    }

    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void updateJurnal(Jurnal jurnal) {
//         * Baris kode yang digunakan untuk mengupdate data barang
//         * yang sudah dimasukkan di Firebase Realtime Database

        database.child("jurnal") //akses parent index, ibaratnya seperti nama tabel
                .child(jurnal.getKey()) //select jurnal berdasarkan key
                .setValue(jurnal) //set value jurnal yang baru
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        /**
                         * Baris kode yang akan dipanggil apabila proses update jurnal sukses
                         */
                        Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil diupdatekan", Snackbar.LENGTH_LONG).setAction("Oke", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        }).show();
                    }
                });
    }

    private void submitJurnal(Jurnal jurnal) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        database.child("jurnal").push().setValue(jurnal).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etJudul.setText("");
                etTahun.setText("");
                etPengarang.setText("");
                etLink.setText("");
                Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }
    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, FirebaseDBCreateActivity.class);
    }
}
