package com.example.sistemas.casalinda;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sistemas.casalinda.Utilidades.claseGlobal;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity {

    ArrayList<TipoId> tipoList;
    ArrayList<String>listaTipo;
    ArrayList<String> paisList;
    ArrayList<Provincia> provinciaList;
    ArrayList  <String>listaProvincias;
    ArrayList<Ciudad>ciudadList;
    ArrayList<String>listaCiudad;
    Connection connect;
    String country,city;
   // String cedula,nombre,apellido,telefono,correo,direccion;
    Button nuevo,guarda, editar, salir;

    private boolean isFirstTime=true;
    public String tp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cliente);

        salir=findViewById(R.id.btnSalir);
        nuevo = findViewById(R.id.btnNuevo);
        guarda=findViewById(R.id.btnGuardar);
        editar=findViewById(R.id.btnEditar);
        final Spinner tipo = findViewById(R.id.spTipo);
        final Spinner pais = findViewById(R.id.spPais);
        final Spinner provincia = findViewById(R.id.spProvincia);
        final Spinner ciudad = findViewById(R.id.spCiudad);


        EditText cedula = findViewById(R.id.tiedtCedula);
        TextView vcedula = findViewById(R.id.txtCedula);
        EditText nombre = findViewById(R.id.tiedtNombre);
        EditText apellido = findViewById(R.id.tiedtApellido);
        EditText telefono = findViewById(R.id.tiedtTelefono);
        EditText correo = findViewById(R.id.tiedtCorreo);
        EditText direccion = findViewById(R.id.tiedtDireccion);

        tipo.setEnabled(false);
        cedula.setEnabled(false);
        nombre.setEnabled(false);
        apellido.setEnabled(false);
        telefono.setEnabled(false);
        correo.setEnabled(false);
        direccion.setEnabled(false);
        pais.setEnabled(false);
        provincia.setEnabled(false);
        ciudad.setEnabled(false);
        editar.setEnabled(false);
        guarda.setEnabled(false);


