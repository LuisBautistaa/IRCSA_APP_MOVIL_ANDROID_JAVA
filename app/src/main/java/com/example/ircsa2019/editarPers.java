package com.example.ircsa2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editarPers extends AppCompatActivity {

    private String datosId;
    private FirebaseFirestore mFirestore;
    Spinner nombre,persona,apePaterno,apeMaterno,calle,colonia,municipio,estado;
    TextView rfc,curp,fech,num,cod;
    Button modificar,cancelar,regresar;
    int contador = 0;
    ArrayAdapter<CharSequence> adapter,adapter1,adapter2,adapter3,adapter4,adapter5,adapter6,adapter7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pers);

        datosId = getIntent().getStringExtra("datosId");
        mFirestore = FirebaseFirestore.getInstance();
        ObtenerDatos();

        nombre= (Spinner)findViewById(R.id.spNombreAc);
        persona= (Spinner)findViewById(R.id.spTIpPersAc);
        apePaterno= (Spinner)findViewById(R.id.spApePatAc);
        apeMaterno= (Spinner)findViewById(R.id.spApeMatAc);
        calle= (Spinner)findViewById(R.id.spCalleAc);
        colonia= (Spinner)findViewById(R.id.spColoniaAc);
        municipio= (Spinner)findViewById(R.id.spMunicipioAc);
        estado= (Spinner)findViewById(R.id.spEstadoAc);
        rfc=(TextView)findViewById(R.id.txtRfcAc);
        curp=(TextView)findViewById(R.id.txtCurpAc);
        fech=(TextView)findViewById(R.id.txtFecNacAc);
        num=(TextView)findViewById(R.id.txtNumAc);
        cod=(TextView)findViewById(R.id.txtCodPosAc);
        modificar=(Button)findViewById(R.id.btnActualizarAc);
        cancelar=(Button)findViewById(R.id.btnCancelarAc);
        regresar=(Button)findViewById(R.id.btnExitAc);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (editarPers.this);
                alerta.setMessage("Desea cancelar los cambios.")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //startActivity(new Intent(editarPers.this, listaDatos.class));
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
                salir.setTitle("CANCELAR");
                salir.show();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (editarPers.this);
                alerta.setMessage("¿Desea Regresar?.")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(editarPers.this, listaDatos.class));
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

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder (editarPers.this);
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
                                startActivity(new Intent(editarPers.this, listaDatos.class));
                            }
                        });
                AlertDialog salir = alerta.create();
                salir.setTitle("ACTUALIZAR");
                salir.show();


            }
        });

       adapter=ArrayAdapter.createFromResource(this,R.array.nombre,android.R.layout.simple_spinner_item);
        nombre.setAdapter(adapter);

       adapter1=ArrayAdapter.createFromResource(this,R.array.personas,android.R.layout.simple_spinner_item);
        persona.setAdapter(adapter1);

        adapter2=ArrayAdapter.createFromResource(this,R.array.Apellidos,android.R.layout.simple_spinner_item);
        apePaterno.setAdapter(adapter2);

        adapter3=ArrayAdapter.createFromResource(this,R.array.Apellidos,android.R.layout.simple_spinner_item);
        apeMaterno.setAdapter(adapter3);

         adapter4=ArrayAdapter.createFromResource(this,R.array.calles,android.R.layout.simple_spinner_item);
        colonia.setAdapter(adapter4);

        adapter5=ArrayAdapter.createFromResource(this,R.array.colonias,android.R.layout.simple_spinner_item);
        calle.setAdapter(adapter5);

        adapter6=ArrayAdapter.createFromResource(this,R.array.municipios,android.R.layout.simple_spinner_item);
        municipio.setAdapter(adapter6);

         adapter7=ArrayAdapter.createFromResource(this,R.array.estados,android.R.layout.simple_spinner_item);
        estado.setAdapter(adapter7);
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
    ///MOSTRAR DATOS EN EL TEXT VIEW CON LOS DATOS QUE ESTAN EN LA BD
    private void ObtenerDatos(){
        mFirestore.collection("DatPers").document(datosId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String tipPers1 = documentSnapshot.getString("Persona");
                    String nom1 = documentSnapshot.getString("Nombre");
                    String apePat1 = documentSnapshot.getString("ApePat");
                    String apeMat1 = documentSnapshot.getString("ApeMat");
                    String rfc1 = documentSnapshot.getString("Rfc");
                    String curp1 = documentSnapshot.getString("Curp");
                    String naci1 = documentSnapshot.getString("FecNac");
                    String calle1 = documentSnapshot.getString("Calle");
                    String colonia1 = documentSnapshot.getString("Colonia");
                    String municipio1 = documentSnapshot.getString("Municipio");
                    String estado1 = documentSnapshot.getString("Estado");
                    String num1 = documentSnapshot.getString("Numero");
                    String cod1 = documentSnapshot.getString("Cod");


                    int posicion= adapter1.getPosition(tipPers1);
                    persona.setSelection(posicion);
                    int posicion1= adapter.getPosition(nom1);
                    nombre.setSelection(posicion1);
                    int posicion2= adapter2.getPosition(apePat1);
                    apePaterno.setSelection(posicion2);
                    int posicion3= adapter3.getPosition(apeMat1);
                    apeMaterno.setSelection(posicion3);
                    int posicion4= adapter5.getPosition(calle1);
                    calle.setSelection(posicion4);
                    int posicion5= adapter4.getPosition(colonia1);
                    colonia.setSelection(posicion5);
                    int posicion6= adapter6.getPosition(municipio1);
                    municipio.setSelection(posicion6);
                    int posicion7= adapter7.getPosition(estado1);
                    estado.setSelection(posicion7);
                    rfc.setText(rfc1);
                    curp.setText(curp1);
                    fech.setText(naci1);
                    num.setText(num1);
                    cod.setText(cod1);
                }
            }
        });
    }
    //
    ///ACTUALIZAR
    private void ActualizarDatos(){

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


        //  mFirestore.collection("Plantas").document(plantaId).update(map);



        mFirestore.collection("DatPers").document(datosId).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(editarPers.this, "Los datos han sido actualizada exitosamente.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(editarPers.this, listaDatos.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(editarPers.this, "La planta no se pudo actualizar, intente de nuevo.", Toast.LENGTH_SHORT).show();
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
