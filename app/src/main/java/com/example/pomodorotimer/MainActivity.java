package com.example.pomodorotimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private long mStartTime = 1500000;

    private CountDownTimer mCountDownTimer;

    private Button mStartPauseButton;
    private Button mResetButton;

    private TextView mTimerText;

    private boolean mTimerRunning;

    private long mTimeRemaining = mStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerText = findViewById(R.id.timer);
        mStartPauseButton = findViewById(R.id.start_pause_button);
        mResetButton = findViewById(R.id.reset_button);

        mStartPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeRemaining = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                mStartPauseButton.setText("start");
                updateTimerText();
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning = true;
        mStartPauseButton.setText("pause");
        mResetButton.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mStartPauseButton.setText("start");
        mResetButton.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mTimeRemaining = mStartTime;
        updateTimerText();
    }

    private void updateTimerText(){
        int minutes = (int) mTimeRemaining / 1000 / 60;
        int seconds = (int) mTimeRemaining / 1000 % 60;
        String formattedTime = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        mTimerText.setText(formattedTime);
    }
}
