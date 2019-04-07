package com.example.snakedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DrawEngineOptions extends View
{
    private TileType[][] grid;
    private Paint paint = new Paint();
    private float gridSpacing;
    private float tileSizeMultiplier;
    private float gridSpacingMultiplier;
    private int gridWidth;
    private int gridHeight;

    public DrawEngineOptions(Context context, AttributeSet attrs) { super(context, attrs); }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(grid != null)
        {
            Log.d("gridHeight", String.valueOf(Data.gridHeight));
            float tileSizeX = (float) (getWidth() * tileSizeMultiplier / gridWidth);
            float tileSizeY = tileSizeX;
            SetGridSpacing(tileSizeX * gridSpacingMultiplier);

            float centerX = getWidth() / 2;
            float centerY = getHeight() / 2;

            float bottomLeftX = centerX - ((tileSizeX * grid.length) / 2);
            float bottomLeftY = centerY - ((tileSizeY * grid[0].length) / 2);

            for (int row = 0; row < grid.length; ++row) {
                for (int col = 0; col < grid[0].length; ++col) {
                    switch (grid[row][col]) {
                        case Empty:
                            paint.setColor(Color.argb(200, 255, 255, 255));
                            break;
                    }

                    if (grid[row][col] != TileType.Wall) {
                        paint.setStyle(Paint.Style.FILL);
                        canvas.drawRect(bottomLeftX + row * tileSizeX + this.gridSpacing, bottomLeftY + (col + 1) * tileSizeY,
                                bottomLeftX + (row + 1) * tileSizeX, bottomLeftY + col * tileSizeY + this.gridSpacing, paint);
                    }
                }
            }
        }
    }

    public void SetGridWidth(int gridWidth) { this.gridWidth = gridWidth; }
    public void SetGridHeight(int gridHeight) { this.gridHeight = gridHeight; }
    public void SetTileSizeMultiplier(float tileSizeMultiplier) { this.tileSizeMultiplier = tileSizeMultiplier; }
    public void SetGridSpacingMultiplier(float gridSpacingMultiplier) { this.gridSpacingMultiplier = gridSpacingMultiplier; }
    public void SetGrid(TileType[][] grid)
    {
        this.grid = grid;
    }
    public void SetGridSpacing(float gridSpacing)
    {
        this.gridSpacing = gridSpacing;
    }
}