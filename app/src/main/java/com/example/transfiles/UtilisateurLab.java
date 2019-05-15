package com.example.transfiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.example.transfiles.Database.UtilisateurCursorWrapper;
import com.example.transfiles.Database.UtilisateurDBHelper;
import com.example.transfiles.Database.UtilisateurDbSchema;
import static com.example.transfiles.Database.UtilisateurDbSchema.UtilisateurTable.NAME;
import static com.example.transfiles.Database.UtilisateurDbSchema.UtilisateurTable.Cols.PRENOM;
import static com.example.transfiles.Database.UtilisateurDbSchema.UtilisateurTable.Cols.NOM;
import static com.example.transfiles.Database.UtilisateurDbSchema.UtilisateurTable.Cols.EMAIL;
import static com.example.transfiles.Database.UtilisateurDbSchema.UtilisateurTable.Cols.MDP;
import static java.sql.Types.NULL;
public class UtilisateurLab {

    private static UtilisateurLab sUtilisateurLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static UtilisateurLab get(Context context)
    {
        if (sUtilisateurLab == null)
        {
            sUtilisateurLab = new UtilisateurLab(context);
        }
        return sUtilisateurLab;
    }

    public UtilisateurLab(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new UtilisateurDBHelper(mContext).getWritableDatabase();
    }
    //tableUtilisateur
    public void addUtilisateur(utilisateur Utilisateur)
    {
        ContentValues values = getContentValues(Utilisateur);
        mDatabase.insert(UtilisateurDbSchema.UtilisateurTable.NAME, null, values);
    }

    private UtilisateurCursorWrapper queryBackground (String whereClause, String[]whereArgs)
    {
        Cursor cursor = mDatabase.query(
                UtilisateurDbSchema.BackgroundTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new UtilisateurCursorWrapper(cursor);
    }


    public void updateUtilisateur(utilisateur Utilisateur, int id)
    {
        ContentValues values = getContentValues(Utilisateur);
        mDatabase.update(UtilisateurDbSchema.UtilisateurTable.NAME, values, "id = ?", new String[]{String.valueOf(id)});
    }

    private static ContentValues getContentValues(utilisateur Utilisateur)
    {
        ContentValues values = new ContentValues();
        values.put(UtilisateurDbSchema.UtilisateurTable.Cols.PRENOM, Utilisateur.getPrenom());
        values.put(UtilisateurDbSchema.UtilisateurTable.Cols.NOM, Utilisateur.getNom());
        values.put(UtilisateurDbSchema.UtilisateurTable.Cols.MDP, Utilisateur.getMdp());
        values.put(UtilisateurDbSchema.UtilisateurTable.Cols.EMAIL, Utilisateur.getEmail());
        values.put(UtilisateurDbSchema.UtilisateurTable.Cols.TEL, Utilisateur.getTel());
        return values;
    }

    private static ContentValues getContentValues()
    {
        return null;
    }

    public  List<utilisateur> getUtilisateur()
    {
        List<utilisateur> utilisateurs = new ArrayList<>();
        UtilisateurCursorWrapper cursor = queryUtilisateur(null, null);
        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                utilisateurs.add(cursor.getUtilisateur());
                cursor.moveToNext();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this.mContext,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            cursor.close();
        }
        return utilisateurs;
    }

