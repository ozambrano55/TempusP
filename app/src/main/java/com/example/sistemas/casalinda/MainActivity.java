package com.example.sistemas.casalinda;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sistemas.casalinda.Utilidades.claseGlobal;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView edtFuncionario, edtPunto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    //recibirDatos();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        claseGlobal objEscritura=(claseGlobal)getApplicationContext();
        claseGlobal objLectura=(claseGlobal)getApplicationContext();

        //Bundle extras =getIntent().getExtras();
        //String d1=extras.getString("c_funcionario");
        //String d2=extras.getString("funcionario");
        //String d3=extras.getString("c_punto_venta");
        //String d4=extras.getString("punto");
        //Llenamos nombre de punto y funcionaio
        //edtFuncionario=(TextView) findViewById(R.id.edtFuncionario);
        //edtPunto=(TextView)findViewById(R.id.edtPunto) ;

        //edtFuncionario.setText(d2);
        //edtPunto.setText(d3);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //codigo nuevo
        View hView =navigationView.getHeaderView(0);
        TextView punto=(TextView)hView.findViewById(R.id.edtPunto);
        TextView funcionario=(TextView)hView.findViewById(R.id.edtFuncionario);
        punto.setText(objLectura.getPunto());
        funcionario.setText(objLectura.getFuncionario());
        //fin codigo nuevo
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);



    }

    /*public void recibirDatos(){
        Bundle extras= getIntent().getExtras();
        String d1= extras.getString("dato01");
        edtFuncionario=(TextView) findViewById(R.id.edtFuncionario);
        edtFuncionario.setText(d1);

    }
    */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alerta=new AlertDialog.Builder(this);
            alerta.setMessage("Desea Salir de la Aplicacion")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm =getSupportFragmentManager();

        if (id == R.id.nav_inicio) {
            // Handle the camera action
            fm.beginTransaction().replace(R.id.contenedor, new inicio()).commit();
        } else if (id == R.id.nav_cartera) {
            //fm.beginTransaction().replace(R.id.contenedor, new cartera()).commit();
            Intent intent= new Intent( MainActivity.this, CarteraActivity.class );
            startActivity(intent);
        } else if (id == R.id.nav_Pedidos) {
            //fm.beginTransaction().replace(R.id.contenedor, new Pedido()).commit();
            Intent intent= new Intent( MainActivity.this, PedidoActivity.class );
            startActivity(intent);
        } else if (id == R.id.nav_Productos) {
           // fm.beginTransaction().replace(R.id.contenedor, new Producto()).commit();
            Intent intent= new Intent( MainActivity.this, ProductoActivity.class );
            startActivity(intent);
        } else if (id == R.id.nav_salir) {
            super.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
