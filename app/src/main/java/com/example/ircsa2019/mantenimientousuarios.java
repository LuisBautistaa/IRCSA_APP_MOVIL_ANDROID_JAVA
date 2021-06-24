package com.example.ircsa2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class mantenimientousuarios extends AppCompatActivity {

    private Spinner Nomuser;
    EditText us;
    EditText con;
    EditText fini;
    EditText ffin;
    Button alt,consul;
    Button sal;
    TextView ivSalir;
    TextView ivRegresar;



    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimientousuarios);

        Nomuser = (Spinner) findViewById(R.id.spinner);

        String[] opciones = {"Luis", "Humberto", "Angel","Javier"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        Nomuser.setAdapter(adapter);

        us = (EditText) findViewById(R.id.usuario);
        con = (EditText) findViewById(R.id.contrasena);
        fini = (EditText) findViewById(R.id.feci);
        ffin = (EditText) findViewById(R.id.fecf);
        alt=(Button)findViewById(R.id.alta);



        alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alta();
            }
        });

        ivSalir = (TextView) findViewById(R.id.salir);
        ivSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (mantenimientousuarios.this);
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


        ivRegresar = (TextView) findViewById(R.id.regresar);
        ivRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (mantenimientousuarios.this);
                alerta.setMessage("¿Desea Regresar?.")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(mantenimientousuarios.this, Pantalla1.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog salir = alerta.create();
                salir.setTitle("REGRESAR");
                salir.show();

            }
        });


    }
    public void alta(){
        String use=us.getText().toString();
        String contra=con.getText().toString();
        String feci=fini.getText().toString();
        String fecf=ffin.getText().toString();

        Map<String, Object>  user= new HashMap<>();
        user.put("login",use );
        user.put("password", contra);
        user.put("FechaInicio", feci);
        user.put("FechaFinal", fecf);

        db.collection("datousuarios")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("datousuarios", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(mantenimientousuarios.this, "ha dado de alta", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("datousuarios", "Error adding document", e);
                        Toast.makeText(mantenimientousuarios.this, "error al dar  de alta", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