    public UtilisateurCursorWrapper findUtilisateur(String mdp, String email)
    {
        UtilisateurCursorWrapper cursor = queryUtilisateur("Mdp = ? AND Email = ?", new String[] {String.valueOf(mdp), String.valueOf(email)});
        try
        {

            if(cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor;
        }
        finally
        {
            cursor.close();
        }
    }

    private UtilisateurCursorWrapper queryUtilisateur (String whereClause, String[]whereArgs)
    {
        Cursor cursor = mDatabase.query(
                UtilisateurDbSchema.UtilisateurTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new UtilisateurCursorWrapper(cursor);
    }

    public utilisateur getUser_connexion(String email, String password)
    {
        UtilisateurCursorWrapper cursor = queryUtilisateur("Email = ? AND Mdp = ?", new String[]{email, password});

        try
        {
            if(cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return  cursor.getUtilisateur();
        }
        finally {
            cursor.close();
        }
    }

    //tableLobby
    private static ContentValues getContentValuesLob(lobbyinfo LobbyInfo) {
        ContentValues values = new ContentValues();
        values.put(UtilisateurDbSchema.LobbyTable.Cols.NOM, LobbyInfo.getNom());
        values.put(UtilisateurDbSchema.LobbyTable.Cols.MDP, LobbyInfo.getMdp());
        return values;
    }

    public void addLobbyInfo (lobbyinfo LobbyInfo)
    {
        ContentValues values = getContentValuesLob(LobbyInfo);
        mDatabase.insert(UtilisateurDbSchema.LobbyTable.NAME, null, values);
    }

    public void updateLobbyInfo(lobbyinfo LobbyInfo, int id)
    {
        ContentValues values = getContentValuesLob(LobbyInfo);
        mDatabase.update(UtilisateurDbSchema.LobbyTable.NAME, values, "id = ?", new String[]{String.valueOf(id)});
    }

    public  List<lobbyinfo> getLobbyInfo()
    {
        List<lobbyinfo> lobbys = new ArrayList<>();
        UtilisateurCursorWrapper cursor = queryLobby(null, null);
        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                lobbys.add(cursor.getLobby());
                cursor.moveToNext();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this.mContext,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            cursor.close();
        }
        return lobbys;
    }
    public lobbyinfo getLobby(String nom, String password)
    {
        UtilisateurCursorWrapper cursor = queryUtilisateur("Nom = ? AND Mdp = ?", new String[]{nom, password});

        try
        {
            if(cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return  cursor.getLobby();
        }
        finally {
            cursor.close();
        }
    }

    public UtilisateurCursorWrapper findLobbyInfo(String nom, String mdp)
    {
        UtilisateurCursorWrapper cursor = queryLobby("Mdp =? AND Nom =?", new String[] {String.valueOf(nom), String.valueOf(mdp)});
        try
        {
            if(cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor;
        }
        finally
        {
            cursor.close();
        }
    }

    private UtilisateurCursorWrapper queryLobby(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                UtilisateurDbSchema.UtilisateurTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new UtilisateurCursorWrapper(cursor);
    }

    //tableUtilisateurLobby
    private static ContentValues getContentValuesLob(utilisateurlobby UtilisateurLobby) {
        ContentValues values = new ContentValues();
        values.put(UtilisateurDbSchema.UtilisateurLobbyTable.Cols.HISTORIQUE, UtilisateurLobby.getHistorique());
        return values;
    }

    public void addUtilisateurLobby (utilisateurlobby UtilisateurLobby)
    {
        ContentValues values = getContentValuesLob(UtilisateurLobby);
        mDatabase.insert(UtilisateurDbSchema.UtilisateurLobbyTable.NAME, null, values);
    }

    public void updateUtilisateurLobby(utilisateurlobby UtilisateurLobby, int id)
    {
        ContentValues values = getContentValuesLob(UtilisateurLobby);
        mDatabase.update(UtilisateurDbSchema.UtilisateurLobbyTable.NAME, values, "id = ?", new String[]{String.valueOf(id)});
    }

    public  List<utilisateurlobby> getUtilisateurLobby()
    {
        List<utilisateurlobby> utilisateurlobbies = new ArrayList<>();
        UtilisateurCursorWrapper cursor = queryLobby(null,null);
        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                utilisateurlobbies.add(cursor.getUtilisateurLobby());
                cursor.moveToNext();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this.mContext,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            cursor.close();
        }
        return utilisateurlobbies;
    }

    public UtilisateurCursorWrapper findUtilisateurLobby(String historique)
    {
        UtilisateurCursorWrapper cursor = queryLobby("historique =?", new String[] {String.valueOf(historique)});
        try
        {
            if(cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor;
        }
        finally
        {
            cursor.close();
        }
    }

    private UtilisateurCursorWrapper queryUtilisateurLobby(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                UtilisateurDbSchema.UtilisateurLobbyTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new UtilisateurCursorWrapper(cursor);
    }
}
