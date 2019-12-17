package com.example.sistemas.casalinda;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemas.casalinda.Utilidades.claseGlobal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PuntoActivity extends AppCompatActivity {

    Spinner comboPersonas;

    TextView twfuncioanrio;
    ArrayList<String> listaPuntos;
    ArrayList<Punto> puntosList;
    //String  c_funcionario, funcionario,c_punto_venta,punto;
    Connection connect;
    //claseGlobal objEscritura=(claseGlobal)getApplicationContext();
    //claseGlobal objLectura=(claseGlobal)getApplicationContext();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto);

        //Bundle extras = getIntent().getExtras();
        //String d1 = extras.getString("c_funcionario");
        //String d2 = extras.getString("funcionario");

        claseGlobal objEscritura=(claseGlobal)getApplicationContext();
        claseGlobal objLectura=(claseGlobal)getApplicationContext();


        twfuncioanrio = (TextView) findViewById(R.id.txtFuncionario);
        twfuncioanrio.setText(objLectura.getFuncionario());
        //c_funcionario=
        //funcionario=d2;
        consultarpersona();
        comboPersonas = (Spinner) findViewById(R.id.comboPersonas);
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listaPuntos);
        comboPersonas.setAdapter(adaptador);

    }

    public void consultarpersona() {
        try {
            Punto peresona = null;

            puntosList = new ArrayList<Punto>();

            ConnectionStr conStr = new ConnectionStr();
            connect = conStr.connectionclasss();        // Connect to database

            String query = "select Rtrim(C_Punto_Venta)C_punto, rtrim(N_Punto_Venta)n_Punto_venta, C_Bodega from Fac_Puntos_Venta where Flag_Vigente=1";
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs!=null) {
                while (rs.next()) {
                    puntosList.add(new Punto(rs.getString("c_punto"),rs.getString("n_Punto_venta")));
                   // c_punto_venta=rs.getString(1);
                    //objEscritura.setC_punto_venta(rs.getString(1));
                    //objEscritura.setC_funcionario(rs.getString(3));
                }
            }
            obtenerLista();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void obtenerLista() {
        listaPuntos = new ArrayList<String>();
        //listaPuntos.add("Seleccione");
        for (int i = 0; i < puntosList.size(); i++) {
            listaPuntos.add(puntosList.get(i).getC_punto() + " - " + puntosList.get(i).getPunto());
        }
    }

    public void goMainP(View view) {
        Intent goMainP = new Intent(PuntoActivity.this, MainActivity.class);
        //goMainP.putExtra("c_funcionario",c_funcionario);
        //goMainP.putExtra("funcionario", funcionario);
        claseGlobal objEscritura=(claseGlobal)getApplicationContext();
        claseGlobal objLectura=(claseGlobal)getApplicationContext();

        String seleccion=comboPersonas.getSelectedItem().toString();
        //punto=seleccion;

        objEscritura.setPunto(seleccion);
       objEscritura.setC_punto_venta(seleccion.substring(0,3));
        //goMainP.putExtra("c_funcionario", c_funcionario);
        //goMainP.putExtra("funcionario", funcionario);
        //goMainP.putExtra("c_punto_venta", c_punto_venta);
        //goMainP.putExtra("punto",punto);

        startActivity(goMainP);
        finish();
    }

    public void salir(View view) {
        finish();
    }
}