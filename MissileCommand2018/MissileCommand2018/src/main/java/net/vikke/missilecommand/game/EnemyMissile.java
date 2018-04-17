/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

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
    
    public EnemyMissile(double x, double y, double xp, double yp, double speed) {
        this.x = x;
        this.y = y;
        startX = x;
        startY = y;
        endX = xp;
        endY = yp;
        this.speed = speed;
        this.speedSq = Game.square(speed);
        isActive = true;
        
        double length = Game.norm((x - xp), (y - yp));
        this.dx = (xp - x) / length * speed;
        this.dy = (yp - y) / length * speed;
    }
    
    public boolean move() {
        // if not active abort
        if (!isActive) {
            return false;
        }
        // if JavaFX setup, then draw missile
        if (Game.gfx != null) {
            Game.gfx.drawEnemyMissile(x, y, dx, dy);
        }
        
        // change x and y coordinates according to dx and dy
        x += dx;
        y += dy;
        
        // if whithin a distance of speed from destination, then undraw missile
        if (Game.normSquared((x - endX), (y - endY)) < speedSq) {
            unDraw();
        }
        return isActive;
    }
    
    public boolean checkColission(Explosion explosion) {
        if (Game.normSquared((x - explosion.x), (y - explosion.y)) < explosion.currRadius2) {
            unDraw();
            return true;
        }
        return false;
    }
    
    /*
    *  unDraw()
    *  overdraw missile in graphics and set active state to false
    */
    public void unDraw() {
        // if JavaFX setup, then undraw in graphics
        if (Game.gfx != null) {
            Game.gfx.unDrawEnemyMissile(startX, startY, x, y);
        }
        // set active state to false, missile to be removed after handling
        isActive = false;
    }
    
    /*
    *  getX()
    *  get x-coordinate of missile
    */
    public double getX() {
        return this.x;
    }
    
    /*
    *  getY()
    *  get y-coordinate of missile
    */
    public double getY() {
        return this.y;
    }
    
    /*
    *  getSpeed()
    *  get missile speed
    */
    public double getSpeed() {
        return this.speed;
    }
    
    /*
    *  getActive() {
    *  get activity state of missile
    */
    public boolean getActive() {
        return this.isActive;
    }
}
