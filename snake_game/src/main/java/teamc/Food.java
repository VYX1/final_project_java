package teamc;

import java.util.LinkedList;
import java.util.List;
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

    public int getX() { return x; } // render
    public int getY() { return y; } // render
    public Color getColor() { return color; } // render
 
    public static Food spawnFood(
        LinkedList<Snake.Segment> snakeBody,
        List<Food> existingFoods) {

        int x, y;
        do {
            x = RAND.nextInt(Board.GRID_WIDTH);
            y = RAND.nextInt(Board.GRID_HEIGHT);
    } while (!isValidPosition(x, y, snakeBody, existingFoods));

    return new Food(x, y, Color.RED);
} // generates food

    private static boolean isValidPosition(int x, int y, LinkedList<Snake.Segment> snakeBody, List<Food> existingFoods) {
        boolean onSnake = snakeBody.stream()
            .anyMatch(segment -> segment.x == x && segment.y == y);
        boolean onFood = existingFoods.stream()
            .anyMatch(food -> food.getX() == x && food.getY() == y);
    return !onSnake && !onFood; // valid if neither
} // checks for overlap on food spawn with snake or other food
}