package com.example.gospodarstwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DodajTankowanie extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    String ConnectionResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_tankowanie);

        TextView paliwo_tank = (TextView) findViewById(R.id.paliwo_tank);
        TextView data_tank = (TextView) findViewById(R.id.data_tank);
        TextView id_maszyny_tank = (TextView) findViewById(R.id.id_maszyny_tank);
        TextView id_zbiornik_tank = (TextView) findViewById(R.id.id_zbiornik_tank);
        TextView id_kierowcy_tank = (TextView) findViewById(R.id.id_kierowcy_tank);
        TextView nowy_przebieg_tank = (TextView) findViewById(R.id.nowy_przebieg_tank);

        data_tank.setFocusable(false);
        data_tank.clearFocus();

        data_tank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new com.example.gospodarstwo.DatePicker();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button add = (Button) findViewById(R.id.add_tank);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paliwo = paliwo_tank.getText().toString();
                String data = data_tank.getText().toString();
                String id_m = id_maszyny_tank.getText().toString();
                String id_z = id_zbiornik_tank.getText().toString();
                String id_k = id_kierowcy_tank.getText().toString();
                String n_przebieg = nowy_przebieg_tank.getText().toString();

                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection conn = databaseConnection.connectionclass();
                try {
                    if (conn != null) {
                        if (paliwo.equals("") || data.equals("") || id_m.equals("") || id_z.equals("") || id_k.equals("") || n_przebieg.equals("")){
                            Toast.makeText(getApplicationContext(), "Pola nie mogą być puste!", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(DodajTankowanie.this, ZatankujActivity.class);
                            startActivity(intent);
                            finish();
                            String query = "exec Nowe_Tankowanie " + paliwo + ",'" + data + "'," + id_m + "," + id_z + "," + id_k + "," + n_przebieg;
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

    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayofMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayofMonth);
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
        String strDate = format.format(c.getTime());
        TextView data_tank = (TextView) findViewById(R.id.data_tank);
        data_tank.setText(strDate);

    }
}