package com.android.curso.cdsweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.curso.cdsweb.Adaptador.CompraListaAdapter;
import com.android.curso.cdsweb.Modelo.Compra;
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

public class MisComprasActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Compra> comprasList;
    String url = "http://192.168.0.155:81/ventacds/listaVentas.php";
    CompraListaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_compras);


        recyclerView = findViewById(R.id.rcvCompras);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        comprasList=new ArrayList<>();

        cargarRecycler();
    }

    private void cargarRecycler()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array= new JSONArray(response);
                            for(int i=0; i<array.length(); i++)
                            {
                                JSONObject object= array.getJSONObject(i);
                                comprasList.add(new Compra(object.getString("Id"), object.getString("Artista"),
                                                            object.getString("Album"), object.getString("Fecha"),
                                                            object.getString("Cantidad"), object.getString("PrecioFinal"),
                                                            object.getString("Portada"), object.getString("Cuenta"),
                                                            object.getString("IdVenta")));
                            }
                            adapter=new CompraListaAdapter(MisComprasActivity.this, comprasList, MisComprasActivity.this);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MisComprasActivity.this, "Se detectaron problemas de red", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String > params=new HashMap<String, String>();
                params.put("cuenta",Sesion._cuenta);

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
            intent=new Intent(MisComprasActivity.this, CdsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.compras) {
            intent=new Intent(MisComprasActivity.this, MisComprasActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.cerrarsesion) {
            Sesion._cuenta="";
            intent=new Intent(MisComprasActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
