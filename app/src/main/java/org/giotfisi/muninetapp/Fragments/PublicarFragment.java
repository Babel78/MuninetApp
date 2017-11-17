package org.giotfisi.muninetapp.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.giotfisi.muninetapp.Models.Publicacion;
import org.giotfisi.muninetapp.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicarFragment extends Fragment {

    ImageView image_foto_tomar,image_foto_publicar;
    Button btn_publicar;
    EditText ed_comentario;

    FirebaseStorage storage=FirebaseStorage.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();

    //Data
    String Url_imagen="";
    Bitmap bitmap;

    public PublicarFragment() {
    }
    public static PublicarFragment newInstance(){
        return new PublicarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_publicar, container, false);
        image_foto_publicar=v.findViewById(R.id.foto_publicar);
        image_foto_tomar=v.findViewById(R.id.foto_img);
        btn_publicar=v.findViewById(R.id.btn_publicar);
        ed_comentario=v.findViewById(R.id.ed_comentario);
        image_foto_tomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarIntent();
            }
        });

        btn_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharpref=getContext().getSharedPreferences("fnombre",getContext().MODE_PRIVATE);
                String nombre=sharpref.getString("Nombre","Usuario");
                String n=nombre.trim().toLowerCase();
                    if(!ed_comentario.getText().toString().equals("")){
                        if(bitmap!=null) {
                            uploadPhoto(bitmap, n,ed_comentario.getText().toString());
                        }
                        else{
                            justComment(nombre,ed_comentario.getText().toString());
                        }
                        Toast.makeText(getContext(),"Publicacion Guardada",Toast.LENGTH_LONG).show();
                        limpiar();
                    }
                    else{
                        Toast.makeText(getContext(),"Escriba un Comentario",Toast.LENGTH_LONG).show();
                    }

            }
        });

        return v;
    }

    private void llamarIntent(){
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getContext().getPackageManager())!=null){
            startActivityForResult(takePictureIntent,1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK ){
            switch (requestCode) {
                case 1:
                    Bundle extras=data.getExtras();
                    Bitmap imageBitmap= (Bitmap) extras.get("data");
                    image_foto_publicar.setImageBitmap(imageBitmap);
                    bitmap=imageBitmap;
                    break;
            }
        }
    }

    private byte[] getImageCompressed(Bitmap foto){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.JPEG,100,baos);
        return baos.toByteArray();
    }

    private void uploadPhoto(Bitmap photo, String Id, final String comentario){
        final String idImage=Id.concat(".jpg");
        Random r=new Random();
        int num_random=r.nextInt(999999);
        final String path="Fotos/"+num_random+idImage;
        StorageReference storageReference=storage.getReference(path);
        final byte[] data=getImageCompressed(photo);

        SharedPreferences sharpref=getContext().getSharedPreferences("fnombre",getContext().MODE_PRIVATE);
        String nombre=sharpref.getString("Nombre","Usuario");
        final String name=nombre.trim().toLowerCase();

        UploadTask uploadTask=storageReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri url=taskSnapshot.getDownloadUrl();;
                UpdateDataBase(name,comentario,url.toString());
            }
        });
    }
    private void UpdateDataBase(String username,String comentario,String UrlImage){
        DatabaseReference databaseReference=database.getReference();
        Publicacion p=new Publicacion(username,comentario,UrlImage);
        databaseReference.push().setValue(p);
    }
    private void justComment(String username ,String comentario){
        DatabaseReference databaseReference=database.getReference();
        Publicacion p=new Publicacion(username,comentario,"");
        databaseReference.push().setValue(p);
    }


    private void limpiar(){
        ed_comentario.setText("");
    }
}
