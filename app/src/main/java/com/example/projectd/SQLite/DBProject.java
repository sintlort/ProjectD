package com.example.projectd.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBProject extends SQLiteOpenHelper {

    private static final String namaDatabse = "project_d.db";
    private static final int versiDatabase = 1;

    static abstract class MyColumns_1 implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "tb_user";
        static final String username = "username";
        static final String nama_user = "nama_user";
        static final String tgl_lahir = "tgl_lahir";
        static final String gender = "gender";
        static final String alamat = "alamat";
        static final String email = "email";
        static final String telp = "telp";
    }

    static abstract class MyColumns_2 implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "tb_project";
        static final String nama_project = "nama_project";
        static final String start_project = "start_project";
        static final String end_project = "end_project";
        static final String desc_project = "desc_project";
        static final String status_project = "status_project";
        static final String no_hp = "no_hp";
        static final String max_orang = "max_orang";
        static final String user_id = "user_id";
    }

    static abstract class MyColumns_3 implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "tb_detail_project";
        static final String id_project = "id_project";
        static final String id_user = "id_user";
        static final String nama = "nama";
        static final String noHP = "noHP";
    }

    //Query yang digunakan untuk membuat Tabel
    private static final String SQL_CREATE_ENTRIES_USER = "CREATE TABLE "+MyColumns_1.NamaTabel+
            "("+MyColumns_1.username+" TEXT NOT NULL, "+MyColumns_1.nama_user+
            " TEXT NOT NULL, "+MyColumns_1.tgl_lahir+" DATE NOT NULL, "+MyColumns_1.gender+
            " TEXT NOT NULL, "+MyColumns_1.alamat+" TEXT NOT NULL, "+MyColumns_1.email+
            " TEXT NOT NULL, "+MyColumns_1.telp+" TEXT NOT NULL)";

    private static final String SQL_CREATE_ENTRIES_PROJECT_D = "CREATE TABLE "+MyColumns_2.NamaTabel+
            "("+MyColumns_2.nama_project+" TEXT NOT NULL, "+MyColumns_2.start_project+
            " DATE NOT NULL, "+MyColumns_2.end_project+" DATE NOT NULL, "+MyColumns_2.desc_project+
            " TEXT NOT NULL, "+MyColumns_2.status_project+" BOOLEAN NOT NULL, "+MyColumns_2.no_hp+
            " TEXT NOT NULL, "+MyColumns_2.max_orang+" INT NOT NULL, "+MyColumns_2.user_id+" INT NOT NULL)";

    private static final String SQL_CREATE_ENTRIES_PROJECT_D_DETAIL = "CREATE TABLE "+MyColumns_3.NamaTabel+
            "("+MyColumns_3.id_project+" TEXT NOT NULL, "+MyColumns_3.id_user+
            " TEXT NOT NULL, "+MyColumns_3.nama+" DATE NOT NULL, "+MyColumns_3.noHP+
            " TEXT NOT NULL )";

    //Query yang digunakan untuk mengupgrade Tabel
    private static final String SQL_DELETE_ENTRIES_USER = "DROP TABLE IF EXISTS "+MyColumns_1.NamaTabel;
    private static final String SQL_DELETE_ENTRIES_PROJECT_D = "DROP TABLE IF EXISTS "+MyColumns_2.NamaTabel;
    private static final String SQL_DELETE_ENTRIES_PROJECT_D_DETAIL = "DROP TABLE IF EXISTS "+MyColumns_3.NamaTabel;

    public DBProject(Context context) {
        super(context, namaDatabse, null, versiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_USER);
        db.execSQL(SQL_CREATE_ENTRIES_PROJECT_D);
        db.execSQL(SQL_CREATE_ENTRIES_PROJECT_D_DETAIL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_USER);
        db.execSQL(SQL_DELETE_ENTRIES_PROJECT_D);
        db.execSQL(SQL_DELETE_ENTRIES_PROJECT_D_DETAIL);
        onCreate(db);
    }
}
