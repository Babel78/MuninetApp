package org.giotfisi.muninetapp.Fragments;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.giotfisi.muninetapp.AdapterPubView;
import org.giotfisi.muninetapp.Models.Publicacion;
import org.giotfisi.muninetapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    ArrayList<Publicacion> lista_publicaciones;
    RecyclerView recyclerView_publicaciones;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference();


    public InicioFragment() {
        // Required empty public constructor
    }
    public static InicioFragment newInstance(){
        return new InicioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_inicio, container, false);
        lista_publicaciones=new ArrayList<>();
        recyclerView_publicaciones=v.findViewById(R.id.my_recycler);
        final AdapterPubView adapterPubView=new AdapterPubView(getContext(),lista_publicaciones);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

                adapterPubView.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        recyclerView_publicaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_publicaciones.setAdapter(adapterPubView);
        return v;
    }

    public void showData(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            String username=ds.getValue(Publicacion.class).getName_user();
            String comentario=ds.getValue(Publicacion.class).getComentario();
            String URL_Image=ds.getValue(Publicacion.class).getUrl_imagen();
            Publicacion p=new Publicacion(username,comentario,URL_Image);
            lista_publicaciones.add(p);
        }
    }

}
