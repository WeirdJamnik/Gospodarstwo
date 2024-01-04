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

public class CzesciActivity extends AppCompatActivity {

    private ArrayList<GetCzesc> itemsArrayList;
    private CzesciActivity.MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private boolean success = false;
    private DatabaseConnection databaseConnection;
    private String msg = "Internet Connection Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czesci);

        recyclerView = findViewById(R.id.czesci_recycle);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseConnection = new DatabaseConnection();
        itemsArrayList = new ArrayList<>();

        Button czesci_dod = (Button)findViewById(R.id.czesci_dod);

        czesci_dod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CzesciActivity.this, DodajWymianeCzesci.class);
                startActivity(intent);
                finish();

            }
        });

        CzesciActivity.SyncData orderdata = new CzesciActivity.SyncData();
        orderdata.execute("");
    }
    private class SyncData extends AsyncTask<String, String, String> {
        protected String doInBackground(String... strings) {
            try {
                Connection con = databaseConnection.connectionclass();
                if (con == null) {
                    success = false;
                } else {
                    String query = "select * from Wymiany_czesci_view";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                itemsArrayList.add(new GetCzesc(rs.getString("id"),
                                        rs.getString("marka"),
                                        rs.getString("model"),
                                        rs.getString("nazwa_czesci"),
                                        rs.getString("data_wym"),
                                        rs.getString("uwagi"),
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
                myAdapter = new CzesciActivity.MyAdapter(itemsArrayList, CzesciActivity.this);
                recyclerView.setAdapter(myAdapter);

            }
        }

    }
    public class MyAdapter extends RecyclerView.Adapter<CzesciActivity.MyAdapter.ViewHolder>
    {
        private ArrayList<GetCzesc> values;
        public Context context;

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView nazwa_czesci, data_wym_cze, marka_wym, model_wym, uwagi_wym;
            CardView card_view4;
            LinearLayout lin_visible_cze;
            View layout;
            Button usun_wym;

            public ViewHolder(View v)
            {
                super(v);
                layout = v;
                nazwa_czesci = v.findViewById(R.id.nazwa_czesci);
                data_wym_cze = v.findViewById(R.id.data_wym_cze);
                marka_wym = v.findViewById(R.id.marka_wym);
                model_wym = v.findViewById(R.id.model_wym);
                uwagi_wym = v.findViewById(R.id.uwagi_wym);
                card_view4 = v.findViewById(R.id.card_view4);
                lin_visible_cze = v.findViewById(R.id.lin_visible_cze);
                usun_wym = v.findViewById(R.id.usun_wym);

                usun_wym.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetCzesc getCzesc = itemsArrayList.get(getAbsoluteAdapterPosition());
                        DatabaseConnection databaseConnection = new DatabaseConnection();
                        Connection conn = databaseConnection.connectionclass();
                        try {
                            if (conn != null) {
                                Intent intent = new Intent(CzesciActivity.this, WymianyActivity.class);
                                startActivity(intent);
                                finish();
                                String query = "delete from Wymiany_czesci where id= " + getCzesc.getId();
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

                card_view4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        GetCzesc getCzesc = itemsArrayList.get(getAbsoluteAdapterPosition());
                        getCzesc.setVisible(!getCzesc.isVisible());
                        notifyItemChanged(getAbsoluteAdapterPosition());

                    }
                });

            }
        }

        public MyAdapter(ArrayList<GetCzesc> Gospodarstwo, Context context)
        {
            this.values = Gospodarstwo;
            this.context = context;
        }

        public CzesciActivity.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.recycle_czesci, parent, false);
            CzesciActivity.MyAdapter.ViewHolder vh = new CzesciActivity.MyAdapter.ViewHolder(v);
            return vh;
        }


        @Override
        public void onBindViewHolder(CzesciActivity.MyAdapter.ViewHolder holder, final int position){
            final GetCzesc getCzesc = values.get(position);
            holder.nazwa_czesci.setText(getCzesc.getNazwa_czesci());
            holder.data_wym_cze.setText(getCzesc.getData_wym());
            holder.marka_wym.setText(getCzesc.getMarka());
            holder.model_wym.setText(getCzesc.getModel());
            holder.uwagi_wym.setText(getCzesc.getUwagi());

            boolean isVisible = itemsArrayList.get(position).isVisible();
            holder.lin_visible_cze.setVisibility(isVisible ? View.VISIBLE : View.GONE);

        }

        @Override
        public int getItemCount(){
            return values.size();
        }


    }
}