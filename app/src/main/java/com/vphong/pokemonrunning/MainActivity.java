package com.vphong.pokemonrunning;

import android.content.pm.ActivityInfo;
import android.graphics.MaskFilter;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView tvScore;
    Button btn;
    CheckBox cb1, cb2, cb3;
    SeekBar sb1, sb2, sb3;
    int moveStep, maxProgress;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        loadId();
        handleCheckBox();
        final Random rand = new Random();
        moveStep = 6;
        score = 100;
        maxProgress = sb1.getMax();
        final CountDownTimer countDownTimer = new CountDownTimer(30000, 200) {
            @Override
            public void onTick(long l) {
                int step1 = rand.nextInt(moveStep) + 1;
                int step2 = rand.nextInt(moveStep) + 1;
                int step3 = rand.nextInt(moveStep) + 1;

                checkWinner(this);

                sb1.setProgress(sb1.getProgress() + step1);
                sb2.setProgress(sb2.getProgress() + step2);
                sb3.setProgress(sb3.getProgress() + step3);
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Finish", Toast.LENGTH_SHORT).show();
            }
        };
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetRace();

                if (cb1.isChecked() || cb2.isChecked() || cb3.isChecked()) {
                    disableComponent();
                    btn.setVisibility(View.INVISIBLE);
                    countDownTimer.start();
                } else {
                    Toast.makeText(MainActivity.this, "Vui long dat cuoc truoc khi choi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void loadId() {
        tvScore = findViewById(R.id.tvScore);
        btn = (Button) findViewById(R.id.btnStart);

        cb1 = (CheckBox) findViewById(R.id.ckb1);
        cb2 = (CheckBox) findViewById(R.id.ckb2);
        cb3 = (CheckBox) findViewById(R.id.ckb3);

        sb1 = (SeekBar) findViewById(R.id.sb1);
        sb2 = (SeekBar) findViewById(R.id.sb2);
        sb3 = (SeekBar) findViewById(R.id.sb3);
    }

    void disableComponent() {
        sb1.setEnabled(false);
        sb2.setEnabled(false);
        sb3.setEnabled(false);

        cb1.setEnabled(false);
        cb2.setEnabled(false);
        cb3.setEnabled(false);
    }

    void enableComponent() {
        cb1.setEnabled(true);
        cb2.setEnabled(true);
        cb3.setEnabled(true);
    }

    void handleCheckBox() {
        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb1.setChecked(true);
                cb2.setChecked(false);
                cb3.setChecked(false);
            }
        });
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb2.setChecked(true);
                cb1.setChecked(false);
                cb3.setChecked(false);
            }
        });
        cb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb3.setChecked(true);
                cb1.setChecked(false);
                cb2.setChecked(false);
            }
        });
    }

    void checkWinner(CountDownTimer timer) {
        if (sb1.getProgress() >= maxProgress - 10) {
            timer.cancel();
            Toast.makeText(this, "ElectricBu Win !", Toast.LENGTH_SHORT).show();
            btn.setVisibility(View.VISIBLE);
            enableComponent();
            updateScore(cb1);

        }
        if (sb2.getProgress() >= maxProgress - 10) {
            timer.cancel();
            Toast.makeText(this, "Pikachu Win !", Toast.LENGTH_SHORT).show();
            btn.setVisibility(View.VISIBLE);
            enableComponent();
            updateScore(cb2);
        }
        if (sb3.getProgress() >= maxProgress - 10) {
            timer.cancel();
            Toast.makeText(this, "Crocodile Win !", Toast.LENGTH_SHORT).show();
            btn.setVisibility(View.VISIBLE);
            enableComponent();
            updateScore(cb3);
        }
    }

    void resetRace() {
        sb1.setProgress(5);
        sb2.setProgress(5);
        sb3.setProgress(5);
    }

    void updateScore(CheckBox cb) {
        if (cb.isChecked()) {
            score += 20;
            Toast.makeText(this, "Ban duoc cong 20d", Toast.LENGTH_SHORT).show();
        }
        else {
            score -= 20;
            Toast.makeText(this, "Ban bi tru 20d", Toast.LENGTH_SHORT).show();
        }
        tvScore.setText(score + "");

        if (score <= 0) {
            btn.setText("Game Over");
            btn.setTextSize(30);
            btn.setEnabled(false);
            tvScore.setText("Over");
            Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
        }
    }
}
