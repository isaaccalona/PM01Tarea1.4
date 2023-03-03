package com.example.pm01tarea14.Class;


import java.sql.Blob;

public class Usuarios {
    private int codigo;
    private String nombre;
    private String descripcion;
    private Blob image;

    public Usuarios(int codigo, String nombre, String descripcion, Blob image) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.image = image;
    }
    public Usuarios() {}

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
