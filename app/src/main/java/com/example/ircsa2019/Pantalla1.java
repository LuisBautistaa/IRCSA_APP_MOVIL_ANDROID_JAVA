package com.example.ircsa2019;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Pantalla1 extends AppCompatActivity {
    //se declara el textview
    TextView ivSalir;
    TextView ivRegresar;
    Button a, b, c, d, e;
    //tipo de letra
    private TextView texto1;
    private Typeface foxx;

    //para el boton de salir
    int contador = 0;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla1);

        //tipo de letra
        String fuente1 = "fuentes/foxx.otf";
        this.foxx = Typeface.createFromAsset(getAssets(), fuente1);

        texto1 = (TextView) findViewById(R.id.TVtexto1);
        texto1.setTypeface(foxx);


        ///botones cerrar sesion, salir con boton y sin boton
        //Añadir el Listener y cerrar la aplicación:[SALIR]
        ivSalir = (TextView) findViewById(R.id.button7);
        ivSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (Pantalla1.this);
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



        //Añadir el Listener y cerrar la aplicación [CERRAR SESIÓN]
        ivRegresar = (TextView) findViewById(R.id.button6);
        ivRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (Pantalla1.this);
                alerta.setMessage("Desea cerrar sesión.")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(Pantalla1.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
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
                salir.setTitle("CERRAR SESIÓN");
                salir.show();

            }
        });


        a = (Button) findViewById(R.id.button1);
        b = (Button) findViewById(R.id.button2);
        c = (Button) findViewById(R.id.button3);
      //  d = (Button) findViewById(R.id.button4);
        //  e = (Button) findViewById(R.id.button5);


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pantalla1.this, Pantalla2.class);
                startActivity(intent);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pantalla1.this, DatosPersonales.class);
                startActivity(intent);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pantalla1.this, mantenimientousuarios.class);
                startActivity(intent);
            }
        });
/*
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pantalla1.this, Pantalla5.class);
                startActivity(intent);
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pantalla1.this, Pantalla6.class);
                startActivity(intent);
            }
        });

*/
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