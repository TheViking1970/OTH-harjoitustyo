/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

import net.vikke.missilecommand.graphics.Graphics;

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
 
public class Game extends Application {
 
    int score = 0;
    int level = 1;
    int levelMissiles = 0;
    int spawnedMissiles = 0;
    
    double mouseX = 0;
    double mouseY = 0;
    
    int cWidth = 800;
    int cHeight = 800;

    List<Missile> missiles;
    List<EnemyMissile> enemyMissiles;
    List<Explosion> explosions;

    List<EnemyMissile> enemiesToRemove;
    List<Explosion> explosionsToAdd;

    Turret turretLeft;
    Turret turretMid;
    Turret turretRight;
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
        gfx = new Graphics(primaryStage, cWidth, cHeight, "Missile Command 2018");
        // setup inputhandlers
        handleKeyboardInput(primaryStage);
        handleMouseInput(primaryStage);
        
        // setup level 1
        setUpLevel();
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                spawnEnemyMissiles();
                handleEnemyMissiles();
                handleExplosions();
                handlePlayerMissiles();
                drawPlayfield();
                drawScore();
            }
        }.start();
    }

    private void drawTurretsAndGround() {
        for (Turret turret : turrets) {
            if (Game.gfx != null) {
                Game.gfx.drawTurret(turret.x, turret.y + 42, turret.ammo);
            }
        }
        if (Game.gfx != null) {
            Game.gfx.drawBaseGround();
        }
    }
    
    private void spawnEnemyMissiles() {
        if (spawnedMissiles >= levelMissiles) {
            return;
        }
        if (enemyMissiles.size() < 4 + level) {
            if (Math.random() > .975) {
                double startX = Math.random() * cWidth;
                double endX = Math.random() * (cWidth - 100) + 50;
                double speed = 1 + (level - 1) / 20;
                enemyMissiles.add(new EnemyMissile(startX, 0, endX, 775, speed));
                spawnedMissiles++;
            }
        }
        if (spawnedMissiles >= levelMissiles && enemyMissiles.size() == 0) {
            // end level
            level++;
            setUpLevel();
        }
    }
    
    private void handleEnemyMissiles() {
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
            }
        }
        // remove all enemymissiles marked for removal
        for (EnemyMissile missile : enemiesToRemove) {
            enemyMissiles.remove(missile);
        }
    }
    
    private void checkCollisionWithCity(EnemyMissile missile) {
        // iterate trough all cities to detect collision
        for (City city : cities) {
            // if distance from misile to city is within threshold, then collision with city
            if (city.isAlive && city.dist2(missile) < 625) {
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
    
    private void handleExplosions() {
        // empty the arraylist
        List<Explosion> explosionsToRemove = new ArrayList<>();
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
    
    private void handlePlayerMissiles() {
        List<Missile> toRemove = new ArrayList<>();
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
    
    private void drawScore() {
        if (Game.gfx != null) {
            Game.gfx.drawScore(score);
        }
    }
    
    private void drawPlayfield() {
        for (City city : cities) {
            city.draw();
        }
        drawTurretsAndGround();
    }
    
    private void drawBackground() {
        if (Game.gfx != null) {
            Game.gfx.drawEmptyBackground();
        }
    }

    private void handleKeyboardInput(Stage primaryStage) {
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                keysToTurret.put(KeyCode.A, turretLeft);
                keysToTurret.put(KeyCode.LEFT, turretLeft);
                keysToTurret.put(KeyCode.S, turretMid);
                keysToTurret.put(KeyCode.DOWN, turretMid);
                keysToTurret.put(KeyCode.D, turretRight);
                keysToTurret.put(KeyCode.RIGHT, turretRight);
                Missile missile = null;
                if (keysToTurret.containsKey(e.getCode())) {
                    missile = keysToTurret.get(e.getCode()).fire(mouseX, mouseY);
                }
                if (missile != null) {
                    missiles.add(missile);
                }
            }
        });
    }
 
    private void handleMouseInput(Stage primaryStage) {
        primaryStage.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });    
    }
    
    
    private void makeTurrets() {
        this.turretLeft = new Turret(100, 50, 735, 20);
        this.turretMid = new Turret(10, 400, 735, 20);
        this.turretRight = new Turret(100, 750, 735, 20);
        this.turrets = Arrays.asList(turretLeft, turretMid, turretRight);
    }
    
    private void makeCities() {
        this.cities[0] = new City(150, 775, 60);
        this.cities[1] = new City(225, 775, 60);
        this.cities[2] = new City(300, 775, 60);
        this.cities[3] = new City(500, 775, 60);
        this.cities[4] = new City(575, 775, 60);
        this.cities[5] = new City(650, 775, 60);
    }
    
    private String[] getLevelColors() {
        return new String[]{"BLACK", "YELLOW", "RED", "PINK", "CORAL", "BLUE", "GRAY"};
    }
    
    private void setUpLevel() {
        spawnedMissiles = 0;
        levelMissiles = 10 + (level - 1) * 5;
        Game.gfx.setColors(getLevelColors());
        missiles = new ArrayList<>();
        enemyMissiles = new ArrayList<>();
        explosions = new ArrayList<>();
        makeCities();
        makeTurrets();
        drawBackground();
        drawPlayfield();
    }
    
    static double square(double x) {
        return x * x;
    }
    static double normSquared(double x, double y) {
        return x * x + y * y;
    }
    static double norm(double x, double y) {
        return Math.sqrt(normSquared(x, y));
    }
    
}