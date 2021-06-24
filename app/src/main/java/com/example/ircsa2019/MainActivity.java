///CTRL+ALT+L SIRVE PARA ORDENAR EL CODIGO

package com.example.ircsa2019;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //se declara el textview
    TextView ivSalir;

    //TIPO DE LETRA
    private TextView texto1;
    private Typeface foxx;

    //defining view objects
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar, btnLogin;
    private ProgressDialog progressDialog;


    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    //para el boton de salir TECLAS DEL CEL
    int contador = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///para el tipo de letra
        String fuente1 = "fuentes/foxx.otf";
        this.foxx = Typeface.createFromAsset(getAssets(), fuente1);

        texto1 = (TextView) findViewById(R.id.TVtexto1);
        texto1.setTypeface(foxx);



        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText) findViewById(R.id.TxtPassword);
        btnLogin = (Button) findViewById(R.id.botonLogin);
        btnRegistrar = (Button) findViewById(R.id.botonRegistrar);


        progressDialog = new ProgressDialog(this);

        //asociamos un oyente al evento clic del botón

        btnLogin.setOnClickListener(this);


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

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registrar.class);
                startActivity(intent);
            }
        });
    }

    ///METODO LOGIN USER
    private void loguearUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();



        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            int pos = email.indexOf("@");
                            String user = email.substring(0, pos);
                            Toast.makeText(MainActivity.this, "Bienvenido: " + TextEmail.getText(), Toast.LENGTH_LONG).show();

                            Intent intencion = new Intent(getApplication(), Pantalla1.class);
                            startActivity(intencion);

                            /*
                            intencion.putExtra(WellcomeActivity.user, user);

                            */

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(MainActivity.this, "Este usuario ya existe.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No se pudo realizar la acción, verifique los datos.", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.botonRegistrar:
                //Invocamos al método:
                btnRegistrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, Registrar.class);
                        startActivity(intent);
                    }
                });
                break;

            case R.id.botonLogin:
                loguearUsuario();
                break;
        }


    }


    //para el boton de salir con las teclas DEL TELEFONO
    @Override
    public void onBackPressed() {
        if (contador == 0) {
            Toast.makeText(getApplicationContext(), "Presione de nuevo para salir de la aplicación", Toast.LENGTH_SHORT).show();
            contador++;
        } else {
            super.onBackPressed();
        }
        new CountDownTimer(3000, 1000) {

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