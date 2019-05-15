package com.example.transfiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.transfiles.Database.UtilisateurDBHelper;

public class MainActivity extends AppCompatActivity {

    UtilisateurDBHelper mUtilisateurDBHelper;
    UtilisateurLab mUtilisateurLab;
    public RadioButton radiobtn;
    public String civilite;
    private EditText nom;
    private EditText prenom;
    private EditText mdp;
    private EditText email;
    private EditText tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUtilisateurDBHelper = new UtilisateurDBHelper(this);
        mUtilisateurLab = UtilisateurLab.get(MainActivity.this.getApplicationContext());

        civilite = getString(R.string.tradio_one);

        nom =(EditText) findViewById(R.id.nom);
        prenom =(EditText) findViewById(R.id.prenom);
        mdp =(EditText) findViewById(R.id.mdp);
        email =(EditText) findViewById(R.id.email);
        tel = (EditText) findViewById(R.id.tel);


        final Button button_inscription =(Button) findViewById(R.id.button_inscription);
        final Button button_connexion = (Button) findViewById(R.id.button_connexion);

        RadioGroup radioGroups = (RadioGroup) findViewById(R.id.radio_grp);
        radioGroups.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.radio_one) {
                    civilite = getString(R.string.tradio_one);
                }
                else if (checkedId == R.id.radio_two) {
                    civilite = getString(R.string.tradio_two);
                }
                else if (checkedId == R.id.radio_three) {
                    civilite = getString(R.string.tradio_three);
                }
            }
        });
        button_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), Connexion.class);
                startActivityForResult(myIntent, 0);
            }
        });

        button_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String civil = civilite;

                String test1 = nom.getText().toString();
                String test2 = prenom.getText().toString();
                String test3 = mdp.getText().toString();
                String test4 = email.getText().toString();
                String test5 = tel.getText().toString();


                if(isNom(test1)==false)
                {
                    Log.i("test", "LPas nom ");
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.nToast), Toast.LENGTH_SHORT).show();
                }
                else if(isPrenom(test2)==false)
                {
                    Log.i("test", "LPas prenom ");
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.pToast), Toast.LENGTH_SHORT).show();
                }
                else if(test3.matches(""))
                {
                    Log.i("test", "LPas mdp ");
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.mToast), Toast.LENGTH_SHORT).show();
                }
                else if(isEmail(test4)==false )
                {
                    Log.i("test", "LPas email ");
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.eToast), Toast.LENGTH_SHORT).show();
                }
                else if(isTel(test5)==false)
                {
                    Log.i("test", "LPas tel ");
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.tToast), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    utilisateur uti = new utilisateur(test1,test2,test3,test4,test5);
                    mUtilisateurLab.addUtilisateur(uti);
                    Intent myIntent = new Intent(getBaseContext(), Compte.class);
                    myIntent.putExtra("Nom", test1);
                    myIntent.putExtra("Prenom", test2);
                    myIntent.putExtra("Mdp", test3);
                    myIntent.putExtra("Email", test4);
                    myIntent.putExtra("Tel", test5);
                    myIntent.putExtra("RadioId", civil);
                    startActivity(myIntent);
                }
            }
        });
    }
    public static boolean isNom(String str) {
        String expression = "^[A-Z][a-z]{1,24}$|[A-Z][a-z/-]{1,24}$";
        return str.matches(expression);
    }
    public static boolean isPrenom(String str) {
        String expression = "^[A-Z][a-z]{1,24}$|[A-Z][a-z/-]{1,24}$";
        return str.matches(expression);
    }
    public static boolean isEmail(String str) {
        String expression = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
        return str.matches(expression);
    }
    public static boolean isTel(String str) {
        String expression = "^\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}$";
        return str.matches(expression);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("DICJ","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("DICJ","onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("DICJ","onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("DICJ","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("DICJ","onResume");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("DICJ","onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("DICJ", "onKeyDown");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

}