/*
            tipo.setEnabled(true);
            cedula.setEnabled(false);
            nombre.setEnabled(false);
            apellido.setEnabled(false);
            telefono.setEnabled(false);
            correo.setEnabled(false);
            direccion.setEnabled(false);
            pais.setEnabled(false);
            provincia.setEnabled(false);
            ciudad.setEnabled(false);
*/
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo.setEnabled(true);
                cargaTipo();
                ArrayAdapter<CharSequence> adaptadorTipo = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaTipo);
                tipo.setAdapter(adaptadorTipo);
                nuevo.setEnabled(false);
                guarda.setEnabled(true);
            }
        });
        guarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ce,no,ap,di,te,co;
                ce=cedula.getText().toString();
                no=nombre.getText().toString();
                ap=apellido.getText().toString();
                di=direccion.getText().toString();
                te=telefono.getText().toString();
                co=correo.getText().toString();
              try {
                  switch (tp) {
                      case "CI":
                          if (vcedula.getText().equals("Cédula Correcta")) {
                              grabaCliente(ce,no,ap,tp,di,city,te,co);
                          } else {
                              salirp("Documento Identidad", "Digite Cédula válida", 1, 1);
                          }
                          break;
                      case "CE":
                          //grabaCliente();
                          break;
                      case "RUC":
                          //grabaCliente();
                          break;
                  }
              }catch (Exception e){salir(e.getMessage());}

            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salirp("Ficha Cliente","Desea Salir",2,1);
            }
        });

        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstTime)
                {isFirstTime=false;}
                else {
                    tp = tipoList.get(position).getC_tipo_identi();
                    activarCampos();
                    tipo.setEnabled(false);
                    cargaPais();
                    ArrayAdapter<CharSequence>adaptadorPais=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,paisList);
                    pais.setAdapter(adaptadorPais);
                }
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
                    c = cedula.getText().toString();
                    if (String.valueOf(verificarcedula(c)).equals("true")) {

                        vcedula.setText("Cédula Correcta");
                    } else {
                        vcedula.setText("Cédula Incorrecta");
                    }
                }
            });

        pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargaProvincia(parent.getItemAtPosition(position).toString());
                ArrayAdapter<CharSequence>adaptadorProvincia=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,listaProvincias);
                provincia.setAdapter(adaptadorProvincia);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargaCiudad(position);
                ArrayAdapter<CharSequence>adaptadorCiudad=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,listaCiudad);
                ciudad.setAdapter(adaptadorCiudad);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ciudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city=ciudadList.get(position).getC_ciudad();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void grabaCliente(String ce, String no, String ap, String t, String di, String c, String te, String co) {
        String ConnectionResult = "";
        claseGlobal objEscritura=(claseGlobal)getApplicationContext();
        claseGlobal objLectura=(claseGlobal)getApplicationContext();



        try {
            ConnectionStr conStr = new ConnectionStr();
            connect = conStr.connectionclasss();
            if (connect == null) {
                ConnectionResult = "Revisar tu conexion a internet!";
                Toast.makeText(getApplicationContext(), ConnectionResult, Toast.LENGTH_LONG).show();
            } else {
                CallableStatement call = connect.prepareCall("{call sp_insertCliente (?,?,?,?,?,?,?,?,?,?)}");
                call.setString(1,ce);//C_Cod_Cliente
                call.setString(2,no);//D_Nom_Cliente
                call.setString(3,ap);//D_Ape_Cliente
                call.setString(4,t);//C_Tipo_Identi
                call.setString(5,ce);//Num_Identi
                call.setString(6,di);//Direccion_Clien
                call.setString(7,c);//C_Ciudad
                call.setString(8,te);//Tel1
                call.setString(9,co);//Contacto_mail
                call.setString(10,objLectura.getC_funcionario());//C_Funcionario
                call.execute();

            }
        }catch (Exception e){salirp("Guarda Cliente",e.getMessage(),1,1);}

    }

    //Validar atras
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {




        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // Esto es lo que hace mi botón al pulsar ir a atrás

            //Toast.makeText(getApplicationContext(), "Voy hacia atrás!!",     Toast.LENGTH_SHORT).show();
            //onBackPressed();

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Quiere salir de la ficha cliente");
            builder.setTitle("Confirmación");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                            finish();
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


    private void activarCampos(){
    TextInputEditText cedula=findViewById(R.id.tiedtCedula);
    TextView vcedula=findViewById(R.id.txtCedula);
    TextInputEditText nombre=findViewById(R.id.tiedtNombre);
    TextInputEditText apellido =findViewById(R.id.tiedtApellido);
    TextInputEditText telefono=findViewById(R.id.tiedtTelefono);
    TextInputEditText correo =findViewById(R.id.tiedtCorreo);
    TextInputEditText direccion=findViewById(R.id.tiedtDireccion);

    Spinner pais=findViewById(R.id.spPais);
    Spinner provincia=findViewById(R.id.spProvincia);
    Spinner ciudad =findViewById(R.id.spCiudad);

    cedula.setEnabled(true);
    nombre.setEnabled(true);
    apellido.setEnabled(true);
    telefono.setEnabled(true);
    correo.setEnabled(true);
    direccion.setEnabled(true);
   // pais.setEnabled(true);
    provincia.setEnabled(true);
    ciudad.setEnabled(true);
}


    //CargaTipo

    public void cargaTipo() {
        try {


            tipoList = new ArrayList<TipoId>();

            ConnectionStr conStr = new ConnectionStr();
            connect = conStr.connectionclasss();        // Connect to database

            String query = "select rtrim (C_Tipo_Identi)C_Tipo_Identi, rtrim(D_Tipo_Identi)D_Tipo_Identi from Gen_Tipo_Identificacion";
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs!=null) {
                while (rs.next()) {
                    tipoList.add( new TipoId( rs.getString("C_Tipo_Identi"),rs.getString("D_Tipo_Identi")));
                }
            }
                obtenerListat();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            salir(e.getMessage());

        }
    }
    public void obtenerListat() {
        listaTipo= new ArrayList<String>();
        //listaPuntos.add("Seleccione");
        for (int i = 0; i < tipoList.size(); i++) {
            listaTipo.add(tipoList.get(i).getD_tipo_identi());
        }
    }
    public void cargaPais() {
        try {


            paisList = new ArrayList<String>();

            ConnectionStr conStr = new ConnectionStr();
            connect = conStr.connectionclasss();        // Connect to database

            String query = "select (rtrim(C_Pais)+'-'+ rtrim(N_Pais_En)) as Pais from Gen_Paises where c_pais='ec'";
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

    public void cargaProvincia( String pai) {
        try {
            String pa;
            pa=pai.substring(0,2);
           // Provincia provincia = null;

           provinciaList = new ArrayList<Provincia>();

            ConnectionStr conStr = new ConnectionStr();
            connect = conStr.connectionclasss();        // Connect to database

            String query = "select Rtrim(C_Region)C_Region, rtrim(N_Region)N_Region from Gen_Regiones where c_pais='"+pa+"'";
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs!=null) {
                while (rs.next()) {
                    provinciaList.add(new Provincia(rs.getString("C_Region"),rs.getString("N_Region")));

                }
            }
            obtenerListap();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void obtenerListap() {
        listaProvincias= new ArrayList<String>();
        //listaPuntos.add("Seleccione");
        for (int i = 0; i < provinciaList.size(); i++) {
            listaProvincias.add(provinciaList.get(i).getN_region());
        }
    }
    public void cargaCiudad( int i) {
        try {

            String pro=provinciaList.get(i).getC_region();
            Ciudad ciudad = null;
            ciudadList = new ArrayList<Ciudad>();

            ConnectionStr conStr = new ConnectionStr();
            connect = conStr.connectionclasss();        // Connect to database

            String query = "select  rtrim(C_Ciudad)C_Ciudad, rtrim(N_Ciudad)N_Ciudad from Gen_Ciudades  where C_Region='"+pro+"'";
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs!=null) {
                while (rs.next()) {
                    ciudadList.add(new Ciudad(rs.getString("C_Ciudad"),rs.getString("N_Ciudad")));

                }
            }
            obtenerListac();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void obtenerListac() {
        listaCiudad= new ArrayList<String>();
        //listaPuntos.add("Seleccione");
        for (int i = 0; i < ciudadList.size(); i++) {
            listaCiudad.add(ciudadList.get(i).getN_ciudad());
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
    public void salirp(String t,String m,int a, int b){
        LinearLayout linear = findViewById(R.id.linear_layout);

        AlertDialog.Builder alerta=new AlertDialog.Builder(this);
        alerta.setMessage(m)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       switch (a) {
                           case 1:
                               dialog.cancel();
                               break;
                           case 2:
                                finish();
                               break;


                       }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (b) {
                            case 1:
                                dialog.cancel();
                                break;
                            case 2:
                                finish();
                                break;


                        }
                    }
                });
        AlertDialog titulo=alerta.create();
        titulo.setTitle(t);
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
