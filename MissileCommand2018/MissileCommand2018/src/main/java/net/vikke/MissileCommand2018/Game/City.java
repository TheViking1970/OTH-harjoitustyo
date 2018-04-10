/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.net.vikke.Game;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tom
 */
public class City {
    GraphicsContext gc;
    double x;
    double y;
    double width;
    double leftX;
    Paint cityColor;
    Paint destroyedColor;
    Paint backgroundColor;
    
    boolean isAlive;
    
    public City(GraphicsContext gc, double x, double y, double width, Paint cityColor, Paint destroyedColor, Paint backgroundColor) {
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.width = width;
        this.cityColor = cityColor;
        this.destroyedColor = destroyedColor;
        this.backgroundColor = backgroundColor;
        isAlive = true;
        leftX = x - width / 2;
    }
    
    public void hit() {
        isAlive = false;
        unDraw();
    }
    
    public double dist2(EnemyMissile missile) {
        return (missile.x-x)*(missile.x-x)+(missile.y-y)*(missile.y-y);
    }
    
    public void draw() {
        if(isAlive) {
            drawCity();
        }
        else {
            drawDestroyedCity();
        }
    }
    
    private void drawCity() {
        double x = leftX;
        gc.setFill(cityColor);
        gc.fillPolygon(new double[]{x, x, x + 10, x + 20, x + 30, x + 40, x + 50, x + 60, x + 60},
                new double[]{y, y - 20, y - 10, y - 20, y - 10, y - 20, y - 10, y - 20, y}, 9);
    }

    private void drawDestroyedCity() {
        double x = leftX;
        gc.setFill(destroyedColor);
        gc.fillPolygon(new double[]{x, x, x + 10, x + 20, x + 30, x + 40, x + 50, x + 60, x + 60},
                new double[]{y, y - 5, y - 10, y - 5, y - 10, y - 5, y - 10, y - 5, y}, 9);
    }
    
    private void unDraw() {
        gc.setFill(backgroundColor);
        gc.fillRect(leftX, y - 20, width, 20);
    }
    

}
