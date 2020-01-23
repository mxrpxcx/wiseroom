package com.alura.wiseroom.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.alura.wiseroom.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        View easySplashScreenView = new EasySplashScreen(SplashScreen.this)
                    .withFullScreen()
                    .withTargetActivity(MainActivity.class)
                    .withSplashTimeOut(3000)
                    .withBackgroundResource(R.color.colorPrimaryDark)
                    .withHeaderText("")
                    .withFooterText("")
                    .withBeforeLogoText("")
                    .withLogo(R.drawable.psdlogo)
                    .withAfterLogoText("")
                    .create();
        setContentView(easySplashScreenView);
    }
}