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
public class Missile {
    GraphicsContext gc;
    double x;
    double y;
    double dx;
    double dy;
    double speed;
    double speed2;
    Paint color;
    Paint backgroundColor;
    double startX;
    double startY;
    double endX;
    double endY;
    boolean isActive;
    
    public Missile(GraphicsContext gc, Paint color, Paint backgroundColor, double x, double y, double xp, double yp, double speed) {
        this.gc = gc;
        this.color = color;
        this.backgroundColor = backgroundColor;
        this.x = x;
        this.y = y;
        startX = x;
        startY = y;
        endX = xp;
        endY = yp;
        isActive = true;
        this.speed = speed;
        this.speed2 = speed*speed;
        double stepLength = Math.sqrt((x - xp) * (x - xp) + (y - yp) * (y - yp));
        this.dx = (xp - x) / stepLength * speed;
        this.dy = (yp - y) / stepLength * speed;
    }
    
    public boolean move() {
        if(!isActive) {
            return false;
        }
        gc.setStroke(color);
        gc.setLineWidth(1);
        gc.strokeLine(x, y, x+dx, y+dy);
        x += dx;
        y += dy;
        
        double dist2 = (x-endX)*(x-endX)+(y-endY)*(y-endY);
        if(dist2<speed2) {
            remove();
        }
        return isActive;
    }
    
    public void remove() {
        gc.setStroke(backgroundColor);
        gc.setLineWidth(3);
        gc.strokeLine(startX, startY, x, y);
        isActive = false;
    }
}
