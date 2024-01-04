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


public class DodajZamowienie extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    String ConnectionResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodaj_zamowienie);

        TextView produkt_dod = (TextView) findViewById(R.id.produkt_dod);
        TextView ilosc_dod = (TextView) findViewById(R.id.ilosc_dod);
        TextView data_dod = (TextView) findViewById(R.id.data_dod);
        TextView id_kierowcy_dod = (TextView) findViewById(R.id.id_kierowcy_dod);
        TextView id_maszyny_dod = (TextView) findViewById(R.id.id_maszyny_dod);


        data_dod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new com.example.gospodarstwo.DatePicker();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        data_dod.setFocusable(false);
        data_dod.clearFocus();

        Button add_zam = (Button) findViewById(R.id.add_zam);

        add_zam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String produkt = produkt_dod.getText().toString();
                String ilosc = ilosc_dod.getText().toString();
                String data = data_dod.getText().toString();
                String id_kierowcy = id_kierowcy_dod.getText().toString();
                String id_maszyny = id_maszyny_dod.getText().toString();

                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection conn = databaseConnection.connectionclass();
                try {
                    if (conn != null) {
                        if (produkt.equals("") || ilosc.equals("") || data.equals("")|| id_kierowcy.equals("")|| id_maszyny.equals("")){
                            Toast.makeText(getApplicationContext(), "Pola nie mogą być puste!", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(DodajZamowienie.this, ZamowActivity.class);
                            startActivity(intent);
                            finish();
                            String query = "exec Nowe_Zamowienie '" + produkt + "'," + ilosc + ",'" + data + "', NULL, 0," + id_kierowcy + "," + id_maszyny;
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
    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayofMonth);
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
        String strDate = format.format(c.getTime());
        TextView data_dod = (TextView) findViewById(R.id.data_dod);
        data_dod.setText(strDate);
    }

}