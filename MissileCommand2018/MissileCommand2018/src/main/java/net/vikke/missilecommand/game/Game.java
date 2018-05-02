/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

import net.vikke.missilecommand.graphics.Graphics;
import net.vikke.missilecommand.input.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import net.vikke.missilecommand.helper.Helper;
 
public class Game extends Application {
 
    int score = 0;
    int bonus = 0;
    int level = 1;
    int levelMissiles = 0;
    int spawnedMissiles = 0;
    boolean levelUp = false;
    
    public static double mouseX = 0;
    public static double mouseY = 0;
    
    String currentState = "start";
    String nextState = "";
    public static boolean gotoNextState = false;
    public static boolean nextStateTriggerEnabled = true;
    boolean showBonus = false;
    
    static int cWidth = 800;
    static int cHeight = 800;

    public static List<Missile> missiles;
    List<EnemyMissile> enemyMissiles;
    List<Explosion> explosions;

    List<EnemyMissile> enemiesToRemove;
    List<Explosion> explosionsToAdd;
    List<Explosion> explosionsToRemove;
    List<Missile> toRemove;

    public static Turret turretLeft;
    public static Turret turretMid;
    public static Turret turretRight;
    List<Turret> turrets;

    City[] cities = new City[6];

    HashMap<KeyCode, Turret> keysToTurret = new HashMap<>();

    static Graphics gfx;
    
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        
        // setup graphics
        setUpGraphics(primaryStage);
        
        // setup inputhandlers
        Input input = new Input();
        input.handleKeyboardInput(primaryStage);
        input.handleMouseInput(primaryStage);
        
