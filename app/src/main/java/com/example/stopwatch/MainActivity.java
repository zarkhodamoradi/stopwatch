package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txtTime;
    ImageButton btnStart;
    ImageButton btnStop;
    ImageButton btnReset;

    boolean running;
    int seconds = 0;
    boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupView();
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");

        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = true;

            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wasRunning= true ;
                running= false ;

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seconds=0;
                running= false ;
            }
        });

        runTimer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = wasRunning;
        wasRunning = false;
    }


    public void SetupView() {
        txtTime = findViewById(R.id.txtTime);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnReset = findViewById(R.id.btnResume);

    }

    public void runTimer(){
        Handler handler= new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                String pHours = "";
                String pSeconds= "";
                String pMinuets ="";

                int hours = (seconds /3600);
                if (hours < 10 ) pHours="0";
                else pHours="";


                int minutes = (seconds%3600)/60 ;
                if (minutes <10) pMinuets="0";
                else pMinuets= "";

                int showingSeconds= (seconds%60 );
                if(showingSeconds<10) pSeconds="0";
                else pSeconds="";

                String time = String.format(Locale.getDefault(), "%s%d : %s%d : %s%d" , pHours,hours, pMinuets,minutes , pSeconds,showingSeconds);
                txtTime.setText(time);

                if (running){
                    seconds++;
                }
                handler.postDelayed(this, 1000);

            }
        });
    }
}