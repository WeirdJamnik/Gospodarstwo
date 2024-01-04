package com.example.gospodarstwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CardView Wymiany = findViewById(R.id.Wymiany);

        CardView Zbiorniki = findViewById(R.id.Zbiorniki);
        CardView Zamow = findViewById(R.id.Zamów);
        CardView  Kierowcy = findViewById(R.id.Kierowcy);

        CardView Maszyny = findViewById(R.id.Maszyny);
        Maszyny.setOnClickListener((View v) ->{
                Intent intent = new Intent(MainActivity.this, MaszynyActivity.class);
                startActivity(intent);
        });

        Wymiany.setOnClickListener((View v) ->{
                Intent intent = new Intent(MainActivity.this, WymianyActivity.class);
                startActivity(intent);
        });

        CardView Zatankuj = findViewById(R.id.Zatankuj);
        Zatankuj.setOnClickListener((View v) ->{
                Intent intent = new Intent(MainActivity.this, ZatankujActivity.class);
                startActivity(intent);
        });

        Zbiorniki.setOnClickListener((View v) ->{
                Intent intent = new Intent(MainActivity.this, ZbiornikiActivity.class);
                startActivity(intent);
        });
        Zamow.setOnClickListener((View v) -> {
                Intent intent = new Intent(MainActivity.this, ZamowActivity.class);
                startActivity(intent);
        });
        Kierowcy.setOnClickListener((View v) ->{
                Intent intent = new Intent(MainActivity.this, KierowcyActivity.class);
                startActivity(intent);
        });


    }
}