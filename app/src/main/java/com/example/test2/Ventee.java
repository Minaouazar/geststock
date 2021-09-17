package com.example.test2;
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


public class Ventee extends AppCompatActivity {

    Cursor cur;
    SQLiteDatabase db;
    LinearLayout layNaviguer, layRecherche;
    DatePickerDialog.OnDateSetListener setListener;
    EditText _nomprenom, _marchendise,_prixx,_txtRecherchevente;
    ImageButton _btnRecherche;
    TextView _datevente;
    Button _btnFirst,_btnPrevious,_btnNext,_btnLast;
    Button _btnAdd,_btnUpdate,_btnDelete;
    Button _btnCancel,_btnSave;


    int op = 0;
    String x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventee);

        layNaviguer = (LinearLayout) findViewById(R.id.layNaviguer);
        layRecherche= (LinearLayout) findViewById(R.id.layRecherche);

        _txtRecherchevente = (EditText) findViewById(R.id.txtRecherchevent);
        _nomprenom = (EditText) findViewById(R.id.txtnom);
        _marchendise = (EditText) findViewById(R.id.txtmarchendise);
        _prixx = (EditText) findViewById(R.id.txtprix);
        _datevente = (TextView) findViewById(R.id.txtdate2);



        Calendar calendar= Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        _datevente.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Ventee.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                _datevente.setText(date);
            }
        };


        _btnAdd = (Button) findViewById(R.id.btnAdd);
        _btnUpdate = (Button) findViewById(R.id.btnUpdate);
        _btnDelete = (Button) findViewById(R.id.btnDelete);

        _btnFirst = (Button) findViewById(R.id.btnfirst);
        _btnPrevious = (Button) findViewById(R.id.btnPrevious);
        _btnNext = (Button) findViewById(R.id.btnNext);
        _btnLast = (Button) findViewById(R.id.btnlast);

        _btnCancel = (Button) findViewById(R.id.btnCancel);
        _btnSave = (Button) findViewById(R.id.btnSave);

        _btnRecherche = (ImageButton) findViewById(R.id.btnRecherche);


        db = openOrCreateDatabase("vente",MODE_PRIVATE,null);
        // Création de la table vente
        db.execSQL("CREATE TABLE IF NOT EXISTS vente (id integer primary key autoincrement, Nomprenom VARCHAR, marchendise VARCHAR,prixx Double, datevente Text);");

        layNaviguer.setVisibility(View.INVISIBLE);
        _btnSave.setVisibility(View.INVISIBLE);
        _btnCancel.setVisibility(View.INVISIBLE);

        _btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur = db.rawQuery("select * from vente where Nomprenom like ?", new String[]{"%" + _txtRecherchevente.getText().toString() + "%"});
                try {
                    cur.moveToFirst();
                    _nomprenom.setText(cur.getString(1));
                    _marchendise.setText(cur.getString(2));
                    _prixx.setText(cur.getString(3));
                    _datevente.setText(cur.getString(4));
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
                    _nomprenom.setText("");
                    _marchendise.setText("");
                    _prixx.setText("");
                    _datevente.setText("");
                    layNaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });

        _btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToFirst();
                    _nomprenom.setText(cur.getString(1));
                    _marchendise.setText(cur.getString(2));
                    _prixx.setText(cur.getString(3));
                    _datevente.setText(cur.getString(4));
                    _btnPrevious.setEnabled(false);
                    _btnNext.setEnabled(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun compte n'est existant.",Toast.LENGTH_SHORT).show();

                }
            }
        });
        _btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToLast();
                    _nomprenom.setText(cur.getString(1));
                    _marchendise.setText(cur.getString(2));
                    _prixx.setText(cur.getString(3));
                    _datevente.setText(cur.getString(4));
                    _btnPrevious.setEnabled(true);
                    _btnNext.setEnabled(false);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun compte n'est existant.",Toast.LENGTH_SHORT).show();

                }
            }
        });

        _btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToNext();
                    _nomprenom.setText(cur.getString(1));
                    _marchendise.setText(cur.getString(2));
                    _prixx.setText(cur.getString(3));
                    _datevente.setText(cur.getString(4));
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
                    _nomprenom.setText(cur.getString(1));
                    _marchendise.setText(cur.getString(2));
                    _prixx.setText(cur.getString(3));
                    _datevente.setText(cur.getString(4));
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
                _nomprenom.setText("");
                _marchendise.setText("");
                _prixx.setText("");
                _datevente.setText("");
                _btnSave.setVisibility(View.VISIBLE);
                _btnCancel.setVisibility(View.VISIBLE);
                _btnUpdate.setVisibility(View.INVISIBLE);
                _btnDelete.setVisibility(View.INVISIBLE);
                _btnAdd.setEnabled(false);
                layNaviguer.setVisibility(View.INVISIBLE);
                layRecherche.setVisibility(View.INVISIBLE);
            }
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
                    Toast.makeText(getApplicationContext(),"Sélectionnez un compte puis appyuer sur le bouton de modification",Toast.LENGTH_SHORT).show();
                }

            }
        });

        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (op == 1){
                    // insertion
                    if (_nomprenom.getText().toString().equals("") || _marchendise.getText().toString().equals("") || _prixx.getText().toString().equals("")|| _datevente.getText().toString().equals("")) {
                        Toast.makeText(Ventee.this, "il manque des information", Toast.LENGTH_SHORT).show();
                    } else  {
                        db.execSQL("insert into vente (Nomprenom,marchendise,prixx,datevente) values (?,?,?,?);", new String[] {_nomprenom.getText().toString(), _marchendise.getText().toString(),_prixx.getText().toString(),_datevente.getText().toString()});
                    } }else if (op == 2) {
                    // Mise à jour
                    db.execSQL("update vente set Nomprenom=?, marchendise=?,prixx=?, datevente=? where id=?;", new String[] {_nomprenom.getText().toString(),  _marchendise.getText().toString(),_prixx.getText().toString(),_datevente.getText().toString(),x});
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
            }
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
                    Toast.makeText(getApplicationContext(),"Sélectionner un compte puis appyuer sur le bouton de suppresssion",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });



    }

    private AlertDialog MesOptions(){
        AlertDialog MiDia = new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Est ce que vous voulez supprimer ce compte?")
                .setIcon(R.drawable.validate)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.execSQL("delete from vente where id=?;",new String[] {cur.getString(0)});
                        _nomprenom.setText("");
                        _marchendise.setText("");
                        _prixx.setText("");
                        _datevente.setText("");
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