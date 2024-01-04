package com.example.gospodarstwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



public class KartaPracownikaActivity extends AppCompatActivity {

    String ConnectionResult="";
    String id2;

    private String msg = "Internet Connection Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karta_pracownika);
        id2 = getIntent().getExtras().getString("id1");
        Button update = (Button)findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection conn = databaseConnection.connectionclass();
                try {
                    if (conn!=null){
                        Intent intent = new Intent(KartaPracownikaActivity.this, UpdateKierowca.class);
                        String id3 = id2;
                        intent.putExtra("id3", id3);
                        startActivity(intent);
                        finish();
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

        SyncDataKarta();
    }
    public void SyncDataKarta () {
        TextView imie_kier = (TextView) findViewById(R.id.imie_kier);
        TextView nazw_kier = (TextView) findViewById(R.id.nazw_kier);
        TextView pesel_kier= (TextView) findViewById(R.id.pesel_kier);
        TextView nr_telefonu = (TextView) findViewById(R.id.nr_telefonu);
        TextView id = (TextView) findViewById(R.id.id);

        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection conn = databaseConnection.connectionclass();
            if (conn!=null){
                String query = "Select * From Kierowca where id =" + id2;
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next())
                {
                    id.setText(rs.getString(1));
                    imie_kier.setText(rs.getString(2));
                    nazw_kier.setText(rs.getString(3));
                    pesel_kier.setText(rs.getString(4));
                    nr_telefonu.setText(rs.getString(5));
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