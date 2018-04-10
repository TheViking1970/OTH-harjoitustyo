/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.net.vikke.Game;

import main.java.net.vikke.Graphics.Graphics;

import java.util.ArrayList;
import java.util.List;
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
 
        int mouseX = 0;
        int mouseY = 0;
        
        Paint missileColor;
        Paint enemyMissileColor;
        Paint backgroundColor;
        Paint foregroundColor;
        Paint cityColor;
        Paint destroyedCityColor;
        Paint explosionColor;
        
        List<Missile> missiles;
        List<EnemyMissile> enemyMissiles;
        List<Explosion> explosions;

        static Graphics gfx;

    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Missile Command 2018");
        Group root = new Group();
        Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        gfx = new Graphics(gc);
        
        
        missileColor = Color.RED;
        enemyMissileColor = Color.PINK;
        backgroundColor = Color.BLACK;
        foregroundColor = Color.YELLOW;
        cityColor = Color.BLUE;
        destroyedCityColor = Color.RED;
        explosionColor = Color.CYAN;

        double[] baseX = new double[]{  0,   0,  25,  75, 125, 325, 375, 425, 475, 675, 725, 775, 800, 800};
        double[] baseY = new double[]{800, 750, 725, 725, 775, 775, 725, 725, 775, 775, 725, 725, 750, 800};
        
        
        missiles = new ArrayList<>();
        enemyMissiles = new ArrayList<>();
        explosions = new ArrayList<>();

        Turret turretLeft = new Turret(gc, 100, 50,735, 20);
        Turret turretMid = new Turret(gc, 100, 400,735, 20);
        Turret turretRight = new Turret(gc, 100, 750,735, 20);
        
        City[] cities = new City[6];
        cities[0] = new City(gc, 150,775, 60, cityColor, destroyedCityColor, backgroundColor);
        cities[1] = new City(gc, 225,775, 60, cityColor, destroyedCityColor, backgroundColor);
        cities[2] = new City(gc, 300,775, 60, cityColor, destroyedCityColor, backgroundColor);
        cities[3] = new City(gc, 500,775, 60, cityColor, destroyedCityColor, backgroundColor);
        cities[4] = new City(gc, 575,775, 60, cityColor, destroyedCityColor, backgroundColor);
        cities[5] = new City(gc, 650,775, 60, cityColor, destroyedCityColor, backgroundColor);
        
        gc.setFill(backgroundColor);
        gc.fillPolygon(new double[]{0, 800, 800, 0}, new double[]{0, 0, 800, 800}, 4);

        for(City city : cities) {
            city.draw();
        }
        
        drawTurret(gc, turretLeft.x, turretLeft.y+42, foregroundColor);
        drawTurret(gc, turretMid.x, turretMid.y+42, foregroundColor);
        drawTurret(gc, turretRight.x, turretRight.y+42, foregroundColor);
        gc.fillRect(0, 775, 800, 25);
        
        gc.setFill(foregroundColor);
        //gc.fillPolygon(baseX, baseY, 14);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // ENEMY
                if(enemyMissiles.size()<100) {
                    if(Math.random()>.95) {
                        enemyMissiles.add(new EnemyMissile(Math.random()*800,0, Math.random()*700+50,775, 1));
                    }
                }
                List<EnemyMissile> enemiesToRemove = new ArrayList<>();
                List<Explosion> explosionsToAdd = new ArrayList<>();
                for(EnemyMissile missile : enemyMissiles) {
                    for(Explosion explosion : explosions) {
                        if(missile.checkColission(explosion)) {
                            explosionsToAdd.add(new Explosion(gc, missile.x, missile.y, 50, explosionColor, backgroundColor));
                        }
                    }
                    if(!missile.move()) {
                        enemiesToRemove.add(missile);
                    }
                    else {
                        for(City city : cities) {
                            if(!city.isAlive) {
                                continue;
                            }
                            if(city.dist2(missile)<625) {
                                city.hit();
                                missile.unDraw();
                                explosions.add(new Explosion(gc, city.x, city.y, 40, explosionColor, backgroundColor));
                            }
                        }
                    }
                }
                for(EnemyMissile missile : enemiesToRemove) {
                    enemyMissiles.remove(missile);
                }
                // end ENEMY
                
                // EPLOSIONS
                List<Explosion> explosionsToRemove = new ArrayList<>();
                for(Explosion explosion : explosions) {
                    if(!explosion.animate()) {
                        explosionsToRemove.add(explosion);
                    }
                }
                for(Explosion explosion : explosionsToRemove) {
                    explosions.remove(explosion);
                }
                for(Explosion explosion : explosionsToAdd) {
                    explosions.add(explosion);
                }
                // end EXPLOSIONS
                
                // PLAYER
                List<Missile> toRemove = new ArrayList<>();
                for(Missile missile : missiles) {
                    boolean isActive = missile.move();
                    if(!isActive) {
                        toRemove.add(missile);
                        explosions.add(new Explosion(gc, missile.endX, missile.endY, 50, explosionColor, backgroundColor));
                    }
                }
                for(Missile missile : toRemove) {
                    missiles.remove(missile);
                }
                // end PLAYER
                
                for (City city : cities) {
                    city.draw();
                }

                drawTurret(gc, turretLeft.x, turretLeft.y + 42, foregroundColor);
                drawTurret(gc, turretMid.x, turretMid.y + 42, foregroundColor);
                drawTurret(gc, turretRight.x, turretRight.y + 42, foregroundColor);
                gc.fillRect(0, 775, 800, 25);

            }
        }.start();
        
        
        Scene theScene = new Scene(root, 800, 800, false, SceneAntialiasing.DISABLED);
        primaryStage.setScene(theScene);
        
        primaryStage.show();
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,
            new EventHandler<KeyEvent>() {
                //@Override
                public void handle(KeyEvent e) {
                    KeyCode kc = e.getCode();
                    Missile missile = null;
                    if(kc==KeyCode.A || kc==KeyCode.LEFT) {
                        missile = turretLeft.fire(gc, missileColor, backgroundColor, mouseX, mouseY);
                    }
                    if(kc==KeyCode.S || kc==KeyCode.DOWN) {
                        missile = turretMid.fire(gc, missileColor,backgroundColor, mouseX, mouseY);
                    }
                    if(kc==KeyCode.D || kc==KeyCode.RIGHT) {
                        missile = turretRight.fire(gc, missileColor, backgroundColor,mouseX, mouseY);
                    }
                    if(missile!=null) {
                        missiles.add(missile);
                    }
            }
        });    
        primaryStage.addEventHandler(MouseEvent.MOUSE_CLICKED,
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    gc.setStroke(Color.BLUE);
                    gc.strokeLine(400,800, mouseX, mouseY);
            }
        });    
        primaryStage.addEventHandler(MouseEvent.MOUSE_MOVED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    mouseX = (int) e.getX();
                    mouseY = (int) e.getY();
                }
        });    }

    private void drawCity(GraphicsContext gc, City city, Paint color) {
        gc.setFill(color);
        double width = 60;
        double x = city.x - width/2;
        double y = city.y;
        gc.fillPolygon(new double[] {x,    x, x+10, x+20, x+30, x+40, x+50, x+60, x+60},
                       new double[] {y, y-20, y-10, y-20, y-10, y-20, y-10, y-20,    y}, 9);
    }

    private void drawDestroyedCity(GraphicsContext gc, City city, Paint color, Paint backgroundColor) {
        gc.setFill(color);
        double width = 60;
        double x = city.x - width/2;
        double y = city.y;
        gc.setFill(backgroundColor);
        gc.fillRect(x, y-20, width, 20);
        gc.setFill(color);
        gc.fillPolygon(new double[] {x,    x, x+10, x+20, x+30, x+40, x+50, x+60, x+60},
                       new double[] {y,  y-5, y-10,  y-5, y-10,  y-5, y-10,  y-5,    y}, 9);
    }
    
    private void drawTurret(GraphicsContext gc, double xm, double y, Paint color) {
        gc.setFill(color);
        double width = 100;
        double x = xm - width/2;
        gc.fillPolygon(new double[] {x, x+25, x+30, x+50, x+70, x+75, x+100},
                       new double[] {y, y-40, y-30, y-40, y-30, y-40,     y}, 7);
    }
    
    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);

        gc.setLineWidth(1.0);
        gc.setStroke(Color.RED);
        gc.strokePolyline(new double[]{  0,   0,  25,  75, 125, 325, 375, 425, 475, 675, 725, 775, 800, 800},
                       new double[]{800, 750, 725, 725, 775, 775, 725, 725, 775, 775, 725, 725, 750, 800}, 14);
        
    }
    
    static double square(double x) {
        return x*x;
    }
    static double normSquared(double x, double y) {
        return x*x + y*y;
    }
    static double norm(double x, double y) {
        return Math.sqrt(normSquared(x,y));
    }
}