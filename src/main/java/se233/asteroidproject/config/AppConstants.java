package se233.asteroidproject.config;

public class AppConstants {
    public static final int DEFAULT_LARGE_ASTEROIDS_COUNT = 3;
    public static final int DEFAULT_MEDIUM_ASTEROIDS_COUNT = 0;
    public static final int DEFAULT_SMALL_ASTEROIDS_COUNT = 1;
    public static final int DEFAULT_ALIEN_COUNT = 1;
    public static final int MAX_ASTEROID_COUNT = 10;
    public static final int MAX_ALIEN_COUNT = 1;

    public static final int MAX_BULLET_FREQUENCY = 5; //  bps = bullets per second
    public static final int BULLET_LIFE_TIME = 1500; //1.5 seconds bullet lifetime. maybe i should make it longer idk

    public enum Points {
// points for the asteroids and boss
        LARGE_ASTEROID(5),
        MEDIUM_ASTEROID(10),
        SMALL_ASTEROID(15),
        ALIEN(20);

        private int point = 0;
        public int getPoint(){
            return this.point;
        }

        private Points(int point){
            this.point = point;
        }

    }
    public static final int LEVEL_THRESHOLD = 100; //need 100 points to move to the next level
}
