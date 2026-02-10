package com.example.room;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MaszynyDataBase maszynyDB;
    ListView listView;
    ArrayAdapter<Tokarka> arrayAdapter;
    List<Tokarka> tokarkiList;

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
        //maszynyDB.zwrocTokarkaDAO().wstawTokarke(new Tokarka("Nova", "Nebula", 35, 1500));
        //maszynyDB.zwrocTokarkaDAO().wstawTokarke(new Tokarka("DRHSelmeister", "StratosXL", 55, 2000));

        listView = findViewById(R.id.listView);
        tokarkiList = maszynyDB.zwrocTokarkaDAO().zwrocWszystkieTokarki();
        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, tokarkiList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                maszynyDB.zwrocTokarkaDAO().usunTokarke(tokarkiList.get(i));
                tokarkiList.remove(i);
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}