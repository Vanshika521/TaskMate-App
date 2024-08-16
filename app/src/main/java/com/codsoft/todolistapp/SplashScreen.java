package com.codsoft.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {

    Animation top_anim,bottom_anim;
    ImageView image;
    TextView logo,logo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

//image pass in java file using id from splash.xml file
        image = findViewById(R.id.imageview);

//textView pass in java file using id from splash.xml file
        logo = findViewById(R.id.textView);
        logo2 = findViewById(R.id.textView2);

//Animation defined in java file res<anim<top and bottom animation
//For Quiz image top to bottom and for text bottom to up =Animation
        top_anim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_anim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

//Setting Animation...  image followed by text
        image.setAnimation(top_anim);
        logo.setAnimation(bottom_anim);
        logo2.setAnimation(bottom_anim);

//Define Handler.... for how much time it should run in miliseconds
       new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            //new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //register.class for registration of user...
                Intent intent  = new Intent(SplashScreen.this, login.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
