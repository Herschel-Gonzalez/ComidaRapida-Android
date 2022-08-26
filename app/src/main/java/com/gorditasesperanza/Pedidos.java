package com.gorditasesperanza;

public class Pedidos {
    private String cantidad;
    private String comentarios;
    private String correo;
    private String descripcion;
    private String estado;
    private String fechaDeEntrega;
    private String fechaDeSolicitud;
    private String idPedido;
    private String imagen;
    private String ingredientes;
    private String nombreDelCliente;
    private String platillo;
    private String precio;
    private String calle;
    private String colonia;
    private String numeroCasa;
    private String referencias;


    public Pedidos(){

    }

    public Pedidos(String cantidad, String comentarios, String correo, String descripcion, String estado, String fechaDeEntrega, String fechaDeSolicitud, String idPedido, String imagen, String ingredientes, String nombreDelCliente, String platillo, String precio,String calle, String colonia,String numeroCasa,String referencias) {
        this.cantidad = cantidad;
        this.comentarios = comentarios;
        this.correo = correo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaDeEntrega = fechaDeEntrega;
        this.fechaDeSolicitud = fechaDeSolicitud;
        this.idPedido = idPedido;
        this.imagen = imagen;
        this.ingredientes = ingredientes;
        this.nombreDelCliente = nombreDelCliente;
        this.platillo = platillo;
        this.precio = precio;
        this.calle = calle;
        this.colonia  = colonia;
        this.numeroCasa=numeroCasa;
        this.referencias=referencias;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaDeEntrega() {
        return fechaDeEntrega;
    }

    public void setFechaDeEntrega(String fechaDeEntrega) {
        this.fechaDeEntrega = fechaDeEntrega;
    }

    public String getFechaDeSolicitud() {
        return fechaDeSolicitud;
    }

    public void setFechaDeSolicitud(String fechaDeSolicitud) {
        this.fechaDeSolicitud = fechaDeSolicitud;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getNombreDelCliente() {
        return nombreDelCliente;
    }

    public void setNombreDelCliente(String nombreDelCliente) {
        this.nombreDelCliente = nombreDelCliente;
    }

    public String getPlatillo() {
        return platillo;
    }

    public void setPlatillo(String platillo) {
        this.platillo = platillo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
