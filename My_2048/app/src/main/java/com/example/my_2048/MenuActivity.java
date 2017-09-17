package com.example.my_2048;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import static java.lang.Boolean.FALSE;

public class MenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);


        Button button1=(Button)findViewById(R.id.menu_button1);
        Button button2=(Button)findViewById(R.id.menu_button2);
        Button button3=(Button)findViewById(R.id.menu_button3);
        Button button4=(Button)findViewById(R.id.menu_button4);
        Button button5=(Button)findViewById(R.id.menu_button5);
        final Button button_voice=(Button)findViewById(R.id.voice_button);
        if(Config.VOICE_SWITCH == true)
            button_voice.setBackgroundResource(R.drawable.voice_on);
        else
            button_voice.setBackgroundResource(R.drawable.voice_off);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.SPECIAL_PATTERN = 0;
                if(Config.LINES == 5) {
                    Config.CARD_WIDTH = (Config.CARD_WIDTH*5)/4;
                }
                Config.LINES = 4;
                MainActivity.getMainActivity().finish();
                Intent intent =new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.SPECIAL_PATTERN = 1;
                if(Config.LINES == 5) {
                    Config.CARD_WIDTH = (Config.CARD_WIDTH*5)/4;
                }
                Config.LINES = 4;

                MainActivity.getMainActivity().finish();
                Intent intent =new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.SPECIAL_PATTERN = 0;
                if(Config.LINES == 4) {
                    Config.CARD_WIDTH = (Config.CARD_WIDTH*4)/5;
                }
                Config.LINES = 5;

                MainActivity.getMainActivity().finish();
                Intent intent =new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.SPECIAL_PATTERN = 1;
                if(Config.LINES == 4) {
                    Config.CARD_WIDTH = (Config.CARD_WIDTH*4)/5;
                }
                Config.LINES = 5;
                MainActivity.getMainActivity().finish();
                Intent intent =new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
//                Log.d("TAG","OK");
            }
        });

        button_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Config.VOICE_SWITCH == true) {
                    button_voice.setBackgroundResource(R.drawable.voice_off);
                    Config.VOICE_SWITCH = false;
                }
                else {
                    button_voice.setBackgroundResource(R.drawable.voice_on);
                    MainActivity.getMainActivity().playMoveMusic();
                    Config.VOICE_SWITCH = true;
                }
            }
        });



    }



}
