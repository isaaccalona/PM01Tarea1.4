package com.example.pm01tarea14.Transacciones;

public class Transacciones {
    public static final String NameDatabase = "PM01DB";
    public static final String personasTabla = "persona";

    public static final String codigo ="codigo";
    public static final String nombreCompleto = "nombreCompleto";
    public static final String descripcion = "descripcion";
    public static final String foto = "foto";

    public static final String createTablePersona = "CREATE TABLE " + personasTabla +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombreCompleto TEXT, descripcion TEXT, foto BLOB)";

    public static final String dropTablePersona = "DROP TABLE IF EXIST" + personasTabla;
}
