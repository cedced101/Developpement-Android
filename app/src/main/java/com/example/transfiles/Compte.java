package com.example.transfiles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
public class Compte extends AppCompatActivity {

    public  String scomptenom;
    public  String scompteprenom;
    private TextView mNomTextView;
    private TextView mPrenomTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compte);

        scomptenom = getIntent().getStringExtra("Nom");
        scompteprenom = getIntent().getStringExtra("Prenom");

        mNomTextView = (TextView) findViewById(R.id.comptenom);
        mPrenomTextView = (TextView) findViewById(R.id.compteprenom);
        mNomTextView.setText(scomptenom);
        mPrenomTextView.setText(scompteprenom);

        final  Button btngoJoindreLobby = (Button) findViewById(R.id.btngoJoindreLobby);
        btngoJoindreLobby.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(),JoindreLobby.class);
                startActivityForResult(myIntent, 0);
            }
        });
        final Button btnrInscription =(Button) findViewById(R.id.btnrInscription);
        btnrInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compte, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionparametre) {
            // do something here
            Intent myIntent = new Intent(getBaseContext(), Parametre.class);
            startActivityForResult(myIntent,0);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        Log.i("DICJ","onKeyDown");
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


