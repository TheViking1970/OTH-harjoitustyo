/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

public class City {
    double x;
    double y;
    double width;
    double leftX;
    boolean isAlive;
    
    public City(double x, double y, double width) {
        this.x = x;
        this.y = y;
        this.width = width;
        isAlive = true;
        leftX = x - width / 2;
    }
    
    public void hit() {
        isAlive = false;
        if (Game.gfx != null) {
            Game.gfx.unDrawCity(leftX, y, width);
        }
    }
    
    public double dist2(EnemyMissile missile) {
        return (missile.x - x) * (missile.x - x) + (missile.y - y) * (missile.y - y);
    }
    
    public void draw() {
        if (isAlive) {
            if (Game.gfx != null) {
                Game.gfx.drawCity(leftX, y);
            }
        } else {
            if (Game.gfx != null) {
                Game.gfx.drawDestroyedCity(leftX, y);
            }
        }
    }

}
