package com.example.transfiles;

public class lobbyinfo {
    private String mPrenom;
    private String mNom;
    private String mMdp;
    private String mEmail;
    private String mTel;
    private String Ami;

    public lobbyinfo(String _nom, String _mdp) { //alt-insert-constructor
        mNom = _nom;
        mMdp = _mdp;
    }

    public String getNom() {
        return mNom;
    }
    public String getMdp() {
        return mMdp;
    }
    public String getLobbyInfo() { return mNom + " // " + mMdp; }
}
