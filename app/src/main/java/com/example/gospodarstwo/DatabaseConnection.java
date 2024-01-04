package com.example.gospodarstwo;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    Connection con;
    String ip, database, user, password, port;

    @SuppressLint("NewApi")
    public Connection connectionclass()
    {
        ip = "192.168.1.115";
        database="Gospodarstwo";
        user="sa";
        password="admin";
        port="51483";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ConnectionURL= null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL= "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+ "databasename="+ database+";user="+user+ ";" + "password="+password+";";
            con = DriverManager.getConnection(ConnectionURL);

        }
        catch (SQLException ex) {
            Log.e("Error", ex.getMessage());

        } catch (ClassNotFoundException ex) {
            Log.e("Error", ex.getMessage());
        }
        return con;
    }
}
