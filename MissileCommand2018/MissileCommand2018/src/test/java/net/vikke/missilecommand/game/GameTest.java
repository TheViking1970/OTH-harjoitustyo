/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import javafx.stage.Stage;
import net.vikke.missilecommand.database.Database;
import static net.vikke.missilecommand.game.Game.nextStateTriggerEnabled;
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
    Database database;
    Connection connection;

    @Before
    public void setUp() {
        Game.setUpGraphics(null);
        game = new Game();
        game.setUpLevel(true);
        try {
            database = new Database("jdbc:sqlite:database/MC-highscores.db");
        } catch (ClassNotFoundException ex) {
        }

        try {
            connection = database.getConnection();
            game.setUpDatabase(connection);
        } catch (SQLException ex) {
        }
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
    
    @Test
    public void testaaResetoiMuuttujat() {
        game.level = 10;
        game.resetVariables();
        assertTrue(game.level == 1);
    }
    
    @Test
    public void testaacheckCollisionWithExplosion() {
        EnemyMissile missile = new EnemyMissile(0, 0, 1, 1, 1);
        Explosion explosion = new Explosion(0, 0, 100);
        for (int i = 0; i < 5; i++) {
            explosion.animate();
        }
        game.checkCollisionWithExplosion(missile);
        //assertTrue(game.score > 0);
    }
    
    @Test
    public void testaaKolmeTurrettia() {
        boolean b1 = game.turretLeft != null;
        game.numTurrets = 1;
        game.makeTurrets();
        boolean b2 = game.turretLeft == null;
        System.out.println(b1+" :: "+b2);
        assertTrue(b1 && b2);
    }
    
    @Test
    public void testaaNaytaScreeneja() throws SQLException {
        game.showStartScreen();
        boolean b1 = game.nextStateTriggerEnabled == true;
        boolean b2 = game.showBonusScreen().equals("play");
        game.nextStateTriggerEnabled = false;
        game.showEndScreen();
        boolean b3 = game.nextStateTriggerEnabled == true;
        assertTrue(b1 && b2 && b3);
    }
    
    @Test
    public void testaaAmmuTurretteja() {
        game.fireTurret("LEFT", 100, 100);
        game.fireTurret("MID", 100, 100);
        game.fireTurret("RIGHT", 100, 100);
        assertTrue(game.missiles.size() == 3);
    }
}
