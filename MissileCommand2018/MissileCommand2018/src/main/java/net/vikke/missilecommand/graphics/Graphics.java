/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.graphics;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Tom
 */
public class Graphics {
    
    boolean testMode = false;
    
    GraphicsContext gc;
    Group root;
    Canvas canvas;
    Stage primaryStage;
    Paint missileColor;
    Paint enemyMissileColor;
    Paint backgroundColor;
    Paint foregroundColor;
    Paint cityColor;
    Paint destroyedCityColor;
    Paint explosionColor;
    Font logoFont = Font.font("sans-serif", FontWeight.BOLD, 125);
    Font logo2018Font = Font.font("sans-serif", FontWeight.BOLD, 150);
    Font chooseFont = Font.font("sans-serif", FontWeight.BOLD, 20);
    public static Font buttonFont = Font.font("sans-serif", FontWeight.BOLD, 20);
    Font scoreFont = Font.font("sans-serif", FontWeight.BOLD, 30);
    Font ammoFont = Font.font("sans-serif", FontWeight.BOLD, 20);
    
    int cWidth;
    int cHeight;
    
    public static List<Rectangle> turretButtonRectangles = new ArrayList<>();
    public static List<Rectangle> difficultyButtonRectangles = new ArrayList<>();
    public static List<Button> turretButtons = new ArrayList<>();
    public static List<Button> difficultyButtons = new ArrayList<>();
    
    Group groupTurrets = new Group();
    Group groupDifficulties = new Group();

    
    public Graphics() {
        this.testMode = true;
    }
    
    public Graphics(Stage primaryStage, int cWidth, int cHeight, String title) {
        this.cWidth = cWidth;
        this.cHeight = cHeight;
        this.root = new Group();
        this.canvas = new Canvas(cWidth, cHeight);
        this.gc = canvas.getGraphicsContext2D();
        this.primaryStage = primaryStage;
        root.getChildren().add(canvas);
        setupButtons();
        Scene theScene = new Scene(root, cWidth, cHeight, false, SceneAntialiasing.DISABLED);
        primaryStage.setTitle("Missile Command 2018");
        primaryStage.setScene(theScene);

        primaryStage.show();
    }
    
    public void setColors(String[] colors) {
        this.backgroundColor = Color.web(colors[0]);
        this.foregroundColor = Color.web(colors[1]);
        this.missileColor = Color.web(colors[2]);
        this.enemyMissileColor = Color.web(colors[3]);
        this.explosionColor = Color.web(colors[4]);
        this.cityColor = Color.web(colors[5]);
        this.destroyedCityColor = Color.web(colors[6]);
    }
    
    /**
    *  drawEnemyMissile(
    *    double current x position
    *    double current y position
    *    double delta x
    *    double delta y)
    */
    public void drawEnemyMissile(double x, double y, double dx, double dy) {
        
        if (testMode) {
            return;
        }
        
        // remove previous missile head
        gc.setFill(backgroundColor);
        gc.fillOval(x - 2, y - 2, 4, 4);
        // draw previous and next missile pathline
        gc.setStroke(enemyMissileColor);
        gc.setLineWidth(1);
        gc.strokeLine(x - dx, y - dy, x, y);
        gc.strokeLine(x, y, x + dx, y + dy);
        // draw missile head
        gc.setFill(enemyMissileColor);
        gc.fillOval(x - 1, y - 1, 2, 2);
    }
    
    public void unDrawEnemyMissile(double startX, double startY, double x, double y) {

        if (testMode) {
            return;
        }
        
        gc.setStroke(backgroundColor);
        gc.setLineWidth(3);
        gc.strokeLine(startX, startY, x, y);
    }
    
    public void drawMissile(double x, double y, double dx, double dy) {

        if (testMode) {
            return;
        }

        gc.setStroke(missileColor);
        gc.setLineWidth(1);
        gc.strokeLine(x, y, x + dx, y + dy);
    }
    
    public void unDrawMissile(double startX, double startY, double endX, double endY) {

        if (testMode) {
            return;
        }

        gc.setStroke(backgroundColor);
        gc.setLineWidth(3);
        gc.strokeLine(startX, startY, endX, endY);
    }

    public void drawCity(double x, double y) {

        if (testMode) {
            return;
        }

        gc.setFill(cityColor);
        gc.fillPolygon(new double[]{x, x, x + 10, x + 20, x + 30, x + 40, x + 50, x + 60, x + 60},
                new double[]{y, y - 20, y - 10, y - 20, y - 10, y - 20, y - 10, y - 20, y}, 9);
    }

    public void drawDestroyedCity(double x, double y) {

        if (testMode) {
            return;
        }

        gc.setFill(destroyedCityColor);
        gc.fillPolygon(new double[]{x, x, x + 10, x + 20, x + 30, x + 40, x + 50, x + 60, x + 60},
                new double[]{y, y - 5, y - 10, y - 5, y - 10, y - 5, y - 10, y - 5, y}, 9);
    }

    public void unDrawCity(double x, double y, double width) {

        if (testMode) {
            return;
        }

        gc.setFill(backgroundColor);
        gc.fillRect(x, y - 20, width, 20);
    }
    
    public void drawEmptyBackground() {

        if (testMode) {
            return;
        }

        gc.setFill(backgroundColor);
        gc.fillPolygon(new double[]{0, cWidth,  cWidth,       0}, 
                       new double[]{0,      0, cHeight, cHeight}, 4);
    }
    
