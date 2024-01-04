package com.example.gospodarstwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WymianyActivity extends AppCompatActivity {

    CardView czesci_wym, olej_wym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wymiany);

        czesci_wym = findViewById(R.id.czesci_wym);
        olej_wym = findViewById(R.id.olej_wym);

        czesci_wym.setOnClickListener((View v) ->{
            Intent intent = new Intent(WymianyActivity.this, CzesciActivity.class);
            startActivity(intent);
        });


        olej_wym.setOnClickListener((View v) ->{
            Intent intent = new Intent(WymianyActivity.this, OlejeActivity.class);
            startActivity(intent);
        });
    }
}