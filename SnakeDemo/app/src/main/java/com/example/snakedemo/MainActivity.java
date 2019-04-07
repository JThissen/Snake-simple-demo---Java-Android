package com.example.snakedemo;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;
    private ColorDrawable colorDrawable;
    private Button buttonPlay;
    private Button buttonOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPlay = (Button) findViewById(R.id.menu_button);
        buttonPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, SnakeActivity.class));
            }
        });
        buttonOptions = (Button) findViewById(R.id.options_button);
        buttonOptions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, OptionsActivity.class));
            }
        });
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_main);
        AnimateBackground();
    }

    private void AnimateBackground()
    {
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }
}
