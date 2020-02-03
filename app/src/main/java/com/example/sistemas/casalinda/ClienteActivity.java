package com.example.sistemas.casalinda;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity {

    ArrayList<String> tipoList;
    ArrayList<String> paisList;
    ArrayList<String> provinciaList;
    Connection connect;
    private boolean isFirstTime=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        Spinner tipo=findViewById(R.id.spTipo);
        Spinner pais=findViewById(R.id.spPais);
        Spinner provincia=findViewById(R.id.spProvincia);
        Spinner ciudad =findViewById(R.id.spCiudad);


        TextInputEditText cedula=findViewById(R.id.tiedtCedula);
        TextView vcedula=findViewById(R.id.txtCedula);

        cargaTipo();
        ArrayAdapter<CharSequence> adaptadorTipo=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,tipoList);
        tipo.setAdapter(adaptadorTipo);

        cargaPais();
        ArrayAdapter<CharSequence> adaptadorPais=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,paisList);
        pais.setAdapter(adaptadorPais);



        pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              try {
                  if (isFirstTime) {
                      isFirstTime = false;
                  } else {
                      cargaProvincia(String.valueOf( pais.getSelectedItem()));
                      ArrayAdapter<String>adaptadorProvincia=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,provinciaList);
                      provincia.setAdapter(adaptadorProvincia);
                  }
              }catch (Exception e ){salir(e.getMessage());}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        cedula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String c;
                c=cedula.getText().toString();
                if (String.valueOf( verificarcedula(c)).equals("true")) {

                    vcedula.setText("Cédula Correcta");
                }else
                {
                    vcedula.setText("Cédula Incorrecta");
                }
            }
        });
    }
    //CargaTipo

    public void cargaTipo() {
        try {


            tipoList = new ArrayList<String>();

            ConnectionStr conStr = new ConnectionStr();
            connect = conStr.connectionclasss();        // Connect to database

            String query = "select Rtrim(D_Tipo_Identi)D_Tipo_Identi from Gen_Tipo_Identificacion";
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs!=null) {
                while (rs.next()) {
                    tipoList.add(rs.getString("D_Tipo_Identi"));
                }
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            salir(e.getMessage());

        }
    }
    public void cargaPais() {
        try {


            paisList = new ArrayList<String>();

            ConnectionStr conStr = new ConnectionStr();
            connect = conStr.connectionclasss();        // Connect to database

            String query = "select (rtrim(C_Pais)+'-'+ rtrim(N_Pais_En)) as Pais from Gen_Paises";
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs!=null) {
                while (rs.next()) {
                    paisList.add(rs.getString("Pais"));
                }
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            salir(e.getMessage());

        }
    }

    public void cargaProvincia( String pro) {
        try {
            String pa;
            pa=pro.substring(0,2);

            provinciaList = new ArrayList<String>();

            ConnectionStr conStr = new ConnectionStr();
            connect = conStr.connectionclasss();        // Connect to database

            String query = "select rtrim(n_region) as N_Region from Gen_Regiones where c_pais='"+pa+"'";
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs!=null) {
                while (rs.next()) {
                    provinciaList.add(rs.getString("N_Region"));
                }
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            salir(e.getMessage());

        }
    }
    public void salir(String e){
        LinearLayout linear = findViewById(R.id.linear_layout);

        AlertDialog.Builder alerta=new AlertDialog.Builder(this);
        alerta.setMessage(e)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // finish();
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
    //valida cedula
    public boolean verificarcedula (String cedula){
        int total=0;
        int tamonoLonguitudCedula=10;
        int[]coeficientes={2,1,2,1,2,1,2,1,2};
        int numeroProvincias=24;
        int tercerDigito=6;
        if (cedula.matches("[0-9]*")&& cedula.length()==tamonoLonguitudCedula)
        {
            int provincia=Integer.parseInt(cedula.charAt(0)+""+cedula.charAt(1));
            int digitoTres=Integer.parseInt(cedula.charAt(2)+"");
            if ((provincia>0 && provincia<=numeroProvincias)
                    && digitoTres<tercerDigito)
            {
                int digitoVerificadorRecibido=Integer.parseInt(cedula.charAt(9)+"");
                for (int i=0;i<coeficientes.length;i++){
                    int valor=Integer.parseInt(coeficientes[i]+"")*Integer.parseInt(cedula.charAt(i)+"");
                    total=valor>=10? total +(valor-9):total+valor;

                }
                int digitoVerificadorObtenido=total>=10?
                        (total%10)!=0?
                                10-(total%10):
                                (total%10):total;
                return digitoVerificadorObtenido == digitoVerificadorRecibido;
            }
            return false;
        }
        return false;
    }
}
