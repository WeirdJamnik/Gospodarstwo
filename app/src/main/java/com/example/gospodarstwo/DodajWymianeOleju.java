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

public class DodajWymianeOleju extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    String ConnectionResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_wymiane_oleju);

        TextView ilosc_olej_dod = (TextView) findViewById(R.id.ilosc_olej_dod);
        TextView data_olej_dod = (TextView) findViewById(R.id.data_olej_dod);
        TextView nazwa_olej_dod = (TextView) findViewById(R.id.nazwa_olej_dod);
        TextView id_olej_maszyny = (TextView) findViewById(R.id.id_olej_maszyny);

        data_olej_dod.setFocusable(false);
        data_olej_dod.clearFocus();

        Button add_olej = (Button) findViewById(R.id.add_olej);

        add_olej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ilosc = ilosc_olej_dod.getText().toString();
                String data = data_olej_dod.getText().toString();
                String nazwa = nazwa_olej_dod.getText().toString();
                String id_m = id_olej_maszyny.getText().toString();

                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection conn = databaseConnection.connectionclass();
                try {
                    if (conn != null) {
                        if (ilosc.equals("") || data.equals("") || nazwa.equals("") || id_m.equals("")){
                            Toast.makeText(getApplicationContext(), "Żadne pola nie mogą być puste!", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(DodajWymianeOleju.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            String query = "exec Nowa_wym_olej " + ilosc + ",'" + data + "','" + nazwa + "'," + id_m;
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

        data_olej_dod.setOnClickListener(new View.OnClickListener() {
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
        TextView data_olej_dod = (TextView) findViewById(R.id.data_olej_dod);
        data_olej_dod.setText(strDate);

    }
}