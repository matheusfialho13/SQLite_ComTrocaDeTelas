package com.example.matheusfialho.sqlite_comtrocadetelas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Crud.db";
    public static final String TABELA = "Clientes";
    public static String _ID = "_ID";
    public static String NOME = "Nome";
    public static String IDADE = "Idade";
    public static String CELL = "Cell";
    public static String FOTO = "Foto";
    private static final int DATABASE_VERSION = 8;
    private final String CREATE_TABLE = "CREATE TABLE "+ TABELA +" ("
            +_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +NOME+" TEXT NOT NULL, "
            +IDADE+" INTEGER, "
            +CELL+" TEXT NOT NULL, "
            +FOTO+" BLOB)";

    //private final String DROP_TABLE = "DROP TABLE "+TABELA;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(CREATE_TABLE); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        // Creating tables again
        onCreate(db);
    }
}
