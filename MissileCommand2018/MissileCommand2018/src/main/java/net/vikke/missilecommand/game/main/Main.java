/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import net.vikke.missilecommand.database.Database;
import net.vikke.missilecommand.game.Game;
import net.vikke.missilecommand.input.Input;

public class Main extends Application {

    
    static Game game;
    static Database database;
    Connection connection;
    
    public static void main(String[] args) {
        game = new Game();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {

        setupDatabase("jdbc:sqlite:database/MC-highscores.db");
        
        // setup graphics & database
        game.setUpGraphics(primaryStage);
        game.setUpDatabase(connection);

        // setup inputhandlers
        Input input = new Input();
        input.handleKeyboardInput(primaryStage);
        input.handleMouseInput(primaryStage);

        // setup level 1
        game.setUpLevel(false);
        
        setupAnimationTimer();

    }
    
    /**
     * Käynnistä JavaFX:n animointimoottori
     */
    private void setupAnimationTimer() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    game.stateMachine();
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                game.spawnEnemyMissiles();
                game.handleEnemyMissiles();
                game.handleExplosions();
                game.handlePlayerMissiles();
                game.drawPlayfield();
                game.drawScore();
            }
        }.start();
    }
    
    /**
     * Luo yhteys tietokantaan
     * 
     * @param jdbcOsoite    JDBC-osoite tietokantaan
     */
    private void setupDatabase(String jdbcOsoite) {
        try {
            database = new Database(jdbcOsoite);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            this.connection = database.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