        // setup level 1
        setUpLevel(false);
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                stateMachine();
                spawnEnemyMissiles();
                handleEnemyMissiles();
                handleExplosions();
                handlePlayerMissiles();
                drawPlayfield();
                drawScore();
            }
        }.start();
    }
    
    static void setUpGraphics(Stage primaryStage) {
        if (primaryStage == null) {
            gfx = new Graphics();
        } else {
            gfx = new Graphics(primaryStage, cWidth, cHeight, "Missile Command 2018");
        }
    }
    
    private void drawTurretsAndGround() {
        for (Turret turret : turrets) {
            gfx.drawTurret(turret.x, turret.y + 42, turret.ammo);
        }
        gfx.drawBaseGround();
    }
    
    public void spawnEnemyMissiles() {
        if (spawnedMissiles >= levelMissiles) {
            if (!showBonus && enemyMissiles.size() == 0) {
                // end level
                levelUp = true;
            }
            // otherwise just wait for all missiles to die
            return;
        }
        if (enemyMissiles.size() < 4 + level) {
            if (enemyMissiles.size() == 0 || Math.random() > .975) {
                double startX = Math.random() * cWidth;
                double endX = Math.random() * (cWidth - 100) + 50;
                double speed = 1 + (level - 1) / 20;
                enemyMissiles.add(new EnemyMissile(startX, 0, endX, 775, speed));
                spawnedMissiles++;
            }
        }
    }
    
    public void handleEnemyMissiles() {
        enemiesToRemove = new ArrayList<>();
        explosionsToAdd = new ArrayList<>();
        
        // iterate trough all enemymissiles
        for (EnemyMissile missile : enemyMissiles) {
            checkCollisionWithExplosion(missile);
            // move missile, if method returns false, then add missile to be removed
            if (!missile.move()) {
                enemiesToRemove.add(missile);
            } else {
                checkCollisionWithCity(missile);
                checkCollisionWithTurret(missile);
            }
        }
        // remove all enemymissiles marked for removal
        for (EnemyMissile missile : enemiesToRemove) {
            enemyMissiles.remove(missile);
        }
    }

    private void checkCollisionWithTurret(EnemyMissile missile) {
        // iterate trough all cities to detect collision
        for (Turret turret : turrets) {
            // if distance from misile to city is within threshold, then collision with city
            if (turret.isAlive && Helper.normSquared(turret.x - missile.x, turret.y - missile.y) < 1600) {
                turret.hit();
                missile.unDraw();
                explosions.add(new Explosion(turret.x, turret.y, 40));
            }
        }
    }
    
    private void checkCollisionWithCity(EnemyMissile missile) {
        // iterate trough all cities to detect collision
        for (City city : cities) {
            // if distance from misile to city is within threshold, then collision with city
            if (city.isAlive && Helper.normSquared(city.x - missile.x, city.y - missile.y) < 625) {
                city.hit();
                missile.unDraw();
                explosions.add(new Explosion(city.x, city.y, 40));
            }
        }
    }
    
    private void checkCollisionWithExplosion(EnemyMissile missile) {
        // iterate trough all explosions
        for (Explosion explosion : explosions) {
            if (missile.checkColission(explosion)) {
                score += 100;
                explosionsToAdd.add(new Explosion(missile.x, missile.y, 50));
            }
        }
    }
    
    public void handleExplosions() {
        // empty the arraylist
        explosionsToRemove = new ArrayList<>();
        // iterate trough all eplosions, if animation ended then mark for removal
        for (Explosion explosion : explosions) {
            if (!explosion.animate()) {
                explosionsToRemove.add(explosion);
            }
        }
        // remove all explosions marked for removal
        for (Explosion explosion : explosionsToRemove) {
            explosions.remove(explosion);
        }
        // add all exliosions generated by explosions of enemymissiles
        for (Explosion explosion : explosionsToAdd) {
            explosions.add(explosion);
        }
    }
    
    public static void fireTurret(String tStr, double x, double y) {
        Turret turret = null;
        if (tStr.equals("LEFT")) {
            turret = turretLeft;
        } else if (tStr.equals(("MID"))) {
            turret = turretMid;
        } else if (tStr.equals("RIGHT")) {
            turret = turretRight;
        }
        if (turret != null && turret.ammo > 0) {
            missiles.add(turret.fire(x, y));
        }
    }

    
    public void handlePlayerMissiles() {
        toRemove = new ArrayList<>();
        for (Missile missile : missiles) {
            boolean isActive = missile.move();
            if (!isActive) {
                toRemove.add(missile);
                explosions.add(new Explosion(missile.endX, missile.endY, 50));
            }
        }
        for (Missile missile : toRemove) {
            missiles.remove(missile);
        }
    }
    
    public void drawScore() {
        gfx.drawScore(score);
    }
    
    public void drawPlayfield() {
        for (City city : cities) {
            city.draw();
        }
        drawTurretsAndGround();
        
    }
    
    private void drawBackground() {
        gfx.drawEmptyBackground();
    }
    
    private void makeTurrets() {
        this.turretLeft = new Turret(10, 50, 735, 20);
        this.turretMid = new Turret(10, 400, 735, 20);
        this.turretRight = new Turret(10, 750, 735, 20);
        this.turrets = Arrays.asList(turretLeft, turretMid, turretRight);
    }
    
    public void makeCities() {
        this.cities[0] = new City(150, 775, 60);
        this.cities[1] = new City(225, 775, 60);
        this.cities[2] = new City(300, 775, 60);
        this.cities[3] = new City(500, 775, 60);
        this.cities[4] = new City(575, 775, 60);
        this.cities[5] = new City(650, 775, 60);
    }
    
    private String[] getLevelColors(int level) {
        // does not yet implement colorchanges according to level
        return new String[]{"BLACK", "YELLOW", "RED", "PINK", "CORAL", "BLUE", "GRAY"};
    }
    
    public void checkLevelUp() {
        if (levelUp) {
            // if flag set, then do levelup
            level++;
            nextState = "bonus";
            gotoNextState = true;
            levelUp = false;
            showBonus = true;
        }
    }

    private void showStartScreen() {
        gfx.drawStartScreen();
        nextStateTriggerEnabled = true;
    }

    private void showBonusScreen() {
        int citiesAlive = 0;
        for (City city : cities) {
            if (city.isAlive) {
                citiesAlive++;
            }
        }
        bonus = 500 * citiesAlive + 10 * (turretLeft.ammo + turretMid.ammo + turretRight.ammo);
        gfx.drawBonus(citiesAlive, turretLeft.ammo + turretMid.ammo + turretRight.ammo);
        nextStateTriggerEnabled = true;
    }

    private void showEndScreen() {
        gfx.drawEndScreen();
        nextStateTriggerEnabled = true;
    }
    
    public void setUpLevel(boolean flag) {
        setUpArrays();
        if (level < 2) {
            makeCities();
        }
        makeTurrets();
        drawBackground();
        drawPlayfield();
        gfx.setColors(getLevelColors(level));
        // add bonus from last level
        score += bonus;
        bonus = 0;
        if (flag) {
            nextStateTriggerEnabled = false;
            showBonus = false;
            levelUp = false;
            spawnedMissiles = 0;
            levelMissiles = 10 + (level - 1) * 5;
        }
        gfx.showButtons(false);
    }
    
    private void setUpArrays() {
        missiles = new ArrayList<>();
        enemyMissiles = new ArrayList<>();
        explosions = new ArrayList<>();
        explosionsToAdd = new ArrayList<>();
        explosionsToRemove = new ArrayList<>();
        toRemove = new ArrayList<>();
    }
    
    public void stateMachine() {
        if (gotoNextState) {
            if (currentState == "start") {
                showStartScreen();
                nextState = "play";
            } else if (currentState == "play") {
                setUpLevel(true);
                nextState = "bonus";
            } else if (currentState == "bonus") {
                showBonusScreen();
                nextState = "play";
            } else if (currentState == "end") {
                showEndScreen();
                nextState = "start";
            }
            currentState = nextState;
        }
        gotoNextState = false;
        checkLevelUp();
    }
    
}