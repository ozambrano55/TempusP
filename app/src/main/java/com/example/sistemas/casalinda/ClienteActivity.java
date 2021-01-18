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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sistemas.casalinda.Utilidades.claseGlobal;
import com.example.sistemas.casalinda.entidades.Ciudad;
import com.example.sistemas.casalinda.entidades.Provincia;
import com.example.sistemas.casalinda.entidades.TipoId;


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
    String d0, d1,city;
   // String cedula,nombre,apellido,telefono,correo,direccion;
    Button nuevo,cancelar,guarda, editar, salir;
    EditText cedula,nombre,apellido,telefono,correo,direccion;
    private boolean isFirstTime=true;
    public String tp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cliente);



        salir=findViewById(R.id.btnSalir);
        nuevo = findViewById(R.id.btnNuevo);
        cancelar=findViewById(R.id.btnCancelar);
        guarda=findViewById(R.id.btnGuardar);
        editar=findViewById(R.id.btnEditar);
        final Spinner tipo = findViewById(R.id.spTipo);
        final Spinner pais = findViewById(R.id.spPais);
        final Spinner provincia = findViewById(R.id.spProvincia);
        final Spinner ciudad = findViewById(R.id.spCiudad);
        ImageButton ok=findViewById(R.id.imbOk);


         cedula = findViewById(R.id.edtCedula);
        TextView vcedula = findViewById(R.id.txtCedula);
         nombre = findViewById(R.id.tiedtNombre);
         apellido = findViewById(R.id.tiedtApellido);
         telefono = findViewById(R.id.tiedtTelefono);
         correo = findViewById(R.id.tiedtCorreo);
         direccion = findViewById(R.id.tiedtDireccion);


        recibirdatos();
        switch (d0){
            case "0":
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
                cancelar.setEnabled(false);
                ok.setEnabled(false);
                tipo.requestFocus();
                desactivaTexto();
                break;
            case "1":


                cedula.setText(d1);
                tipo.setEnabled(false);
                ok.setEnabled(false);
                pais.setEnabled(false);
                desactivaTextot();

                break;
        }




        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activarCampos();
                //tipo.setEnabled(false);
                cargaPais();
                ArrayAdapter<CharSequence>adaptadorPais=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,paisList);
                pais.setAdapter(adaptadorPais);
                tipo.setEnabled(false);
                ok.setEnabled(false);
                cedula.requestFocus();
            }
        });
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cargaTipo();
                    ArrayAdapter<CharSequence> adaptadorTipo = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaTipo);
                    tipo.setAdapter(adaptadorTipo);
                    tipo.setEnabled(true);
                    //ok.setEnabled(true);
                    nuevo.setEnabled(false);
                    guarda.setEnabled(true);
                    cancelar.setEnabled(true);
                    tipo.requestFocus();
                 activarCampos();
                }catch (Exception e){salirp("Nuevo",e.getMessage(),1);}
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               desactivaTexto();
               nuevo.setEnabled(true);
               cancelar.setEnabled(false );
               guarda.setEnabled(false);
               tipo.requestFocus();

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
              try { if (tp.isEmpty())
                        {
                            salirp("Tipo Identificacion","Escoja tipo de identificación",1);
                        }else {
                  if (cedula.getText().toString().isEmpty()) {
                      salirp("Campo Cédula", "Digite una Cedula", 1);
                  } else {
                      if (nombre.getText().toString().isEmpty()) {
                          salirp("Campo Nombre", "Digite un Nombre", 1);
                      } else {
                          if (apellido.getText().toString().isEmpty()) {
                              salirp("Campo Apellido", "Digite un Apellido", 1);
                          } else if (direccion.getText().toString().isEmpty()) {
                              salirp("Campo Direccion", "Digite una direccion", 1);
                          } else {

                              switch (tp) {
                                  case "CI":
                                      if (ValidarCedula(cedula.getText().toString())) {
                                          grabaCliente(ce, no, ap, tp, di, city, te, co);
                                          desactivaTexto();
                                      } else {
                                          salirp("Documento Identidad", "Digite Cédula válida", 1);

                                      }
                                      break;
                                  case "CE":
                                      grabaCliente(ce, no, ap, tp, di, city, te, co);
                                      desactivaTexto();
                                      break;
                                  case "RUC":
                                      if (ce.length() == 13) {
                                          grabaCliente(ce, no, ap, tp, di, city, te, co);
                                          desactivaTexto();
                                      } else {
                                          salirp("Documento Identidad", "Digite RUC válido con 13 dígitos", 1);
                                      }
                                      break;
                              }
                          }
                      }
                  }
              }
              }catch (Exception e){salir(e.getMessage());}


            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir("Desea Salir");
            }
        });

tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ok.setEnabled(true);
        tp = tipoList.get(position).getC_tipo_identi();
        desactivaTextot();
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

                   if (tp.equals("CI")) {
                       try{
                       String c;
                       c = cedula.getText().toString();
                        if (c.length()==10) {
                            if (String.valueOf(ValidarCedula(c)).equals("true")) {

                                vcedula.setText("Cédula Correcta");
                            } else {
                                vcedula.setText("Cédula Incorrecta");
                            }
                        }else {
                            vcedula.setText("");
                        }
                        }catch (Exception e){
                           //salir(e.getMessage());
                       }
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

    //Recibir datos
    public void recibirdatos(){
        Bundle extras=getIntent().getExtras();
        d0=extras.getString("d0");
        d1=extras.getString("d1");
    }

    private void grabaCliente(String ce, String no, String ap, String t, String di, String c, String te, String co) {
        String ConnectionResult = "";
       // claseGlobal objEscritura=(claseGlobal)getApplicationContext();
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

                salirp("Registro Cliente","Cliente "+no+" "+ap+" Guardado satisfactoriamente",1);


            }
        }catch (Exception e){salirp("Guarda Cliente",e.getMessage(),1);}

    }

    private void desactivaTexto( ) {
        EditText cedula=findViewById(R.id.edtCedula);
        TextView vcedula=findViewById(R.id.txtCedula);
        EditText nombre=findViewById(R.id.tiedtNombre);
        EditText apellido =findViewById(R.id.tiedtApellido);
        EditText telefono=findViewById(R.id.tiedtTelefono);
        EditText correo =findViewById(R.id.tiedtCorreo);
        EditText direccion=findViewById(R.id.tiedtDireccion);

        Spinner pais=findViewById(R.id.spPais);
        Spinner provincia=findViewById(R.id.spProvincia);
        Spinner ciudad =findViewById(R.id.spCiudad);
        Spinner tipo=findViewById(R.id.spTipo);

        guarda.setEnabled(false);
        nuevo.setEnabled(true);
        tipo.setEnabled(false);
        cedula.setEnabled(false);
        nombre.setEnabled(false);
        apellido.setEnabled(false);
        telefono.setEnabled(false);
        correo.setEnabled(false);
        direccion.setEnabled(false);
        // pais.setEnabled(true);
        provincia.setEnabled(false);
        ciudad.setEnabled(false);

        cedula.setText("");
        vcedula.setText("");
        nombre.setText("");
        apellido.setText("");
        telefono.setText("");
        correo.setText("");
        direccion.setText("");


    }
    private void desactivaTextot( ) {
        EditText cedula=findViewById(R.id.edtCedula);
        TextView vcedula=findViewById(R.id.txtCedula);
        EditText nombre=findViewById(R.id.tiedtNombre);
        EditText apellido =findViewById(R.id.tiedtApellido);
        EditText telefono=findViewById(R.id.tiedtTelefono);
        EditText correo =findViewById(R.id.tiedtCorreo);
        EditText direccion=findViewById(R.id.tiedtDireccion);

        Spinner pais=findViewById(R.id.spPais);
        Spinner provincia=findViewById(R.id.spProvincia);
        Spinner ciudad =findViewById(R.id.spCiudad);
        Spinner tipo=findViewById(R.id.spTipo);

        guarda.setEnabled(false);
        nuevo.setEnabled(true);
        //tipo.setEnabled(false);
        cedula.setEnabled(false);
        nombre.setEnabled(false);
        apellido.setEnabled(false);
        telefono.setEnabled(false);
        correo.setEnabled(false);
        direccion.setEnabled(false);
        // pais.setEnabled(true);
        provincia.setEnabled(false);
        ciudad.setEnabled(false);

        if (d0=="0"){
            cedula.setText("");
            vcedula.setText("");
            nombre.setText("");
            apellido.setText("");
            telefono.setText("");
            correo.setText("");
            direccion.setText("");
        }else if(d0=="1"){
           // cedula.setText("");
            vcedula.setText("");
            nombre.setText("");
            apellido.setText("");
            telefono.setText("");
            correo.setText("");
            direccion.setText("");
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


    Spinner tipo=findViewById(R.id.spTipo);
    Spinner pais=findViewById(R.id.spPais);
    Spinner provincia=findViewById(R.id.spProvincia);
    Spinner ciudad =findViewById(R.id.spCiudad);

    tipo.setEnabled(true);
    cedula.setEnabled(true);
    nombre.setEnabled(true);
    apellido.setEnabled(true);
    telefono.setEnabled(true);
    correo.setEnabled(true);
    direccion.setEnabled(true);
   // pais.setEnabled(true);
    provincia.setEnabled(true);
    ciudad.setEnabled(true);
    guarda.setEnabled(true);
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
                        //dialog.cancel();
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
    public void salirp(String t,String m,int a){
        LinearLayout linear = findViewById(R.id.linear_layout);

        AlertDialog.Builder alerta=new AlertDialog.Builder(this);
        alerta.setMessage(m)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
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
             ;
        AlertDialog titulo=alerta.create();
        titulo.setTitle(t);
        titulo.show();
        //finish();
        //super.onBackPressed();

    }
 /*   //valida cedula
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
    //Función booleana para validar cédula
    */
  /*  public boolean ValidaCedula(String cedula) {

        //Declaración de variables a usar
        boolean valida=false;
        byte primeros2, tercerD, Dverificador, multiplicar, suma=0, aux;
        byte []digitos=new byte[9];

        //Primer try comprueba la longitud de cadena que no sea diferente de 10
        try {
            if(cedula.length()!=10) {
                //DatosIncorrectos("La c\u00e9dula debe contener 10 d\u00edgitos sin espacios<--\n");}

                //Segundo try comprueba que todos los dígitos sean numéricos
                try {

                    //Transformación de cada carácter a un byte
                    Dverificador = Byte.parseByte("" + cedula.charAt(9));
                    primeros2 = Byte.parseByte(cedula.substring(0, 2));
                    tercerD = Byte.parseByte("" + cedula.charAt(2));
                    for (byte i = 0; i < 9; i++) {
                        digitos[i] = Byte.parseByte("" + cedula.charAt(i));
                    }
                    //Verificar segundo dígito
                    if (primeros2 >= 1 & primeros2 <= 24) {
                        if (tercerD <= 6) {
                            //Módulo 10 multiplicar digitos impares por 2
                            for (byte i = 0; i < 9; i = (byte) (i + 2)) {
                                multiplicar = (byte) (digitos[i] * 2);
                                if (multiplicar > 9) {
                                    multiplicar = (byte) (multiplicar - 9);
                                }
                                suma = (byte) (suma + multiplicar);
                            }
                            //Módulo 10 multiplicar digitos pares por 1
                            for (byte i = 1; i < 9; i = (byte) (i + 2)) {
                                multiplicar = (byte) (digitos[i] * 1);
                                suma = (byte) (suma + multiplicar);
                            }
                            //Obtener la decena superior de la suma
                            aux = suma;
                            while (aux % 10 != 0) {
                                aux = (byte) (aux + 1);
                            }
                            suma = (byte) (aux - suma);
                            //Comprobar la suma con dígito verificador (Último Dígito)
                            if (suma != Dverificador) {
                                // throw new DatosIncorrectos("Revise nuevamente los d\u00edgitos de su c\u00e9dula<--\n");
                            }
                            valida = true;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("La c\u00e9dula debe contener solo d\u00edgitos num\u00e9ricos<--\n");
                }
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return valida;
    }
*/
    public boolean ValidarCedula(String cedula){
        int c, suma=0, acum, resta=0;

        for (int i = 0; i < cedula.length()-1; i++) {
            c=Integer.parseInt(cedula.charAt(i)+"");
            if(i%2==0){
                c=c*2;
                if(c>9){
                    c=c-9;
                }
            }

            suma=suma+c;
        }

        if (suma%10 !=0) {
            acum=((suma/10)+1)*10;
            resta=acum-suma;
        }

        int ultimo=Integer.parseInt(cedula.charAt(9)+"");

        return ultimo == resta;
    }

}
