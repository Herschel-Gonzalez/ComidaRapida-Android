package com.gorditasesperanza;

public class Usuarios {
    private String apellidos;
    private String calle;
    private String colonia;
    private String correo;
    private String nombre;
    private String numeroDeCasa;
    private String referencias;
    private String tipo;

    public Usuarios(){

    }

    public Usuarios(String apellidos, String calle, String colonia, String correo, String nombre, String numeroDeCasa, String referencias, String tipo) {
        this.apellidos = apellidos;
        this.calle = calle;
        this.colonia = colonia;
        this.correo = correo;
        this.nombre = nombre;
        this.numeroDeCasa = numeroDeCasa;
        this.referencias = referencias;
        this.tipo = tipo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCalle() {
        return calle;
    }

    public String getColonia() {
        return colonia;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroDeCasa() {
        return numeroDeCasa;
    }

    public String getReferencias() {
        return referencias;
    }

    public String getTipo() {
        return tipo;
    }
}
