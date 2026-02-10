package com.example.room;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    MaszynyDataBase maszynyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        maszynyDB = MaszynyDataBase.zwrocBazeDanych(MainActivity.this);
        maszynyDB.zwrocTokarkaDAO().wstawTokarke(new Tokarka("Nova", "Nebula", 35, 1500));
        maszynyDB.zwrocTokarkaDAO().wstawTokarke(new Tokarka("DRHSelmeister", "StratosXL", 55, 2000));


    }
}