package com.example.projectd.Model;

import java.io.Serializable;

public class progressImageModel implements Serializable {
    private String id;
    private String id_project;
    private String id_user;
    private String image;
    private String created_at;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "progressImageModel{" +
                "id='" + id + '\'' +
                ", id_project='" + id_project + '\'' +
                ", id_user='" + id_user + '\'' +
                ", image='" + image + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
