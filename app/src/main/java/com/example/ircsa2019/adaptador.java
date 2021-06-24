package com.example.ircsa2019;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class adaptador extends FirestoreRecyclerAdapter<datosPers,adaptador.ViewHolder> {
    Activity activity;
    FirebaseFirestore mFirestore;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public adaptador(@NonNull FirestoreRecyclerOptions<datosPers> options, Activity activity) {
        super(options);
        this.activity=activity;
        mFirestore= FirebaseFirestore.getInstance();
    }

    @Override//establece los valores que tendra cada una de las vistas
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull datosPers datos) {
        DocumentSnapshot datosDocument = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = datosDocument.getId();

        final String[] nomCompleto={""};
/*
        holder.puesto.setText(datos.getTipPers());
        holder.nombre.setText(datos.getNombre());
        holder.direccion.setText(datos.getMunicipio());
        /*.nombre.setText(datos.getApeMat());
        holder.direccion.setText(datos.getCalle());
        holder.direccion.setText(datos.getColonia());
        holder.direccion.setText(datos.getMunicipio());
        holder.direccion.setText(datos.getEstado());*/

        mFirestore.collection("DatPers").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String tipPers1 = documentSnapshot.getString("Persona");
                    String nom1 = documentSnapshot.getString("Nombre");
                    String nom2 = documentSnapshot.getString("ApePat");
                    String nom3 = documentSnapshot.getString("ApeMat");
                    String calle1 = documentSnapshot.getString("Calle");
                    String colonia1 = documentSnapshot.getString("Colonia");
                    String municipio1 = documentSnapshot.getString("Municipio");
                    String estado1 = documentSnapshot.getString("Estado");

                    nomCompleto[0]=nom1+" "+nom2+" "+nom3;

                    String direccion=calle1+" "+colonia1+" "+municipio1+" "+estado1;
                    holder.puesto.setText(tipPers1);

                    holder.nombre.setText(nomCompleto[0]);

                    holder.direccion.setText(direccion);
                }
            }
        });


        //Boton Editar
        holder.actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, editarPers.class);
                intent.putExtra("datosId", id);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[]opciones={"Si","No"};
                final AlertDialog.Builder  alertOpciones= new AlertDialog.Builder(activity);
                alertOpciones.setTitle("Seguro que Deseas Eliminar A: " + nomCompleto[0]);
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(opciones[i].equals("Si")){
                            mFirestore.collection("DatPers").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(activity, "los datos se an eliminado",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(activity, "los datos NO se an eliminado",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            if(opciones[i].equals("No")){
                                dialogInterface.dismiss();
                            }
                        }

                    }
                });
                alertOpciones.show();


            }
        });

    }

    @NonNull
    @Override//crea las vista que necesitamos mostrar
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_diseno,parent,false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView puesto,nombre,direccion;
        Button eliminar,actualizar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            puesto=(TextView) itemView.findViewById(R.id.textViewPuesto);
            nombre=(TextView) itemView.findViewById(R.id.textViewNombre);
            direccion=(TextView) itemView.findViewById(R.id.textViewDireccion);
            eliminar=(Button)itemView.findViewById(R.id.btnEliminar);
            actualizar=(Button)itemView.findViewById(R.id.btnEditar);
        }
    }
}

