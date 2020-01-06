package com.example.sistemas.casalinda;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    TextView tvCodigo;
    TextView tvNombre;
    EditText edCant;
    Spinner spUni;
    TextView tvTotal;

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
    }
}
