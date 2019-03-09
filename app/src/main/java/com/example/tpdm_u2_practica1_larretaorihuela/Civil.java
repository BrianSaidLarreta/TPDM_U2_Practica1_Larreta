package com.example.tpdm_u2_practica1_larretaorihuela;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Civil {
    private BaseDatos base;
    private int id;
    private String descripcion, ubicacion, fecha;
    private float presupuesto;
    protected String error;
    Civil[] civil = null;
    Civil civ;
    Cursor c;

    public Civil(Activity activity){
        base = new BaseDatos(activity, "civiles",null,1);
    }

    public Civil(int id, String des, String ubi, String fec, float pres){
        this.id = id;
        descripcion = des;
        ubicacion = ubi;
        fecha = fec;
        presupuesto = pres;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(float presupuesto) {
        this.presupuesto = presupuesto;
    }

    public boolean insertar(Civil civil){
        //String mensaje="";
        try{
            SQLiteDatabase insertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("DESCRIPCION",civil.getDescripcion());
            datos.put("UBICACION",civil.getUbicacion());
            datos.put("FECHA",civil.getFecha());
            datos.put("PRESUPUESTO",civil.getPresupuesto());

            long resultado = insertar.insert("PROYECTOS","IDPROYECTO",datos);
            insertar.close();

            if(resultado == -1){
                return false;
            }
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public Civil[] consultar(){
        try{
            Civil [] civil = null;
            SQLiteDatabase busqueda = base.getReadableDatabase();
            String sql = "SELECT * FROM PROYECTOS";
            c = busqueda.rawQuery(sql,null);

            if(c.moveToFirst()){
                civil = new Civil[c.getCount()];
                int pos = 0;
                do{
                    civil[pos] = new Civil(c.getInt(0), c.getString(1),
                            c.getString(2), c.getString(3), c.getFloat(4));
                }while (c.moveToNext());
            }
            return civil;

        }catch(SQLiteException e){
            return null;
        }
    }

    public Civil[] consultar(String columna, String clave){
        String sql="";
        SQLiteDatabase busqueda = base.getReadableDatabase();
        Cursor c = null;
        try{
            Civil[] a = null;
            if(columna == "IDPROYECTO"){
                sql = "SELECT * FROM PROYECTOS WHERE " + columna + "="+ clave;
                c = busqueda.rawQuery(sql,null);
            }else{
                sql = "SELECT * FROM PROYECTOS WHERE " + columna + "="+ "'" + clave + "'";
                c = busqueda.rawQuery(sql,null);
            }
            if(c.moveToFirst()){
                a = new Civil[c.getCount()];
                int pos = 0;
                do{
                    a[pos]=new Civil(c.getInt(0), c.getString(1),
                            c.getString(2),c.getString(3), c.getFloat(4));
                    pos++;
                }while(c.moveToNext());
            }
            return a;
        }catch (SQLiteException e){
            return null;
        }
    }

    public boolean eliminar(int id){
        int resultado;
        try{
            SQLiteDatabase eliminar = base.getWritableDatabase();
            String[] claves = {id+""};
            resultado = eliminar.delete("PROYECTOS","IDPROYECTO=?",claves);
            eliminar.close();

        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return resultado > 0;
    }

    public boolean actualizar(Civil civil){
        try{
            SQLiteDatabase actualizar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("DESCRIPCION",civil.getDescripcion());
            datos.put("UBICACION",civil.getUbicacion());
            datos.put("FECHA",civil.getFecha());
            datos.put("PRESUPUESTO",civil.getPresupuesto());

            long resultado = actualizar.update("PROYECTOS",datos,"IDPROYECTO=?",new String[]{""+civil.getId()});
            actualizar.close();

            if(resultado == -1) return false;
        }catch (SQLiteException e){
            error = e.getMessage();
            return  false;
        }
        return  true;
    }

}
