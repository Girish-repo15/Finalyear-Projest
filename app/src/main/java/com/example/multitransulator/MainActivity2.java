package com.example.multitransulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        LottieAnimationView dd=findViewById(R.id.lottie);
        dd.setAlpha(0f);
        dd.animate().setDuration(6000).alpha(1f).withEndAction(() -> {
            dd.pauseAnimation();
            finish();
            startActivity(new Intent(MainActivity2.this,Transulation.class));
        }).withStartAction(() -> {
            dd.playAnimation();
overridePendingTransition(androidx.appcompat.R.anim.abc_fade_out,androidx.appcompat.R.anim.abc_fade_in);
        });
    }
}