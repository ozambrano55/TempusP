package com.example.sistemas.casalinda;

import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.euicc.DownloadableSubscription;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.sistemas.casalinda.Utilidades.claseGlobal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.sistemas.casalinda.adaptadores.AdaptadorRecyclerView.CAN;
import static com.example.sistemas.casalinda.adaptadores.AdaptadorRecyclerView.COD;
import static com.example.sistemas.casalinda.adaptadores.AdaptadorRecyclerView.NOM;
import static com.example.sistemas.casalinda.adaptadores.AdaptadorRecyclerView.TOT;
import static com.example.sistemas.casalinda.adaptadores.AdaptadorRecyclerView.UNI;

public class PedidoEditar extends AppCompatActivity {

    private String codigo;
    private String nombre;
    private String cantidad;
    private String unitario;
    private String total;

    String bodega, nbodega, canti,ubica;

    TextView tvCodigo;
    TextView tvNombre;
    EditText edCant;
    Spinner spUni;
    TextView tvTotal;
    ArrayList<Existencia> existenciaList;
    ArrayList<String> listaExistencias;

    ArrayList<String> listaPrecios;
    ArrayList<Precio>preciosList;
    Connection connect;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_editar);

        codigo=getIntent().getStringExtra(COD);
        nombre=getIntent().getStringExtra(NOM);
        cantidad=getIntent().getStringExtra(CAN);
        unitario=getIntent().getStringExtra(UNI);
        total=getIntent().getStringExtra(TOT);

        tvCodigo= findViewById(R.id.tvCod);
        tvCodigo.setText(codigo);

        tvNombre= findViewById(R.id.tvNom);
        tvNombre.setText(nombre);

        edCant= findViewById(R.id.edtCan);
        edCant.setText(cantidad);


        tvTotal= findViewById(R.id.tvTot);
        tvTotal.setText(total);
        try{
            consultarExistencias(codigo);
            consultarprecio(codigo);

            spUni= findViewById(R.id.spnUni);
            ArrayAdapter<String> adaptador;
            adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,listaPrecios);
            spUni.setAdapter(adaptador);

            int seleccion=spUni.getSelectedItemPosition();
            // ca=adaptador.getItem(seleccion).toString();

            spUni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    unitario=(String) spUni.getItemAtPosition(position);
                    unitario=getNumeros(unitario);

                    cantidad=edCant.getText().toString();

                    total=String.valueOf(Double.valueOf(cantidad)* Double.valueOf(unitario));
                    tvTotal.setText( total);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edCant.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        cantidad=edCant.getText().toString();
                        if(cantidad.equals("")){
                            cantidad="0";
                        }

                        total=String.valueOf((double)Math.round ((Double.valueOf(cantidad)* Double.valueOf(unitario))*100d)/100);
                        tvTotal.setText( total);
                    }
                    catch (Exception e){
                        salir(e.getMessage());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try{
                        cantidad=edCant.getText().toString();
                        if(cantidad.equals("")){
                            cantidad="0";
                        }

                        total=String.valueOf((double)Math.round ((Double.valueOf(cantidad)* Double.valueOf(unitario))*100d)/100);
                        tvTotal.setText( total);
                    }
                    catch (Exception e){
                        salir(e.getMessage());
                    }
                }
            });


        }
        catch (Exception e){
            //Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            salir(e.getMessage());
        }

    }

    public static String getNumeros(String cadena){
        char []cadena_div=cadena.toCharArray();
        String d="";
        String n="";
        for (int i =0;i<cadena_div.length;i++){
            d=String.valueOf(cadena_div[i]) ;
                if(Character.isDigit(cadena_div[i])||d.equals(".")){
                    n+=cadena_div[i];
                }
/*
            if (d.equals(".")) {

                n+=cadena_div[i];
            }*/
        }
        return n;
    }
    public void salir(String e){
        LinearLayout linear = findViewById(R.id.linear_layout);

            AlertDialog.Builder alerta=new AlertDialog.Builder(this);
            alerta.setMessage(e)
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            //super.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog titulo=alerta.create();
            titulo.setTitle("Salida");
            titulo.show();
            //finish();
            //super.onBackPressed();

    }
    public void consultarExistencias( String c) {
        try {
        String ConnectionResult = "";

            Existencia peresona = null;

            existenciaList = new ArrayList<Existencia>();

        claseGlobal objEscritura=(claseGlobal)getApplicationContext();
        claseGlobal objLectura=(claseGlobal)getApplicationContext();


            ConnectionStr conStr=new ConnectionStr();
            connect=conStr.connectionclasss();
            if (connect == null)
            {
                ConnectionResult = "Revisar tu conexion a internet!";
            }
            else {
                CallableStatement call = connect.prepareCall("{call sp_BuscaProductos (?,?)}");
                call.setString(1, objLectura.getC_punto_venta());
                call.setString(2, c);

                ResultSet rs = call.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        existenciaList.add(new Existencia(rs.getString(10),rs.getString(11),rs.getString(13),rs.getString(16)));
                       /* bodega = (rs.getString(10));
                        nbodega = (rs.getString(11));
                        canti = (rs.getString(13));
                        ubica = (rs.getString(16));
*/
                    }


                }
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public void consultarprecio(String c) {
        try {
            Precio precio = null;
            claseGlobal objLectura=(claseGlobal)getApplicationContext();
            preciosList = new ArrayList<Precio>();

            ConnectionStr conStr = new ConnectionStr();
            connect = conStr.connectionclasss();        // Connect to database

            CallableStatement call = connect.prepareCall("{call sp_BuscaPrecioProducto (?,?)}");
            call.setString(1, objLectura.getC_punto_venta());
            call.setString(2, c);

            ResultSet rs = call.executeQuery();

            if (rs!=null) {
                while (rs.next()) {
                    preciosList.add(new Precio(rs.getString("Tipo"),rs.getString("Precio")));

                }
            }
            obtenerLista();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void obtenerLista() {
        listaPrecios = new ArrayList<String>();
        //listaPuntos.add("Seleccione");
        for (int i = 0; i < preciosList.size(); i++) {
            listaPrecios.add(preciosList.get(i).getTipo() + " - " + preciosList.get(i).getPrecio());
        }
    }

}
