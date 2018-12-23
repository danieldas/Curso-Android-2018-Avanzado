package com.android.curso.cdsweb.Adaptador;


import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.curso.cdsweb.ItemClickListener;
import com.android.curso.cdsweb.Modelo.Cd;
import com.android.curso.cdsweb.R;
import com.android.curso.cdsweb.Sesion;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdListaAdapter extends RecyclerView.Adapter<CdListaAdapter.MyViewHolder>{
    private List<Cd> cdsList;
    private Context context;
    private LayoutInflater inflater;
    Activity mActivity;

    public CdListaAdapter(Context context, List<Cd> cdsList, Activity mActivity) {
        //parámetros que se envian desde la actividad
        this.context = context;
        this.cdsList = cdsList;
        this.mActivity=mActivity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = inflater.inflate(R.layout.cds_items, parent, false);
        return new MyViewHolder(rootView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView _tvArtista, _tvAlbum, _tvGenero, _tvPrecio, _tvCantidad;
        private ImageView _imgPortada;
        ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            _tvArtista = itemView.findViewById(R.id.tvArtista);
            _tvAlbum = itemView.findViewById(R.id.tvAlbum);
            _tvGenero = itemView.findViewById(R.id.tvGenero);
            _tvPrecio = itemView.findViewById(R.id.tvPrecio);
            _tvCantidad = itemView.findViewById(R.id.tvCantidad);
            _imgPortada = itemView.findViewById(R.id.imgPortada);



            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Cd cds = cdsList.get(position);
        //Pasar valor a los elementos xml
        holder._tvAlbum.setText("Album: "+cds.getAlbum());
        holder._tvArtista.setText("Artista: "+cds.getArtista());
        holder._tvGenero.setText("Género: "+cds.getGenero());
        holder._tvPrecio.setText("Precio: "+cds.getPrecio());
        holder._tvCantidad.setText("Cantidad: "+cds.getCantidad());

        Glide.with(context).load(cds.getPortada()).into(holder._imgPortada);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mView = layoutInflaterAndroid.inflate(R.layout.compra_nueva, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput.setCancelable(false);
                final AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

                Button _btnComprar=mView.findViewById(R.id.btnComprar);
                TextView _tvArtistaCompra=mView.findViewById(R.id.tvArtistaNuevo);
                TextView _tvAlbumCompra=mView.findViewById(R.id.tvAlbumNuevo);
                final EditText _etCantidadCompra=mView.findViewById(R.id.etCantidad);
                ImageView _imgPortadaCompra=mView.findViewById(R.id.imgPortadaNueva);

                _tvAlbumCompra.setText(cds.getAlbum());
                _tvArtistaCompra.setText(cds.getArtista());
                Glide.with(context).load(cds.getPortada()).into(_imgPortadaCompra);



                _btnComprar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar Cal = Calendar.getInstance();
                        final String fechaActual = Cal.get(Cal.YEAR) + "-" + (Cal.get(Cal.MONTH) + 1) + "-" + Cal.get(Cal.DATE);
                        final String cantidad=_etCantidadCompra.getText().toString();
                        final String fkUsuario=Sesion._cuenta;
                        final String fkCd=cds.getId();

                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url ="http://192.168.0.155:81/ventacds/registrarCompra.php";

                        if(Integer.parseInt(cantidad)<=Integer.parseInt(cds.getCantidad()))
                        {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            alertDialogAndroid.hide();
                                            Toast.makeText(context, "Compra realizada correctamente", Toast.LENGTH_LONG).show();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(context,"No se pudo realizar la accion", Toast.LENGTH_LONG).show();
                                }
                            })
                            {
                                @Override
                                protected Map<String, String> getParams()
                                {
                                    Map<String, String > params=new HashMap<String, String>();

                                    params.put("fecha", fechaActual);
                                    params.put("fkCd", fkCd);
                                    params.put("fkUsuario", fkUsuario);
                                    params.put("cantidad", cantidad);


                                    return params;
                                }
                            };
                            queue.add(stringRequest);
                        }
                        else
                        {
                            Toast.makeText(context, "La cantidad debe ser menor o igual al stock", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

    }
    @Override
    public int getItemCount() {
        return cdsList.size();
    }


}

