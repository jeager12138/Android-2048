package com.example.my_2048;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static MainActivity mainActivity=null;
    private TextView tvScore;
    private TextView highScore;
    private AnimLayout animLayout;
    private int score;
    private GameView gameView;
    private SoundPool soundPool;
    public MainActivity() {
        mainActivity=this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //maybe the above one is useless
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_main);
        //

        SharedPreferences pref=getSharedPreferences("HIGH_SCORE",MODE_PRIVATE);
        Config.HIGH_SCORE=pref.getLong("HIGH_SCORE",0);

        SharedPreferences pref2=getSharedPreferences("HIGH_SCORE2",MODE_PRIVATE);
        Config.HIGH_SCORE2=pref2.getLong("HIGH_SCORE2",0);

        soundPool= new SoundPool(5, AudioManager.STREAM_MUSIC,5);
        soundPool.load(MainActivity.this,R.raw.fail,3);
        soundPool.load(MainActivity.this,R.raw.hit,2);
        soundPool.load(MainActivity.this,R.raw.move,1);
        tvScore=(TextView)findViewById(R.id.tvScore);
        highScore=(TextView)findViewById(R.id.tvHighScore);
        gameView=(GameView)findViewById(R.id.GameView);
        animLayout=(AnimLayout)findViewById(R.id.anim_layer);
        Button button_yellow=(Button)findViewById(R.id.yellow_2048);
        button_yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Designed by 16211115_JaEgeR",Toast.LENGTH_SHORT).show();
            }
        });
        Button button_retry=(Button)findViewById(R.id.retry_button);
        button_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.startGame();
                clearScore();
            }
        });
        Button button_menu=(Button)findViewById(R.id.orange_menu);
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });
    //    Log.d("TAG","onCreate");
    }

    public void playFailMusic() {
        if(Config.VOICE_SWITCH == true)
        soundPool.play(1, 1, 1, 0, 0, 1);
    }
    public void playHitMusic() {
        if(Config.VOICE_SWITCH == true)
        soundPool.play(2, 1, 1, 0, 0, 1);
    }
    public void playMoveMusic() {
        soundPool.play(3, 1, 1, 0, 0, 1);
    }
    public void clearScore() {
        score=0;
        showScore();
    }
    public void showScore() {
        tvScore.setText(score+"");
    }
    public void showHighScore() {
        if(Config.SPECIAL_PATTERN == 0)
            highScore.setText(Config.HIGH_SCORE+"");
        else
            highScore.setText(Config.HIGH_SCORE2+"");
    }
    public void addScore(int s) {
        score+=s;
        showScore();
    }

    public void saveHighScore() {
        if(Config.SPECIAL_PATTERN == 0) {
            SharedPreferences.Editor editor = getSharedPreferences("HIGH_SCORE", MODE_PRIVATE).edit();
            editor.putLong("HIGH_SCORE", Config.HIGH_SCORE);
            editor.apply();
            showHighScore();
        }
        else {
            SharedPreferences.Editor editor = getSharedPreferences("HIGH_SCORE2", MODE_PRIVATE).edit();
            editor.putLong("HIGH_SCORE2",Config.HIGH_SCORE2);
            editor.apply();
            showHighScore();
        }
    }

    public int getScore() {
        return score;
    }
    public GameView getGameView(){return gameView;}
    public AnimLayout getAnimLayout(){return animLayout;}
    public static MainActivity getMainActivity() {
        return mainActivity;
    }
}
