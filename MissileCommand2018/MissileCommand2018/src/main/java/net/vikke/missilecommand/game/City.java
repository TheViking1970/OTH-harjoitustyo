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
    
    /**
     * Konstruktori Kaupungille.
     * 
     * @param x Kaupungin keskipisteen x-koordinaatti
     * @param y Kaupungin keskipisteen y-koordinaatti
     * @param width Kaupungin leveys
     */
    public City(double x, double y, double width) {
        this.x = x;
        this.y = y;
        this.width = width;
        isAlive = true;
        leftX = x - width / 2;
    }
    
    /**
     * Kun kaupuki saa osuman.
     */
    public void hit() {
        isAlive = false;
        Game.gfx.unDrawCity(leftX, y, width);
    }
    
    /**
     * Piirrä kaupunki. kaupungin isAlive-muuttuja vaikuttaa siihen millainen kuva piirretään.
     */
    public void draw() {
        if (isAlive) {
            Game.gfx.drawCity(leftX, y);
        } else {
            Game.gfx.drawDestroyedCity(leftX, y);
        }
    }

}
