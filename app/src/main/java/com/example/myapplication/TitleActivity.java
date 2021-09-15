package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import java.util.ArrayList;

public class TitleActivity extends AppCompatActivity{
    private  static int SPLASH_SCREEN = 5000;
    //VAR
    Animation topAnim, bottomAnim;
    TextView slogan1,slogan2;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //HOOKS
        slogan1 = findViewById(R.id.slogan1); slogan2 = findViewById(R.id.slogan2);
        lottieAnimationView = findViewById(R.id.anim);
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        slogan1.setAnimation(topAnim);
        slogan2.setAnimation(bottomAnim);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TitleActivity.this,SetupActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }

    public void start(View view){
        Intent intent = new Intent(TitleActivity.this,SetupActivity.class);
        startActivity(intent);
    }

}
