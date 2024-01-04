package com.example.gospodarstwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ZamowActivity extends AppCompatActivity {

    private ArrayList<GetZamowienie> itemsArrayList;
    private ZamowActivity.MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private boolean success = false;
    private DatabaseConnection databaseConnection;
    private String msg = "Internet Connection Error";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zamow);

        recyclerView = findViewById(R.id.zamowienia_recycle);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseConnection = new DatabaseConnection();
        itemsArrayList = new ArrayList<>();

        Button nowe_zam = (Button)findViewById(R.id.nowe_zam);

        nowe_zam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZamowActivity.this, DodajZamowienie.class);
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
                    String query = "select * from Zamowienia_view ORDER BY data_zamowienia DESC";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                itemsArrayList.add(new GetZamowienie(rs.getString("id"),
                                        rs.getString("imie"),
                                        rs.getString("nazwisko"),
                                        rs.getString("data_zamowienia"),
                                        rs.getString("marka"),
                                        rs.getString("model"),
                                        rs.getString("nazwa_prod"),
                                        rs.getString("ilosc"),
                                        rs.getString("cena"),
                                        rs.getString("data_odbioru"),
                                        false));

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
        protected void onPostExecute(String msg) {
            if (success) {
                myAdapter = new MyAdapter(itemsArrayList, ZamowActivity.this);
                recyclerView.setAdapter(myAdapter);

            }
        }

    }

    public class MyAdapter extends RecyclerView.Adapter<ZamowActivity.MyAdapter.ViewHolder>
    {
        private ArrayList<GetZamowienie> values;
        public Context context;

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView imie_zam, nazwisko_zam, data_zam, model_zam, marka_zam, nazw_prod_zam,ilosc_zam, cena_zam, data_odbioru_zam;
            CardView card_view2;
            LinearLayout lin_visible_zam;
            View layout;
            Button usun, aktualizuj;

            public ViewHolder(View v)
            {
                super(v);
                layout = v;
                imie_zam = v.findViewById(R.id.imie_zam);
                nazwisko_zam = v.findViewById(R.id.nazwisko_zam);
                data_zam = v.findViewById(R.id.data_zam);
                model_zam = v.findViewById(R.id.model_zam);
                marka_zam= v.findViewById(R.id.marka_zam);
                nazw_prod_zam = v.findViewById(R.id.nazw_prod_zam);
                ilosc_zam = v.findViewById(R.id.ilosc_zam);
                cena_zam = v.findViewById(R.id.cena_zam);
                data_odbioru_zam = v.findViewById(R.id.data_odbioru_zam);
                card_view2 = v.findViewById(R.id.card_view2);
                lin_visible_zam = v.findViewById(R.id.lin_visible_zam);
                usun = v.findViewById(R.id.usun);
                aktualizuj = v.findViewById(R.id.aktualizuj);

                usun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetZamowienie getZamowienie = itemsArrayList.get(getAbsoluteAdapterPosition());
                        DatabaseConnection databaseConnection = new DatabaseConnection();
                        Connection conn = databaseConnection.connectionclass();
                        try {
                            if (conn != null) {
                                String query = "delete from Zamawianie where id= " + getZamowienie.getId();
                                Statement st = conn.createStatement();
                                ResultSet rs = st.executeQuery(query);
                            }
                            else
                            {
                                msg = "No connection";
                            }
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                        Intent intent = new Intent(ZamowActivity.this, ZamowActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                aktualizuj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetZamowienie getZamowienie = itemsArrayList.get(getAbsoluteAdapterPosition());
                        Intent intent = new Intent(ZamowActivity.this, UpdateZamowienie.class);
                        String id = getZamowienie.getId();
                        intent.putExtra("id", id);
                        startActivity(intent);
                        finish();
                    }
                });

                card_view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        GetZamowienie getZamowienie = itemsArrayList.get(getAbsoluteAdapterPosition());
                        getZamowienie.setVisible(!getZamowienie.isVisible());
                        notifyItemChanged(getAbsoluteAdapterPosition());

                    }
                });

            }
        }

        public MyAdapter(ArrayList<GetZamowienie> Gospodarstwo, Context context)
        {
            this.values = Gospodarstwo;
            this.context = context;
        }

        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.recycle_zamowienia, parent, false);
            MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);
            return vh;
        }


        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position){
            final GetZamowienie getZamowienie = values.get(position);
            holder.imie_zam.setText(getZamowienie.getImie_zam());
            holder.nazwisko_zam.setText(getZamowienie.getNazwisko_zam());
            holder.data_zam.setText(getZamowienie.getData_zam());
            holder.model_zam.setText(getZamowienie.getModel_zam());
            holder.marka_zam.setText(getZamowienie.getMarka_zam());
            holder.nazw_prod_zam.setText(getZamowienie.getNazw_prod_zam());
            holder.ilosc_zam.setText(getZamowienie.getIlosc_zam());
            holder.cena_zam.setText(getZamowienie.getCena_zam());
            holder.data_odbioru_zam.setText(getZamowienie.getData_odbioru_zam());

            boolean isVisible = itemsArrayList.get(position).isVisible();
            holder.lin_visible_zam.setVisibility(isVisible ? View.VISIBLE : View.GONE);

        }

        @Override
        public int getItemCount(){
            return values.size();
        }


    }
}