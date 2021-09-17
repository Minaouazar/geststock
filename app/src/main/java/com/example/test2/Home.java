package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
        import androidx.cardview.widget.CardView;

        import android.content.Intent;
        import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class Home extends AppCompatActivity {
    CardView achatt;
    CardView ventt;
    CardView stockk;
    CardView util;
    String EmailH;
    CardView note;
    CardView deconnexion;
    TextView Email ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Email = (TextView)findViewById(R.id.textView1);
        Intent intent = getIntent();
        EmailH = intent.getStringExtra(Connexion.UserEmail);
        Email.setText(Email.getText().toString()+ EmailH);


        achatt=findViewById(R.id.client);
        ventt=findViewById(R.id.fournisseur);
        stockk=findViewById(R.id.stock);
        util=findViewById(R.id.utilisateur);
        note=findViewById(R.id.note);
        deconnexion=findViewById(R.id.deconnecter);

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Home.this, Notee.class));

            }
        });
        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                Toast.makeText(Home.this,"Déconnexion", Toast.LENGTH_LONG).show();

            }
        });
        achatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Home.this, Achat.class));
            }
        });
        ventt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Ventee.class));

            }
        });

        stockk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Stock.class));

            }
        });
        util.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Home.this, Utilisateurs.class));
            }
        });




























    }

}







