package com.example.playmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    ImageView logoImg,textImg1,textImg2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logoImg = findViewById(R.id.logoImg);
        textImg1 = findViewById(R.id.textImg1);
        textImg2 = findViewById(R.id.textImg2);

        Intent intent = new Intent(this,MainActivity.class);

        Animation fadeIn = AnimationUtils.loadAnimation(this,R.anim.anim_logo);
        Animation move = AnimationUtils.loadAnimation(this,R.anim.anim_text1);
        Animation moveTwo = AnimationUtils.loadAnimation(this,R.anim.anim_text2);

        logoImg.setAnimation(fadeIn);
        textImg1.setAnimation(move);
        textImg2.setAnimation(moveTwo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },4000);
    }
}