package teamc;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public class Snake {
    private LinkedList<Segment> body; // snake body segments (as list of cordinates)
    private Direction direction;      // current movement direction
    private boolean canChangeDirection = true; 
    private int growCounter = 0;
    public int score = 0;
    private final Color headColor;
    private final Color bodyColor;

    public Snake(int startX, int startY, Color headColor, Color bodyColor) {
        this.headColor = headColor;
        this.bodyColor = bodyColor;

        body = new LinkedList<>();
        body.add(new Segment(startX, startY, headColor)); // Head color
        body.add(new Segment(startX - 1, startY, bodyColor));
        body.add(new Segment(startX - 2, startY, bodyColor));
        direction = Direction.RIGHT;
    } // initialize snake

    public void move() {
        Segment head = body.getFirst();
        int newX = head.x;
        int newY = head.y; // find current head position

        switch (direction) {
            case UP:
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                newX++;
                break;
        } // account for where head will be based on dir

        body.addFirst(new Segment(newX, newY, headColor)); // add new head
        if(body.size() > 1) {
            body.get(1).color = bodyColor; // body color
        }

        if (growCounter > 0) {
            growCounter--;
        } else {
            body.removeLast();
        } // remove tail (unless growing) 
        canChangeDirection = true; // resets only one move input per frame
    }

    public void grow() {
        growCounter += 1; // grow by 1 segment next move
    } 

    public boolean hasEatenSelf() {
        Segment head = body.getFirst();
        return body.stream()
            .skip(1) // Skip head (prevents insta lose)
            .anyMatch(segment -> segment.x == head.x && segment.y == head.y);
    } 


    public void setDirection(Direction newDir) {
        if (canChangeDirection && !direction.isOpposite(newDir)) { // prevents 180 turns
            direction = newDir;
            canChangeDirection = false; // uses up the one move input per frame
        }
    }

    public LinkedList<Segment> getBody() { return body; } // gives board class access to check out of bounds
    public Direction getDirection() { return direction; } // not used 


    public static class Segment { // helper class to store snake information
        public int x, y;
        public Color color;
        public Segment(int x, int y, Color color) { // all public so board class can access it
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public boolean isOpposite(Direction other) {
            return (this == UP && other == DOWN) ||
                   (this == DOWN && other == UP) ||
                   (this == LEFT && other == RIGHT) ||
                   (this == RIGHT && other == LEFT); // for preventing 180 turns
        }
    }
}