/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.vikke.missilecommand.game.main.Main;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tom
 */
public class DatabaseTest {
    
    Database database;
    Connection connection;
    
    @Before
    public void setUp() {
        try {
            database = new Database("jdbc:sqlite:database/MC-highscores.db");
        } catch (ClassNotFoundException ex) {}

        try {
            connection = database.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testaaTietokantaa() {
        String sql = "SELECT score, name FROM highscores ORDER BY score DESC LIMIT 1";
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            result.next();
            assertTrue(result.getInt("score") != 0);
        } catch (SQLException ex) {}
    }
}
