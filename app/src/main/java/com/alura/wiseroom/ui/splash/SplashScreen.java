package com.alura.wiseroom.ui.splash;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.wiseroom.R;
import com.alura.wiseroom.ui.colaborador.ActivityLogin;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        View easySplashScreenView = new EasySplashScreen(SplashScreen.this)
                    .withFullScreen()
                    .withTargetActivity(ActivityLogin.class)
                    .withSplashTimeOut(3000)
                    .withBackgroundResource(R.color.colorPrimaryDark)
                    .withHeaderText("")
                    .withFooterText("")
                    .withBeforeLogoText("")
                    .withLogo(R.drawable.logo2)
                    .withAfterLogoText("")
                    .create();
        setContentView(easySplashScreenView);
    }
}
