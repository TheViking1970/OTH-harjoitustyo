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
public class CityTest {
    
    City city;
    
    @Before
    public void setUp() {
        city = new City(400, 400, 100);
    }
    
    @Test
    public void testaaOsumaa() {
        city.hit();
        assertTrue(!city.isAlive);
    }
    
    @Test
    public void testaaEtaisyys() {
        EnemyMissile missile = new EnemyMissile(0, 0, 400, 400, 1);
        double dist2 = 400*400 + 400*400;
        assertTrue(Math.abs(city.dist2(missile) - dist2) < 0.05);
    }
}
