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

public class OlejeActivity extends AppCompatActivity {

    private ArrayList<GetOlej> itemsArrayList;
    private OlejeActivity.MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private boolean success = false;
    private DatabaseConnection databaseConnection;
    private String msg = "Internet Connection Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oleje);

        recyclerView = findViewById(R.id.oleje_recycle);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseConnection = new DatabaseConnection();
        itemsArrayList = new ArrayList<>();

        Button olej_dod = (Button)findViewById(R.id.olej_dod);

        olej_dod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OlejeActivity.this, DodajWymianeOleju.class);
                startActivity(intent);
                finish();

            }
        });

        OlejeActivity.SyncData orderdata = new OlejeActivity.SyncData();
        orderdata.execute("");
    }
    private class SyncData extends AsyncTask<String, String, String> {
        protected String doInBackground(String... strings) {
            try {
                Connection con = databaseConnection.connectionclass();
                if (con == null) {
                    success = false;
                } else {
                    String query = "select * from Wymiany_oleju_view";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                itemsArrayList.add(new GetOlej(rs.getString("id"),
                                        rs.getString("marka"),
                                        rs.getString("model"),
                                        rs.getString("nazwa_oleju"),
                                        rs.getString("data"),
                                        rs.getString("ilosc"),
                                        rs.getString("przebieg"),
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
                myAdapter = new OlejeActivity.MyAdapter(itemsArrayList, OlejeActivity.this);
                recyclerView.setAdapter(myAdapter);

            }
        }

    }
    public class MyAdapter extends RecyclerView.Adapter<OlejeActivity.MyAdapter.ViewHolder>
    {
        private ArrayList<GetOlej> values;
        public Context context;

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView nazwa_olej, data_wym_olej, marka_olej, model_olej, przebieg_olej, ilosc_olej;
            CardView card_view5;
            LinearLayout lin_visible_olej;
            View layout;
            Button usun_olej;

            public ViewHolder(View v)
            {
                super(v);
                layout = v;
                nazwa_olej = v.findViewById(R.id.nazwa_olej);
                data_wym_olej = v.findViewById(R.id.data_wym_olej);
                marka_olej = v.findViewById(R.id.marka_olej);
                model_olej = v.findViewById(R.id.model_olej);
                przebieg_olej = v.findViewById(R.id.przebieg_olej);
                ilosc_olej = v.findViewById(R.id.ilosc_olej);
                card_view5 = v.findViewById(R.id.card_view5);
                lin_visible_olej = v.findViewById(R.id.lin_visible_olej);
                usun_olej = v.findViewById(R.id.usun_olej);

                usun_olej.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetOlej getOlej = itemsArrayList.get(getAbsoluteAdapterPosition());
                        DatabaseConnection databaseConnection = new DatabaseConnection();
                        Connection conn = databaseConnection.connectionclass();
                        try {
                            if (conn != null) {
                                Intent intent = new Intent(OlejeActivity.this, WymianyActivity.class);
                                startActivity(intent);
                                finish();
                                String query = "delete from Wymiany_oleju where id= " + getOlej.getId();
                                Statement st = conn.createStatement();
                                ResultSet rs = st.executeQuery(query);
                            }
                            else
                            {
                                msg = "No";
                            }
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });

                card_view5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        GetOlej getOlej = itemsArrayList.get(getAbsoluteAdapterPosition());
                        getOlej.setVisible(!getOlej.isVisible());
                        notifyItemChanged(getAbsoluteAdapterPosition());

                    }
                });

            }
        }

        public MyAdapter(ArrayList<GetOlej> Gospodarstwo, Context context)
        {
            this.values = Gospodarstwo;
            this.context = context;
        }

        public OlejeActivity.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.recycle_oleje, parent, false);
            OlejeActivity.MyAdapter.ViewHolder vh = new OlejeActivity.MyAdapter.ViewHolder(v);
            return vh;
        }


        @Override
        public void onBindViewHolder(OlejeActivity.MyAdapter.ViewHolder holder, final int position){
            final GetOlej getOlej = values.get(position);
            holder.nazwa_olej.setText(getOlej.getNazwa_oleju());
            holder.data_wym_olej.setText(getOlej.getData());
            holder.marka_olej.setText(getOlej.getMarka());
            holder.model_olej.setText(getOlej.getModel());
            holder.przebieg_olej.setText(getOlej.getPrzebieg());
            holder.ilosc_olej.setText(getOlej.getIlosc());

            boolean isVisible = itemsArrayList.get(position).isVisible();
            holder.lin_visible_olej.setVisibility(isVisible ? View.VISIBLE : View.GONE);

        }

        @Override
        public int getItemCount(){
            return values.size();
        }


    }
}