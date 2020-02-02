package com.example.pomodorotimer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private long mStartTime = 16000;

    private CountDownTimer mCountDownTimer;

    private ImageButton mStartPauseButton;
    private Drawable mStartIcon;
    private Drawable mPauseIcon;
    private Button mResetButton;

    private ProgressBar mTimerProgressBar;

    private boolean mTimerRunning;

    private long mTimeRemaining = mStartTime;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerProgressBar = findViewById(R.id.timerProgressBar);

        mStartPauseButton = findViewById(R.id.start_pause_button);
        mResetButton = findViewById(R.id.reset_button);

        mStartIcon = getDrawable(R.drawable.ic_play_circle_outline_black_40dp);
        mPauseIcon = getDrawable(R.drawable.ic_pause_circle_outline_black_40dp);

        mStartPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (mTimerRunning) {
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
        mCountDownTimer = new CountDownTimer(mTimeRemaining, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeRemaining = millisUntilFinished;
                updateTimerProgressBar();
            }

            @Override
            public void onFinish() {
                mStartPauseButton.setImageDrawable(mStartIcon);
                mTimerRunning = false;
                mResetButton.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
        mStartPauseButton.setImageDrawable(mPauseIcon);
        mResetButton.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mStartPauseButton.setImageDrawable(mStartIcon);
        mResetButton.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mTimeRemaining = mStartTime;
        updateTimerProgressBar();
    }

    private void updateTimerProgressBar(){
        mTimerProgressBar.setProgress(0);
        mTimerProgressBar.setMax(100);
        int progress = (int)((mStartTime-mTimeRemaining)/mStartTime*100);
        mTimerProgressBar.setProgress(progress);
    }

    }