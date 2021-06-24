package com.example.ircsa2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DatosPersonales extends AppCompatActivity {

    Spinner nombre,persona,apePaterno,apeMaterno,calle,colonia,municipio,estado;
    TextView rfc,curp,fech,num,cod;
    Button alta,consultar;
    int contador = 0;
    Button mButtonMenu;
    TextView ivSalir;
    TextView ivRegresar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        nombre= (Spinner)findViewById(R.id.spNombre);
        persona= (Spinner)findViewById(R.id.spTIpPers);
        apePaterno= (Spinner)findViewById(R.id.spApePat);
        apeMaterno= (Spinner)findViewById(R.id.spApeMat);
        calle= (Spinner)findViewById(R.id.spCalle);
        colonia= (Spinner)findViewById(R.id.spColonia);
        municipio= (Spinner)findViewById(R.id.spMunicipio);
        estado= (Spinner)findViewById(R.id.spEstado);
        rfc=(TextView)findViewById(R.id.txtRfc);
        curp=(TextView)findViewById(R.id.txtCurp);
        fech=(TextView)findViewById(R.id.txtFecNac);
        num=(TextView)findViewById(R.id.txtNum);
        cod=(TextView)findViewById(R.id.txtCodPos);
        alta=(Button)findViewById(R.id.btnAlta);
        consultar=(Button)findViewById(R.id.btnConsultar);

        mButtonMenu  = findViewById(R.id.button8);

        alta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alerta = new AlertDialog.Builder (DatosPersonales.this);
                alerta.setMessage("Desea dar de alta")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                insertar();
                                limpiarCampos();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                AlertDialog salir = alerta.create();
                salir.setTitle("ALTA");
                salir.show();


                //https://www.youtube.com/watch?v=gzLMWz91YrQ
            }
        });

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent=new Intent(DatosPersonales.this,listaDatos.class);
              startActivity(new Intent(DatosPersonales.this,listaDatos.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
              finish();
                //startActivity(intent);

            }
        });


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.nombre,android.R.layout.simple_spinner_item);
        nombre.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.personas,android.R.layout.simple_spinner_item);
        persona.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.Apellidos,android.R.layout.simple_spinner_item);
        apePaterno.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this,R.array.Apellidos,android.R.layout.simple_spinner_item);
        apeMaterno.setAdapter(adapter3);

        ArrayAdapter<CharSequence> adapter4=ArrayAdapter.createFromResource(this,R.array.calles,android.R.layout.simple_spinner_item);
        colonia.setAdapter(adapter4);

        ArrayAdapter<CharSequence> adapter5=ArrayAdapter.createFromResource(this,R.array.colonias,android.R.layout.simple_spinner_item);
        calle.setAdapter(adapter5);

        ArrayAdapter<CharSequence> adapter6=ArrayAdapter.createFromResource(this,R.array.municipios,android.R.layout.simple_spinner_item);
        municipio.setAdapter(adapter6);

        ArrayAdapter<CharSequence> adapter7=ArrayAdapter.createFromResource(this,R.array.estados,android.R.layout.simple_spinner_item);
        estado.setAdapter(adapter7);



        ///botones cerrar sesion, salir con boton y sin boton
        //Añadir el Listener y cerrar la aplicación:[SALIR]
        ivSalir = (TextView) findViewById(R.id.button7);
        ivSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (DatosPersonales.this);
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
                startActivity(new Intent(DatosPersonales.this, Pantalla1.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });

        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DatosPersonales.this, Pantalla1.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });




    }

    public void insertar(){
        String nom=nombre.getSelectedItem().toString();//spinner
        String tipPers=persona.getSelectedItem().toString();
        String pat=apePaterno.getSelectedItem().toString();
        String mat=apeMaterno.getSelectedItem().toString();
        String rf=rfc.getText().toString();
        String cur=curp.getText().toString();
        String nac=fech.getText().toString();
        String call=calle.getSelectedItem().toString();
        String col=colonia.getSelectedItem().toString();
        String mun=municipio.getSelectedItem().toString();
        String est=estado.getSelectedItem().toString();
        String nume=num.getText().toString();
        String codi=cod.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("Persona", tipPers);
        user.put("Nombre", nom);
        user.put("ApePat", pat);
        user.put("ApeMat", mat);
        user.put("Rfc", rf);
        user.put("Curp", cur);
        user.put("FecNac", nac);
        user.put("Calle", call);
        user.put("Colonia", col);
        user.put("Municipio", mun);
        user.put("Estado", est);
        user.put("Numero", nume);
        user.put("Cod", codi);

// Add a new document with a generated ID
        db.collection("DatPers")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DatPers", "Agregado con Exito con ID: " + documentReference.getId());
                        Toast.makeText(DatosPersonales.this,"Agregado con Exito",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DapPers", "Error agregando el registro", e);
                        Toast.makeText(DatosPersonales.this,"Fallo al Registrar",Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void limpiarCampos(){
        persona.setId(0);
        nombre.setId(0);
        apePaterno.setId(0);
        apeMaterno.setId(0);
        calle.setId(0);
        colonia.setId(0);
        municipio.setId(0);
        estado.setId(0);
        rfc.setText("");
        curp.setText("");
        fech.setText("");
        num.setText("");
        cod.setText("");

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
