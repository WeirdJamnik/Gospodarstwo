package com.example.gospodarstwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

public class ZbiornikiActivity extends AppCompatActivity {

    private ArrayList<GetZbiornik> itemsArrayList;
    private Adapter adapter;
    private ListView listView;
    private boolean success = false;
    private DatabaseConnection databaseConnection;
    private String msg = "Internet Connection Error";
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbiorniki);

        listView = findViewById(R.id.zbiorniki_lista);
        itemsArrayList = new ArrayList<GetZbiornik>();

        databaseConnection = new DatabaseConnection();
        itemsArrayList = new ArrayList<>();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ZbiornikiActivity.this, DostawyPaliwaActivity.class);
                String id_zbiornik = itemsArrayList.get(position).getNumer_zbiornika();
                intent.putExtra("id_zbiornik", id_zbiornik);
                startActivity(intent);
                finish();
            }
        });

        SyncData orderdata = new SyncData();
        orderdata.execute("");
    }

        private class SyncData extends AsyncTask<String, String, String> {
            protected String doInBackground(String... strings) {
                try {
                    Connection con = databaseConnection.connectionclass();
                    if (con == null) {
                        success = false;
                    } else {
                        String query = "select * from Zbiornik";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs != null) {
                            while (rs.next()) {
                                try {
                                    itemsArrayList.add(new GetZbiornik(rs.getString("Numer_zbiornika"),
                                            rs.getString("ilosc_paliwa_w_zbiorniku"),
                                            rs.getString("pojemnosc_zbiornika")));
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
                    adapter= new SyncData.Adapter(itemsArrayList, ZbiornikiActivity.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter((ListAdapter) adapter);
                }
            }

            public class Adapter extends BaseAdapter
            {
                public class ViewHolder{
                    TextView numer_zbiornika, ilosc_paliwa, poj_zbior_paliwa;
                }

                public Context context;
                public ArrayList<GetZbiornik> arrayList;

                private Adapter(ArrayList<GetZbiornik> Gospodarstwo, Context context){
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
                    SyncData.Adapter.ViewHolder viewHolder = null;
                    if(rowView == null)
                    {
                        LayoutInflater inflater = getLayoutInflater();
                        rowView = inflater.inflate(R.layout.lista_zbiorniki, parent, false);
                        viewHolder = new SyncData.Adapter.ViewHolder();
                        viewHolder.numer_zbiornika = rowView.findViewById(R.id.numer_zbiornika);
                        viewHolder.ilosc_paliwa = rowView.findViewById(R.id.ilosc_paliwa);
                        viewHolder.poj_zbior_paliwa = rowView.findViewById(R.id.poj_zbior_paliwa);
                        rowView.setTag(viewHolder);
                    }else {
                        viewHolder = (SyncData.Adapter.ViewHolder) convertView.getTag();
                    }

                    viewHolder.numer_zbiornika.setText(arrayList.get(position).getNumer_zbiornika());
                    viewHolder.ilosc_paliwa.setText(arrayList.get(position).getIlosc_paliwa_w_zbiorniku());
                    viewHolder.poj_zbior_paliwa.setText(arrayList.get(position).getPojemnosc_zbiornika());

                    return rowView;
                }
            }


    }
}
