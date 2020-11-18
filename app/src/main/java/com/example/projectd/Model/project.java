package com.example.projectd.Model;

public class project {

    private String nama_project;
    private String start_project;
    private String end_project;
    private String desc_project;
    private String status_project;
    private String no_hp;
    private String max_orang;
    private String user_id;

    public String getNama_project() {
        return nama_project;
    }

    public void setNama_project(String nama_project) {
        this.nama_project = nama_project;
    }

    public String getStart_project() {
        return start_project;
    }

    public void setStart_project(String start_project) {
        this.start_project = start_project;
    }

    public String getEnd_project() {
        return end_project;
    }

    public void setEnd_project(String end_project) {
        this.end_project = end_project;
    }

    public String getDesc_project() {
        return desc_project;
    }

    public void setDesc_project(String desc_project) {
        this.desc_project = desc_project;
    }

    public String getStatus_project() {
        return status_project;
    }

    public void setStatus_project(String status_project) {
        this.status_project = status_project;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getMax_orang() {
        return max_orang;
    }

    public void setMax_orang(String max_orang) {
        this.max_orang = max_orang;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "project{" +
                "nama_project='" + nama_project + '\'' +
                ", start_project='" + start_project + '\'' +
                ", end_project='" + end_project + '\'' +
                ", desc_project='" + desc_project + '\'' +
                ", status_project='" + status_project + '\'' +
                ", no_hp='" + no_hp + '\'' +
                ", max_orang='" + max_orang + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
