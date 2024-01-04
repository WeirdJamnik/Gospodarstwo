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

public class UpdatePrzeglad extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView data_przegladu;
    String ConnectionResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_przeglad);

        Button aktualizuj = (Button) findViewById(R.id.update_data_przeg);
        data_przegladu = (TextView) findViewById(R.id.data_przegladu);
        data_przegladu.setFocusable(false);
        data_przegladu.clearFocus();

        data_przegladu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new com.example.gospodarstwo.DatePicker();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        String id = getIntent().getExtras().getString("id");


        aktualizuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String przeglad = data_przegladu.getText().toString();
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection conn = databaseConnection.connectionclass();
                try {
                    if (conn != null) {
                        if (przeglad.equals("")) {
                            Toast.makeText(getApplicationContext(), "Pole nie może być puste!", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(UpdatePrzeglad.this, MaszynyActivity.class);
                            startActivity(intent);
                            finish();
                            String query = "Update Maszyna set data_wyg_przegl = '"+ przeglad + "' where id =" + id;
                            Statement st = conn.createStatement();
                            ResultSet rs = st.executeQuery(query);
                        }
                    } else {
                        ConnectionResult = "Check conn";
                    }
                } catch (Exception ex) {
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
        TextView data_przegladu = (TextView) findViewById(R.id.data_przegladu);
        data_przegladu.setText(strDate);
    }
}