/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

import net.vikke.missilecommand.helper.Helper;

public class EnemyMissile {
    double x;
    double y;
    double startX;
    double startY;
    double endX;
    double endY;
    double dx;
    double dy;
    double speed;
    double speedSq;
    boolean isActive;
    
    /**
     * Konstruktori Vihollisohjukselle
     * 
     * @param x Ohjuksen aloituksen x-koordinaatti
     * @param y Ohjuksen aloituksen y-koordinaatti
     * @param xp    Ohjuksen maalin x-piste
     * @param yp    Ohjuksen maalin y-koordinaatti
     * @param speed 
     */
    public EnemyMissile(double x, double y, double xp, double yp, double speed) {
        this.x = x;
        this.y = y;
        startX = x;
        startY = y;
        endX = xp;
        endY = yp;
        this.speed = speed;
        this.speedSq = Helper.square(speed);
        isActive = true;
        
        double length = Helper.norm((x - xp), (y - yp));
        this.dx = (xp - x) / length * speed;
        this.dy = (yp - y) / length * speed;
    }
    
    /**
     * Liikutetaan ohjusta eteenpäin sen konstruktorissa määräämässä suunnassa.
     * @return 
     */
    public boolean move() {
        // if not active abort
        if (!isActive) {
            return false;
        }
        Game.gfx.drawEnemyMissile(x, y, dx, dy);
        
        // change x and y coordinates according to dx and dy
        x += dx;
        y += dy;
        
        // if whithin a distance of speed from destination, then undraw missile
        if (Helper.normSquared((x - endX), (y - endY)) < speedSq) {
            unDraw();
        }
        return isActive;
    }
    
    /**
     * Tarkistetaan kuinka lähellä ohjus on räjähdykseen.
     * 
     * @param explosion Mihini räjähdykseen suoritetaan vertailu
     * @return true, jos ohjus liian lähellä, muuten false
     */
    public boolean checkColission(Explosion explosion) {
        if (Helper.normSquared((x - explosion.x), (y - explosion.y)) < explosion.currRadius2) {
            unDraw();
            return true;
        }
        return false;
    }
    
    /**
     * Poistetaan ohjus ja sen jälki.
     */
    public void unDraw() {
        Game.gfx.unDrawEnemyMissile(startX, startY, x, y);

        // set active state to false, missile to be removed after handling
        isActive = false;
    }
    
    /**
     * Hae ohjuksen x-koordinaatti.
     * @return ohjuksen x-koordinaatti
     */
    public double getX() {
        return this.x;
    }
    
    /**
     * Hae ohjuksen y-koordinaatti.
     * @return ohjuksen y-koordinaatti
     */
    public double getY() {
        return this.y;
    }
    
    /**
     * hae ohjuksen nopeus.
     * @return ohjuksennopeus
     */
    public double getSpeed() {
        return this.speed;
    }
    
    /**
     * Hae onko ohjus aktiivinen.
     * @return true, jos aktiivinen, muuten false
     */
    public boolean getActive() {
        return this.isActive;
    }
}
