package com.example.android.eggtimer;


import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView timerTextView;
    Boolean timerRunning = false;
    Button button = (Button)findViewById(R.id.button);
    CountDownTimer countDownTimer;
    public void resetTimer(){
        timerTextView.setText("0:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        seekBar.setEnabled(true);
        button.setText("Start");
        timerRunning = false;
    }
    public void update(int secondsLeft){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft- minutes*60;
        String secondValue = String.valueOf(seconds);
        if(secondValue.length() == 1){
            secondValue = "0" + secondValue;
        }
        timerTextView.setText(String.valueOf(minutes)+":"+secondValue);
    }
    public void startTimer(View view){
        //final Button button = (Button)findViewById(R.id.button);
            if(!timerRunning){
                Log.i("Button Pressed", "Start Button");
                timerRunning = true;
                button.setText("Stop");
                seekBar.setEnabled(false);
                countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 , 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        update((int) millisUntilFinished / 1000);
                    }

                    @Override
                    public void onFinish() {
                        timerTextView.setText("0:00");
                        MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                        mplayer.start();
                        Log.i("Timer Done", "Finished");
                        button.setText("Start");
                        timerRunning = false;
                    }
                }.start();
            }
        else{
                resetTimer();
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar)findViewById(R.id.timerSeekbar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
