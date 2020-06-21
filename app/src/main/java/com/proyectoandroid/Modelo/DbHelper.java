package com.proyectoandroid.Modelo;

import android.content.Context;
import androidx.annotation.Nullable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String db_name = "sistema.sqlite";
    private static final int DB_SCHEME_VERSION = 1;

    public DbHelper(Context context) {
        super(context,db_name,null,DB_SCHEME_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contactos (nombre text not null, numero text not null)");
    }
}
