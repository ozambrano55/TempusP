package com.example.sistemas.casalinda;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistemas.casalinda.Utilidades.claseGlobal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.sistemas.casalinda.AdaptadorRecyclerView.CAN;
import static com.example.sistemas.casalinda.AdaptadorRecyclerView.COD;
import static com.example.sistemas.casalinda.AdaptadorRecyclerView.NOM;
import static com.example.sistemas.casalinda.AdaptadorRecyclerView.TOT;
import static com.example.sistemas.casalinda.AdaptadorRecyclerView.UNI;

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

        tvNombre= findViewById(R.id.tvNomb);
        tvNombre.setText(nombre);

        edCant= findViewById(R.id.edtCan);
        edCant.setText(cantidad);

        spUni= findViewById(R.id.spnUni);
        //spUni.

        tvTotal= findViewById(R.id.tvTot);
        tvTotal.setText(total);

        consultarExistencias(codigo);
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



}
