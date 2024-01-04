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

public class DodajWymianeCzesci extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String ConnectionResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_wymiane_czesci);

        TextView nazwa_cze_dod = (TextView) findViewById(R.id.nazwa_cze_dod);
        TextView data_cze_dod = (TextView) findViewById(R.id.data_cze_dod);
        TextView uwagi_cze_dod = (TextView) findViewById(R.id.uwagi_cze_dod);
        TextView id_cze_maszyny = (TextView) findViewById(R.id.id_cze_maszyny);

        data_cze_dod.setFocusable(false);
        data_cze_dod.clearFocus();

        Button add_cze = (Button) findViewById(R.id.add_cze);

        add_cze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nazwa = nazwa_cze_dod.getText().toString();
                String data = data_cze_dod.getText().toString();
                String uwagi = uwagi_cze_dod.getText().toString();
                String id_m = id_cze_maszyny.getText().toString();

                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection conn = databaseConnection.connectionclass();
                try {
                    if (conn != null) {
                        if (nazwa.equals("") || data.equals("") || uwagi.equals("") || id_m.equals("")){
                            Toast.makeText(getApplicationContext(), "Żadne pola nie mogą być puste!", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(DodajWymianeCzesci.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            String query = "exec Nowa_wym_cze '" + nazwa + "','" + data + "','" + uwagi + "'," + id_m;
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

        data_cze_dod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new com.example.gospodarstwo.DatePicker();
                datepicker.show(getSupportFragmentManager(), "date picker");
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
        TextView data_cze_dod = (TextView) findViewById(R.id.data_cze_dod);
        data_cze_dod.setText(strDate);

    }
}