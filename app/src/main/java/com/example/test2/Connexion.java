package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.textfield.TextInputEditText;

    public class Connexion extends AppCompatActivity {
        AwesomeValidation awesomeValidation;

        Button conButton ;
        TextInputEditText Email, Password ;
        String EmailH, PasswordH;
        Boolean EditTextEmptyH;
        SQLiteDatabase sqLiteDatabaseObj;
        SQLiteHelper sqLiteHelper;
        Cursor cursor;
        String TempPassword = "NOT_FOUND" ;
        public static final String UserEmail = "";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_connexion);


            Email = (TextInputEditText) findViewById(R.id.email);
            Password = (TextInputEditText) findViewById(R.id.pass);
            conButton = (Button) findViewById(R.id.buttoncon);
            sqLiteHelper = new SQLiteHelper(this);
            awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
            awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.invalide_email);


            conButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (awesomeValidation.validate()) {
                        CheckEditTextStatus();
                        connexionFonction();

                    }
                }
            });

        }

        public void connexionFonction(){
            if(EditTextEmptyH) {

                sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

                cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_Email + "=?", new String[]{EmailH}, null, null, null);

                while (cursor.moveToNext()) {
                    if (cursor.isFirst()) {
                        cursor.moveToFirst();
                        TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));
                        cursor.close(); } }


                CheckFinalResult();

            }
            else {

                Toast.makeText(Connexion.this,"Entrez le mot de passe et L'email",Toast.LENGTH_LONG).show();

            } }

        public void CheckEditTextStatus(){

            EmailH = Email.getText().toString();
            PasswordH = Password.getText().toString();

            if( TextUtils.isEmpty(EmailH) || TextUtils.isEmpty(PasswordH)){

                EditTextEmptyH = false ;

            }
            else {

                EditTextEmptyH = true ;
            }
        }

        public void CheckFinalResult(){
           if(TempPassword.equalsIgnoreCase(PasswordH) )
            {
                Toast.makeText(Connexion.this,"Connexion reussite :)",Toast.LENGTH_LONG).show();

                Intent intent= new Intent(Connexion.this, Home.class);
                intent.putExtra(UserEmail, EmailH);
                startActivity(intent);

            }
            else {
                Toast.makeText(Connexion.this,"Erreur de connexion :(",Toast.LENGTH_LONG).show();
            }
            TempPassword = "NOT_FOUND" ;
        }

        public void connecter(View view) {
            Intent b=new Intent(this, Inscription.class);
            startActivity(b);
            finish();
        }}