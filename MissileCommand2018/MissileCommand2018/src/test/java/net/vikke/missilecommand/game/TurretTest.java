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
public class TurretTest {
    
    Turret turret;
    
    @Before
    public void setUp() {
        turret = new Turret(10, 0,0, 2);
    }
    
    @Test
    public void testaaAmpua() {
        Missile missile = turret.fire(100,100);
        assertTrue(missile != null);
    }
    
    @Test
    public void testaaAmpuaYliAmmonMaaraa() {
        Missile missile = null;
        for(int i=0; i<11; i++) {
            missile = turret.fire(100,100);
        }
        assertTrue(missile==null);
    }
    
    @Test
    public void testaaOsumaa() {
        turret.hit();
        assertTrue(turret.ammo==0 && turret.isAlive==false);
    }
}
