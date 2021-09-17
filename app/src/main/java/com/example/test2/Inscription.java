package com.example.test2;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.textfield.TextInputEditText;




public class Inscription extends AppCompatActivity {


    TextInputEditText Name,Email, Password,Phone,Prenom;
    Button inscription;
    String NameH, EmailH, PasswordH,PhoneH,PrenomH;
    Boolean EditTextEmptyH;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryH ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        inscription = (Button)findViewById(R.id.buttonincription);
        Email = (TextInputEditText) findViewById(R.id.email);
        Password = (TextInputEditText)findViewById(R.id.pass);
        Name = (TextInputEditText)findViewById(R.id.Nom);
        Phone=(TextInputEditText)findViewById(R.id.Phone);
        Prenom = (TextInputEditText)findViewById(R.id.Prenom);
        sqLiteHelper = new SQLiteHelper(this);
        //validation style
        awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);
        //ajouter les validation
        awesomeValidation.addValidation(this,R.id.Phone,"[0]{1}[0-9]{9}$",R.string.invalide_mobile);
        awesomeValidation.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.invalide_email);



        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(awesomeValidation.validate()){

                    SQLiteDataBaseBuild();

                    SQLiteTableBuild();

                    CheckEditTextStatus();

                    CheckingEmailAlreadyExistsOrNot();

                    EmptyEditTextAfterDataInsert();}}


        });


    }

    public void SQLiteDataBaseBuild(){
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_Email + " VARCHAR PRIMARY KEY NOT NULL, " + SQLiteHelper.Table_Column_1_Name + " VARCHAR, " + SQLiteHelper.Table_Column_3_Password + " VARCHAR, " + SQLiteHelper.Table_Column_4_Phone + " VARCHAR, " + SQLiteHelper.Table_Column_5_Prenom + " VARCHAR);");
    }

    public void InsertDataIntoSQLiteDatabase(){

        if(EditTextEmptyH == true)
        {
            SQLiteDataBaseQueryH = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,email,password,phone,prenom) VALUES('"+NameH+"', '"+EmailH+"', '"+PasswordH+"', '"+PhoneH+"', '"+PrenomH+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryH);
            sqLiteDatabaseObj.close();

            Toast.makeText(Inscription.this,"Inscription :)", Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(Inscription.this,"il manque des information!", Toast.LENGTH_LONG).show();
        }
    }
    public void EmptyEditTextAfterDataInsert(){

        Name.getText().clear();
        Email.getText().clear();
        Password.getText().clear();
        Phone.getText().clear();
        Prenom.getText().clear();
    }
    public void CheckEditTextStatus(){
        NameH = Name.getText().toString() ;
        EmailH = Email.getText().toString();
        PasswordH = Password.getText().toString();
        PhoneH=Phone.getText().toString();
        PrenomH=Prenom.getText().toString();

        if(TextUtils.isEmpty(NameH) || TextUtils.isEmpty(EmailH) || TextUtils.isEmpty(PasswordH) || TextUtils.isEmpty(PhoneH) || TextUtils.isEmpty(PrenomH)){

            EditTextEmptyH= false ; }
        else {
            EditTextEmptyH = true ;
        }
    }
    public void CheckingEmailAlreadyExistsOrNot() {

        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_Email + "=?", new String[]{EmailH}, null, null, null);
        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {
                cursor.moveToFirst();
                F_Result = "Email Found";
                cursor.close();
            }
        }
        CheckFinalResult();

    }
    public void CheckFinalResult(){

        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            Toast.makeText(Inscription.this,"l'email existe déja",Toast.LENGTH_LONG).show();

        }
        else {
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }

    public void connecter(View view) {
        Intent in=new Intent(this,Connexion.class);
        startActivity(in);
        finish();
    }
}