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

public class KierowcyActivity extends AppCompatActivity {

    private ArrayList<GetKierowca> itemsArrayList;
    private Adapter adapter;
    private ListView listView;
    private boolean success = false;
    private DatabaseConnection databaseConnection;
    private String msg = "Internet Connection Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kierowcy);

        listView = findViewById(R.id.kierowcy_lista);
        itemsArrayList = new ArrayList<GetKierowca>();

        databaseConnection = new DatabaseConnection();
        itemsArrayList = new ArrayList<>();

        SyncData orderdata = new SyncData();
        orderdata.execute("");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(KierowcyActivity.this, KartaPracownikaActivity.class);
                String id1 = itemsArrayList.get(position).getId();
                intent.putExtra("id1", id1);
                startActivity(intent);
                finish();
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
                    String query = "select * from Kierowca";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                itemsArrayList.add(new GetKierowca(rs.getString("id"),
                                        rs.getString("imie"),
                                        rs.getString("nazwisko"),
                                        rs.getString("pesel")));
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
                adapter= new Adapter(itemsArrayList, KierowcyActivity.this);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setAdapter((ListAdapter) adapter);


            }
        }

        public class Adapter extends BaseAdapter
        {

            public class ViewHolder{
                TextView imie, nazwisko, pesel;
            }


            public Context context;
            public ArrayList<GetKierowca> arrayList;

            private Adapter(ArrayList<GetKierowca> Gospodarstwo, Context context){
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
                    rowView = inflater.inflate(R.layout.lista_kierowcy, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.pesel = rowView.findViewById(R.id.pesel);
                    viewHolder.imie = rowView.findViewById(R.id.imie);
                    viewHolder.nazwisko = rowView.findViewById(R.id.nazwisko);
                    rowView.setTag(viewHolder);

                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                viewHolder.imie.setText(arrayList.get(position).getImie());
                viewHolder.nazwisko.setText(arrayList.get(position).getNazwisko());
                viewHolder.pesel.setText(arrayList.get(position).getPesel());
                rowView.setTag(arrayList.get(position).getPesel());

                return rowView;
            }
        }


    }
}
