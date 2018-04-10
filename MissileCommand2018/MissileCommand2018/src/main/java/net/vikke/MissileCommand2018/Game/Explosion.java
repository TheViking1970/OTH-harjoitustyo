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
public class Explosion {
    GraphicsContext gc;
    double x;
    double y;
    double radius;
    double currRadius;
    double currRadius2;
    double angle;
    Paint explosionColor;
    Paint backgroundColor;
    boolean isActive;
    
    public Explosion(GraphicsContext gc, double x, double y, double radius, Paint explosionColor, Paint backgroundColor) {
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.explosionColor = explosionColor;
        this.backgroundColor = backgroundColor;
        currRadius = 0;
        currRadius2 = 0;
        angle = Math.PI;
        isActive = true;
    }
    
    public boolean animate() {
        //draw(backgroundColor);
        angle -= .025;
        currRadius = radius*Math.sin(angle);
        currRadius2 = currRadius*currRadius;
        if(currRadius>0) {
            if(angle>Math.PI/2) {
                draw(explosionColor,2);
            }
            else {
                draw(backgroundColor,2.5);
            }
        }
        else {
            isActive = false;
        }
        return isActive;
    }
    
    public void draw(Paint color, double width) {
        gc.setStroke(color);
        gc.setLineWidth(width);
        gc.strokeOval(x-currRadius, y-currRadius, currRadius*2, currRadius*2);
    }
    
}
