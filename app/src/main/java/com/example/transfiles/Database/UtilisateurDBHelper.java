package com.example.transfiles.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.annotation.Nullable;
import com.example.transfiles.Database.UtilisateurDbSchema.UtilisateurTable;
import com.example.transfiles.Database.UtilisateurDbSchema.LobbyTable;
import com.example.transfiles.Database.UtilisateurDbSchema.UtilisateurLobbyTable;
public class UtilisateurDBHelper extends SQLiteOpenHelper
{
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "utilisateurDatabase";

    public UtilisateurDBHelper(@Nullable Context context)
    {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //table Utilisateur
        db.execSQL("CREATE TABLE "+ UtilisateurTable.NAME + "(" +
                "id integer primary key autoincrement," +
                UtilisateurTable.Cols.PRENOM + ", " +
                UtilisateurTable.Cols.NOM + ", " +
                UtilisateurTable.Cols.MDP + ", " +
                UtilisateurTable.Cols.EMAIL + ", " +
                UtilisateurTable.Cols.TEL + ")");
        //table Lobby
        db.execSQL("CREATE TABLE "+ UtilisateurDbSchema.LobbyTable.NAME + "(" +
                "id integer primary key autoincrement," +
                LobbyTable.Cols.NOM + "," +
                LobbyTable.Cols.MDP + ")");
        //table UtilisateurLobby
        db.execSQL("CREATE TABLE "+ UtilisateurDbSchema.UtilisateurLobbyTable.NAME + "(" +
                "id integer primary key autoincrement," +
                UtilisateurLobbyTable.Cols.HISTORIQUE + ")");
        //table background
        db.execSQL("CREATE TABLE "+ UtilisateurDbSchema.BackgroundTable.NAME + "(" +
                "id integer primary key autoincrement," +
                UtilisateurDbSchema.BackgroundTable.Cols.VALUER + "," +
                UtilisateurDbSchema.BackgroundTable.Cols.VALUEG + "," +
                UtilisateurDbSchema.BackgroundTable.Cols.VALUEB + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
