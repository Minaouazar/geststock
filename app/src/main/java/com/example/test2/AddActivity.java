package com.example.test2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText produit_title, stock_unity,  stock_quantity;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        produit_title = findViewById(R.id.nomproduit);
        stock_unity= findViewById(R.id.unityproduit);
        stock_quantity = findViewById(R.id.quantityproduit);

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addBook(produit_title.getText().toString().trim(),Integer.valueOf(stock_quantity.getText().toString().trim()),
                        stock_unity.getText().toString().trim());
            }
        });
    }
}
