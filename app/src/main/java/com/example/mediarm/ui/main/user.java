package com.example.mediarm.ui.main;

public class user {
    String Nombre, Apellido, Contraseña, Email, Numero;

    public user(){}

    public user(String nombre, String apellido, String contraseña, String email, String numero) {
        Numero = numero;
        Apellido = apellido;
        Email = email;
        this.Contraseña = contraseña;
        this.Nombre = nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }
}
