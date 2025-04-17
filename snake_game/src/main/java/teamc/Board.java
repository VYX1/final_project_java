package teamc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Board {
    private Pane root;
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private Snake snake;
    private AnimationTimer gameLoop;
    private boolean gameOver = false;
    private List<Food> foods = new ArrayList<>();
    private static final int MAX_FOODS = 5;
    private int score = 0;


    // grid setup
    static int CELL_SIZE = 10;
    static int GRID_WIDTH = 80;
    static int GRID_HEIGHT = 60;

    public Board() {
        initializeBoard(); //set scene for primary controller
        setupGameLoop();
        setupInputHandling();
        spawnInitialFood();
    }   

    private void initializeBoard() {
        gameCanvas = new Canvas(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE);
        root = new Pane(gameCanvas);
        gc = gameCanvas.getGraphicsContext2D();
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight()); //conversion to grid
        snake = new Snake(GRID_WIDTH / 2, GRID_HEIGHT / 2); //initialize snake in center
    }

    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000) { // set game speed (update interval in nanoseconds)
                    updateGame();
                    render();
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }

    private void setupInputHandling() {
        gameCanvas.setFocusTraversable(true);  // focus canvas (prevents error with keyboard input)
        gameCanvas.setOnKeyPressed(this::handleKeyPress); // send key input to handler
    }

    private void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) { // gets keycodes
            case UP:
                snake.setDirection(Snake.Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Snake.Direction.DOWN);
                break;
            case LEFT:
                snake.setDirection(Snake.Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Snake.Direction.RIGHT);
                break;
        }
    }
    

    private void spawnInitialFood() {
        while (foods.size() < MAX_FOODS) {
            foods.add(Food.spawnFood(snake.getBody(), foods)); // spawn initial food, avoid spawning inside snake or on other food
        }
    }

    private void updateGame() {
        if (!gameOver) {
            snake.move();
            checkCollisions();
            checkFoodCollision(); // this event updates the game each frame where fps is determined by gameLoop
        }
    }

    private void checkFoodCollision() {
        Snake.Segment head = snake.getBody().getFirst();
        foods.removeIf(food -> { // lambda operator
            if (food.getX() == head.x && food.getY() == head.y) {
                snake.grow(); 
                score += 1;
                return true; // remove this food (case by case)
            }
            return false; // keep this food
        });

        while (foods.size() < MAX_FOODS) {
            foods.add(Food.spawnFood(snake.getBody(), foods));
        } // when food eaten instantly spawn a new one
    }

    private void checkCollisions() {
        Snake.Segment head = snake.getBody().getFirst();
        if (head.x < 0 || head.x >= GRID_WIDTH || 
            head.y < 0 || head.y >= GRID_HEIGHT) {
            gameOver(); // out of bounds check
        }
        if (snake.hasEatenSelf()) {
            gameOver(); 
        }
    }

    private void gameOver() {
        gameOver = true;  //stop gameloop
        gameLoop.stop();  
        App.finalScore = score;
        try {
            App.setRoot("game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getScore() { return score; } 

    private void render() {
        gc.setFill(Color.BLACK); // redraw everything each frame to prevent visual bugs with snake size
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight()); 

        
        for (Snake.Segment segment : snake.getBody()) {
            gc.setFill(segment.color);
            gc.fillRect(
                segment.x * CELL_SIZE, 
                segment.y * CELL_SIZE, 
                CELL_SIZE - 1, // leave small gap between snake segments
                CELL_SIZE - 1
            );
        }

        for (Food food : foods) { // loop for each food
            gc.setFill(food.getColor());
            gc.fillOval( // circular food
                food.getX() * CELL_SIZE,  // x
                food.getY() * CELL_SIZE, // y
                CELL_SIZE, // w
                CELL_SIZE // h
            );
        }
    }

    public Pane getView() {
        return root; // for primary controller
    }
}