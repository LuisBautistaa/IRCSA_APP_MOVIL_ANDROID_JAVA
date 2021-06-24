package com.example.ircsa2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Pantalla2 extends AppCompatActivity {

    EditText mEditTextNombre;
    EditText mEditTextCarac;
    EditText mEditTextCuida;
    Button mButtonCrearDatos;
    Button mButtonMostrarDatos;
    Button mButtonMenu;
    TextView ivSalir;
    TextView ivRegresar;
    int contador = 0;


    //TIPO DE LETRA
    private TextView texto1;
    private Typeface foxx;


    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);






        //se obtiene el acceso de todas las conexiones
        mFirestore = FirebaseFirestore.getInstance();


        mEditTextNombre = findViewById(R.id.editTextNombre);
        mEditTextCarac = findViewById(R.id.editTextCaract);
        mEditTextCuida = findViewById(R.id.editTextCuida);
        mButtonCrearDatos = findViewById(R.id.btnCrearDatos);
        mButtonMostrarDatos = findViewById(R.id.btnMostarDatos);
        mButtonMenu  = findViewById(R.id.button8);


        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pantalla2.this, Pantalla1.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });



        mButtonMostrarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pantalla2.this, catalagoPlantasActivity.class));
            }
        });


        mButtonCrearDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (Pantalla2.this);
                alerta.setMessage("Desea registrar la nueva planta")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                crearDatos();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog salir = alerta.create();
                salir.setTitle("REGISTRAR");
                salir.show();

            }
        });


        ///botones cerrar sesion, salir con boton y sin boton
        //Añadir el Listener y cerrar la aplicación:[SALIR]
        ivSalir = (TextView) findViewById(R.id.button7);
        ivSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (Pantalla2.this);
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
                startActivity(new Intent(Pantalla2.this, Pantalla1.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });

        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pantalla2.this, Pantalla1.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });


    }


    private void crearDatos() {
        //Se obtieene de lo que nos de el EDITtEXT que ingrese el usuario
        final String nombre = mEditTextNombre.getText().toString();
        final String caracteristicas = mEditTextCarac.getText().toString();
        final String cuidados = mEditTextCuida.getText().toString();

        //recibe como parametro un conjunto de objetos y/o atributos
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        //map.put("search", nombre.T);  para buscar en bd
        map.put("caracteristicas", caracteristicas);
        map.put("cuidados", cuidados);

        //crear datos en la bd actualizar con spinner (podría funcionar)
        //  mFirestore.collection("Plantas").document("1").set(map);


        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Se debe ingresar el nombre de la planta", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(caracteristicas)) {
            Toast.makeText(this, "Se debe ingresar las caracteristicas de la planta", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(cuidados)) {
            Toast.makeText(this, "Se debe ingresar los cuidados de la planta", Toast.LENGTH_LONG).show();
            return;
        }




        //SUCCESLISTENER PARA SABER SI EL DATO SE HA REGISTRADO CORRECTAMENTE
        mFirestore.collection("Plantas").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(Pantalla2.this, "La planta ha sido registrada exitosamente.", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Pantalla2.this, "La planta no se pudo registrar, intente de nuevo.", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void limpiarCampos(){

        mEditTextNombre.setText("");
        mEditTextCuida.setText("");
        mEditTextCarac.setText("");

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