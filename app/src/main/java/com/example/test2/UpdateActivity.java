package com.example.test2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText produit_title, stock_unity,  stock_quantity;
    Button update_button, delete_button;

    String id, produit, unity, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

       produit_title= findViewById(R.id.nomproduit2);
       stock_unity = findViewById(R.id.unityproduit2);
        stock_quantity = findViewById(R.id.quantityproduit2);

        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(produit);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                com.example.test2.MyDatabaseHelper myDB = new com.example.test2.MyDatabaseHelper(UpdateActivity.this);
                produit = produit_title.getText().toString().trim();
                unity = stock_unity.getText().toString().trim();
                quantity = stock_quantity.getText().toString().trim();

                myDB.updateData(id, produit, unity, quantity);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("produit") &&
                getIntent().hasExtra("unity") && getIntent().hasExtra("quantity") ){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            produit = getIntent().getStringExtra("produit");
            unity = getIntent().getStringExtra("unity");
            quantity = getIntent().getStringExtra("quantity");


            //Setting Intent Data
            produit_title.setText(produit);
           stock_unity.setText(unity);
            stock_quantity.setText(quantity);


            Log.d("stev", produit+" "+unity+" "+quantity );
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + produit + " ?");
        builder.setMessage("Are you sure you want to delete " + produit+ " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                com.example.test2.MyDatabaseHelper myDB = new com.example.test2.MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