    public void drawTurret(double xm, double y, int ammo) {

        if (testMode) {
            return;
        }

        double width = 100;
        double x = xm - width / 2;
        String ammoText = ammo == 0 ? "---" : String.valueOf(ammo);
        gc.setFill(foregroundColor);
        gc.fillPolygon(new double[]{x, x + 25, x + 30, x + 50, x + 70, x + 75, x + 100},
                       new double[]{y, y - 40, y - 30, y - 40, y - 30, y - 40, y}, 7);
        gc.setFill(backgroundColor);
        gc.setFont(ammoFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(ammoText, xm, y - 10);
    }
    
    public void drawScore(int score) {
        
        if (testMode) {
            return;
        }

        // simulate old games with score turning to zero (in this case oly for show, the real score is correct)
        score %= 100000;
        String paddedScore = "00000" + score;
        String scoreText = " SCORE: " + paddedScore.substring(paddedScore.length() - 5) + " ";
        double dxScoreText = scoreText.length() * 15 / 2;
        gc.setFill(backgroundColor);
        gc.fillRect(400 - dxScoreText, 0, dxScoreText * 2, 30);
        gc.setFill(Color.WHITE);
        gc.setFont(scoreFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(scoreText, cWidth / 2, 30);
    }
    
    public void drawBaseGround() {

        if (testMode) {
            return;
        }

        gc.setFill(foregroundColor);
        gc.fillRect(0, cHeight - 25, cWidth, 25);
    }
    
    public void showButtons(boolean show) {
        groupTurrets.setVisible(show);
        groupDifficulties.setVisible(show);
    }
    
    public void drawExplosion(double x, double y, double currRadius, boolean draw) {

        if (testMode) {
            return;
        }

        // if attribute draw is true then draw the explosion, otherwise undraw it
        if (draw) {
            gc.setStroke(explosionColor);
            gc.setLineWidth(2);
        } else {
            gc.setStroke(backgroundColor);
            gc.setLineWidth(2.5);
        }
        gc.strokeOval(x - currRadius, y - currRadius, currRadius * 2, currRadius * 2);
    }

    public void setupButtons() {
        
        setupTurretButtons();
        setupDifficultyButtons();
        
        groupDifficulties.setVisible(true);
        groupTurrets.setVisible(true);

        root.getChildren().add(groupDifficulties);
        root.getChildren().add(groupTurrets);
    }
    
    public void setupTurretButtons() {
        Button btn1 = new Button("turret", "1", 100, 300, 100, 100);
        Button btn3 = new Button("turret", "3", 100, 425, 100, 100);
        groupTurrets.getChildren().addAll(btn3.getPane(), btn1.getPane());
        turretButtonRectangles.add(btn1.getRect());
        turretButtonRectangles.add(btn3.getRect());
        turretButtons.add(btn1);
        turretButtons.add(btn3);
        btn3.setSelected();
    }
    
    public void setupDifficultyButtons() {
        Button btnEasy = new Button("difficulty", " EASY ", 600, 300, 100, 58);
        Button btnNormal = new Button("difficulty", "NORM", 600, 300 + 58 + 25, 100, 58);
        Button btnHard = new Button("difficulty", "HARD", 600, 300 + 58 + 58 + 50, 100, 58);
        groupDifficulties.getChildren().addAll(btnHard.getPane(), btnNormal.getPane(), btnEasy.getPane());
        difficultyButtonRectangles.add(btnEasy.getRect());
        difficultyButtonRectangles.add(btnNormal.getRect());
        difficultyButtonRectangles.add(btnHard.getRect());
        difficultyButtons.add(btnEasy);
        difficultyButtons.add(btnNormal);
        difficultyButtons.add(btnHard);
        btnNormal.setSelected();
    }
    
    public void drawStartScreen() {
        
        if (testMode) {
            return;
        }

        drawStartScreenLogo();
        drawStartScreenHelp();
        drawStartScreenButtons();
        
    }
    
    private void drawStartScreenButtons() {
        gc.setFont(chooseFont);
        gc.setFill(Color.GRAY);
        gc.fillText("# TURRETS", 150, 285);

        gc.setFont(chooseFont);
        gc.setFill(Color.GRAY);
        gc.fillText("DIFFICULTY", 650, 285);

        showButtons(true);
    }
    
    private void drawStartScreenHelp() {
        gc.setFill(Color.WHITE);
        gc.setFont(scoreFont);
        gc.fillText("AIM WITH MOUSE", cWidth / 2, 300);
        gc.fillText("FIRE TURRETS WITH:", cWidth / 2, 350);
        gc.fillText("A / S / D", cWidth / 2, 400);
        gc.fillText("or", cWidth / 2, 450);
        gc.fillText("LEFT / DOWN / RIGHT", cWidth / 2, 500);
        gc.fillText("PRESS SPACEBAR TO START", cWidth / 2, 600);
    }
    
    private void drawStartScreenLogo() {
        gc.setFill(Color.WHITE);
        gc.setFont(logoFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("MISSILE", cWidth / 2, 150);
        gc.fillText("COMMAND", cWidth / 2, 225);
        gc.setFont(logo2018Font);
        gc.setFill(Color.RED);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(10);
        gc.strokeText("2018", cWidth / 2, 200);
        gc.setFill(Color.BLACK);
        gc.fillText("2018", cWidth / 2, 200);
    }
    
    public void drawEndScreen() {

        if (testMode) {
            return;
        }
        
    }
    
    public void drawBonus(int cities, int missiles) {

        if (testMode) {
            return;
        }

        gc.setFill(Color.WHITE);
        gc.setFont(scoreFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("CITIES: " + cities + " x 500 = " + (cities * 500), cWidth / 2, 200);
        gc.fillText("MISSILES: " + missiles + " x 10 = " + (missiles * 10), cWidth / 2, 300);
        gc.fillText("TOTAL: " + (cities * 500 + missiles * 10), cWidth / 2, 400);
        gc.fillText("PRESS SPACEBAR TO CONTINUE", cWidth / 2, 600);
    }
 
}
