package fr.ece.edu.ec.chess_tracker.business;

import java.io.Serializable;

public class Player implements Serializable {
    private int idUser;
    private String email;
    private String pwd;
    private int Elo;
    private String name;
    private String surname;

    public Player(String email, String pwd, int elo, String name, String surname) {
        this.email = email;
        this.pwd = pwd;
        Elo = elo;
        this.name = name;
        this.surname = surname;
    }

    public Player(int idUser, String email, String pwd, int elo, String name, String surname) {
        this.idUser = idUser;
        this.email = email;
        this.pwd = pwd;
        Elo = elo;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getElo() {
        return Elo;
    }

    public void setElo(int elo) {
        Elo = elo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", Elo=" + Elo +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
