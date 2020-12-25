package com.example.projectd.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBProject extends SQLiteOpenHelper {

    private static final String namaDatabse = "PROJECT_D";
    private static final int versiDatabase = 1;

    static abstract class MyColumns implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "tb_project";
        static final String id_project = "id";
        static final String nama_project = "nama_project";
        static final String start_project = "start_project";
        static final String end_project = "end_project";
        static final String desc_project = "desc_project";
        static final String status_project = "status_project";
        static final String no_hp = "no_hp";
        static final String max_orang = "max_orang";
        static final String user_id = "user_id";
        static final String project_image = "project_image";
    }

    private static final String SQL_CREATE_ENTRIES_PROJECT_D = "CREATE TABLE "+MyColumns.NamaTabel+
            "( "+ MyColumns.id_project+" INT PRIMARY KEY, "+MyColumns.nama_project+" TEXT NOT NULL, "+MyColumns.start_project+
            " TEXT NOT NULL, "+MyColumns.end_project+" TEXT NOT NULL, "+MyColumns.desc_project+
            " TEXT NOT NULL, "+MyColumns.status_project+" INT NOT NULL, "+MyColumns.no_hp+
            " TEXT NOT NULL, "+MyColumns.max_orang+" INT NOT NULL, "+MyColumns.user_id+" INT NOT NULL, "+MyColumns.project_image+" TEXT )";

    //Query yang digunakan untuk mengupgrade Tabel
    private static final String SQL_DELETE_ENTRIES_PROJECT_D = "DROP TABLE IF EXISTS "+MyColumns.NamaTabel;

    public DBProject(Context context) {
        super(context, namaDatabse, null, versiDatabase);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_PROJECT_D);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_PROJECT_D);
        onCreate(db);
    }
}
