package com.gorditasesperanza;

public class Platillos {
    private String nombre;
    private String ingredientes;
    private String descripcion;
    private String precio;
    private String imagen;

    public Platillos() {
    }

    public Platillos(String nombre, String ingredientes, String descripcion, String precio, String imagen) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
