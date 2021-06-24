package com.example.ircsa2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class listaDatos extends AppCompatActivity {

    Button regresar,salir;

    RecyclerView recyclerDatos;
    adaptador mAdapter;
    FirebaseFirestore mFirestore;
    int contador = 0;
    Button mButtonMenu;
    TextView ivSalir;
    TextView ivRegresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_datos);



        recyclerDatos=(RecyclerView)findViewById(R.id.listaRecycler);

        //Toast.makeText(listaDatos.this,"entro al general",Toast.LENGTH_SHORT).show();

        recyclerDatos.setLayoutManager(new LinearLayoutManager(this));
        mFirestore = FirebaseFirestore.getInstance();
        mButtonMenu  = findViewById(R.id.button8);


        Query query = mFirestore.collection("DatPers");

        FirestoreRecyclerOptions<datosPers> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<datosPers>()
                .setQuery(query,datosPers.class).build();
        mAdapter = new adaptador(firestoreRecyclerOptions, this);
        mAdapter.notifyDataSetChanged();
        recyclerDatos.setAdapter(mAdapter);





        ///botones cerrar sesion, salir con boton y sin boton
        //Añadir el Listener y cerrar la aplicación:[SALIR]
        ivSalir = (TextView) findViewById(R.id.button7);
        ivSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (listaDatos.this);
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

        //Añadir el Listener y cerrar la aplicación [REGRESAR SESIÓN]
        ivRegresar = (TextView) findViewById(R.id.button6);
        ivRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(listaDatos.this, DatosPersonales.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });

        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(listaDatos.this, Pantalla1.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        //Toast.makeText(listaDatos.this,"entro al Start",Toast.LENGTH_SHORT).show();
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        //Toast.makeText(listaDatos.this,"entro al Stop",Toast.LENGTH_SHORT).show();
        super.onStop();
        mAdapter.stopListening();
    }



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
