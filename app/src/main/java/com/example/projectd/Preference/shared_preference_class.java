package com.example.projectd.Preference;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class shared_preference_class {

    /** Pendeklarasian key-data berupa String, untuk sebagai wadah penyimpanan data.
     * Jadi setiap data mempunyai key yang berbeda satu sama lain */
    static final String LOGGED_IN_USER = "logged_in_user", LOGGED_IN_NAME = "logged_in_name";
    static final String LOGGED_IN_TANGGAL_LAHIR = "logged_in_dob", LOGGED_IN_ALAMAT = "logged_in_address";
    static final String LOGGED_IN_EMAIL = "logged_in_email", LOGGED_IN_TELP = "logged_in_phone";
    static final String KEY_STATUS_LOGIN = "logged_in_status", LOGGED_IN_JENISKELAMIN = "logged_in_gender";

    /** Pendlakarasian Shared Preferences yang berdasarkan paramater context */
    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /** Deklarasi Edit Preferences dan mengubah data
     *  yang memiliki key KEY_USERNAME_SEDANG_LOGIN dengan parameter username */
    public static void setLoggedInUser(Context context, String username, String name, String dob, String address, String email, String phone, String gender){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(LOGGED_IN_USER, username);
        editor.putString(LOGGED_IN_NAME, name);
        editor.putString(LOGGED_IN_TANGGAL_LAHIR, dob);
        editor.putString(LOGGED_IN_ALAMAT, address);
        editor.putString(LOGGED_IN_EMAIL, email);
        editor.putString(LOGGED_IN_TELP, phone);
        editor.putString(LOGGED_IN_JENISKELAMIN, gender);
        editor.putBoolean(KEY_STATUS_LOGIN, true);
        editor.apply();
    }

    /** Mengembalikan nilai dari key KEY_USERNAME_SEDANG_LOGIN berupa String */
    public static ArrayList<String> getLoggedInUser(Context context){
        ArrayList<String> LOGGED_ARRAY = new ArrayList<String>();
        LOGGED_ARRAY.add(getSharedPreference(context).getString(LOGGED_IN_USER,""));
        LOGGED_ARRAY.add(getSharedPreference(context).getString(LOGGED_IN_NAME,""));
        LOGGED_ARRAY.add(getSharedPreference(context).getString(LOGGED_IN_TANGGAL_LAHIR,""));
        LOGGED_ARRAY.add(getSharedPreference(context).getString(LOGGED_IN_ALAMAT,""));
        LOGGED_ARRAY.add(getSharedPreference(context).getString(LOGGED_IN_EMAIL,""));
        LOGGED_ARRAY.add(getSharedPreference(context).getString(LOGGED_IN_TELP,""));
        LOGGED_ARRAY.add(getSharedPreference(context).getString(LOGGED_IN_JENISKELAMIN,""));
        return (LOGGED_ARRAY);
    }

    /** Mengembalikan nilai dari key KEY_STATUS_SEDANG_LOGIN berupa boolean */
    public static boolean getLoggedInStatus(Context context){
        return getSharedPreference(context).getBoolean(KEY_STATUS_LOGIN,false);
    }

    public static String getUsername(Context context){
        return getSharedPreference(context).getString(LOGGED_IN_USER,"");
    }

    /** Deklarasi Edit Preferences dan menghapus data, sehingga menjadikannya bernilai default
     *  khusus data yang memiliki key KEY_USERNAME_SEDANG_LOGIN dan KEY_STATUS_SEDANG_LOGIN */
    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(LOGGED_IN_USER);
        editor.remove(LOGGED_IN_NAME);
        editor.remove(LOGGED_IN_TANGGAL_LAHIR);
        editor.remove(LOGGED_IN_ALAMAT);
        editor.remove(LOGGED_IN_EMAIL);
        editor.remove(LOGGED_IN_TELP);
        editor.remove(LOGGED_IN_JENISKELAMIN);
        editor.remove(KEY_STATUS_LOGIN);
        editor.apply();
    }
}
