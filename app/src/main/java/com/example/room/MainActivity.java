package com.example.room;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    EditText editTextNazwa;
    EditText editTextModel;
    EditText editTextSrednica;
    EditText editTextMoc;

    Button buttonDodaj;

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

        editTextNazwa = findViewById(R.id.editTextNazwa);
        editTextModel = findViewById(R.id.editTextModel);
        editTextSrednica = findViewById(R.id.editTextSrednica);
        editTextMoc = findViewById(R.id.editTextMoc);
        buttonDodaj = findViewById(R.id.buttonDodaj);

        buttonDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nazwa = editTextNazwa.getText().toString();
                String model = editTextModel.getText().toString();
                int srednica = Integer.parseInt(editTextSrednica.getText().toString());
                int moc = Integer.parseInt(editTextMoc.getText().toString());
                maszynyDB.zwrocTokarkaDAO().wstawTokarke(new Tokarka(nazwa, model, srednica, moc));
                tokarkiList.add(new Tokarka(nazwa, model, srednica, moc));
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}