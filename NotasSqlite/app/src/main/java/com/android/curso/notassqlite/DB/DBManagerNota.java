package com.android.curso.notassqlite.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.curso.notassqlite.Modelo.Nota;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 1/12/2018.
 */

public class DBManagerNota {
     DBHelper helper;
     Context context;
     SQLiteDatabase db;

    public DBManagerNota(Context c){
        context=c;
    }

    public DBManagerNota open(){
        helper=new DBHelper(context);
        db=helper.getWritableDatabase();
        return this;
    }

    public void cerrar(){
        helper.close();
    }
    public void insertar(String titulo, String contenido, String fecha, String tipo)
    {
        ContentValues contentValues= new ContentValues();
        contentValues.put(helper.TITULO, titulo);
        contentValues.put(helper.CONTENIDO, contenido);
        contentValues.put(helper.TIPO, tipo);
        contentValues.put(helper.FECHA, fecha);

        db.insert(helper.TABLE_NAME,null, contentValues);
    }

    public void actualizar(int id, String titulo, String contenido, String fecha, String tipo)
    {
        ContentValues contentValues= new ContentValues();
        contentValues.put(helper.TITULO, titulo);
        contentValues.put(helper.CONTENIDO, contenido);
        contentValues.put(helper.TIPO, tipo);
        contentValues.put(helper.FECHA, fecha);

        db.update(helper.TABLE_NAME, contentValues,helper.IDNOTA+"="+id, null);
    }

    public void eliminar(int id)
    {
        db.delete(helper.TABLE_NAME, helper.IDNOTA+"="+id, null);
    }

    public Cursor cargarCursorBuscar(String parametro, String tipo) {
        if (tipo.equals("Todo"))
        {
            String[] args = new String[] {"%"+ parametro+"%", "%"+ parametro+"%"};
            return db.rawQuery(" SELECT * FROM tnota WHERE (titulo LIKE ? OR contenido LIKE ? )", args);
        }
        else
        {
            String[] args = new String[] {"%"+ parametro+"%", "%"+ parametro+"%", tipo};
            return db.rawQuery(" SELECT * FROM tnota WHERE (titulo LIKE ? OR contenido LIKE ?) and tipo =?", args);
        }
    }

    public List<Nota> Buscar(String parametro, String tipo){
        List<Nota>  list= new ArrayList<>();
        Cursor c= cargarCursorBuscar(parametro, tipo);

        while (c.moveToNext()){
            Nota nota= new Nota();
            nota.setIdNota(c.getString(0));
            nota.setTitulo(c.getString(1));
            nota.setContenido(c.getString(2));
            nota.setTipo(c.getString(3));
            nota.setFecha(c.getString(4));

            list.add(nota);
        }
        return list;
    }

    public Cursor cargarCursor() {
        return db.rawQuery("SELECT * FROM tnota", null);
    }

    public List<Nota> getListaNotas(){
        List<Nota>  list= new ArrayList<>();
        Cursor c= cargarCursor();

        while (c.moveToNext()){
            Nota nota= new Nota();
            nota.setIdNota(c.getString(0));
            nota.setTitulo(c.getString(1));
            nota.setContenido(c.getString(2));
            nota.setTipo(c.getString(3));
            nota.setFecha(c.getString(4));

            list.add(nota);
        }
        return list;
    }

}
