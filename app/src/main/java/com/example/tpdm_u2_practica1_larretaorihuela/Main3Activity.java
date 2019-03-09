package com.example.tpdm_u2_practica1_larretaorihuela;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main3Activity extends AppCompatActivity {

    EditText descripcion, ubicacion, fecha, presupuesto;
    Button actualizar, eliminar, regresar;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        descripcion = findViewById(R.id.editadescripcion);
        ubicacion = findViewById(R.id.editaubicacion);
        fecha = findViewById(R.id.editafecha);
        presupuesto = findViewById(R.id.editapresupuesto);
        actualizar = findViewById(R.id.actualizarproyecto);
        eliminar = findViewById(R.id.eliminarproyecto);
        regresar = findViewById(R.id.regresarproyecto2);

        Bundle parametros = getIntent().getExtras();
        descripcion.setText(parametros.getString("descripcion"));
        ubicacion.setText(parametros.getString("ubicacion"));
        fecha.setText(parametros.getString("fecha"));
        presupuesto.setText(""+parametros.getFloat("presupuesto"));
        id = parametros.getInt("id");


        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void actualizar(){
        String mensaje = "";
        Civil civ = new Civil(this);
        if(vacios()){
            mensaje = "Verifique los datos ingresados, hay datos en blanco o vacíos";
        }else{
            boolean respuesta = civ.actualizar(new Civil(id,descripcion.getText().toString(),
                    ubicacion.getText().toString(), fecha.getText().toString(), Float.parseFloat(presupuesto.getText().toString())));
            if(respuesta){
                mensaje = "Se actualizó correctamente el proyecto: "+ descripcion.getText().toString();
            }else{
                mensaje = "Error algo salió mal: " + civ.error;
            }
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCÓN").setMessage(mensaje).setPositiveButton("OK",null).show();
    }

    private void eliminar(){
        String mensaje ="";
        Civil civ = new Civil(this);
        boolean respuesta = civ.eliminar(id);

        if(respuesta){
            mensaje = "Se Eliminó exitosamente el proyecto " + descripcion.getText().toString();
        }else{
            mensaje = "Error! algo salió mal: \n" + civ.error;
        }

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCIÓN").setMessage(mensaje).setPositiveButton("OK",null).show();

        descripcion.setText("");
        ubicacion.setText("");
        fecha.setText("");
        presupuesto.setText("");
    }

    private boolean vacios(){
        if(descripcion.getText().toString().isEmpty()) return true;
        if(ubicacion.getText().toString().isEmpty()) return true;
        if(fecha.getText().toString().isEmpty()) return true;
        if(presupuesto.getText().toString().isEmpty()) return true;

        return false;
    }
}
