package com.android.curso.cdsweb.Modelo;

/**
 * Created by Daniel on 7/12/2018.
 */

public class Compra {
    String Id;
    String Artista;
    String Album;
    String Fecha;
    String Cantidad;
    String PrecioFinal;
    String Portada;
    String Cuenta;
    String IdVenta;

    public Compra(String id, String artista, String album, String fecha, String cantidad, String precioFinal, String portada, String cuenta, String idVenta) {
        Id = id;
        Artista = artista;
        Album = album;
        Fecha = fecha;
        Cantidad = cantidad;
        PrecioFinal = precioFinal;
        Portada = portada;
        Cuenta = cuenta;
        IdVenta = idVenta;
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

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getPrecioFinal() {
        return PrecioFinal;
    }

    public void setPrecioFinal(String precioFinal) {
        PrecioFinal = precioFinal;
    }

    public String getPortada() {
        return Portada;
    }

    public void setPortada(String portada) {
        Portada = portada;
    }

    public String getCuenta() {
        return Cuenta;
    }

    public void setCuenta(String cuenta) {
        Cuenta = cuenta;
    }

    public String getIdVenta() {
        return IdVenta;
    }

    public void setIdVenta(String idVenta) {
        IdVenta = idVenta;
    }
}
