package com.example.gospodarstwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DodajDostawe extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String ConnectionResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_dostawe);

        TextView paliwo_add = (TextView) findViewById(R.id.paliwo_add);
        TextView data_add = (TextView) findViewById(R.id.data_add);
        data_add.setFocusable(false);
        data_add.clearFocus();

        Button add = (Button) findViewById(R.id.add_dostawa);

        String id_zbiornik_dodaj = getIntent().getExtras().getString("id_zbiornik_dodaj");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paliwo = paliwo_add.getText().toString();
                String data = data_add.getText().toString();

                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection conn = databaseConnection.connectionclass();
                try {
                    if (conn != null) {
                        if (paliwo.equals("") || data.equals("")){
                            Toast.makeText(getApplicationContext(), "Pola nie mogą być puste!", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(DodajDostawe.this, ZbiornikiActivity.class);
                        startActivity(intent);
                        finish();
                        String query = "exec Nowa_Dostawa " + paliwo
                                + ",'" + data + "'," + id_zbiornik_dodaj;
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(query);
                    }
                }
                    else
                    {
                        ConnectionResult="Check conn";
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        data_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new com.example.gospodarstwo.DatePicker();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayofMonth);
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
        String strDate = format.format(c.getTime());
        TextView data_add = (TextView) findViewById(R.id.data_add);
        data_add.setText(strDate);
    }
}