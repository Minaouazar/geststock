package com.example.test2;


    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
    import android.util.Log;

    import java.util.ArrayList;
    import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

      static String DATABASE_NAME="Produits";

        public static final String TABLE_NAME="UserTable";



        public static final String Table_Column_1_Name="name";
        public static final String Table_Column_Email="email";
        public static final String Table_Column_3_Password="Password";
        public static final String Table_Column_4_Phone="phone";
        public static final String Table_Column_5_Prenom="prenom";



        public SQLiteHelper(Context context) {

            super(context, DATABASE_NAME, null, 11);

        }

        @Override
        public void onCreate(SQLiteDatabase database) {

            String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_Email+"  VARCHAR PRIMARY KEY, "+Table_Column_1_Name+" VARCHAR, "+Table_Column_3_Password+" VARCHAR, "+Table_Column_4_Phone+" VARCHAR, "+Table_Column_5_Prenom+" VARCHAR)";
            database.execSQL(CREATE_TABLE);



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
            if (oldVersion >= newVersion)
                return;

        }}
