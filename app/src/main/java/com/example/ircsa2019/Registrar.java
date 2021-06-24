package com.example.ircsa2019;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Registrar extends AppCompatActivity {

    //se declara el textview
    TextView ivSalir;
    TextView ivRegresar;

    //TIPO DE LETRA
    private TextView texto1;
    private Typeface foxx;

    //DECLARAMOS OBJETO FIREBASEAUTH
    private FirebaseAuth firebaseAuth;

    //defining view objects
    private EditText TextEmail1;
    private EditText TextPassword1;
    private Button btnRegistrar1;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        ///para el tipo de letra
        String fuente1 = "fuentes/foxx.otf";
        this.foxx = Typeface.createFromAsset(getAssets(), fuente1);
        texto1 = (TextView) findViewById(R.id.TVtexto1);
        texto1.setTypeface(foxx);

        //Inicializamos objeto
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        btnRegistrar1 = (Button) findViewById(R.id.botonRegistrar1);
        TextEmail1 = (EditText) findViewById(R.id.TxtEmail1);
        TextPassword1 = (EditText) findViewById(R.id.TxtPassword1);
        //BARRA DE PROGRESO
        progressDialog = new ProgressDialog(this);

        //asociamos un oyente al evento clic del botón

        //Añadir el Listener y cerrar la aplicación [SALIR CON BOTON]
        ivSalir = (TextView) findViewById(R.id.botonSalir);
        ivSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.exit(0);
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        //Boton cancelar
        ivRegresar = (TextView) findViewById(R.id.botonCancelR);
        ivRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (Registrar.this);
                alerta.setMessage("Desea cancelar.")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Registrar.this, MainActivity.class));
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



        btnRegistrar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder (Registrar.this);
                alerta.setMessage("Desea Registrar el Usuario")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                registrarUsuario();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                startActivity(new Intent(Registrar.this, MainActivity.class));
                            }
                        });
                AlertDialog salir = alerta.create();
                salir.setTitle("REGISTRAR");
                salir.show();
            }
        });
    }

    private void registrarUsuario() {

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail1.getText().toString().trim();
        String password = TextPassword1.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //registramos un nuevo usuario
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            Toast.makeText(Registrar.this, "Se ha registrado el usuario con el email: " + TextEmail1.getText(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Registrar.this, MainActivity.class));
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(Registrar.this, "Ya existe el usuario.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Registrar.this, "No se pudo registrar el usuario, verifique los datos.", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });

    }

}