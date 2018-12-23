package com.android.curso.cdsweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.curso.cdsweb.Adaptador.CdListaAdapter;
import com.android.curso.cdsweb.Modelo.Cd;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Cd> cdsList;
    Spinner _spGenero;
    EditText _etBuscar;
    ImageView _imgBuscar;
    String url = "http://192.168.0.155:81/ventacds/listaCds.php";
    CdListaAdapter adapter;
    //StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cds);


        recyclerView = findViewById(R.id.rcvCds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cdsList=new ArrayList<>();

       _spGenero=findViewById(R.id.spGenero);
       _etBuscar=findViewById(R.id.etBuscar);
       //cargarRecycler();

       _spGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               cdsList.clear();
               cargarRecycler();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
        _imgBuscar=findViewById(R.id.imgBuscar);
        _imgBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdsList.clear();
                cargarRecycler();
            }
        });


    }

    private void cargarRecycler()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for(int i=0; i<array.length(); i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                cdsList.add(new Cd(object.getString("Id"),object.getString("Artista"),
                                                    object.getString("Album"), object.getString("Genero"),
                                                    object.getString("Anio"), object.getString("Precio"),
                                                    object.getString("Cantidad"),object.getString("Portada")));
                            }
                            adapter= new CdListaAdapter(CdsActivity.this,
                                            cdsList,CdsActivity.this);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CdsActivity.this, "Se detectaron problemas de red", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String > params=new HashMap<String, String>();
                params.put("gen",_spGenero.getSelectedItem().toString());
                params.put("parametro",_etBuscar.getText().toString());

                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent;
        if (id == R.id.cds) {
            intent=new Intent(CdsActivity.this, CdsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.compras) {
            intent=new Intent(CdsActivity.this, MisComprasActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.cerrarsesion) {
            intent=new Intent(CdsActivity.this, MainActivity.class);
            startActivity(intent);
            Sesion._cuenta="";
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
