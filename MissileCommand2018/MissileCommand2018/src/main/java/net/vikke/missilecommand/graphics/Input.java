/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.graphics;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.vikke.missilecommand.game.Game;

/**
 *
 * @author Tom
 */
public class Input {
    Group group;
    Pane pane;
    Rectangle rect;
    Text text;
    TextField input;
    Button button;
    String txt = "";
    Font fontInput = Font.font("monospace", FontWeight.BOLD, 30);
    
    public Input(double x, double y, Group group) {
        this.group = group;
        pane = new Pane();
        rect = new Rectangle();
        rect.setWidth(x * 2 - 100);
        rect.setHeight(200);
        rect.setFill(Color.BLACK);
        rect.setTranslateX(50);
        rect.setTranslateY(y - 50);
        text = new Text();
        text.setText("Your name:");
        text.setFont(fontInput);
        text.setFill(Color.WHITE);
        text.setTranslateX(x - 75);
        text.setTranslateY(y);
        text.setTextAlignment(TextAlignment.CENTER);
        input = new TextField();
        input.setAlignment(Pos.CENTER);
        input.setPrefWidth(150);
        input.setTranslateX(x - 75);
        input.setTranslateY(y + 25);
        input.setFont(fontInput);
        button = new Button("OK");
        button.setTranslateX(x + 100);
        button.setTranslateY(y + 25);
        button.setFont(fontInput);
        pane.getChildren().addAll(rect, text, input, button);
        group.setVisible(false);
        handleKeyPresses();
        handleMouseClick();
    }
    
    /**
     * Hoitaa syötettä HighScore-ruudun osalta
     */
    private void handleKeyPresses() {
        input.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                txt = input.getText();
                if (txt.length() > 3) {
                    txt = txt.substring(txt.length() - 3);
                }
                input.setText(txt.toUpperCase());
                input.positionCaret(3);
            }
        });
    }
    
    /**
     * Hoitaa HighScore syöttöruudun OK-nappia
     */
    private void handleMouseClick() {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                group.setVisible(false);
                Game.setHighScore(txt.toUpperCase());
            }
        });
    }
    
    /**
     * Tekstikentän Pane-elementti
     * @return Pane
     */
    public Pane getPane() {
        return this.pane;
    }
    
}
