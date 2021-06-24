package com.example.ircsa2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditarPlantasActivity extends AppCompatActivity {

     private String plantaId;
     private FirebaseFirestore mFirestore;

    //TIPO DE LETRA
    private TextView texto1;
    private Typeface foxx;

    EditText mEditTextNombre;
    EditText mEditTextCarac;
    EditText mEditTextCuida;
    Button mButtonActualizar;
    TextView ivRegresar;
    TextView ivSalir;
    int contador = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_plantas);

        ///para el tipo de letra
        String fuente1 = "fuentes/foxx.otf";
        this.foxx = Typeface.createFromAsset(getAssets(), fuente1);

        texto1 = (TextView) findViewById(R.id.TVtexto1);
        texto1.setTypeface(foxx);


        plantaId = getIntent().getStringExtra("plantaId");
        mFirestore = FirebaseFirestore.getInstance();

        ObtenerDatos();

        mEditTextNombre = findViewById(R.id.editTextNombre);
        mEditTextCarac = findViewById(R.id.editTextCaract);
        mEditTextCuida = findViewById(R.id.editTextCuida);
        mButtonActualizar = findViewById(R.id.btnActualizar);



        //CANCELAR ACTUALIZACION DE DATOS
        ivRegresar = (TextView) findViewById(R.id.button6);
        ivRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (EditarPlantasActivity.this);
                alerta.setMessage("Desea cancelar los cambios realizados.")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(EditarPlantasActivity.this, catalagoPlantasActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog salir = alerta.create();
                salir.setTitle("CANCELAR");
                salir.show();

            }
        });


        ///botones cerrar sesion, salir con boton y sin boton
        //Añadir el Listener y cerrar la aplicación:[SALIR]
        ivSalir = (TextView) findViewById(R.id.button7);
        ivSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (EditarPlantasActivity.this);
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


        mButtonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder (EditarPlantasActivity.this);
                alerta.setMessage("Desea guardar los cambios realizados")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActualizarDatos();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                startActivity(new Intent(EditarPlantasActivity.this, catalagoPlantasActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                finishAfterTransition();
                            }
                        });
                AlertDialog salir = alerta.create();
                salir.setTitle("ACTUALIZAR");
                salir.show();


            }
        });


    }




    ///MOSTRAR DATOS EN EL TEXT VIEW CON LOS DATOS QUE ESTAN EN LA BD
    private void ObtenerDatos(){
        mFirestore.collection("Plantas").document(plantaId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String nombre = documentSnapshot.getString("nombre");
                    String caracteristicas = documentSnapshot.getString("caracteristicas");
                    String cuidados = documentSnapshot.getString("cuidados");


                    mEditTextNombre.setText(nombre);
                    mEditTextCarac.setText(caracteristicas);
                    mEditTextCuida.setText(cuidados);
                }
            }
        });
    }



    ///ACTUALIZAR
    private void ActualizarDatos(){

        String nombre = mEditTextNombre.getText().toString();
        String caracteristicas = mEditTextCarac.getText().toString();
        String cuidados = mEditTextCuida.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("caracteristicas", caracteristicas);
        map.put("cuidados", cuidados);


      //  mFirestore.collection("Plantas").document(plantaId).update(map);



        mFirestore.collection("Plantas").document(plantaId).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditarPlantasActivity.this, "La planta ha sido actualizada exitosamente.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditarPlantasActivity.this, catalagoPlantasActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditarPlantasActivity.this, "La planta no se pudo actualizar, intente de nuevo.", Toast.LENGTH_SHORT).show();
            }
        });

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