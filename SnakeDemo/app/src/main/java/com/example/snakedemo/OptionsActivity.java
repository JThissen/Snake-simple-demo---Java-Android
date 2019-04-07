package com.example.snakedemo;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class OptionsActivity extends AppCompatActivity
{
    private Handler handler = new Handler();
    private TextView textView;
    private Toolbar toolbar;
    private AnimationDrawable animationDrawable;
    private RelativeLayout relativeLayout;
    private SnakeEngine snakeEngine;
    private DrawEngineOptions drawEngineOptions;
    private final long frame = 125;
    private float tileSizeMultiplier = 0.8f;
    private float gridSpacingMultiplier = 0.15f;
    private int gridWidth = 10;
    private SeekBar widthSeekBar;
    private TextView widthText;
    private SeekBar heightSeekBar;
    private TextView heightText;
    private SeekBar.OnSeekBarChangeListener seekBarChangeListenerHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_options);
        seekBarChangeListenerHeight = new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                Data.gridHeight = progress + 8;
                int value = Data.gridHeight - 2;
                heightText.setText(String.format("Height: %d", value));
                InitializeDrawEngineOptions();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        };
        SetToolbar();
        AnimateBackground();
        InitializeSeekbars();
        snakeEngine = new SnakeEngine(gridWidth, Data.gridHeight, 4);
        InitializeDrawEngineOptions();
    }

    private void AnimateBackground()
    {
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }

    private void InitializeDrawEngineOptions()
    {
        drawEngineOptions = (DrawEngineOptions) findViewById(R.id.drawEngineOptions);
        drawEngineOptions.SetGridWidth(gridWidth);
        drawEngineOptions.SetGridHeight(Data.gridHeight);
        snakeEngine.SetGridHeight(Data.gridHeight);
        drawEngineOptions.SetGridSpacingMultiplier(gridSpacingMultiplier);
        drawEngineOptions.SetTileSizeMultiplier(tileSizeMultiplier);
        drawEngineOptions.SetGrid(snakeEngine.CreateGridOptions());
        drawEngineOptions.invalidate();
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

    private void InitializeSeekbars()
    {
        heightSeekBar = findViewById(R.id.height);
        heightSeekBar.setProgress(Data.gridHeight - 8);
        heightSeekBar.setOnSeekBarChangeListener(seekBarChangeListenerHeight);
        Data.gridHeight = heightSeekBar.getProgress() + 8;
        heightText = findViewById(R.id.heightText);
        int value = Data.gridHeight - 2;
        heightText.setText(String.format("Height: %d", value));
    }
}