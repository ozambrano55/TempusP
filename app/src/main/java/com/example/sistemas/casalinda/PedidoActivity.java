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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemas.casalinda.Utilidades.claseGlobal;
import com.example.sistemas.casalinda.adaptadores.AdaptadorRecyclerView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class PedidoActivity extends AppCompatActivity {
    String  cod,nomb,cant,cant1,unit,total;
    int posicion;

    Connection connect;



    int SOLICITO_UTILIZAR_CAMARA;
    private ZXingScannerView vistaescaner;
    EditText etCodigo,etNomb, etCant,etCedula;


    final AdaptadorRecyclerView adaptadorRecyclerView=new AdaptadorRecyclerView(new InterfazClickRecyclerView() {
        @Override
        public void onClick(View v, Pedido p) {
            //Toast.makeText(PedidoActivity.this, "Daniel es "+p.toString(),Toast.LENGTH_LONG).show();
            try{
                //adaptadorRecyclerView.actualizarPedido( Integer.valueOf(posicion),new Pedido(cod, nomb, cant, unit, total));
            }catch (Exception e){
               // Toast.makeText(PedidoActivity.this,"Error: "+e.getMessage(),Toast.LENGTH_LONG ).show();
            }

        }

    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        //Permiso por el usuario
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},SOLICITO_UTILIZAR_CAMARA);

        final Button btnAgregar=findViewById(R.id.btnAgregar);
        Button btnGrabar= findViewById(R.id.btnGuardar);
        final Button btnEscaner=findViewById(R.id.btnEscaner);
        RecyclerView recyclerViewPedidos =findViewById(R.id.pRecycler);

        etCodigo=findViewById(R.id.edtCodigo);
        //etNomb=findViewById(R.id.edtNombre);
        etCant=findViewById(R.id.edtCant);
        etCedula=findViewById(R.id.edtCedula);

        final TextView textViewProforma=findViewById(R.id.txtProforma);
        final TextView textViewFactura=findViewById(R.id.txtFactura);
        final  TextView textViewPedido=findViewById(R.id.txtPedido);


        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerViewPedidos);

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
                    String dato="";

                   if (codigo.isEmpty()|ca.isEmpty()) {
                       Toast.makeText(PedidoActivity.this, "Rellena los campos", Toast.LENGTH_SHORT).show();
                       return;
                   }else {
                       consultarproducto(codigo,Integer.parseInt(ca) );
                       if (cod.isEmpty() | cant.isEmpty()) {
                           etCodigo.setText("");
                           etCodigo.setHint("Codigo No existe");
                           etCant.setText("");
                           etCant.setHint("Cant.");

                       } else {
                           if (Double.valueOf(cant1) >=Double.valueOf(cant)) {
                               int registros =adaptadorRecyclerView.getItemCount();
                                int a=0;
                                int d=0;
                                int pa=0;
                                int c;
                                Double u;
                               while ( a<registros)
                               {
                                   dato=adaptadorRecyclerView.getPedidos().get(a).getCodigo();
                                   c=Integer.parseInt( adaptadorRecyclerView.getPedidos().get(a).getCantidad());
                                   u=Double.parseDouble(adaptadorRecyclerView.getPedidos().get(a).getUnitario());
                                   //dato=adaptadorRecyclerView;
                                    if(cod.equals( dato)){
                                        d=d+1;
                                        pa=a;
                                        cant=String.valueOf(c+Integer.parseInt(cant)) ;
                                        total=String.valueOf( (double)Math.round ((u*Double.valueOf(cant))*100d)/100) ;
                                    }
                                       a++;
                               }
                               if (d==1){
                                   adaptadorRecyclerView.actualizarPedido(pa, new Pedido(cod, nomb, cant, unit, total));
                                   textViewProforma.setText(String.valueOf((double) Math.round(((adaptadorRecyclerView.tot)) * 100d) / 100));
                                   textViewFactura.setText(String.valueOf((double) Math.round((((adaptadorRecyclerView.tot)) * 1.12) * 100d) / 100));
                                   etCodigo.requestFocus();
                               }else {
                                   adaptadorRecyclerView.agregarPedido(new Pedido(cod, nomb, cant, unit, total));
                                   etCodigo.requestFocus();
                                    }
                               //cod=adaptadorRecyclerView.getPedidos().get(i ).getCodigo()

                               etCodigo.setText("");
                               etCodigo.setHint("Código de Producto");
                               etCant.setText("");
                               etCant.setHint("Cant.");
                               textViewProforma.setText(String.valueOf((double) Math.round(((adaptadorRecyclerView.tot)) * 100d) / 100));
                               textViewFactura.setText(String.valueOf((double) Math.round((((adaptadorRecyclerView.tot)) * 1.12) * 100d) / 100));
                           }
                           else {
                               if (Double.valueOf(cant1) > 0) {
                                   Toast.makeText(PedidoActivity.this, "Ingrese una cantidad Menor o igual a " + cant1, Toast.LENGTH_LONG).show();
                                   etCant.setText("");
                                   etCant.setHint("Cant.");
                                   etCant.requestFocus();
                               }
                               else
                               {
                                   Toast.makeText(PedidoActivity.this, "No hay existencia, digite otro producto" , Toast.LENGTH_LONG).show();
                                   etCodigo.setText("");
                                   etCodigo.setHint("Codigo No existe");
                                   etCant.setText("");
                                   etCant.setHint("Cant.");
                                   etCodigo.requestFocus();
                               }
                           }
                       }
                   }
               } catch (Exception e){
                   Toast.makeText(PedidoActivity.this,"Error ONCREATE: "+e.getMessage() ,Toast.LENGTH_LONG).show();
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
                    btnAgregar.setEnabled(false);
                    btnEscaner.setEnabled(false );
                }
            }
        });



    }
    public void onSaveInstanceState(Bundle estado) {

        super.onSaveInstanceState(estado);
        //estado.putIntArray("Pedido",Pedido);
    }

    public void onResume() {
        super.onResume();
        final TextView textViewProforma=findViewById(R.id.txtProforma);
        final TextView textViewFactura=findViewById(R.id.txtFactura);
        claseGlobal objLectura = (claseGlobal) getApplicationContext();
        try {
                if(objLectura.getPos()!=null) {
                    posicion = objLectura.getPos();
                    cod = objLectura.getCodigo();
                    nomb = objLectura.getNombre();
                    cant = objLectura.getCantidad().toString();
                    unit = objLectura.getUnitario().toString();
                    total = objLectura.getTotal().toString();
                    adaptadorRecyclerView.actualizarPedido(posicion, new Pedido(cod, nomb, cant, unit, total));
                    textViewProforma.setText(String.valueOf((double) Math.round(((adaptadorRecyclerView.tot)) * 100d) / 100));
                    textViewFactura.setText(String.valueOf((double) Math.round((((adaptadorRecyclerView.tot)) * 1.12) * 100d) / 100));
                }
        }catch (Exception e){
            //Toast.makeText(PedidoActivity.this,"ERROR ON RESUME:"+ e.getMessage(),Toast.LENGTH_LONG).show();
            }
    }
    public void consultarproducto(String c, int ca){
        String ConnectionResult = "";
        claseGlobal objEscritura=(claseGlobal)getApplicationContext();
        claseGlobal objLectura=(claseGlobal)getApplicationContext();

        try {
            ConnectionStr conStr=new ConnectionStr();
            connect=conStr.connectionclasss();
            if (connect == null)
            {
                ConnectionResult = "Revisar tu conexion a internet!";
                Toast.makeText(getApplicationContext(),ConnectionResult,Toast.LENGTH_LONG).show();
            }
            else {
                CallableStatement call=connect.prepareCall("{call sp_BuscaProducto (?,?)}");
                call.setString(1, objLectura.getC_punto_venta());
                call.setString(2, c);

                ResultSet rs=call.executeQuery();


                if (rs.next()) {
                    cod=(rs.getString(2));
                    nomb=(rs.getString(3));
                    if(((double)Math.round( Double.valueOf( rs.getString(8))*100d)/100)==0){
                        unit= String.valueOf ((double)Math.round( Double.valueOf( rs.getString(12))*100d)/100);
                    }else{
                        unit= String.valueOf ((double)Math.round( Double.valueOf( rs.getString(8))*100d)/100);
                    }

                    cant1=(rs.getString(13));
                    cant=String.valueOf(ca);
                    total=String.valueOf( (double)Math.round ((Double.valueOf(unit)*Double.valueOf(cant))*100d)/100) ;
                }
                else{
                    cod="";
                    nomb="";
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


   //METODO PARA ESCANEAR
public void escaner(){
        IntentIntegrator intent =new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);

        intent.setPrompt("ESCANEAR CODIGO")
                .setCameraId(0)
                .setOrientationLocked(false)
                .setBeepEnabled(false)
                .setCaptureActivity(CaptureActivityPortrait.class)
                .setBarcodeImageEnabled(false)
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        etCodigo=findViewById(R.id.edtCodigo);
        IntentResult result =IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents()==null){
                Toast.makeText(this,"Cancelaste el escaneo", Toast.LENGTH_LONG).show();

            }else   {
                etCodigo.setText(result.getContents());
                etCant.requestFocus();
            }

        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
    private ItemTouchHelper.Callback createHelperCallback(){
        ItemTouchHelper.SimpleCallback simpleCallback=
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP| ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        final TextView textViewProforma=findViewById(R.id.txtProforma);
                        final TextView textViewFactura=findViewById(R.id.txtFactura);
                       try {
                           deleteItem(viewHolder.getAdapterPosition());
                           if (adaptadorRecyclerView.tot==0){
                               textViewProforma.setText("0.00");
                               textViewFactura.setText("0.00");

                           }
                           else {
                               textViewProforma.setText(String.valueOf((double) Math.round(((adaptadorRecyclerView.tot)) * 100d) / 100));
                               textViewFactura.setText(String.valueOf((double) Math.round((((adaptadorRecyclerView.tot)) * 1.12) * 100d) / 100));
                           }
                       }catch(Exception e){
                           Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

                       }
                    }
                };
        return simpleCallback;
    }
    private void deleteItem(final int position){
        adaptadorRecyclerView.eliminar(position);

    }
}
