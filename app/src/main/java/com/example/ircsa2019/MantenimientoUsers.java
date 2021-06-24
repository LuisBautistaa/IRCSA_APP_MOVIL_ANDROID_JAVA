package com.example.ircsa2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;




public class MantenimientoUsers extends AppCompatActivity {

    private Spinner Nomuser;
    Button most;
    TextView TextEmail1;
    TextView TextPassword1;
    Button btnAltas;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_users);

        btnAltas = (Button) findViewById(R.id.btnAltas);
        TextEmail1 = (EditText) findViewById(R.id.TxtEmail1);
        TextPassword1 = (EditText) findViewById(R.id.TxtPassword1);

        Nomuser = (Spinner) findViewById(R.id.spin);
        most=(Button)findViewById(R.id.MOSTRAR);

        String[] opciones = {"Luis", "Humberto", "Angel","Javier"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        Nomuser.setAdapter(adapter);

        btnAltas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder (MantenimientoUsers.this);
                alerta.setMessage("Desea dar de alta el Usuario")
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
                                startActivity(new Intent(MantenimientoUsers.this, Pantalla1.class));
                            }
                        });
                AlertDialog salir = alerta.create();
                salir.setTitle("ALTA");
                salir.show();
            }
        });

        }
        public void MOSTRAR(){
            if (user != null) {
                // Name, email address, and profile photo Url
                String name = user.getDisplayName();
                String email = user.getEmail();
                Uri photoUrl = user.getPhotoUrl();

                // Check if user's email is verified
                boolean emailVerified = user.isEmailVerified();

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getIdToken() instead.
                String uid = user.getUid();

            }

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
                            Toast.makeText(MantenimientoUsers.this, "Se ha registrado el usuario con el email: " + TextEmail1.getText(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MantenimientoUsers.this, MainActivity.class));
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(MantenimientoUsers.this, "Ya existe el usuario.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MantenimientoUsers.this, "No se pudo registrar el usuario, verifique los datos.", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    }

