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

public class UpdateZamowienie extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


    String ConnectionResult="";
    TextView produkt_akt, ilosc_akt, data_akt, data_obioru_akt, cena_akt, id_kierowcy_akt, id_maszyny_akt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_zamowienie);

        Button aktualizuj = (Button)findViewById(R.id.akt_zam);

        produkt_akt = (TextView) findViewById(R.id.produkt_akt);
        ilosc_akt = (TextView) findViewById(R.id.ilosc_akt);
        data_akt = (TextView) findViewById(R.id.data_akt);
        data_obioru_akt = (TextView) findViewById(R.id.data_obioru_akt);
        cena_akt = (TextView) findViewById(R.id.cena_akt);
        id_kierowcy_akt = (TextView) findViewById(R.id.id_kierowcy_akt);
        id_maszyny_akt = (TextView) findViewById(R.id.id_maszyny_akt);
        data_obioru_akt.setFocusable(false);
        data_obioru_akt.clearFocus();


        data_obioru_akt.setOnClickListener(new View.OnClickListener() {
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
                String produkt = produkt_akt.getText().toString();
                String ilosc = ilosc_akt.getText().toString();
                String data_zam = data_akt.getText().toString();
                String data_odb = data_obioru_akt.getText().toString();
                String cena = cena_akt.getText().toString();
                String id_kier = id_kierowcy_akt.getText().toString();
                String id_masz = id_maszyny_akt.getText().toString();

                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection conn = databaseConnection.connectionclass();
                try {
                    if (conn!=null){
                        if(produkt.equals("") || ilosc.equals("") || data_zam.equals("") || data_odb.equals("") || cena.equals("") || id_kier.equals("") || id_masz.equals("") )
                        {
                            Toast.makeText(getApplicationContext(), "Pola nie mogą być puste!", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Intent intent = new Intent(UpdateZamowienie.this, ZamowActivity.class);
                            startActivity(intent);
                            finish();
                            String query = "exec Update_Zamawianie " + id
                                    + ",'" + produkt
                                    + "'," + ilosc
                                    + ",'" + data_zam
                                    + "','" + data_odb
                                    + "'," + cena
                                    + "," + id_kier
                                    + "," + id_masz;
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

        getZamowienieInfo();
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayofMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayofMonth);
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
        String strDate = format.format(c.getTime());
        TextView data_obioru_akt = (TextView) findViewById(R.id.data_obioru_akt);
        data_obioru_akt.setText(strDate);
    }

    private void getZamowienieInfo () {
        String id = getIntent().getExtras().getString("id");
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection conn = databaseConnection.connectionclass();
            if (conn!=null){
                String q = "SELECT * FROM Zamawianie WHERE id="+id;
                Statement stmt1 = conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery(q);
                while (rs1.next()) {
                    produkt_akt.setText(rs1.getString("nazwa_prod"));
                    ilosc_akt.setText(rs1.getString("ilosc"));
                    data_akt.setText(rs1.getString("data_zamowienia"));
                    data_obioru_akt.setText(rs1.getString("data_odbioru"));
                    cena_akt.setText(rs1.getString("cena"));
                    id_kierowcy_akt.setText(rs1.getString("Kierowca_id"));
                    id_maszyny_akt.setText(rs1.getString("Maszyna_id"));
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
}