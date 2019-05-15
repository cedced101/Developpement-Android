package com.example.transfiles;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class PopUp extends Activity {
    SeekBar seekR;
    SeekBar seekG;
    SeekBar seekB;
    TextView tseekR;
    TextView tseekG;
    TextView tseekB;
    int valueR,valueG,valueB;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        final Button btn_sendBackground = (Button) findViewById(R.id.btnsendbackground);
        btn_sendBackground.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                doSomethingWithColor();
            }
        });
        final Button btn_savebackground = (Button) findViewById(R.id.btnsavebackground);
        btn_savebackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Button btn_quitter = (Button) findViewById(R.id.btnrparametre);
        btn_quitter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7), (int) (height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 20;

        getWindow().setAttributes(params);
        Initialize();
        seekRvalue();
        seekGvalue();
        seekBvalue();

    }
    public void Initialize()
    {
        seekR = (SeekBar) findViewById(R.id.seekR);
        seekG = (SeekBar) findViewById(R.id.seekG);
        seekB = (SeekBar) findViewById(R.id.seekB);
        tseekR = (TextView) findViewById(R.id.textSeekR);
        tseekG = (TextView) findViewById(R.id.textSeekG);
        tseekB = (TextView) findViewById(R.id.textSeekB);
    }

    public void seekRvalue()
    {
        seekR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueR = progress;
                tseekR.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void seekGvalue()
    {
        seekG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueG = progress;
                tseekG.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void seekBvalue()
    {
        seekB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueB = progress;
                tseekB.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void doSomethingWithColor() {
      int color;
      background mBackground = new background(valueR,valueG,valueB);
      color  = Color.rgb(mBackground.getValueR(),mBackground.getValueG(),mBackground.getValueB());
      View view = this.getWindow().getDecorView();
      view.setBackgroundColor(color);
    }

}

