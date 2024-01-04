package com.example.gospodarstwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DostawyPaliwaActivity extends AppCompatActivity {

    private ArrayList<GetDostawy> itemsArrayList;
    private Adapter adapter;
    private ListView listView;
    private boolean success = false;
    private DatabaseConnection databaseConnection;
    private String msg = "Internet Connection Error";
    private String id_zbiornik;
    String ConnectionResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dostawy_paliwa);

        listView = findViewById(R.id.dostawy_lista);
        itemsArrayList = new ArrayList<GetDostawy>();

        id_zbiornik = getIntent().getExtras().getString("id_zbiornik");

        databaseConnection = new DatabaseConnection();
        itemsArrayList = new ArrayList<>();

        DostawyPaliwaActivity.SyncData orderdata = new DostawyPaliwaActivity.SyncData();
        orderdata.execute("");

        Button nowa_dostawa = (Button) findViewById(R.id.nowa_dostawa);

        nowa_dostawa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection conn = databaseConnection.connectionclass();
                try {
                    if (conn != null) {
                        Intent intent = new Intent(DostawyPaliwaActivity.this, DodajDostawe.class);
                        String id_zbiornik_dodaj = id_zbiornik;
                        intent.putExtra("id_zbiornik_dodaj", id_zbiornik_dodaj);
                        startActivity(intent);
                        finish();
                    } else {
                        ConnectionResult = "Check conn";
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private class SyncData extends AsyncTask<String, String, String> {
        protected String doInBackground(String... strings) {
            try {
                Connection con = databaseConnection.connectionclass();
                if (con == null) {
                    success = false;
                } else {
                    String query = "select * from Dostawy_paliwa where Zbiornik_id= "+ id_zbiornik+" order by data_dostawy desc";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                itemsArrayList.add(new GetDostawy(rs.getString("data_dostawy"),
                                        rs.getString("ilosc_zam_pal"),
                                        rs.getString("Zbiornik_id")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data Found!";
                        success = false;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }
        @Override
        protected void onPostExecute(String msg)
        {
            if(success)
            {
                adapter= new SyncData.Adapter(itemsArrayList, DostawyPaliwaActivity.this);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setAdapter((ListAdapter) adapter);
            }
        }

        public class Adapter extends BaseAdapter
        {

            public class ViewHolder{
                TextView data, ilosc_paliwa;
            }

            public Context context;
            public ArrayList<GetDostawy> arrayList;

            private Adapter(ArrayList<GetDostawy> Gospodarstwo, Context context){
                this.arrayList = Gospodarstwo;
                this.context = context;

            }

            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                View rowView = convertView;
                ViewHolder viewHolder = null;
                if(rowView == null)
                {
                    LayoutInflater inflater = getLayoutInflater();
                    rowView = inflater.inflate(R.layout.lista_dostawy, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.data = rowView.findViewById(R.id.data_dostawa);
                    viewHolder.ilosc_paliwa = rowView.findViewById(R.id.paliwo_dostawa);
                    rowView.setTag(viewHolder);

                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.data.setText(arrayList.get(position).getData());
                viewHolder.ilosc_paliwa.setText(arrayList.get(position).getIlosc_paliwa());

                return rowView;
            }
        }
}
}