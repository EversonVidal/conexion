package com.example.cliente.conexion;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.*;
import java.util.*;

import static java.util.Locale.*;

public class MainActivity extends AppCompatActivity {
    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@192.168.43.96:1521:xe";//COLOCAR EL IP QUE CORRESPONDE
    private static final String DEFAULT_USERNAME = "user1";
    private static final String DEFAULT_PASSWORD = "1234567";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Locale.setDefault(Locale.ENGLISH);

        Button boton=(Button)findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener(){
            TextView textview = (TextView) findViewById(R.id.texto);
            private Connection connection;
            String cadena="Usuarios:\n\n";

            @Override
            public void onClick(View arg0){
                try {
                    this.connection = createConnection();

                    Statement stmt=connection.createStatement();

                    ResultSet rs=stmt.executeQuery("select nombre from usuario");

                    while(rs.next()) {
                        cadena=cadena+rs.getString(1)+"\n \n";
                        textview.setText(cadena);


                    }
                    connection.close();
                }
                catch (Exception e) {

                    textview.setText(e.toString());
                }
            }

        });



    }

    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        return createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

}
