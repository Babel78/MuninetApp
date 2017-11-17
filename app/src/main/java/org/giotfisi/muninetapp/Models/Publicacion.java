package org.giotfisi.muninetapp.Models;

/**
 * Created by Axel on 16/11/2017.
 */

public class Publicacion {

    private String name_user;
    private String comentario;
    private String Url_imagen;

    public Publicacion(String name_user, String comentario, String url_imagen) {
        this.name_user = name_user;
        this.comentario = comentario;
        Url_imagen = url_imagen;
    }

    public Publicacion(){

    }

    public String getUrl_imagen() {
        return Url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        Url_imagen = url_imagen;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
