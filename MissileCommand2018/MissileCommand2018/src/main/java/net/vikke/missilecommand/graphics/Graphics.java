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
    Font highscoreFont = Font.font("monospace", FontWeight.BOLD, 30);
    Font prevHighsFont = Font.font("monospace", FontWeight.BOLD, 20);
    Font scoreFont = Font.font("sans-serif", FontWeight.BOLD, 30);
    Font infoFont = Font.font("sans-serif", FontWeight.BOLD, 15);
    Font ammoFont = Font.font("sans-serif", FontWeight.BOLD, 20);
    
    int cWidth;
    int cHeight;
    
    public static List<Rectangle> turretButtonRectangles = new ArrayList<>();
    public static List<Rectangle> difficultyButtonRectangles = new ArrayList<>();
    public static List<Button> turretButtons = new ArrayList<>();
    public static List<Button> difficultyButtons = new ArrayList<>();
    
    Group groupTurrets = new Group();
    Group groupDifficulties = new Group();
    Group groupInputs = new Group();

    /**
     * Overload metodi käytetään testimoodissa
     */
    public Graphics() {
        this.testMode = true;
    }
    
    /**
     * Alusta garphics API
     * @param primaryStage Stage
     * @param cWidth Canvas width
     * @param cHeight Canvas Height
     * @param title Window title
     */
    public Graphics(Stage primaryStage, int cWidth, int cHeight, String title) {
        this.cWidth = cWidth;
        this.cHeight = cHeight;
        this.root = new Group();
        this.canvas = new Canvas(cWidth, cHeight);
        this.gc = canvas.getGraphicsContext2D();
        this.primaryStage = primaryStage;
        root.getChildren().add(canvas);
        setupButtons();
        setupInputs();
        Scene theScene = new Scene(root, cWidth, cHeight, false, SceneAntialiasing.DISABLED);
        primaryStage.setTitle("Missile Command 2018");
        primaryStage.setScene(theScene);

        primaryStage.show();
    }
    
    /**
     * Pelissä käytetyt värit
     * @param colors värinimiä
     */
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
     * Piirrä vihollisohjus
     * 
     * @param x nykyien x-koordinaatti
     * @param y nykyinen y-koordinaatti
     * @param dx x muutos
     * @param dy y muutos
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
    
    /**
     * Poista (piirrä yli taustavärillä) vihollisohjus 
     * @param startX alku x-koordinaatti
     * @param startY alku y-koordinaatti
     * @param x loppu x-koordinaatti
     * @param y loppu y-koordinaatti
     */
    public void unDrawEnemyMissile(double startX, double startY, double x, double y) {

        if (testMode) {
            return;
        }
        
        gc.setStroke(backgroundColor);
        gc.setLineWidth(3);
        gc.strokeLine(startX, startY, x, y);
    }
    
    /**
     * Piirrä pelaajan ohjus
     * 
     * @param x alku x-koordinaatti
     * @param y alku y-koordinaatti
     * @param dx x-muutos
     * @param dy y-muutos
     */
    public void drawMissile(double x, double y, double dx, double dy) {

        if (testMode) {
            return;
        }

        gc.setStroke(missileColor);
        gc.setLineWidth(1);
        gc.strokeLine(x, y, x + dx, y + dy);
    }
    
    /**
     * Poista (piirrä yli taustavärillä) plaajan ohjus
     * @param startX alku x-koordinaatti
     * @param startY alku y-koordinaatti
     * @param endX loppu x-koordinaatti
     * @param endY loppu y-koordinaatti
     */
    public void unDrawMissile(double startX, double startY, double endX, double endY) {

        if (testMode) {
            return;
        }

        gc.setStroke(backgroundColor);
        gc.setLineWidth(3);
        gc.strokeLine(startX, startY, endX, endY);
    }

    /**
     * Piirrä kaupunki
     * 
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     */
    public void drawCity(double x, double y) {

        if (testMode) {
            return;
        }

        gc.setFill(cityColor);
        gc.fillPolygon(new double[]{x, x, x + 10, x + 20, x + 30, x + 40, x + 50, x + 60, x + 60},
                new double[]{y, y - 20, y - 10, y - 20, y - 10, y - 20, y - 10, y - 20, y}, 9);
    }

    /**
     * Piirrä tuhottu kaupunki
     * 
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     */
    public void drawDestroyedCity(double x, double y) {

        if (testMode) {
            return;
        }

        gc.setFill(destroyedCityColor);
        gc.fillPolygon(new double[]{x, x, x + 10, x + 20, x + 30, x + 40, x + 50, x + 60, x + 60},
                new double[]{y, y - 5, y - 10, y - 5, y - 10, y - 5, y - 10, y - 5, y}, 9);
    }

    /**
     * Poista (piirrä yli taustavärillä) kaupunki
     * 
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     * @param width  kaupungin leveys
     */
    public void unDrawCity(double x, double y, double width) {

        if (testMode) {
            return;
        }

        gc.setFill(backgroundColor);
        gc.fillRect(x, y - 20, width, 20);
    }
    
    /**
     * Täytä ruutu taustavärillä
     */
    public void drawEmptyBackground() {

        if (testMode) {
            return;
        }

        gc.setFill(backgroundColor);
        gc.fillPolygon(new double[]{0, cWidth,  cWidth,       0}, 
                       new double[]{0,      0, cHeight, cHeight}, 4);
    }
    
    /**
     * Piirrä Tykki
     * 
     * @param xm x-koordinaatti
     * @param y y-koordinaatti
     * @param ammo ammusten määrä
     */
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
    
    /**
     * Piirrä tämänhetkinen pistesaldo ruudulle
     * 
     * @param score pisteet
     */
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
    
    /**
     * piirrä maapinta
     */
    public void drawBaseGround() {

        if (testMode) {
            return;
        }

        gc.setFill(foregroundColor);
        gc.fillRect(0, cHeight - 25, cWidth, 25);
    }
    
    /**
     * Näytä valintanapit
     * 
     * @param show jos true, niin näytä, muuten piilota 
     */
    public void showButtons(boolean show) {
        groupTurrets.setVisible(show);
        groupDifficulties.setVisible(show);
    }
    
    /**
     * Piirrä räjähdys
     * 
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     * @param currRadius tämänhetkinen säde
     * @param draw jos true, niin piirretään, muuten poistetaan (piirretään taustavärillä)
     */
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

    /**
     * Alusta Tekstikenttä syötteelle
     */
    public void setupInputs() {
        Input input = new Input(cWidth / 2, 500, groupInputs);
        groupInputs.getChildren().add(input.getPane());
        root.getChildren().add(groupInputs);
    }
    
    /**
     * Alusta valintanapit
     */
    public void setupButtons() {
        
        setupTurretButtons();
        setupDifficultyButtons();
        
        groupDifficulties.setVisible(true);
        groupTurrets.setVisible(true);

        root.getChildren().add(groupDifficulties);
        root.getChildren().add(groupTurrets);
    }
    
    /**
     * Alusta Tykki-napit
     */
    public void setupTurretButtons() {
        Button btn1 = new Button("turret-1", "1", 100, 300, 100, 100);
        Button btn3 = new Button("turret-3", "3", 100, 425, 100, 100);
        groupTurrets.getChildren().addAll(btn3.getPane(), btn1.getPane());
        turretButtonRectangles.add(btn1.getRect());
        turretButtonRectangles.add(btn3.getRect());
        turretButtons.add(btn1);
        turretButtons.add(btn3);
        btn3.setSelected();
    }
    
    /**
     * Alusta Vaikeustaso-napit
     */
    public void setupDifficultyButtons() {
        Button btnEasy = new Button("difficulty-1", " EASY ", 600, 300, 100, 58);
        Button btnNormal = new Button("difficulty-2", "NORM", 600, 300 + 58 + 25, 100, 58);
        Button btnHard = new Button("difficulty-3", "HARD", 600, 300 + 58 + 58 + 50, 100, 58);
        groupDifficulties.getChildren().addAll(btnHard.getPane(), btnNormal.getPane(), btnEasy.getPane());
        difficultyButtonRectangles.add(btnEasy.getRect());
        difficultyButtonRectangles.add(btnNormal.getRect());
        difficultyButtonRectangles.add(btnHard.getRect());
        difficultyButtons.add(btnEasy);
        difficultyButtons.add(btnNormal);
        difficultyButtons.add(btnHard);
        btnNormal.setSelected();
    }
    
    /**
     * Piirrä aloitusruutu
     * @param highscores Array korkeimmista tuloksista
     */
    public void drawStartScreen(String[] highscores) {
        
        if (testMode) {
            return;
        }
        drawEmptyBackground();
        drawStartScreenLogo();
        drawHighScores(highscores);
        drawStartScreenHelp();
        drawStartScreenButtons();
    }
    
    /**
     * Piirrä korkeimmat tulokset
     * @param highscores Array korkeimmista tuloksista
     */
    private void drawHighScores(String[] highscores) {
        gc.setFont(highscoreFont);
        gc.setFill(Color.RED);
        gc.fillText("HIGHSCORE", cWidth / 2, 285);
        gc.setFill(Color.WHITE);
        int y = 0;
        int dy = 30;
        for (int i = 0; i < 5; i++) {
            if (i == 1) {
                drawPrevHighsHeader(y, dy);
                y++;
                dy = 25;
            }
            String paddedScore = "00000" + highscores[2 * i];
            String scoreText = paddedScore.substring(paddedScore.length() - 5) + "   " + highscores[2 * i + 1];
            gc.fillText(scoreText, cWidth / 2, 325 + y * dy);
            y++;
        }
    }
    
    /**
     * Piirrä edelliset korkeimmat tulokset
     * @param y y-koordinaatti mihin piirretään
     * @param dy kuinka paljon siirrytään alaspäin joka rivillä
     */
    private void drawPrevHighsHeader(int y, int dy) {
        gc.setFont(prevHighsFont);
        gc.setFill(Color.YELLOW);
        gc.fillText("Previous highs:", cWidth / 2, 325 + y * dy);
        gc.setFill(Color.LIGHTGRAY);
    }
    
    /**
     * Piirrä aloitusruudun napit
     */
    private void drawStartScreenButtons() {
        gc.setFont(chooseFont);
        gc.setFill(Color.GRAY);
        gc.fillText("# TURRETS", 150, 285);

        gc.setFont(chooseFont);
        gc.setFill(Color.GRAY);
        gc.fillText("DIFFICULTY", 650, 285);

        showButtons(true);
    }
    
    /**
     * Piirrä aloitusruudun infotektsit
     */
    private void drawStartScreenHelp() {
        gc.setFill(Color.GRAY);
        gc.setFont(infoFont);
        gc.fillText("AIM WITH MOUSE", cWidth / 2, 500);
        gc.fillText("FIRE TURRETS WITH:", cWidth / 2, 515);
        gc.fillText("A / S / D", cWidth / 2, 530);
        gc.fillText("or", cWidth / 2, 545);
        gc.fillText("LEFT / DOWN / RIGHT", cWidth / 2, 560);
        gc.setFont(scoreFont);
        gc.setFill(Color.WHITE);
        gc.fillText("PRESS SPACEBAR TO START", cWidth / 2, 600);
    }
    
    /**
     * Piirrä aloitusruudun logo
     */
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
    
    /**
     * Piirrä lopetusruutu
     * @param highScore jos true, niin oli korkein tulos ja se näytetään, muuten ei
     * @param score pisteet
     */
    public void drawEndScreen(boolean highScore, int score) {

        if (testMode) {
            return;
        }
        
        gc.setFill(Color.RED);
        gc.setFont(logoFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("GAME", cWidth / 2, 150);
        gc.fillText("OVER", cWidth / 2, 225);

        gc.setFill(Color.WHITE);
        gc.setFont(scoreFont);
        
        if (highScore) {
            gc.fillText("CONGRATULATIONS!", cWidth / 2, 300);
            gc.fillText("NEW HIGH SCORE!", cWidth / 2, 350);
            gc.fillText("Your score: " + score, cWidth / 2, 400);
            groupInputs.setVisible(true);
        }
        
        gc.fillText("PRESS SPACEBAR", cWidth / 2, 600);
    }
    
    /**
     * Piirrä bonukset
     * @param cities elossa olevien kaupunkien lukumäärä
     * @param missiles jäljellä olevien ohjuste lukumäärä
     * @param awardBonusCity jos true, niin pelaaja saa lisäkaupungin
     */
    public void drawBonus(int cities, int missiles, boolean awardBonusCity) {

        if (testMode) {
            return;
        }

        gc.setFill(Color.WHITE);
        gc.setFont(scoreFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("CITIES: " + cities + " x 500 = " + (cities * 500), cWidth / 2, 200);
        gc.fillText("MISSILES: " + missiles + " x 10 = " + (missiles * 10), cWidth / 2, 300);
        gc.fillText("TOTAL: " + (cities * 500 + missiles * 10), cWidth / 2, 400);
        
        if (awardBonusCity) {
            gc.setFill(Color.RED);
            gc.fillText("!!! BONUS CITY AWARDED !!!", cWidth / 2, 500);
        }
        
        gc.setFill(Color.WHITE);
        gc.fillText("PRESS SPACEBAR TO CONTINUE", cWidth / 2, 600);
    }
}
