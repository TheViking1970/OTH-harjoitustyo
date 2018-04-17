/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

public class Turret {
    int ammo;
    boolean isAlive;
    double x;
    double y;
    int speed;
    
    public Turret(int ammo, double x, double y, int speed) {
        this.ammo = ammo;
        this.isAlive = true;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
    
    public Missile fire(double xp, double yp) {
        if (!isAlive || ammo <= 0) {
            return null;
        }
        if (yp > y) {
            yp = y;
        }
        ammo--;
        return new Missile(x, y, xp, yp, speed);
    }
    
    public void hit() {
        isAlive = false;
        ammo = 0;
    }
}
