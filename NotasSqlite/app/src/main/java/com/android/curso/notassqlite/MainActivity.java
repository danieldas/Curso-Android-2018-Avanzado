package com.android.curso.notassqlite;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.curso.notassqlite.Adaptador.NotaListaAdapter;
import com.android.curso.notassqlite.DB.DBManagerNota;
import com.android.curso.notassqlite.Modelo.Nota;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBManagerNota managerNota;
    private RecyclerView recycler;
    private NotaListaAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List<Nota> listaItemsNotas;
    final Context c = this;
    EditText _etBuscar;
    Spinner _spTipoGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Llamar a DBManagerNota y abrir conexión
        managerNota= new DBManagerNota(this);
        managerNota.open();

        _etBuscar=findViewById(R.id.etBuscar);
        _spTipoGeneral=findViewById(R.id.spTipo);

        inicializarRecycler();

        FloatingActionButton _btnAgregar = (FloatingActionButton) findViewById(R.id.btnAgregar);
        _btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogo();
            }
        });

        _etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //método para filtrar la b{usqueda mientras se escribe en el edittext
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Llamar a la consulta para buscar notas
                listaItemsNotas=managerNota.Buscar(_etBuscar.getText().toString(),
                                    _spTipoGeneral.getSelectedItem().toString());
                //Mandar la lista de la consulta al adaptador y ponerlo en el recyclerview
                adapter=new NotaListaAdapter(listaItemsNotas, MainActivity.this);
                recycler.setAdapter(adapter);
                recycler.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        _spTipoGeneral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //método para llamar a la consulta al cambiar el valor del spinner
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Llamar a la consulta para buscar notas
                listaItemsNotas=managerNota.Buscar(_etBuscar.getText().toString(),
                        _spTipoGeneral.getSelectedItem().toString());
                //Mandar la lista de la consulta al adaptador y ponerlo en el recyclerview
                adapter=new NotaListaAdapter(listaItemsNotas, MainActivity.this);
                recycler.setAdapter(adapter);
                recycler.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void inicializarRecycler() {
        listaItemsNotas = managerNota.getListaNotas();

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.rcvLista);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        //Mandar la lista de la consulta al adaptador y ponerlo en el recyclerview
        adapter = new NotaListaAdapter(listaItemsNotas, this);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }

    public void recargarRecycler() {
        listaItemsNotas=managerNota.getListaNotas();
        // Crear un nuevo adaptador
        adapter = new NotaListaAdapter(listaItemsNotas, this);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
        //vaciar el edittext
        _etBuscar.setText("");
    }
    private void mostrarDialogo() {
        //mostrar un dialogo
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        View mView = layoutInflaterAndroid.inflate(R.layout.nota_nueva, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
        alertDialogBuilderUserInput.setView(mView);
        alertDialogBuilderUserInput.setCancelable(false);

        //Llamar a los elementos de xml
        ImageView _imgGuardar=mView.findViewById(R.id.imgGuardarNu);
        final EditText _etTitulo=mView.findViewById(R.id.etTituloNu);
        final EditText _etContenido=mView.findViewById(R.id.etContenidoNu);
        final Spinner _spTipo=mView.findViewById(R.id.spTipoNu);
        TextView _tvCerrar=mView.findViewById(R.id.tvCerrar);

        final AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

        _imgGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //obtener fecha actual
                Calendar Cal = Calendar.getInstance();
                String fechaActual = Cal.get(Cal.DATE) + "/" + (Cal.get(Cal.MONTH) + 1) + "/" + Cal.get(Cal.YEAR) + " " + Cal.get(Cal.HOUR_OF_DAY) + ":" + Cal.get(Cal.MINUTE) + ":" + Cal.get(Cal.SECOND);

                //llamar a la consulta para insertar notas
                managerNota.insertar(_etTitulo.getText().toString(),
                                        _etContenido.getText().toString(),
                                        fechaActual,
                                        _spTipo.getSelectedItem().toString());
                recargarRecycler();
                alertDialogAndroid.hide();
                Toast.makeText(MainActivity.this, "Nota agregada",
                        Toast.LENGTH_SHORT).show();

            }
        });
        _tvCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogAndroid.hide();
            }
        });
    }
}
