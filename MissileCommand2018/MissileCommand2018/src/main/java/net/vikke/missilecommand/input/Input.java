/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.input;

import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.vikke.missilecommand.game.Game;
import net.vikke.missilecommand.game.Missile;
import net.vikke.missilecommand.game.Turret;

/**
 *
 * @author Tom
 */
public class Input {
    
    HashMap<KeyCode, String> keysToTurret = new HashMap<>();
    
    double mouseX;
    double mouseY;
    
    public Input() {
        setUpKeyCodes();
    }
    
    /**
     * Hoitaa näppäimmistön painallukset pelissä
     * @param primaryStage 
     */
    public void handleKeyboardInput(Stage primaryStage) {
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (Game.nextStateTriggerEnabled) {
                    Game.gotoNextState = (e.getCode() == KeyCode.SPACE);
                } else {
                    if (keysToTurret.containsKey(e.getCode())) {
                        Game.fireTurret(keysToTurret.get(e.getCode()), mouseX, mouseY);
                    }
                }
            }
        });
    }
 
    /**
     * Hoitaa hiiren klikkauset pelissä
     * @param primaryStage 
     */
    public void handleMouseInput(Stage primaryStage) {
        primaryStage.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });    
    }

    /**
     * Aseta ohjausnapit
     */
    private void setUpKeyCodes() {
        keysToTurret.put(KeyCode.A, "LEFT");
        keysToTurret.put(KeyCode.LEFT, "LEFT");
        keysToTurret.put(KeyCode.S, "MID");
        keysToTurret.put(KeyCode.DOWN, "MID");
        keysToTurret.put(KeyCode.D, "RIGHT");
        keysToTurret.put(KeyCode.RIGHT, "RIGHT");
    }
    
}
