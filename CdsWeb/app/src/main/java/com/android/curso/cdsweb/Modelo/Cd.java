package com.android.curso.cdsweb.Modelo;

/**
 * Created by Daniel on 8/12/2018.
 */

public class Cd {
    String Id;
    String Artista;
    String Album;
    String Genero;
    String Anio;
    String Precio;
    String Cantidad;
    String Portada;

    public Cd(String id, String artista, String album, String genero, String anio, String precio, String cantidad, String portada) {
        Id = id;
        Artista = artista;
        Album = album;
        Genero = genero;
        Anio = anio;
        Precio = precio;
        Cantidad = cantidad;
        Portada = portada;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getArtista() {
        return Artista;
    }

    public void setArtista(String artista) {
        Artista = artista;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getAnio() {
        return Anio;
    }

    public void setAnio(String anio) {
        Anio = anio;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getPortada() {
        return Portada;
    }

    public void setPortada(String portada) {
        Portada = portada;
    }
}
