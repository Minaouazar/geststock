package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Utilisateurs extends AppCompatActivity {
CardView clientt;
 CardView fournisseurr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateurs);
        clientt=findViewById(R.id.client);
        fournisseurr=findViewById(R.id.fournisseur);
       clientt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Utilisateurs.this,Client.class));
            }
        });
       fournisseurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Utilisateurs.this,Fournisseur.class));
            }
        });
    }
}