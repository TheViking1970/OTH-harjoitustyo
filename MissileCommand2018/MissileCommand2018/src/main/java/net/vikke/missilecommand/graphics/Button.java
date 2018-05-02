/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.graphics;

import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Tom
 */
public class Button {
    Pane pane;
    Rectangle rect;
    Text text;
    boolean selected;
    Paint selectedFillColor = Color.DODGERBLUE;
    Paint selectedStrokeColor = Color.CYAN;
    Paint unSelectedFillColor = Color.DARKBLUE;
    Paint unSelectedStrokeColor = Color.BLUE;

    public Button(String coll, String txt, int x, int y, int w, int h) {
        this.selected = false;
        pane = new Pane();
        pane.setId(coll);
        pane.setPrefSize(w, h);
        rect = new Rectangle(x, y, w, h);
        rect.setStroke(unSelectedStrokeColor);
        rect.setFill(unSelectedFillColor);
        rect.setStrokeWidth(3);
        rect.setArcWidth(25);
        rect.setArcHeight(25);
        text = new Text();
        text.setText(txt);
        text.setFont(Graphics.buttonFont);
        text.setTranslateX(x + (txt.length() > 1 ? 20 : 45));
        text.setTranslateY(y + h / 2 + 10);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        pane.getChildren().addAll(rect, text);
        handleClicks();
    }
        
    private void handleClicks() {
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Pane p = (Pane) e.getSource();
                List<Rectangle> rectangles = (p.getId().equals("turret") ? Graphics.turretButtonRectangles : Graphics.difficultyButtonRectangles);
                List<Button> buttons = (p.getId().equals("turret") ? Graphics.turretButtons : Graphics.difficultyButtons);
                for (Rectangle r : rectangles) {
                    r.setStroke(unSelectedStrokeColor);
                    r.setFill(unSelectedFillColor);
                }
                for (Button b : buttons) {
                    b.selected = false;
                }
                setSelected();
            }
        });
    }
    
    public Pane getPane() {
        return pane;
    }
    
    public Rectangle getRect() {
        return rect;
    }
    
    public void setSelected() {
        rect.setStroke(selectedStrokeColor);
        rect.setFill(selectedFillColor);
        selected = true;
    }

}
