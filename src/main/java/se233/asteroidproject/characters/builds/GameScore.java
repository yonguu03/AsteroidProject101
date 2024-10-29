package se233.asteroidproject.characters.builds;

public class GameScore implements Comparable<Object>{
    private String name;
    private int score;

    public GameScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        GameScore obj = (GameScore) o;
        return obj.getScore() - this.getScore(); // sort all the scores
    }
}
