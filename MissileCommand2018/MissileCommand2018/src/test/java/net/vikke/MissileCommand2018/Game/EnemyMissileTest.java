package Game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import main.java.net.vikke.Game.EnemyMissile;
import main.java.net.vikke.Game.Explosion;
import main.java.net.vikke.Game.Game;
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
public class EnemyMissileTest {
    
    EnemyMissile missile;
    
   
    @Before
    public void setUp() {
        missile = new EnemyMissile(0,0,100,100,2);
    }
    
    @Test
    public void testaaOnkoLuotuOhjus() {
        assertTrue(missile != null);
    }
    
    @Test
    public void testaaLiikuttaaOhjusta() {
        // (0,0) - (100,100) & speed = 2
        // dx = dy = sqrt(2)
        missile.move();
        // get dx and dy SQUARED
        double dx2 = missile.getX()*missile.getX();
        double dy2 = missile.getY()*missile.getY();
        // get missile speed SQUARED
        double speed2 = missile.getSpeed()*missile.getSpeed();
        // get current active state
        boolean act = missile.getActive();
        // test that missile has moved within threshold and state is true
        assertTrue( (dx2 + dy2) < speed2 && act);
    }
    
    @Test
    public void testaaLiikuttaaMelkeinLoppupisteeseen() {
        // with dx = dy = sqrt(2) => 50*sqrt(2) ~ 70.7
        // 70 moves and missile is within threshold
        for(int i = 0; i<69; i++) {
            missile.move();
        }
        // get (100-x) and (100-y) SQUARED
        double dx2 = (100-missile.getX())*(100-missile.getX());
        double dy2 = (100-missile.getY())*(100-missile.getY());
        // get current active state
        boolean act = missile.getActive();
        // get missile speed SQUARED
        double speed2 = missile.getSpeed()*missile.getSpeed();
        // test that missile has NOT moved within threshold and state is true
        assertTrue( !((dx2 + dy2) < speed2) && act);
    }

    @Test
    public void testaaLiikuttaaLoppupisteeseenJaOhi() {
        // with dx = dy = sqrt(2) => 50*sqrt(2) ~ 70.7
        // 70 moves and missile is within threshold
        for (int i = 0; i < 70; i++) {
            missile.move();
        }
        // missile should have moved within threshold or past destination, state should be false
        assertTrue(!missile.getActive());
    }
    
    @Test
    public void testaaUnDraw(){
        missile.unDraw();
        assertTrue(!missile.getActive());
    }
    
    @Test
    public void testaaTormays() {
        // Explosion class has to be redone to not require JavaFx for testing.
        assertTrue(true);
    }
}
