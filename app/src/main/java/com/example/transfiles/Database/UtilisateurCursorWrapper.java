package com.example.transfiles.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.transfiles.background;
import  com.example.transfiles.lobbyinfo;
import com.example.transfiles.utilisateur;
import com.example.transfiles.Database.UtilisateurDbSchema.UtilisateurLobbyTable;
import com.example.transfiles.utilisateurlobby;

public class UtilisateurCursorWrapper extends CursorWrapper {

    public UtilisateurCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public  utilisateur getUtilisateur() {
        String textPrenId = getString(getColumnIndex(UtilisateurDbSchema.UtilisateurTable.Cols.PRENOM));
        String textNomId = getString(getColumnIndex(UtilisateurDbSchema.UtilisateurTable.Cols.NOM));
        String textMdpId = getString(getColumnIndex(UtilisateurDbSchema.UtilisateurTable.Cols.MDP));
        String textEmailId = getString(getColumnIndex(UtilisateurDbSchema.UtilisateurTable.Cols.EMAIL));
        String textTelId = getString(getColumnIndex(UtilisateurDbSchema.UtilisateurTable.Cols.TEL));

        utilisateur Utilisateur= new utilisateur(textPrenId, textNomId, textMdpId, textEmailId, textTelId);
        return Utilisateur;
    }
    public  lobbyinfo getLobby() {
        String textNomId = getString(getColumnIndex(UtilisateurDbSchema.LobbyTable.Cols.NOM));
        String textMdpId = getString(getColumnIndex(UtilisateurDbSchema.LobbyTable.Cols.MDP));

        lobbyinfo LobbyInfo = new lobbyinfo(textNomId,textMdpId);
        return  LobbyInfo;
    }
    public utilisateurlobby getUtilisateurLobby() {
        String textHistoriqueId = getString(getColumnIndex(UtilisateurLobbyTable.Cols.HISTORIQUE));

        utilisateurlobby UtilisateurLobby = new utilisateurlobby(textHistoriqueId);
        return  UtilisateurLobby;
    }
    public background getBackground() {
        int ValueRId= getInt(getColumnIndex(UtilisateurDbSchema.BackgroundTable.Cols.VALUER));
        int ValueGId = getInt(getColumnIndex(UtilisateurDbSchema.BackgroundTable.Cols.VALUEG));
        int ValueBId = getInt(getColumnIndex(UtilisateurDbSchema.BackgroundTable.Cols.VALUEB));

        background Background= new background(ValueRId,ValueGId,ValueBId);
        return Background;
    }
}

