package com.android.curso.notassqlite.Adaptador;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.curso.notassqlite.DetalleActivity;
import com.android.curso.notassqlite.ItemClickListener;
import com.android.curso.notassqlite.Modelo.Nota;
import com.android.curso.notassqlite.R;

import java.util.List;


public class NotaListaAdapter extends RecyclerView.Adapter<NotaListaAdapter.ListaViewHolder> {
    private Context mainContext;
    private List<Nota> items;   // Lista de notas de modelo

    public NotaListaAdapter(List<Nota> items, Context mainContext) {
        this.mainContext = mainContext;
        this.items = items;
    }

    static class ListaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //declarar texteview, img
        TextView _tvTitulo, _tvFecha, _tvId, _tvTipo;
        ImageView _imgIcono;
        ItemClickListener itemClickListener;

        public ListaViewHolder(View v) {
            super(v);

            //Llamar a los elementos de xml
            this._tvId = v.findViewById(R.id.tvId);
            this._tvTitulo = v.findViewById(R.id.tvTitulo);
            this._tvFecha = v.findViewById(R.id.tvFecha);
            this._tvTipo = v.findViewById(R.id.tvTipo);
            this._imgIcono = v.findViewById(R.id.imgIcono);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)  {
            this.itemClickListener.onItemClick(view,getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
    }

    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //Recuperar el layout nota_items en el adaptador
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.nota_items, viewGroup, false);

        return new ListaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListaViewHolder viewHolder, int position) {

        Nota item = items.get(position);
        viewHolder.itemView.setTag(item);  //guardar item
        //mandar valores a los elementos xml
        viewHolder._tvId.setText("Nº: " + item.getIdNota());
        viewHolder._tvTitulo.setText("Título: " + item.getTitulo());
        viewHolder._tvFecha.setText("Fecha: " + item.getFecha());
        viewHolder._tvTipo.setText("Tipo: " + item.getTipo());

        if(item.getTipo().equals("Música"))
        {
            viewHolder._imgIcono.setImageResource(R.drawable.img_musica);
        }
        else if(item.getTipo().equals("Recordatorio"))
        {
            viewHolder._imgIcono.setImageResource(R.drawable.img_recordatorio);
        }
        else if(item.getTipo().equals("Programación"))
        {
            viewHolder._imgIcono.setImageResource(R.drawable.img_programacion);
        }
        else if(item.getTipo().equals("Tareas"))
        {
            viewHolder._imgIcono.setImageResource(R.drawable.img_tareas);
        }


        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, final int pos) {
                //Pasar a otra actividad mandando parámetros
                Intent intent=new Intent(mainContext, DetalleActivity.class);
                intent.putExtra("_id", items.get(pos).getIdNota());
                intent.putExtra("_titulo", items.get(pos).getTitulo());
                intent.putExtra("_contenido", items.get(pos).getContenido());
                intent.putExtra("_fecha", items.get(pos).getFecha());
                intent.putExtra("_tipo", items.get(pos).getTipo());
                mainContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
