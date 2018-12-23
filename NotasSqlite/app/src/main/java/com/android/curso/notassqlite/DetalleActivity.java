package com.android.curso.notassqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.curso.notassqlite.DB.DBManagerNota;

import java.util.Calendar;

public class DetalleActivity extends AppCompatActivity {
    EditText _etTitulo, _etContenido;
    Spinner _spTipo;
    TextView _tvFecha;
    ImageView _imgActualizar, _imgEliminar;
    private DBManagerNota managerNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        //Llamar a DBManagerNota y abrir conexión
        managerNota= new DBManagerNota(this);
        managerNota.open();

        //Llamar a los elementos de xml
        _etTitulo=findViewById(R.id.etTituloDe);
        _etContenido=findViewById(R.id.etContenidoDe);
        _spTipo=findViewById(R.id.spTipoDe);
        _tvFecha=findViewById(R.id.tvFechaDe);
        _imgActualizar=findViewById(R.id.imgGuardarDe);
        _imgEliminar=findViewById(R.id.imgEliminarDe);

        //Recuperar los valores que fueorn enviados como parámetros desde la otra actividad
        final String titulo=getIntent().getExtras().getString("_titulo");
        final String contenido=getIntent().getExtras().getString("_contenido");
        final int id=Integer.parseInt(getIntent().getExtras().getString("_id"));
        final String tipo=getIntent().getExtras().getString("_tipo");
        String fecha=getIntent().getExtras().getString("_fecha");

        //Enviar a los elementos xml los valores de los parámetros
        _etTitulo.setText(titulo);
        _etContenido.setText(contenido);
        _tvFecha.setText(fecha);

        if(tipo.equals("Música"))
        {
            _spTipo.setSelection(0);
        }
        else if(tipo.equals("Programación"))
        {
            _spTipo.setSelection(1);
        }
        if(tipo.equals("Recordatorio"))
        {
            _spTipo.setSelection(2);
        }
        else if(tipo.equals("Tareas"))
        {
            _spTipo.setSelection(3);
        }


        _imgActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recuperar la fecha actual
                Calendar Cal = Calendar.getInstance();
                String fechaActual = Cal.get(Cal.DATE) + "/" + (Cal.get(Cal.MONTH) + 1) + "/" + Cal.get(Cal.YEAR) + " " + Cal.get(Cal.HOUR_OF_DAY) + ":" + Cal.get(Cal.MINUTE) + ":" + Cal.get(Cal.SECOND);

                //ejecutar la consulta de actualizar nota
                managerNota.actualizar(id,_etTitulo.getText()+"",_etContenido.getText()+"", fechaActual, _spTipo.getSelectedItem()+"");
                Toast.makeText(DetalleActivity.this, "Nota modificada correctamente", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DetalleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        _imgEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ejecutar la consulta de eliminar nota
                managerNota.eliminar(id);

                Intent intent= new Intent(DetalleActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(DetalleActivity.this, "Nota eliminada correctamente", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
