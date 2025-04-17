package teamc;

import java.util.LinkedList;
import java.util.Random;

import javafx.scene.paint.Color;

public class Food {
    private static final Random RAND = new Random();
    private int x;
    private int y;
    private Color color;
    
    public Food(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return color; }

    // Generate food at random position (not on snake)
    public static Food spawnFood(LinkedList<Snake.Segment> snakeBody) {
        int x, y;
        do {
            x = RAND.nextInt(Board.GRID_WIDTH);
            y = RAND.nextInt(Board.GRID_HEIGHT);
        } while (isOnSnake(x, y, snakeBody));
        
        return new Food(x, y, Color.RED);
    }

    // Check if position overlaps with snake
    private static boolean isOnSnake(int x, int y, LinkedList<Snake.Segment> snakeBody) {
        return snakeBody.stream()
            .anyMatch(segment -> segment.x == x && segment.y == y);
    }
}