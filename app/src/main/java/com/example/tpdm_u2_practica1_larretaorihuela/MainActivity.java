package com.example.tpdm_u2_practica1_larretaorihuela;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    Civil vector[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.listaproyectos);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarAlerta(position);
            }
        });
    }

    public void mostrarAlerta(final int  pos){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCIÓN").setMessage("Deseas modificar / editar el proyecto: " + vector[pos].getDescripcion())
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        invocarEliminarActualizar(pos);
                    }
                })
                .setNegativeButton("NO",null)
                .show();
    }

    private void invocarEliminarActualizar(int pos){
        Intent eliminactualiza = new Intent(this, Main3Activity.class);
        eliminactualiza.putExtra("id",vector[pos].getId());
        eliminactualiza.putExtra("descripcion",vector[pos].getDescripcion());
        eliminactualiza.putExtra("ubicacion",vector[pos].getUbicacion());
        eliminactualiza.putExtra("fecha",vector[pos].getFecha());
        eliminactualiza.putExtra("presupuesto",vector[pos].getPresupuesto());

        startActivity(eliminactualiza);
    }

    protected void onStart() {
        Civil civ = new Civil(this);
        vector = civ.consultar();
        String[] descripcion = null;

        if(vector == null){
            descripcion = new String[1];
            descripcion[0] = "No hay proyectos aún";
        }else {
            descripcion = new String[vector.length];
            for(int i= 0; i<vector.length; i++){
                Civil temp = vector[i];
                descripcion[i] = temp.getId()+"\n"+temp.getDescripcion();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, descripcion);
        lista.setAdapter(adaptador);


        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.nuevoProyecto:
                Intent nuevoproyecto = new Intent(this,Main2Activity.class);
                startActivity(nuevoproyecto);
                break;
            case R.id.buscarProyecto:
                Intent buscarproyecto = new Intent(this, Main4Activity.class);
                startActivity(buscarproyecto);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
