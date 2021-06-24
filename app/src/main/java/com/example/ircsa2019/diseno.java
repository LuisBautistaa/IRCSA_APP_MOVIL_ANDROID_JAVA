package com.example.ircsa2019;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class diseno extends AppCompatActivity {
    Button actualizar,eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseno);

        eliminar=(Button)findViewById(R.id.btnEliminar);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    public void eliminar(){

    }
}
