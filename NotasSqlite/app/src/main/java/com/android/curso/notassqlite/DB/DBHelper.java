package com.android.curso.notassqlite.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel on 1/12/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME="NotasBD.sqlite";

    private static int DATABASE_VERSION=1;
    public static final String IDNOTA="Id";
    public static final String TITULO="Titulo";
    public static final String CONTENIDO="Contenido";
    public static final String TIPO="Tipo";
    public static final String FECHA="Fecha";

    public static final String TABLE_NAME="tnota";
    public static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+ " ("
                                            +IDNOTA+" integer PRIMARY KEY AUTOINCREMENT, "
                                            +TITULO+ " text NOT NULL, "
                                            +CONTENIDO+ " text NOT NULL, "
                                            +TIPO+ " text NOT NULL, "
                                            +FECHA+ " text NOT NULL); ";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
