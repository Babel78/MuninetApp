package org.giotfisi.muninetapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.giotfisi.muninetapp.Models.Publicacion;

import java.util.ArrayList;

/**
 * Created by Axel on 16/11/2017.
 */

public class AdapterPubView extends RecyclerView.Adapter<AdapterPubView.ViewHolderPubs> {

    ArrayList<Publicacion> lista_publicaciones;
    Context ctx;

    public AdapterPubView(Context context,ArrayList<Publicacion> publicacionArrayList){
        this.lista_publicaciones=publicacionArrayList;
        this.ctx=context;
    }

    @Override
    public ViewHolderPubs onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview,null,false);
        return new ViewHolderPubs(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPubs holder, int position) {
        holder.username.setText(lista_publicaciones.get(position).getName_user());
        holder.comentario.setText(lista_publicaciones.get(position).getComentario());
        Picasso.with(ctx)
                .load(lista_publicaciones.get(position).getUrl_imagen())
                .resize(300,250)
                .into(holder.imagen_pub);
    }

    @Override
    public int getItemCount() {
        return lista_publicaciones.size();
    }

    public class ViewHolderPubs extends RecyclerView.ViewHolder{

        TextView username,comentario;
        ImageView imagen_pub,imagen_user;

        public ViewHolderPubs(View v) {
            super(v);
            username=v.findViewById(R.id.tv_name);
            comentario=v.findViewById(R.id.tv_comentario);
            imagen_pub=v.findViewById(R.id.imag_pub);
            imagen_user=v.findViewById(R.id.img_user);
        }
    }
}
