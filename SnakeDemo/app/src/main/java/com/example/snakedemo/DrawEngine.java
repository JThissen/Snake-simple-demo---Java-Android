package com.example.snakedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DrawEngine extends View
{
    private TileType[][] grid;
    private Paint paint = new Paint();
    private float gridSpacing;
    private float tileSizeMultiplier = 0.8f;
    private float gridSpacingMultiplier = 0.15f;

    public DrawEngine(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void SetGrid(TileType[][] grid)
    {
        this.grid = grid;
    }

    public void SetGridSpacing(float gridSpacing)
    {
       this.gridSpacing = gridSpacing;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(grid != null)
        {
            float tileSizeX = (float) (getWidth() * tileSizeMultiplier / grid.length);
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

                        case Wall:
                            paint.setColor(Color.DKGRAY);
                            break;

                        case SnakeHead:
                            paint.setColor(Color.RED);
                            break;

                        case SnakeTail:
                            paint.setColor(Color.GREEN);
                            break;

                        case Item:
                            paint.setColor(Color.MAGENTA);
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
}