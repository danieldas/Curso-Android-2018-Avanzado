package com.android.curso.cdsweb.Adaptador;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.curso.cdsweb.ItemClickListener;
import com.android.curso.cdsweb.Modelo.Compra;
import com.android.curso.cdsweb.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class CompraListaAdapter extends RecyclerView.Adapter<CompraListaAdapter.MyViewHolder>{
    private List<Compra> comprasList;
    private Context context;
    private LayoutInflater inflater;
    Activity mActivity;

    public CompraListaAdapter(Context context, List<Compra> comprasList, Activity mActivity) {
        //parámetros que se envian desde MainActivity
        this.context = context;
        this.comprasList = comprasList;
        this.mActivity=mActivity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CompraListaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = inflater.inflate(R.layout.compras_items, parent, false);
        return new CompraListaAdapter.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CompraListaAdapter.MyViewHolder holder, int position) {

        final Compra compras = comprasList.get(position);
        Glide.with(context).load(compras.getPortada()).into(holder._imgPortada);
        holder._tvArtista.setText("Artista: "+compras.getArtista());
        holder._tvAlbum.setText("Album: "+compras.getAlbum());
        holder._tvFecha.setText("Fecha: "+compras.getFecha());
        holder._tvPrecio.setText("Precio: "+compras.getPrecioFinal());
        holder._tvCantidad.setText("Cantidad: "+compras.getCantidad());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return comprasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView _tvArtista, _tvAlbum, _tvFecha, _tvPrecio, _tvCantidad;
        private ImageView _imgPortada;
        ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            _tvArtista=itemView.findViewById(R.id.tvArtistaCompra);
            _tvAlbum=itemView.findViewById(R.id.tvAlbumCompra);
            _tvFecha=itemView.findViewById(R.id.tvFecha);
            _tvPrecio=itemView.findViewById(R.id.tvPrecioFinal);
            _tvCantidad=itemView.findViewById(R.id.tvCantidadCompra);
            _imgPortada=itemView.findViewById(R.id.imgPortadaCompra);

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
}

