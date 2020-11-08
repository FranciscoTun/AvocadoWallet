package com.example.avocadowallet.Clases;

import java.util.Date;

public class Usuario {
    int idUsuario;
    String Username;
    String Name;
    String Lastname;
    String Email;
    String Phone;
    String Password;
    int status;
    double monto;
    //int edad;
    //Date fecnac;

    public Usuario(){

    }

    public Usuario(int idUsuario, String username, String name, String lastname, String email, String phone, String password, int status, double monto ) {
        this.idUsuario = idUsuario;
        Username = username;
        Name = name;
        Lastname = lastname;
        Email = email;
        Phone = phone;
        Password = password;
        this.status = status;
        this.monto = monto;

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
