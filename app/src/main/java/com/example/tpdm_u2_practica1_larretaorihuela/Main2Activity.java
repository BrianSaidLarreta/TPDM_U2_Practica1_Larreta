package com.example.tpdm_u2_practica1_larretaorihuela;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    EditText descripcion, ubicacion, fecha, presupuesto;
    Button ingresar,regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        ingresar = findViewById(R.id.ingresaraproyecto);
        regresar = findViewById(R.id.regresaraproyecto);
        descripcion = findViewById(R.id.descripcion);
        ubicacion = findViewById(R.id.ubicacion);
        fecha = findViewById(R.id.fecha);
        presupuesto = findViewById(R.id.presupuesto);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });

    }

    private void ingresar(){
        String mensaje="";
        if(vacios())
            mensaje = "Verifique los datos ingresados, hay datos en blanco o vacíos";
        else {
            Civil civi = new Civil(this);
            boolean respuesta = civi.insertar(new Civil(0,descripcion.getText().toString(),
                    ubicacion.getText().toString(), fecha.getText().toString(),
                    Float.parseFloat(presupuesto.getText().toString())));
           if(respuesta){
                mensaje = "Proyecto"+descripcion.getText().toString()+ "Registrado con Éxito";
            }else {
                mensaje = "Error! No se ha podido registrar el Proyecto: "+ descripcion.getText()+"\n Respuesta de SQLITE: " + civi.error;
            }
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCIÓN").setMessage(mensaje).setPositiveButton("OK",null).show();
    }

    private void regresar(){
            finish();
    }

    private boolean vacios(){
        if(descripcion.getText().toString().isEmpty()) return true;
        if(ubicacion.getText().toString().isEmpty()) return true;
        if(fecha.getText().toString().isEmpty()) return true;
        if(presupuesto.getText().toString().isEmpty()) return true;

        return false;
    }

}
