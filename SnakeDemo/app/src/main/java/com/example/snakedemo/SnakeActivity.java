package com.example.snakedemo;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Set;

public class SnakeActivity extends AppCompatActivity implements View.OnTouchListener
{
    private RelativeLayout relativeLayout;
    private SnakeEngine snakeEngine;
    private DrawEngine drawEngine;
    private Handler handler = new Handler();
    private TextView textView;
    private Toolbar toolbar;
    private AnimationDrawable animationDrawable;
    private final long frame = 125;
    private float touchX;
    private float touchY;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_snake);
        textView = (TextView) findViewById(R.id.score);
        SetToolbar();
        snakeEngine = new SnakeEngine(10, Data.gridHeight, 4);
        drawEngine = (DrawEngine) findViewById(R.id.drawEngine);
        drawEngine.setOnTouchListener(this);
        AnimateBackground();
        RunUpdate();
    }

    private void RunUpdate()
    {
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                snakeEngine.Update();
                if(snakeEngine.GetCurrentGameState() == GameState.Playing)
                    handler.postDelayed(this, frame);

                if(snakeEngine.GetCurrentGameState() == GameState.NotPlaying)
                    GameOver();

                drawEngine.SetGrid(snakeEngine.UpdateGrid());
                textView.setText(String.valueOf(SnakeEngine.score));
                drawEngine.invalidate();
            }
        }, frame);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                float touchReleaseX = event.getX();
                float touchReleaseY = event.getY();

                if(Math.abs(touchReleaseX - touchX) > Math.abs(touchReleaseY - touchY))
                {
                    if(touchReleaseX > touchX)
                        snakeEngine.SetCurrentDirection(Direction.Right);
                    else
                        snakeEngine.SetCurrentDirection(Direction.Left);
                }
                else
                {
                    if(touchReleaseY > touchY)
                        snakeEngine.SetCurrentDirection(Direction.Up);
                    else
                        snakeEngine.SetCurrentDirection(Direction.Down);
                }
                break;
        }
        return true;
    }

    private void GameOver()
    {
        Snackbar snackbar = (Snackbar) Snackbar.make(relativeLayout, "Game Over! Try again?", Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.rgb(30, 247, 255));
        snackbar.getView().setBackgroundColor(Color.DKGRAY);
        snackbar.setAction("RESTART", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                snakeEngine.Restart();
                RunUpdate();
            }
        });
        snackbar.show();
    }

    private void AnimateBackground()
    {
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }

    private void SetToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }
}