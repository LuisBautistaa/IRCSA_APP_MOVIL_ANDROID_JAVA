package com.example.ircsa2019;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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




public class plantasAdapter extends FirestoreRecyclerAdapter<Plantas, plantasAdapter.ViewHolder> {

    Activity activity;
    FirebaseFirestore mFirestore;
    String nombre;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    //constructor
    public plantasAdapter(@NonNull FirestoreRecyclerOptions<Plantas> options, Activity activity) {
        super(options);
        this.activity = activity;
        mFirestore = FirebaseFirestore.getInstance();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_plantas, parent, false);
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Plantas plantas) {
        ///para obtener el id del documento
        DocumentSnapshot plantaDocument = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = plantaDocument.getId();
         nombre = plantas.getNombre();

        holder.textViewNombre.setText(plantas.getNombre());
        holder.textViewCarac.setText(plantas.getCaracteristicas());
        holder.textViewCuid.setText(plantas.getCuidados());

        //boton Editar
        holder.buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditarPlantasActivity.class);
                intent.putExtra("plantaId", id);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        ///boton Eliminar
        holder.buttonEliminar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[]opciones={"Si","No"};
                final AlertDialog.Builder  alertOpciones= new AlertDialog.Builder(activity);
                alertOpciones.setTitle("¿Seguro que Deseas Eliminar a la Planta: " + nombre + "?");
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(opciones[i].equals("Si")){
                            mFirestore.collection("Plantas").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(activity, "Los datos se han eliminado",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(activity, "los datos no se han eliminado",Toast.LENGTH_SHORT).show();
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
        }));
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNombre;
        TextView textViewCarac;
        TextView textViewCuid;
        Button buttonEditar;
        Button buttonEliminar;


    public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewCarac = itemView.findViewById(R.id.textViewCaracteristicas);
            textViewCuid = itemView.findViewById(R.id.textViewCuidados);
            buttonEditar  = itemView.findViewById(R.id.btnEditar);
            buttonEliminar  = itemView.findViewById(R.id.btnEliminar);
        }
    }
}