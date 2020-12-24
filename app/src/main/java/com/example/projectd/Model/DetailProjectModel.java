package com.example.projectd.Model;

import java.io.Serializable;

public class DetailProjectModel implements Serializable {
    private String id;
    private String id_project;
    private String id_user;
    private String nama;
    private String no_hp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_project() {
        return id_project;
    }

    public void setId_project(String id_project) {
        this.id_project = id_project;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    @Override
    public String toString() {
        return "DetailProjectModel{" +
                "id='" + id + '\'' +
                ", id_project='" + id_project + '\'' +
                ", id_user='" + id_user + '\'' +
                ", nama='" + nama + '\'' +
                ", no_hp='" + no_hp + '\'' +
                '}';
    }
}
