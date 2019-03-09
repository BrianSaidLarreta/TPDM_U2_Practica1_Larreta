package com.example.tpdm_u2_practica1_larretaorihuela;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    EditText filtro;
    Button buscar, regresar;
    TextView informacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        filtro = findViewById(R.id.filtro);
        buscar = findViewById(R.id.consultar);
        regresar = findViewById(R.id.regresarproyecto3);
        informacion = findViewById(R.id.informacion);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busqueda();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void busqueda(){
        String mensaje ="";
        Civil[] resultado =null;
        Civil civ = new Civil(this);
        if(filtro.getText().toString().isEmpty()){
            mensaje ="Porfavor ingresa datos para realizar la búsqueda";
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("ATENCIÓN").setMessage(mensaje).setPositiveButton("OK",null).show();
            return;
        }
        try{
            resultado = civ.consultar("IDPROYECTO",""+Integer.parseInt(filtro.getText().toString()));

        }catch (NumberFormatException e){
            resultado = civ.consultar("DESCRIPCION",filtro.getText().toString());
        }
        if(resultado == null){
            mensaje ="No hay coincidencias";
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("ATENCIÓN").setMessage(mensaje).setPositiveButton("OK",null).show();
            return;
        }
        informacion.setText("Descripción: "+ resultado[0].getDescripcion()+"\n" +
                "Ubicación: "+ resultado[0].getUbicacion()+"\n"+"Fecha: "+resultado[0].getFecha()+"\n" +
                "Presupuesto_: "+resultado[0].getPresupuesto());
        filtro.setText("");
    }
}
