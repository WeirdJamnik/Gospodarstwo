package com.example.gospodarstwo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class MaszynyActivity extends AppCompatActivity {

    private ArrayList<GetMaszyna> itemsArrayList;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private boolean success = false;
    private DatabaseConnection databaseConnection;
    private String msg = "Internet Connection Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maszyny);

        recyclerView = findViewById(R.id.maszyny_recycle);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseConnection = new DatabaseConnection();
        itemsArrayList = new ArrayList<>();


        MaszynyActivity.SyncData orderdata = new MaszynyActivity.SyncData();
        orderdata.execute("");
    }
    private class SyncData extends AsyncTask<String, String, String>
    {
        protected String doInBackground(String... strings)
        {
            try
            {
                Connection con = databaseConnection.connectionclass();
                if (con == null)
                {
                    success = false;
                }
                else{
                    String query = "select * from Maszyna";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if( rs!=null)
                    {
                        while (rs.next())
                        {
                            try {
                                itemsArrayList.add(new GetMaszyna(rs.getString("id"),
                                        rs.getString("model"),
                                        rs.getString("marka"),
                                        rs.getString("numer_rejestracji"),
                                        rs.getString("przebieg"),
                                        rs.getString("pojemnosc_zbiornika"),
                                        rs.getString("moc_silnika"),
                                        rs.getString("pojemnosc_silnika"),
                                        rs.getString("masa"),
                                        rs.getString("rok_produkcji"),
                                        rs.getString("data_wyg_przegl"),
                                        false));
                            } catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    }else{
                        msg = "No Data Found!";
                        success = false;
                    }
                }

            }catch (Exception e)
            {
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
                myAdapter = new MyAdapter(itemsArrayList, MaszynyActivity.this);
                recyclerView.setAdapter(myAdapter);
            }
        }

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {
        private ArrayList<GetMaszyna> values;
        public Context context;

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView marka, model, numer_rejestracji, przebieg, poj_sil, masa, moc, poj_zbior, rok_prod, rok_przegl;
            CardView card_view;
            LinearLayout linear_lay;
            Button update_przebieg_maszyny;
            View layout;

            public ViewHolder(View v)
            {
                super(v);
                layout = v;
                marka = v.findViewById(R.id.marka);
                model = v.findViewById(R.id.model);
                przebieg = v.findViewById(R.id.przebieg);
                poj_sil = v.findViewById(R.id.poj_sil);
                masa= v.findViewById(R.id.masa);
                moc = v.findViewById(R.id.moc);
                poj_zbior = v.findViewById(R.id.poj_zbior);
                rok_prod = v.findViewById(R.id.rok_prod);
                rok_przegl = v.findViewById(R.id.rok_przegl);
                numer_rejestracji = v.findViewById(R.id.numer_rejestracji);
                card_view = v.findViewById(R.id.card_view);
                linear_lay = v.findViewById(R.id.linear_lay);
                update_przebieg_maszyny = v.findViewById(R.id.update_przebieg_maszyny);

                card_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        GetMaszyna getMaszyna = itemsArrayList.get(getAbsoluteAdapterPosition());
                        getMaszyna.setVisible(!getMaszyna.isVisible());
                        notifyItemChanged(getAbsoluteAdapterPosition());
                    }
                });

                update_przebieg_maszyny.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetMaszyna getMaszyna = itemsArrayList.get(getAbsoluteAdapterPosition());
                        Intent intent = new Intent(MaszynyActivity.this, UpdatePrzeglad.class);
                        String id = getMaszyna.getId();
                        intent.putExtra("id", id);
                        startActivity(intent);
                        finish();
                    }
                });


            }
        }

        public MyAdapter(ArrayList<GetMaszyna> Gospodarstwo, Context context)
        {
            this.values = Gospodarstwo;
            this.context = context;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.recycle_maszyny, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position){
            final GetMaszyna getMaszyna = values.get(position);
            holder.marka.setText(getMaszyna.getMarka());
            holder.model.setText(getMaszyna.getModel());
            holder.numer_rejestracji.setText(getMaszyna.getNumer_rejestracji());
            holder.przebieg.setText(getMaszyna.getPrzebieg());
            holder.poj_zbior.setText(getMaszyna.getPoj_zbior());
            holder.moc.setText(getMaszyna.getMoc());
            holder.poj_sil.setText(getMaszyna.getPoj_sil());
            holder.masa.setText(getMaszyna.getMasa());
            holder.rok_prod.setText(getMaszyna.getData_prod());
            holder.rok_przegl.setText(getMaszyna.getData_przegl());

            boolean isVisible = itemsArrayList.get(position).isVisible();
            holder.linear_lay.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }

        @Override
        public int getItemCount(){
            return values.size();
        }


    }


}
