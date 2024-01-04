package com.example.gospodarstwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateKierowca extends AppCompatActivity {

    String ConnectionResult="";
    TextView imie_up, nazwisko_up, nr_telefonu_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_kierowca);
        Button update = (Button)findViewById(R.id.update_z);

        imie_up = (TextView) findViewById(R.id.imie_up);
        nazwisko_up = (TextView) findViewById(R.id.nazwisko_up);
        nr_telefonu_up = (TextView) findViewById(R.id.nr_telefonu_up);

        String id3 = getIntent().getExtras().getString("id3");


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String imie = imie_up.getText().toString();
                String nazwisko = nazwisko_up.getText().toString();
                String telefon = nr_telefonu_up.getText().toString();

                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection conn = databaseConnection.connectionclass();
                try {
                    if (conn!=null){
                        if(imie.equals("") || nazwisko.equals("") || telefon.equals("") )
                        {
                            Toast.makeText(getApplicationContext(), "Pola nie mogą być puste!", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Intent intent = new Intent(UpdateKierowca.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            String query = "exec Update_Kierowcy '" + id3
                                    + "','" + imie
                                    + "','" + nazwisko
                                    + "','" + telefon + "'";
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

        getKierowcaInfo();

    }

    private void getKierowcaInfo () {
        String id3 = getIntent().getExtras().getString("id3");
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection conn = databaseConnection.connectionclass();
            if (conn!=null){
                String q = "SELECT * FROM Kierowca WHERE id="+id3;
                Statement stmt1 = conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery(q);
                while (rs1.next()) {
                    imie_up.setText(rs1.getString("imie"));
                    nazwisko_up.setText(rs1.getString("nazwisko"));
                    nr_telefonu_up.setText(rs1.getString("telefon"));
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
