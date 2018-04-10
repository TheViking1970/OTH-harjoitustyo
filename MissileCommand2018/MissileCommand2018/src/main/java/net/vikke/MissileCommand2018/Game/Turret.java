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
public class Turret {
    int ammo;
    boolean isAlive;
    double x;
    double y;
    int speed;
    
    public Turret(GraphicsContext gc, int ammo, double x, double y, int speed) {
        this.ammo = ammo;
        this.isAlive = true;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
    
    public Missile fire(GraphicsContext gc, Paint color, Paint backgroundColor, double xp, double yp) {
        if(ammo>0) {
            if (yp > y) {
                yp = y;
            }
            ammo--;
            return new Missile(gc, color, backgroundColor, x, y, xp, yp, speed);
        }
        return null;
    }
    
    public void hit() {
        isAlive = false;
        ammo = 0;
    }
}
