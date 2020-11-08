package com.example.avocadowallet.Clases;

import java.util.Date;

public class Usuario {
    String Username;
    String Name;
    String Lastname;
    String Email;
    String Phone;
    String Password;
    int status;
    int edad;
    Date fecnac;

    public Usuario(){

    }

    public Usuario(String username, String name, String lastname, String email, String phone, String password, int status, int edad, Date fecnac) {
        Username = username;
        Name = name;
        Lastname = lastname;
        Email = email;
        Phone = phone;
        Password = password;
        this.status = status;
        this.edad = edad;
        this.fecnac = fecnac;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getFecnac() {
        return fecnac;
    }

    public void setFecnac(Date fecnac) {
        this.fecnac = fecnac;
    }
}
