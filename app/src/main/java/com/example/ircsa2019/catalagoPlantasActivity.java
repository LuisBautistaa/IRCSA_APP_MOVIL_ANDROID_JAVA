package com.example.ircsa2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class catalagoPlantasActivity extends AppCompatActivity {


     RecyclerView recyclerViewPlantas;
     plantasAdapter mAdapter;
     FirebaseFirestore mFirestore;
     TextView ivRegresar;
    TextView ivSalir;
    Button mButtonMenu;
    int contador = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalago_plantas);


        recyclerViewPlantas = findViewById(R.id.recyclerPlantas);
        recyclerViewPlantas.setLayoutManager(new LinearLayoutManager(this));
        mFirestore = FirebaseFirestore.getInstance();


        Query  query = mFirestore.collection("Plantas");


        FirestoreRecyclerOptions<Plantas> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Plantas>()
        .setQuery(query, Plantas.class).build();

        mAdapter = new plantasAdapter(firestoreRecyclerOptions, this);
        mAdapter.notifyDataSetChanged();
        recyclerViewPlantas.setAdapter(mAdapter);
        mButtonMenu  = findViewById(R.id.button8);





        //Añadir el Listener y cerrar la aplicación [CERRAR SESIÓN]
        ivRegresar = (TextView) findViewById(R.id.button6);
        ivRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(catalagoPlantasActivity.this, Pantalla2.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });



        ///botones cerrar sesion, salir con boton y sin boton
        //Añadir el Listener y cerrar la aplicación:[SALIR]
        ivSalir = (TextView) findViewById(R.id.button7);
        ivSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (catalagoPlantasActivity.this);
                alerta.setMessage("Desea salir de la aplicación")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                finishAffinity();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog salir = alerta.create();
                salir.setTitle("SALIR");
                salir.show();
            }
        });

        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(catalagoPlantasActivity.this, Pantalla1.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //se llama cuando presionamos el boton de buscar
                searchView(s); // funcion del llamado con el string enterado en searchview sea un parametro
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {

                //cuando solo ponemos una sola letra
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void SearchData(String s){

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //maneja otros clicks de menú aquí
        if(item.getItemId() == R.id.action_settings){
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

     */

    //para el boton de salir con las teclas DEL TELEFONO
    @Override
    public void onBackPressed() {
        if (contador == 0) {
            Toast.makeText(getApplicationContext(), "Presione dos veces para salir de la aplicación", Toast.LENGTH_SHORT).show();
            contador++;


        } else {
            super.onBackPressed();
            finishAffinity();
        }
        new CountDownTimer(1000, 800) {

            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                contador = 0;
            }
        }.start();
    }



}