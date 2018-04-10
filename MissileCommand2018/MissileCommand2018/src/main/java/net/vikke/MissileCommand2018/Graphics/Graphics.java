/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.net.vikke.Graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author Tom
 */
public class Graphics {
    GraphicsContext gc;
    Paint missileColor;
    Paint enemyMissileColor;
    Paint backgroundColor;
    Paint foregroundColor;
    Paint cityColor;
    Paint destroyedCityColor;
    Paint explosionColor;
    
    public Graphics(GraphicsContext gc) {
        this.gc = gc;
        enemyMissileColor = Color.PINK;
        backgroundColor = Color.BLACK;
    }
    
    public void setColors(Paint[] colors) {
        foregroundColor = colors[0];
        backgroundColor = colors[1];
        missileColor = colors[2];
        enemyMissileColor = colors[3];
        explosionColor = colors[4];
        cityColor = colors[5];
        destroyedCityColor = colors[6];
    }
    
    /**
    *  drawEnemyMissile(
    *    double current x position
    *    double current y position
    *    double delta x
    *    double delta y)
    */
    public void drawEnemyMissile(double x, double y, double dx, double dy) {
        // remove previous missile head
        gc.setFill(backgroundColor);
        gc.fillOval(x - 2, y - 2, 4, 4);
        // draw previous and next missile pathline
        gc.setStroke(enemyMissileColor);
        gc.setLineWidth(1);
        gc.strokeLine(x - dx, y - dy, x, y);
        gc.strokeLine(x, y, x + dx, y + dy);
        // draw missile head
        gc.setFill(enemyMissileColor);
        gc.fillOval(x - 1, y - 1, 2, 2);
    }
    
    public void unDrawEnemyMissile(double startX, double startY, double x, double y) {
        gc.setStroke(backgroundColor);
        gc.setLineWidth(3);
        gc.strokeLine(startX, startY, x, y);
    }
}
