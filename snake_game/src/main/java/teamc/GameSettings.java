package teamc;

import javafx.scene.paint.Color;

public class GameSettings {
    public static long GAME_SPEED = 100_000_000; // Default medium (10ms)
    public static Color HEAD_COLOR = Color.DARKGREEN;
    public static Color BODY_COLOR = Color.LIGHTGREEN;

    public enum Difficulty {
        EASY(150_000_000), MEDIUM(100_000_000), HARD(50_000_000);
        public final long interval;
        
        Difficulty(long interval) {
            this.interval = interval;
        }
    }
}