package com.example.projectd.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projectd.Model.project;
import com.example.projectd.Preference.shared_preference_class;
import java.util.ArrayList;
import java.util.List;

public class DBCRUDHelper {
    private Context context;
    private DBProject dbProject;
    private SQLiteDatabase sqLiteDatabase;

    public DBCRUDHelper(Context context) {
        this.context = context;
    }

    public DBCRUDHelper open(){
        dbProject = new DBProject(this.context);
        sqLiteDatabase = dbProject.getWritableDatabase();
        return this;
    }

    public void close(){
        dbProject.close();
    }

    public List<project> otherProjectQuery(){
        List<project> projects = new ArrayList<>();
        String rawQuery = "SELECT * FROM "+DBProject.MyColumns.NamaTabel+" WHERE "+DBProject.MyColumns.user_id+" != "+shared_preference_class.getLoggedInId(context)+" ORDER BY "+ DBProject.MyColumns.user_id+" DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery, null, null);
        if(cursor.moveToFirst()){
            do{
                project project = new project();
                project.setId(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBProject.MyColumns.id_project))));
                project.setNama_project(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.nama_project)));
                project.setStart_project(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.start_project)));
                project.setEnd_project(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.end_project)));
                project.setDesc_project(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.desc_project)));
                project.setStatus_project(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBProject.MyColumns.status_project))));
                project.setNo_hp(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.no_hp)));
                project.setMax_orang(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBProject.MyColumns.max_orang))));
                project.setUser_id(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBProject.MyColumns.user_id))));
                project.setProject_image(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.project_image)));
                projects.add(project);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return projects;
    }

    public List<project> myProjectQuery(){
        List<project> projects = new ArrayList<>();
        String rawQuery = "SELECT * FROM "+DBProject.MyColumns.NamaTabel+" WHERE "+DBProject.MyColumns.user_id+" == "+shared_preference_class.getLoggedInId(context)+" ORDER BY "+ DBProject.MyColumns.user_id+" DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery, null);
        if(cursor.moveToFirst()){
            do{
                project project = new project();
                project.setId(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBProject.MyColumns.id_project))));
                project.setNama_project(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.nama_project)));
                project.setStart_project(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.start_project)));
                project.setEnd_project(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.end_project)));
                project.setDesc_project(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.desc_project)));
                project.setStatus_project(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBProject.MyColumns.status_project))));
                project.setNo_hp(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.no_hp)));
                project.setMax_orang(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBProject.MyColumns.max_orang))));
                project.setUser_id(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBProject.MyColumns.user_id))));
                project.setProject_image(cursor.getString(cursor.getColumnIndexOrThrow(DBProject.MyColumns.project_image)));
                projects.add(project);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return projects;
    }

    public void trunate(){
        sqLiteDatabase.execSQL(" delete from " +DBProject.MyColumns.NamaTabel);
    }

    public Long insert(project Project){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBProject.MyColumns.id_project, Integer.parseInt(Project.getId()));
        contentValues.put(DBProject.MyColumns.nama_project, Project.getNama_project());
        contentValues.put(DBProject.MyColumns.start_project, Project.getStart_project());
        contentValues.put(DBProject.MyColumns.end_project, Project.getEnd_project());
        contentValues.put(DBProject.MyColumns.desc_project, Project.getDesc_project());
        contentValues.put(DBProject.MyColumns.status_project, Integer.parseInt(Project.getStatus_project()));
        contentValues.put(DBProject.MyColumns.no_hp, Project.getNo_hp());
        contentValues.put(DBProject.MyColumns.max_orang, Integer.parseInt(Project.getMax_orang()));
        contentValues.put(DBProject.MyColumns.user_id, Integer.parseInt(Project.getUser_id()));
        contentValues.put(DBProject.MyColumns.project_image, Project.getProject_image());

        return sqLiteDatabase.insert(DBProject.MyColumns.NamaTabel, null, contentValues);
    }
}
