/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

public class Missile {
    double x;
    double y;
    double dx;
    double dy;
    double speed;
    double speed2;
    double startX;
    double startY;
    double endX;
    double endY;
    boolean isActive;
    
    /**
     * Konstruktori pelaajan ohjukselle
     * 
     * @param x ohjuksen aloituksen x-koordinaatti
     * @param y ohjuksen aloituksen y-koordinaatti
     * @param xp    ohjuksen maalin x-koordinaatti
     * @param yp    ohjuksen maalin y-koordinaatti
     * @param speed ohjuksen nopeus
     */
    public Missile(double x, double y, double xp, double yp, double speed) {
        this.x = x;
        this.y = y;
        startX = x;
        startY = y;
        endX = xp;
        endY = yp;
        isActive = true;
        this.speed = speed;
        this.speed2 = speed * speed;
        double stepLength = Math.sqrt((x - xp) * (x - xp) + (y - yp) * (y - yp));
        this.dx = (xp - x) / stepLength * speed;
        this.dy = (yp - y) / stepLength * speed;
    }
    
    /**
     * Liikutetaan ohjusta konstruktorissa määritellun suunnan mukaisesti
     * 
     * @return true, jos ohjus on edelleen aktiivinen, muuten false
     */
    public boolean move() {
        if (!isActive) {
            return false;
        }
        Game.gfx.drawMissile(x, y, dx, dy);
        x += dx;
        y += dy;
        
        double dist2 = (x - endX) * (x - endX) + (y - endY) * (y - endY);
        if (dist2 < speed2) {
            Game.gfx.unDrawMissile(startX, startY, x, y);
            isActive = false;
        }
        return isActive;
    }
    
}