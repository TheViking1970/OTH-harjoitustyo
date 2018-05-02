
package net.vikke.missilecommand.game;

public class Explosion {
    double x;
    double y;
    double radius;
    double currRadius;
    double currRadius2;
    double angle;
    boolean isActive;
    
    /**
     * Konstruktori Räjähdykselle.
     * 
     * @param x räjähdyksen keskipisteen x-koordinaatti
     * @param y räjähdyksen keskipisteen y-koordinaatti
     * @param radius mihin kokoon räjähdys kasvaa
     */
    public Explosion(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        currRadius = 0;
        currRadius2 = 0;
        angle = Math.PI;
        isActive = true;
    }
    
    /**
     * Animoidaan räjähdystä, eli joko räjähdys suurenee tai pienenee.
     * 
     * @return true, jos räjähdys on edelleen aktiivinen, muuten false
     */
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
            Game.gfx.drawExplosion(x, y, currRadius, draw);
        } else {
            // else mark explosion for deletion
            isActive = false;
        }
        return isActive;
    }
    
}
