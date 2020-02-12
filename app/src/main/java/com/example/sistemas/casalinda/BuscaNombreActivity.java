package com.example.sistemas.casalinda;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistemas.casalinda.Utilidades.claseGlobal;
import com.example.sistemas.casalinda.adaptadores.AdaptadorRecyclerView;
import com.example.sistemas.casalinda.entidades.Pedido;
import com.example.sistemas.casalinda.interfaz.InterfazClickRecyclerView;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class BuscaNombreActivity extends AppCompatActivity {
    Connection connect;
    String CODIGO, NOMBRE;
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
        setContentView(R.layout.activity_busca_nombre);

        final Button btnAceptar=findViewById(R.id.btAceptar);
        final Button btnCancelar=findViewById(R.id.btCancelar);
        final TextView txtRecibe=findViewById(R.id.txtRecibe);
        final EditText etBuscar=findViewById(R.id.edtBuscar);
        RecyclerView recyclerViewBusca =findViewById(R.id.pRecyclerB);
        recyclerViewBusca.addItemDecoration(new DividerItemDecoration(BuscaNombreActivity.this, LinearLayoutManager.VERTICAL));
        recyclerViewBusca.setAdapter(adaptadorRecyclerView);

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void consultaClienteProducto(Integer t, String c){
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
                CallableStatement call=connect.prepareCall("{call sp_BuscaCP (?,?)}");
                call.setInt(1, t);
                call.setString(2,c);
                ResultSet rs=call.executeQuery();


                if (rs.next()) {
                    CODIGO=(rs.getString(1));
                    NOMBRE=(rs.getString(2));
                }
                else
                {
                   CODIGO=("");
                    NOMBRE=("");
                }
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}
