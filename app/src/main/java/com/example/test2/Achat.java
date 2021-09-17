package com.example.test2;
        //   db.execSQL("CREATE TABLE IF NOT EXISTS AchatPro (idP integer primary key autoincrement, nameP VARCHAR, quantite VARCHAR, Prix VARCHAR,four VARCHAR,datee Text,id integer  ,foreign key (id) references UserTable (id) );");


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Achat extends AppCompatActivity {


    Cursor cur;
    TextView datee;
    SQLiteDatabase db;
    LinearLayout layNaviguer, layRecherche;
    EditText _txtnom, _txtQuantite,_txtPrix,_txtFor,_txtRechercheproduit;
    ImageButton _btnRecherche;
    Button _btnPrevious,_btnNext;
    Button _btnAdd,_btnUpdate,_btnDelete;
    Button _btnCancel,_btnSave;
    int op = 0;
    String x;
    DatePickerDialog.OnDateSetListener setListener;
     String EmailH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achat);


        layNaviguer = (LinearLayout) findViewById(R.id.layNaviguer);
        layRecherche= (LinearLayout) findViewById(R.id.layRecherche);
        datee=(TextView)findViewById(R.id.txtdate);


        Calendar calendar= Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        datee.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Achat.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                datee.setText(date);
            }
        };



        _txtRechercheproduit = (EditText) findViewById(R.id.txtRechercheproduit);
        _txtQuantite = (EditText) findViewById(R.id.txtQuantite);
        _txtPrix = (EditText) findViewById(R.id.txtPrix);
        _txtFor = (EditText) findViewById(R.id.txtFor);
        _txtnom = (EditText) findViewById(R.id.txtnom);


        _btnAdd = (Button) findViewById(R.id.btnAdd);
        _btnUpdate = (Button) findViewById(R.id.btnUpdate);
        _btnDelete = (Button) findViewById(R.id.btnDelete);

        _btnNext = (Button) findViewById(R.id.btnNext);
        _btnPrevious = (Button) findViewById(R.id.btnPrevious);

        _btnCancel = (Button) findViewById(R.id.btnCancel);
        _btnSave = (Button) findViewById(R.id.btnSave);

        _btnRecherche = (ImageButton) findViewById(R.id.btnRecherche);


        // ouverture d'une connexion vers la base de données
        db = openOrCreateDatabase("Produits",MODE_PRIVATE,null);
        // Création de la table comptes
        //  db.execSQL("CREATE TABLE IF NOT EXISTS AchatPr(idP  primary key autoincrement, nameP VARCHAR, quantite VARCHAR, Prix VARCHAR,four  VARCHAR,datee VARCHAR ,id INTEGER NOT NULL ,FOREIGN KEY (id) REFERENCES UserTable (id));");
        db.execSQL("CREATE TABLE IF NOT EXISTS AchatPr (idP integer primary key autoincrement, nameP VARCHAR, quantite VARCHAR, Prix VARCHAR,four VARCHAR,datee Text,email varchar not null,  foreign key (email) references UserTable (email));");

        layNaviguer.setVisibility(View.INVISIBLE);
        _btnSave.setVisibility(View.INVISIBLE);
        _btnCancel.setVisibility(View.INVISIBLE);

        _btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cur = db.rawQuery("select * from AchatPr where nameP like ?", new String[]{"%" + _txtRechercheproduit.getText().toString() + "%"});
                try {
                    cur.moveToFirst();
                    _txtnom.setText(cur.getString(1));
                    _txtQuantite.setText(cur.getString(2));
                    _txtPrix.setText(cur.getString(3));
                    _txtFor.setText(cur.getString(4));
                    datee.setText(cur.getString(5));

                    if (cur.getCount() == 1){
                        layNaviguer.setVisibility(View.INVISIBLE);
                    } else {
                        layNaviguer.setVisibility(View.VISIBLE);
                        _btnPrevious.setEnabled(false);
                        _btnNext.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun résultat.",Toast.LENGTH_SHORT).show();
                    _txtnom.setText("");
                    _txtQuantite.setText("");
                    _txtPrix.setText("");
                    _txtFor.setText("");
                    datee.setText("");


                    layNaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });

        _btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToNext();
                    _txtnom.setText(cur.getString(1));
                    _txtQuantite.setText(cur.getString(2));
                    _txtPrix.setText(cur.getString(3));
                    _txtFor.setText(cur.getString(4));
                    datee.setText(cur.getString(5));

                    _btnPrevious.setEnabled(true);
                    if (cur.isLast()){
                        _btnNext.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        _btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToPrevious();
                    _txtnom.setText(cur.getString(1));
                    _txtQuantite.setText(cur.getString(2));
                    _txtPrix.setText(cur.getString(3));
                    _txtFor.setText(cur.getString(4));
                    datee.setText(cur.getString(5));

                    _btnNext.setEnabled(true);
                    if (cur.isFirst()){
                        _btnPrevious.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        _btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                op = 1;
                _txtnom.setText("");
                _txtQuantite.setText("");
                _txtPrix.setText("");
                _txtFor.setText("");
                datee.setText("");

                _btnSave.setVisibility(View.VISIBLE);
                _btnCancel.setVisibility(View.VISIBLE);
                _btnUpdate.setVisibility(View.INVISIBLE);
                _btnDelete.setVisibility(View.INVISIBLE);
                _btnAdd.setEnabled(false);
                layNaviguer.setVisibility(View.INVISIBLE);
                layRecherche.setVisibility(View.INVISIBLE);}

        });

        _btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tester si les champs ne sont pas vides
                try {
                    x = cur.getString(0);
                    op = 2;

                    _btnSave.setVisibility(View.VISIBLE);
                    _btnCancel.setVisibility(View.VISIBLE);

                    _btnDelete.setVisibility(View.INVISIBLE);
                    _btnUpdate.setEnabled(false);
                    _btnAdd.setVisibility(View.INVISIBLE);

                    layNaviguer.setVisibility(View.INVISIBLE);
                    layRecherche.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Sélectionnez un produit puis appyuer sur le bouton de modification",Toast.LENGTH_SHORT).show();
                }

            }
        });

        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (_txtnom.getText().toString().equals("") ||_txtQuantite.getText().toString().equals("") || _txtPrix.getText().toString().equals("") ||_txtFor.getText().toString().equals("") || datee.getText().toString().equals("")) {
                    Toast.makeText(Achat.this, "il manque des information", Toast.LENGTH_SHORT).show();
                } else {

                    if (op == 1){
                       // db.execSQL("select *from userTable where  id like ?");
                        // insertion
                        db.execSQL("insert into AchatPr (nameP,quantite,Prix,four,datee,email) values (?,?,?,?,?,?);",
                                new String[] {_txtnom.getText().toString(), _txtQuantite.getText().toString(),_txtPrix.getText().toString(),_txtFor.getText().toString(),datee.getText().toString(),SQLiteHelper.Table_Column_Email});

                    } else if (op == 2) {
                        // Mise à jour
                        db.execSQL("update AchatPr set nameP=?, quantite=?, Prix=?, four=? ,datee=? where idP=?;", new String[] {_txtnom.getText().toString(), _txtQuantite.getText().toString(),_txtPrix.getText().toString(),_txtFor.getText().toString(),datee.getText().toString(),x});
                    }

                    _btnSave.setVisibility(View.INVISIBLE);
                    _btnCancel.setVisibility(View.INVISIBLE);
                    _btnUpdate.setVisibility(View.VISIBLE);
                    _btnDelete.setVisibility(View.VISIBLE);

                    _btnAdd.setVisibility(View.VISIBLE);
                    _btnAdd.setEnabled(true);
                    _btnUpdate.setEnabled(true);
                    _btnRecherche.performClick();
                    layRecherche.setVisibility(View.VISIBLE);
                }}
        });
        _btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 0;

                _btnSave.setVisibility(View.INVISIBLE);
                _btnCancel.setVisibility(View.INVISIBLE);
                _btnUpdate.setVisibility(View.VISIBLE);
                _btnDelete.setVisibility(View.VISIBLE);

                _btnAdd.setVisibility(View.VISIBLE);
                _btnAdd.setEnabled(true);
                _btnUpdate.setEnabled(true);

                layRecherche.setVisibility(View.VISIBLE);
            }
        });


        _btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    x=  cur.getString(0);
                    AlertDialog dial = MesOptions();
                    dial.show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Sélectionner un Produit puis appyuer sur le bouton de suppresssion",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });




    }


    private AlertDialog MesOptions(){
        AlertDialog MiDia = new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Est ce que vous voulez supprimer ce Produit ?")
                //  .setIcon(R.drawable.validate)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.execSQL("delete from AchatPr where idP=?;",new String[] {cur.getString(0)});
                        _txtnom.setText("");
                        _txtQuantite.setText("");
                        _txtPrix.setText("");
                        _txtFor.setText("");
                        datee.setText("");



                        layNaviguer.setVisibility(View.INVISIBLE);
                        cur.close();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        return MiDia;



    }

}