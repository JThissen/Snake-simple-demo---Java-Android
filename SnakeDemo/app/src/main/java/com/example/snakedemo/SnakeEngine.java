package com.example.snakedemo;

import android.widget.RelativeLayout;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeEngine
{
    private int gridWidth;
    private int gridHeight;
    private TileType[][] grid;
    private int snakeInitialSize;
    private List<Coordinate> walls;
    private List<Coordinate> snake;
    private Direction currentDirection;
    private GameState currentGameState;
    private Coordinate itemLocation;
    public static int score;

    public SnakeEngine(int gridWidth, int gridHeight, int snakeInitialSize)
    {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.snakeInitialSize = snakeInitialSize;
        walls = new ArrayList<Coordinate>();
        snake = new ArrayList<Coordinate>();
        currentDirection = Direction.Down;
        currentGameState = GameState.Playing;
        CreateWalls();
        CreateSnake();
        SpawnItem(snake, PickRandomLocation());
        CreateGrid();
        score = 0;
    }

    public void Restart()
    {
        CreateWalls();
        CreateSnake();
        SpawnItem(snake, PickRandomLocation());
        currentDirection = Direction.Down;
        currentGameState = GameState.Playing;
        score = 0;
    }

    public void Update()
    {
        switch (currentDirection)
        {
            case Up:
                UpdateDirection(0, 1);
                break;

            case Down:
                UpdateDirection(0, -1);
                break;

            case Right:
                UpdateDirection(1, 0);
                break;

            case Left:
                UpdateDirection(-1, 0);
                break;
        }

        for(Coordinate c : walls)
        {
            if(c.equals(snake.get(0)))
            {
                currentGameState = GameState.NotPlaying;
            }
        }

        if(snake.get(0).equals(itemLocation))
        {
            score++;
            SpawnItem(snake, PickRandomLocation());
            int tailX = snake.get(snake.size()-1).GetX();
            int tailY = snake.get(snake.size()-1).GetY();
            snake.add(new Coordinate(tailX, tailY));
        }

        for(int i = 1; i < snake.size(); ++i)
        {
            if(snake.get(i).equals(snake.get(0)))
                currentGameState = GameState.NotPlaying;
        }
    }

    private void UpdateDirection(int x, int y)
    {
        for(int i = snake.size()-1; i > 0; --i)
        {
            snake.get(i).SetX(snake.get(i-1).GetX());
            snake.get(i).SetY(snake.get(i-1).GetY());
        }
        snake.get(0).SetX(snake.get(0).GetX() + x);
        snake.get(0).SetY(snake.get(0).GetY() + y);
    }

    private void CreateWalls()
    {
        walls.clear();
        for(int i = 0; i < gridWidth; ++i)
        {
            walls.add(new Coordinate(i, 0));
            walls.add(new Coordinate(i, gridHeight-1));
        }

        for(int i = 1; i < gridHeight-1; ++i)
        {
            walls.add(new Coordinate(0, i));
            walls.add(new Coordinate(gridWidth-1, i));
        }
    }

    private void CreateSnake()
    {
        snake.clear();
        for(int i = 0; i < snakeInitialSize; ++i)
            snake.add(new Coordinate(gridWidth/2, gridHeight/2 + i));
    }

    private TileType[][] CreateGrid()
    {
        grid = new TileType[gridWidth][gridHeight];

        for(int i = 0; i < grid.length; ++i)
        {
            for(int j = 0; j < grid[0].length; ++j)
                grid[i][j] = TileType.Empty;
        }

        for(Coordinate c : walls)
            grid[c.GetX()][c.GetY()] = TileType.Wall;

        for(Coordinate c : snake)
            grid[c.GetX()][c.GetY()] = TileType.SnakeTail;

        grid[snake.get(0).GetX()][snake.get(0).GetY()] = TileType.SnakeHead;
        grid[itemLocation.GetX()][itemLocation.GetY()] = TileType.Item;
        return grid;
    }

    public TileType[][] CreateGridOptions()
    {
        grid = new TileType[gridWidth][gridHeight];

        for(int i = 0; i < grid.length; ++i)
        {
            for(int j = 0; j < grid[0].length; ++j)
                grid[i][j] = TileType.Empty;
        }

        CreateWalls();

        for(Coordinate c : walls)
            grid[c.GetX()][c.GetY()] = TileType.Wall;

        return grid;
    }

    private void SpawnItem(List<Coordinate> snake, Coordinate c)
    {
        for(Coordinate i : snake)
        {
            if(i.equals(c))
            {
                SpawnItem(snake, PickRandomLocation());
                return;
            }
        }
        itemLocation = c;
    }

    private Coordinate PickRandomLocation()
    {
        Random random = new Random();
        int randomX = random.nextInt((gridWidth-1) - 1) + 1;
        int randomY = random.nextInt((gridHeight-1) - 1) + 1;
        return new Coordinate(randomX, randomY);
    }

    public TileType[][] UpdateGrid()
    {
        return CreateGrid();
    }

    public void SetCurrentDirection(Direction currentDirection)
    {
        this.currentDirection = currentDirection;
    }

    public Direction GetCurrentDirection()
    {
        return this.currentDirection;
    }

    public GameState GetCurrentGameState()
    {
        return this.currentGameState;
    }

    public void SetGridHeight(int gridHeight)
    {
        this.gridHeight = gridHeight;
    }
}