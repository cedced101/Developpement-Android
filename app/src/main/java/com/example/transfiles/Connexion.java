package com.example.transfiles;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;

public class Connexion extends AppCompatActivity {
    private EditText mdp;
    private EditText email;
    private UtilisateurLab mUtilisateurLab;
    public SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        mUtilisateurLab = new UtilisateurLab(this);

        email = (EditText) findViewById(R.id.email);
        mdp = (EditText) findViewById(R.id.mdp);

        final Button btngoConnexion = (Button) findViewById(R.id.bidconnexion);

        btngoConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test1 = mdp.getText().toString();
                String test2 = email.getText().toString();

                if (mUtilisateurLab.findUtilisateur(test1, test2) == null) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.connexionToast), Toast.LENGTH_SHORT).show();
                } else {
                    utilisateur mUtilisateur;

                    mUtilisateur = mUtilisateurLab.getUser_connexion(test2, test1);

                    Log.i("Test", mUtilisateur.getNom() + mUtilisateur.getPrenom());

                    Intent myIntent = new Intent(getBaseContext(), Compte.class);
                    myIntent.putExtra("Nom", mUtilisateur.getNom());
                    myIntent.putExtra("Prenom", mUtilisateur.getPrenom());
                    startActivity(myIntent);
                }
            }
        });
    }
}

