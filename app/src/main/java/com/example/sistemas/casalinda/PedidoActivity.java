package com.example.sistemas.casalinda;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemas.casalinda.Utilidades.claseGlobal;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
public class PedidoActivity extends AppCompatActivity {
    String cod,cant,unit,total;
    Connection connect;

    int SOLICITO_UTILIZAR_CAMARA;
    private ZXingScannerView vistaescaner;
    EditText etCodigo,etCant,etCedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        //Permiso por el usuario
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},SOLICITO_UTILIZAR_CAMARA);

        Button btnAgregar=findViewById(R.id.btnAgregar);
        Button btnGrabar= findViewById(R.id.btnGuardar);
        Button btnEscaner=findViewById(R.id.btnEscaner);
        RecyclerView recyclerViewPedidos =findViewById(R.id.pRecycler);

        etCodigo=findViewById(R.id.edtCodigo);
        etCant=findViewById(R.id.edtCant);
        etCedula=findViewById(R.id.edtCedula);
        final TextView textViewProforma=findViewById(R.id.txtProforma);
        final TextView textViewFactura=findViewById(R.id.txtFactura);
        final  TextView textViewPedido=findViewById(R.id.txtPedido);

        final AdaptadorRecyclerView adaptadorRecyclerView=new AdaptadorRecyclerView(new InterfazClickRecyclerView() {
            @Override
            public void onClick(View v, Pedido p) {
                Toast.makeText(PedidoActivity.this,p.toString(),Toast.LENGTH_LONG).show();
            }
        });
       //Configuramos cómo se va a organizar las vistas dentro del RecyclerView; simplemente con un LinearLayout para que
        //aparezcan una debajo de otra
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(PedidoActivity.this  );
        recyclerViewPedidos.setLayoutManager(linearLayoutManager);
        //La línea que divide los elementos
        recyclerViewPedidos.addItemDecoration(new DividerItemDecoration(PedidoActivity.this ,LinearLayoutManager.VERTICAL));
        //El adaptador que se encarga de toda la lógica
        recyclerViewPedidos.setAdapter(adaptadorRecyclerView);
        //adaptadorRecyverView.agregarPedido(new Pedido());

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   String codigo = etCodigo.getText().toString();
                   String ca=etCant.getText().toString() ;
                   claseGlobal objLectura = (claseGlobal) getApplicationContext();

                   //String cantidad=editTextCantidad.getText().toString();
                   //String unitario =editTextUnitario.getText().toString();
                   // String total=editTextTotal.getText().toString();
                   if (codigo.isEmpty()|ca.isEmpty()) {
                       Toast.makeText(PedidoActivity.this, "Rellena los campos", Toast.LENGTH_SHORT).show();
                       return;
                   }else {
                       consultarproducto(codigo, Double.parseDouble(ca));
                       if (cod.isEmpty() | cant.isEmpty()) {
                           //adaptadorRecyclerView.agregarPedido(new Pedido(cod.toString(), cant.toString(), unit.toString(), total.toString()));
                           etCodigo.setText("");
                           etCodigo.setHint("Codigo No existe");
                           etCant.setText("");
                           etCant.setHint("Cant.");
                           //Toast.makeText(PedidoActivity.this,"Codigo No existe",Toast.LENGTH_LONG).show();
                           //textViewProforma.setText( (adaptadorRecyclerView.totString)) ;
                       } else {
                           adaptadorRecyclerView.agregarPedido(new Pedido(cod, cant, unit, total));
                           etCodigo.setText("");
                           etCodigo.setHint("Código de Producto");
                           etCant.setText("");
                           etCant.setHint("Cant.");
                           textViewProforma.setText(String.valueOf((double) Math.round(((adaptadorRecyclerView.tot)) * 100d) / 100));
                           textViewFactura.setText(String.valueOf((double) Math.round((((adaptadorRecyclerView.tot))*1.12) * 100d) / 100));


                       }
                   }
               } catch (Exception e){
                   Toast.makeText(PedidoActivity.this,"Error"+e.getMessage() ,Toast.LENGTH_LONG).show();
               }

                }

        });

        btnEscaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    escaner();
                }catch (Exception e){
                    Toast.makeText(PedidoActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ced = etCedula.getText().toString();
                if (Ced.isEmpty()) {
                    Toast.makeText(PedidoActivity.this, "Digite cédula cliente", Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    textViewPedido.setText("25800001");
                }
            }
        });



    }
    public void consultarproducto(String c, double ca){
        String ConnectionResult = "";



        claseGlobal objEscritura=(claseGlobal)getApplicationContext();
        claseGlobal objLectura=(claseGlobal)getApplicationContext();

        try {
            ConnectionStr conStr=new ConnectionStr();
            connect=conStr.connectionclasss();
            if (connect == null)
            {
                ConnectionResult = "Revisar tu conexion a internet!";
            }
            else {
                CallableStatement call=connect.prepareCall("{call sp_BuscaProducto (?,?)}");
                call.setString(1,objLectura.getC_punto_venta().toString());
                call.setString(2,c.toString());

                ResultSet rs=call.executeQuery();


                if (rs.next()) {
                    cod=(rs.getString(2));
                    unit=(rs.getString(8));

                    cant=String.valueOf(ca);
                    total=String.valueOf( (double)Math.round ((Double.valueOf(unit)*Double.valueOf(cant))*100d)/100) ;
                }
                else{
                    cod="";
                    unit="";
                    cant="";
                    total="0.00";

                }


            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
    //Validar atras
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {




        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        // Esto es lo que hace mi botón al pulsar ir a atrás

            //Toast.makeText(getApplicationContext(), "Voy hacia atrás!!",     Toast.LENGTH_SHORT).show();
            //onBackPressed();

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Quiere salir del pedido");
            builder.setTitle("Confirmación");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setCancelable(false)
            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();
                }
             });

            builder.create();
            builder.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

   /* public void EscanearP(View view){
        vistaescaner=new ZXingScannerView(this);
        vistaescaner.setResultHandler(new zxingscanner());
        setContentView(vistaescaner);
        vistaescaner.startCamera();
    }
    class zxingscanner implements ZXingScannerView.ResultHandler{
        @Override
        public void handleResult(Result result){
            String dato=result.getText();
            //setContentView(R.layout.activity_pedido);
            vistaescaner.stopCamera();
            EditText codigo = (EditText) findViewById(R.id.edtCodigo);
            codigo.setText(dato);
        }
    }*/

   //METODO PARA ESCANEAR
    public void escaner(){
        IntentIntegrator intent =new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);

        intent.setPrompt("ESCANEAR CODIGO")
                .setCameraId(0)
                .setOrientationLocked(false)
                .setBeepEnabled(false)
                .setBarcodeImageEnabled(false)
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result =IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents()==null){
                Toast.makeText(this,"Cancelaste el escaneo", Toast.LENGTH_LONG).show();

            }else   {
                etCodigo.setText(result.getContents().toString());
            }

        }else {
            super .onActivityResult(requestCode,resultCode,data);
        }
    }
}
