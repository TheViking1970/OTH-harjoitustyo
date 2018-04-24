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
public class ExplosonTest {
    
    Explosion explosion;
    
    @Before
    public void setUp() {
        explosion = new Explosion(100, 100, 20);
    }
    
    @Test
    public void testaa() {
        explosion.animate();
        boolean largerThan = explosion.angle >= (Math.PI - .025 - .01);
        boolean smallerThan = explosion.angle <= (Math.PI - .025 + .01);
        assertTrue(largerThan && smallerThan);
    }
}
