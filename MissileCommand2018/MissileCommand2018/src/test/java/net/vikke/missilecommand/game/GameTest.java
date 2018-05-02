/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

import javafx.stage.Stage;
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
public class GameTest {
    
    Game game;
    
    @Before
    public void setUp() {
        Game.setUpGraphics(null);
        game = new Game();
        game.setUpLevel(true);
    }
    
    @Test
    public void testaaSpawnEnemyMissiles() {
        game.spawnEnemyMissiles();
        assertTrue(game.enemyMissiles.size() == 1);
    }
    
    @Test
    public void testaaHandleEnemyMissiles() {
        game.handleEnemyMissiles();
        assertTrue(game.enemiesToRemove.size() == 0);
    }
    
    @Test
    public void testaaHandleExplosions() {
        game.handleExplosions();
        assertTrue(game.explosionsToRemove.size() == 0);
    }
    
    @Test
    public void testaaHandlePlayerMissiles() {
        game.handlePlayerMissiles();
        assertTrue(game.toRemove.size() == 0);
    }

    @Test
    public void testaaDrawPlayfield() {
        game.drawPlayfield();
        assertTrue(true);
    }

    @Test
    public void testaaDrawScore() {
        game.drawScore();
        assertTrue(true);
    }

    @Test
    public void testaaCheckLevelUp() {
        game.levelUp = true;
        game.checkLevelUp();
        assertTrue(game.level == 2);
    }
}
