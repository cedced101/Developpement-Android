package com.example.transfiles;

public class utilisateur {
    private String mPrenom;
    private String mNom;
    private String mMdp;
    private String mEmail;
    private String mTel;

    public utilisateur(String _prenom, String _nom, String _mdp, String _email, String _tel) { //alt-insert-constructor
        mPrenom = _prenom;
        mNom = _nom;
        mMdp = _mdp;
        mEmail = _email;
        mTel = _tel;
    }

    public String getNom() {
        return mNom;
    }

    public String getPrenom() {
        return mPrenom;
    }

    public String getMdp() {
        return mMdp;
    }

    public  String getEmail() {return mEmail;}

    public String getTel() {return mTel;}

    public String getUser() {return mPrenom+ " // " + mNom + " // " + mMdp + " // " + mEmail + " // " +mTel; }
}
