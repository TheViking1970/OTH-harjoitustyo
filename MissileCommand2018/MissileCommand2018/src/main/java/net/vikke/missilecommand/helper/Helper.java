/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.helper;

/**
 *
 * @author Tom
 */
public class Helper {

    public static double square(double x) {
        return x * x;
    }

    public static double normSquared(double x, double y) {
        return x * x + y * y;
    }

    public static double norm(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
    
}
