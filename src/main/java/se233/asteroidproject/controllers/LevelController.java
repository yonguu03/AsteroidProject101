package se233.asteroidproject.controllers;

import se233.asteroidproject.characters.*;
import se233.asteroidproject.characters.builds.Characters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static se233.asteroidproject.config.AppConstants.*;

public class LevelController {
    private AtomicInteger level = new AtomicInteger(1);

    //    Base level characters denotes the count of characters on screen for the base level.
//    Order of characters in array: Small Asteroid, Medium asteroid, Large asteroid, alien.
    private int[] enemyCounts = new int[]{DEFAULT_SMALL_ASTEROIDS_COUNT, DEFAULT_MEDIUM_ASTEROIDS_COUNT, DEFAULT_LARGE_ASTEROIDS_COUNT, DEFAULT_ALIEN_COUNT};


    public int[] getEnemyCounts() {
        return enemyCounts;
    }

    public void setEnemyCounts(int[] enemyCounts) {
        this.enemyCounts = enemyCounts;
    }

    public int getLevel(){
        return this.level.get();
    }
    public void setLevel(int level) {
        this.level.set(level);
    }

    public List<Characters> addEnemiesBasedOnLevel(){
        List<Characters> characters = new ArrayList<>();
        if (this.getLevel() == 1){
            characters.addAll(addAsteroids(enemyCounts[0], enemyCounts[1], enemyCounts[2]));
        }
        else{
            int asteroidsCount = enemyCounts[0] + enemyCounts[1] + enemyCounts[2];
            int smallAsteroidsCount = asteroidsCount < MAX_ASTEROID_COUNT ? enemyCounts[0] * this.getLevel() : enemyCounts[0];
            int mediumAsteroidsCount = enemyCounts[1] != 0 ? (asteroidsCount < MAX_ASTEROID_COUNT ? enemyCounts[1] * this.getLevel() : enemyCounts[1]) : 1;
            int largeAsteroidsCount = enemyCounts[2] != 0 ? (asteroidsCount < MAX_ASTEROID_COUNT ? enemyCounts[2] * this.getLevel() : enemyCounts[2]) : 1;
            int alienCount = enemyCounts[3] < MAX_ALIEN_COUNT ? enemyCounts[3] + 1 : MAX_ALIEN_COUNT;
            setEnemyCounts(new int[] {smallAsteroidsCount, mediumAsteroidsCount, largeAsteroidsCount, alienCount});
            characters.addAll(addAsteroids(smallAsteroidsCount, mediumAsteroidsCount, largeAsteroidsCount));
            characters.addAll(addAlien(alienCount));
        }
        return characters;
    }

    public List<Asteroids> addAsteroids(int numSmallAsteroids, int numMediumAsteroids, int numLargeAsteroids) {
        List<Asteroids> asteroidsList = new ArrayList<>();
        for (int i = 0; i < numSmallAsteroids; i++) {
            AsteroidsVar.SmallAsteroids smallAsteroid = new AsteroidsVar.SmallAsteroids();
            smallAsteroid.setAngle(Math.random() * 360);
            asteroidsList.add(smallAsteroid);
        }
        for (int i = 0; i < numMediumAsteroids; i++) {
            AsteroidsVar.MediumAsteroids mediumAsteroid = new AsteroidsVar.MediumAsteroids();
            mediumAsteroid.setAngle(Math.random() * 360);
            asteroidsList.add(mediumAsteroid);
        }
        for (int i = 0; i < numLargeAsteroids; i++) {
            AsteroidsVar.LargeAsteroids largeAsteroid = new AsteroidsVar.LargeAsteroids();
            largeAsteroid.setAngle(Math.random() * 360);
            asteroidsList.add(largeAsteroid);
        }
        return asteroidsList;
    }

    public List<Boss> addAlien(int alienCount) {
        List<Boss> aliensList = new ArrayList<>();
        for (int i = 0; i < alienCount; i++){
            Boss boss = new Boss();
            aliensList.add(boss);
        }
        return aliensList;
    }

    public int newLevel(int score){
        return score/LEVEL_THRESHOLD + 1;
    }
}
