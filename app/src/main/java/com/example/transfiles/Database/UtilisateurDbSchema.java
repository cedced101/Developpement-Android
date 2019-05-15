package com.example.transfiles.Database;

public class UtilisateurDbSchema {
    public static final class UtilisateurTable
    {
        public static final String NAME = "utilisateur";
        public static final class Cols
        {
            public static final String PRENOM = "Prenom";
            public static final String NOM = "Nom";
            public static final String MDP = "Mdp";
            public static final String EMAIL = "Email";
            public static final String TEL = "Tel";
        }
    }

    public static final class LobbyTable
    {
        public static final String NAME = "lobby";
        public static final class Cols
        {
            public static final String NOM = "Nom";
            public static final String MDP = "Mdp";
        }
    }
    public static final class UtilisateurLobbyTable
    {
        public static  final String NAME = "utilisateurlobby";
        public  static  final  class Cols
        {
            public static final String HISTORIQUE = "Historique";
        }
    }
    public static final class BackgroundTable
    {
        public static final String NAME = "background";
        public static final class  Cols
        {
            public static final String VALUER = "ValueR";
            public static final String VALUEG = "ValueG";
            public static final String VALUEB = "ValueB";
        }
    }
}
