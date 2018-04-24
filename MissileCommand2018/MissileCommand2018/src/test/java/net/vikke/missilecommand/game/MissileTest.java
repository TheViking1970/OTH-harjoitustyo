/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tom
 */
public class MissileTest {
    
    Missile missile;
    
    @Before
    public void setUp() {
        missile = new Missile(0, 0, 100, 100, 2);
    }
    
    @Test
    public void testaaLiikuttaaOhjustaXkoordinatti() {
        missile.move();
        boolean largerThan = missile.x >= (Math.sqrt(2) - .01);
        boolean smallerThan = missile.x <= (Math.sqrt(2) + .01);
        assertTrue(largerThan && smallerThan);
    }

    @Test
    public void testaaLiikuttaaOhjustaYkoordinatti() {
        missile.move();
        boolean largerThan = missile.y >= (Math.sqrt(2) - .01);
        boolean smallerThan = missile.y <= (Math.sqrt(2) + .01);
        assertTrue(largerThan && smallerThan);
    }
}
