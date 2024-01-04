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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ZatankujActivity extends AppCompatActivity {

    private ArrayList<GetTankowanie> itemsArrayList;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private boolean success = false;
    private DatabaseConnection databaseConnection;
    private String msg = "Internet Connection Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zatankuj);

        recyclerView = findViewById(R.id.tankowanie_recycle);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseConnection = new DatabaseConnection();
        itemsArrayList = new ArrayList<>();

        ZatankujActivity.SyncData orderdata = new ZatankujActivity.SyncData();
        orderdata.execute("");

        Button nowe_tankowanie = (Button)findViewById(R.id.nowe_tankowanie);

        nowe_tankowanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZatankujActivity.this, DodajTankowanie.class);
                startActivity(intent);
                finish();
            }
        });
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
                    String query = "SELECT * from Tankowanie_view ORDER BY data_tank DESC";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if( rs!=null)
                    {
                        while (rs.next())
                        {
                            try {
                                itemsArrayList.add(new GetTankowanie(rs.getString("imie"), rs.getString("nazwisko"),
                                        rs.getString("ilosc_zatankowanego_paliwa"),
                                        rs.getString("data_tank"),
                                        rs.getString("marka"),
                                        rs.getString("model"),
                                        rs.getString("przebieg"),
                                        rs.getString("numer_zbiornika"),
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
                myAdapter = new MyAdapter(itemsArrayList, ZatankujActivity.this);
                recyclerView.setAdapter(myAdapter);
            }
        }

    }
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {
        private ArrayList<GetTankowanie> values;
        public Context context;

        public MyAdapter(ArrayList<GetTankowanie> Gospodarstwo, Context context)
        {
            this.values = Gospodarstwo;
            this.context = context;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView imie_tank, nazw_tank, pal_tank, data_tank, marka_tank, model_tank, przebieg_tank, nr_zbior_tank;
            CardView card_view3;
            LinearLayout lin_visible;
            View layout;

            public ViewHolder(View v)
            {
                super(v);
                layout = v;
                imie_tank = v.findViewById(R.id.imie_tank);
                nazw_tank = v.findViewById(R.id.nazw_tank);
                pal_tank = v.findViewById(R.id.pal_tank);
                data_tank = v.findViewById(R.id.data_tank);
                marka_tank = v.findViewById(R.id.marka_tank);
                model_tank = v.findViewById(R.id.model_tank);
                przebieg_tank = v.findViewById(R.id.przebieg_tank);
                nr_zbior_tank = v.findViewById(R.id.nr_zbior_tank);
                card_view3 = v.findViewById(R.id.card_view3);
                lin_visible = v.findViewById(R.id.lin_visible);


                card_view3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        GetTankowanie getTankowanie = itemsArrayList.get(getAbsoluteAdapterPosition());
                        getTankowanie.setVisible(!getTankowanie.isVisible());
                        notifyItemChanged(getAbsoluteAdapterPosition());
                    }
                });
            }
        }


        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.recycle_tankowanie, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position){
            final GetTankowanie getTankowanie = values.get(position);
            holder.imie_tank.setText(getTankowanie.getImie());
            holder.nazw_tank.setText(getTankowanie.getNazwisko());
            holder.pal_tank.setText(getTankowanie.getIlosc_zatankowanego_paliwa());
            holder.data_tank.setText(getTankowanie.getData_tank());
            holder.marka_tank.setText(getTankowanie.getMarka());
            holder.model_tank.setText(getTankowanie.getModel());
            holder.przebieg_tank.setText(getTankowanie.getPrzebieg());
            holder.nr_zbior_tank.setText(getTankowanie.getNumer_zbiornika());

            boolean isVisible = itemsArrayList.get(position).isVisible();
            holder.lin_visible.setVisibility(isVisible ? View.VISIBLE : View.GONE);


        }

        @Override
        public int getItemCount(){
            return values.size();
        }

    }
}