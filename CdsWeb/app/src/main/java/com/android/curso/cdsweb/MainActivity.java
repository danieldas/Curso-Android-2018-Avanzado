package com.android.curso.cdsweb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText _etCuenta, _etPassword;
    Button _btnIngresar;
    final Context c = this;
    private RequestQueue request;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _etCuenta=findViewById(R.id.etCuenta);
        _etPassword=findViewById(R.id.etPassword);
        _btnIngresar=findViewById(R.id.btnIngresar);

        _btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IniciarSesion();
            }
        });
    }


    private void IniciarSesion()
    {
        request=Volley.newRequestQueue(this);
        String url="http://192.168.0.155:81/ventacds/login.php";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.names().get(0).equals("Cuenta"))
                    {
                        Intent intent=new Intent(MainActivity.this, CdsActivity.class);
                        startActivity(intent);
                        Sesion._cuenta=jsonObject.getString("Cuenta");
                        finish();
                    }
                    else{
                        Toast.makeText(c, "credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(c, "Problemas de red", Toast.LENGTH_SHORT).show();
                    }
                }

        )
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params= new HashMap<String, String>();
                params.put("cuenta",_etCuenta.getText().toString());
                params.put("password",_etPassword.getText().toString());
                return params;
            }
        };
        request.add(stringRequest);
    }
}
