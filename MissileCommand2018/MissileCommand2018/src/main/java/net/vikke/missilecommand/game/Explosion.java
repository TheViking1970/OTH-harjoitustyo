/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

public class Explosion {
    double x;
    double y;
    double radius;
    double currRadius;
    double currRadius2;
    double angle;
    boolean isActive;
    
    public Explosion(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        currRadius = 0;
        currRadius2 = 0;
        angle = Math.PI;
        isActive = true;
    }
    
    public boolean animate() {
        // size of the explosion is calculated with trigonometry, therefore the use of angles
        // decrement angle ( angle goes from PI to 0 )
        angle -= .025;
        // calculate the current radius and squared (used for collision detection)
        currRadius = radius * Math.sin(angle);
        currRadius2 = currRadius * currRadius;
        // if radius larger then 0 then draw or undraw explosion
        if (currRadius > 0) {
            // determine whether to draw or undraw explosion
            boolean draw = angle > Math.PI / 2 ? true : false;
            if (Game.gfx != null) {
                Game.gfx.drawExplosion(x, y, currRadius, draw);
            }
        } else {
            // else mark explosion for deletion
            isActive = false;
        }
        return isActive;
    }
    
}
