/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.vikke.missilecommand.graphics.Graphics;
import net.vikke.missilecommand.input.Input;
import net.vikke.missilecommand.helper.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static net.vikke.missilecommand.game.main.Main.main;

//public class Game extends Application {
public class Game {
 
    static int score = 0;
    int highscore = 0;
    int bonus = 0;
    int level = 1;
    boolean newHighScore = false;
    int bonusCities = 0;
    int extraCityBonusCounter = 0;
    int levelMissiles = 0;
    int spawnedMissiles = 0;
    boolean levelUp = false;
    
    int enemyMissilesMin = 3;
    
    public static int numTurrets = 3; // default 3 turrets
    public static int numDifficulty = 2; // default 2=nomral difficulty
    
    public static double mouseX = 0;
    public static double mouseY = 0;
    
    String currentState = "start";
    String nextState = "";
    public static boolean gotoNextState = false;
    public static boolean nextStateTriggerEnabled = true;
    boolean showBonus = false;
    
    static int cWidth = 800;
    static int cHeight = 800;

    public static List<Missile> missiles;
    List<EnemyMissile> enemyMissiles;
    List<Explosion> explosions;

    List<EnemyMissile> enemiesToRemove;
    List<Explosion> explosionsToAdd;
    List<Explosion> explosionsToRemove;
    List<Missile> toRemove;

    public static Turret turretLeft;
    public static Turret turretMid;
    public static Turret turretRight;
    List<Turret> turrets;

    City[] cities = new City[6];

    HashMap<KeyCode, Turret> keysToTurret = new HashMap<>();

    static Graphics gfx;
    static Connection connection;
    
    /**
     * Tallennetaa tietokannan yhteys
     * @param conn Connection
     */
    public static void setUpDatabase(Connection conn) {
        connection = conn;
    }
    
    /**
     * Alustetaan grafiikka API
     * @param primaryStage 
     */
    public static void setUpGraphics(Stage primaryStage) {
        if (primaryStage == null) {
            gfx = new Graphics();
        } else {
            gfx = new Graphics(primaryStage, cWidth, cHeight, "Missile Command 2018");
        }
    }
    
    /**
     * Piirrä Tykit ja alusta
     */
    private void drawTurretsAndGround() {
        for (Turret turret : turrets) {
            if (turret != null) {
                gfx.drawTurret(turret.x, turret.y + 42, turret.ammo);
            }
        }
        gfx.drawBaseGround();
    }
    
    /**
     * Luo vihollisohjuksia
     */
    public void spawnEnemyMissiles() {
        if (spawnedMissiles >= levelMissiles) {
            if (!showBonus && enemyMissiles.size() == 0) {
                // end level
                levelUp = true;
            }
            // otherwise just wait for all missiles to die
            return;
        }
        if (enemyMissiles.size() < enemyMissilesMin * numDifficulty + level) {
            if (enemyMissiles.size() == 0 || Math.random() > .975) {
                double startX = Math.random() * cWidth;
                double endX = Math.random() * (cWidth - 100) + 50;
                double speed = 1 + (level - 1.) / (15. / numDifficulty);
                enemyMissiles.add(new EnemyMissile(startX, 0, endX, 775, speed));
                spawnedMissiles++;
            }
        }
    }
    
    /**
     * Hoida vihollisohjuksia
     */
    public void handleEnemyMissiles() {
        enemiesToRemove = new ArrayList<>();
        explosionsToAdd = new ArrayList<>();
        
        // iterate trough all enemymissiles
        for (EnemyMissile missile : enemyMissiles) {
            checkCollisionWithExplosion(missile);
            // move missile, if method returns false, then add missile to be removed
            if (!missile.move()) {
                enemiesToRemove.add(missile);
            } else {
                checkCollisionWithCity(missile);
                checkCollisionWithTurret(missile);
            }
        }
        // remove all enemymissiles marked for removal
        for (EnemyMissile missile : enemiesToRemove) {
            enemyMissiles.remove(missile);
        }
    }

    /**
     * Tarkista onko Tykkiin osuttu
     * @param missile Vihollisohjus
     */
    private void checkCollisionWithTurret(EnemyMissile missile) {
        // iterate trough all cities to detect collision
        for (Turret turret : turrets) {
            // if distance from misile to city is within threshold, then collision with city
            if (turret != null && turret.isAlive && Helper.normSquared(turret.x - missile.x, turret.y - missile.y) < 1600) {
                turret.hit();
                missile.unDraw();
                explosions.add(new Explosion(turret.x, turret.y, 40));
            }
        }
    }
    
    /**
     * Tarkista onko Kaupunkiin osuttu
     * @param missile Vihollisohjus
     */
    private void checkCollisionWithCity(EnemyMissile missile) {
        // iterate trough all cities to detect collision
        for (City city : cities) {
            // if distance from misile to city is within threshold, then collision with city
            if (city.isAlive && Helper.normSquared(city.x - missile.x, city.y - missile.y) < 625) {
                city.hit();
                missile.unDraw();
                explosions.add(new Explosion(city.x, city.y, 40));
            }
        }
    }
    
    /**
     * Tarkista onko osuttu Räjähdykseen
     * @param missile Vihollisohjus
     */
    public void checkCollisionWithExplosion(EnemyMissile missile) {
        // iterate trough all explosions
        for (Explosion explosion : explosions) {
            if (missile.checkColission(explosion)) {
                score += 100;
                explosionsToAdd.add(new Explosion(missile.x, missile.y, 50));
            }
        }
    }
    
    /**
     * Hoida räjähdykset
     */
    public void handleExplosions() {
        // empty the arraylist
        explosionsToRemove = new ArrayList<>();
        // iterate trough all eplosions, if animation ended then mark for removal
        for (Explosion explosion : explosions) {
            if (!explosion.animate()) {
                explosionsToRemove.add(explosion);
            }
        }
        // remove all explosions marked for removal
        for (Explosion explosion : explosionsToRemove) {
            explosions.remove(explosion);
        }
        // add all exliosions generated by explosions of enemymissiles
        for (Explosion explosion : explosionsToAdd) {
            explosions.add(explosion);
        }
    }
    
    /**
     * Ammu Tykillä
     * @param tStr LEFT, MID tai RIGHT tykki
     * @param x maalin x-koordinaatti
     * @param y maalin y-koordinaatti
     */
    public static void fireTurret(String tStr, double x, double y) {
        Turret turret = null;
        if (tStr.equals("LEFT")) {
            turret = turretLeft;
        } else if (tStr.equals(("MID"))) {
            turret = turretMid;
        } else if (tStr.equals("RIGHT")) {
            turret = turretRight;
        }
        if (turret != null && turret.ammo > 0) {
            missiles.add(turret.fire(x, y));
        }
    }

    /**
     * Hoida Pelaajan ohjuksia
     */
    public void handlePlayerMissiles() {
        toRemove = new ArrayList<>();
        for (Missile missile : missiles) {
            boolean isActive = missile.move();
            if (!isActive) {
                toRemove.add(missile);
                explosions.add(new Explosion(missile.endX, missile.endY, 50));
            }
        }
        for (Missile missile : toRemove) {
            missiles.remove(missile);
        }
    }
    
    /**
     * Piirrä pisteet
     */
    public void drawScore() {
        gfx.drawScore(score);
    }
    
    /**
     * Piirrä pelikenttä
     */
    public void drawPlayfield() {
        for (City city : cities) {
            city.draw();
        }
        drawTurretsAndGround();
        
    }
    
    /**
     * Piirrä tausta
     */
    private void drawBackground() {
        gfx.drawEmptyBackground();
    }
    
    /**
     * Luo Tykkejä
     */
    public void makeTurrets() {
        int ammo = 10 * (4 - numDifficulty);
        if (numTurrets == 3) {
            turretLeft = new Turret(ammo, 50, 735, 20);
            turretMid = new Turret(ammo, 400, 735, 20);
            turretRight = new Turret(ammo, 750, 735, 20);
        } else {
            turretLeft = null;
            turretMid = new Turret(ammo * 3, 400, 735, 20);
            turretRight = null;
        }
        this.turrets = Arrays.asList(turretLeft, turretMid, turretRight);
    }
    
    /**
     * Luo Kaupunkeja
     */
    public void makeCities() {
        this.cities[0] = new City(150, 775, 60);
        this.cities[1] = new City(225, 775, 60);
        this.cities[2] = new City(300, 775, 60);
        this.cities[3] = new City(500, 775, 60);
        this.cities[4] = new City(575, 775, 60);
        this.cities[5] = new City(650, 775, 60);
    }
    
    /**
     * Aseta pelilaudan värit
     * @param level tämänhetinen taso
     * @return 
     */
    private String[] getLevelColors(int level) {
        // does not yet implement colorchanges according to level
        return new String[]{"BLACK", "YELLOW", "RED", "PINK", "CORAL", "BLUE", "GRAY"};
    }
    
    /**
     * Tarkista onko pelaaja selvittänyt tason
     */
    public void checkLevelUp() {
        if (levelUp) {
            // if flag set, then do levelup
            level++;
            nextState = "bonus";
            gotoNextState = true;
            levelUp = false;
            showBonus = true;
        }
    }

    /**
     * Piirrä aloitusruutu
     * @throws SQLException 
     */
    public void showStartScreen() throws SQLException {
        gfx.drawStartScreen(getHighScores());
        nextStateTriggerEnabled = true;
    }

    /**
     * Piirrä bonusruutu
     * @return 
     */
    public String showBonusScreen() {
        int citiesAlive = getCitiesAlive();
        boolean awardBonusCity = checkForBonusCity();
        if (citiesAlive + bonusCities <= 0) {
            gotoNextState = true;
            if (score > highscore) {
                newHighScore = true;
            }
            // bail out to next state!
            return "end";
        }
        restoreDeadCities();
        int ammoLeft = turretLeft == null ? 0 : turretLeft.ammo;
        ammoLeft += turretMid == null ? 0 : turretMid.ammo;
        ammoLeft += turretRight == null ? 0 : turretRight.ammo;
        bonus = 500 * citiesAlive + 10 * ammoLeft;
        gfx.drawBonus(citiesAlive, ammoLeft, awardBonusCity);
        nextStateTriggerEnabled = true;
        return "play";
    }
    
    /**
     * Kuinka monta kaupunkia vielä elossa
     * @return elossa olevien kaupunkien lukumäärä
     */
    public int getCitiesAlive() {
        int citiesAlive = 0;
        for (City city : cities) {
            if (city.isAlive) {
                citiesAlive++;
            }
        }
        return citiesAlive;
    }
    
    /**
     * Mitkä Kaupungit ovat tuhottuja?
     * @return Lista tuhotuista kaupungeista
     */
    public List<City> getDestroyedCities() {
        List<City> destroyedCities = new ArrayList<>();
        for (City city : cities) {
            if (!city.isAlive) {
                destroyedCities.add(city);
            }
        }
        return destroyedCities;
    }
    
    /**
     * Tarkista onko pelaaja saavuttanut bonuskaupungin
     * @return 
     */
    private boolean checkForBonusCity() {
        boolean awardBonusCity = false;
        if (score + bonus > 10000 * (extraCityBonusCounter + 1)) {
            extraCityBonusCounter++;
            bonusCities++;
            awardBonusCity = true;
        }
        return awardBonusCity;
    }
         
    /**
     * Palauta kuolleita kaupunkeja
     */
    private void restoreDeadCities() {
        List<City> destroyedCities = getDestroyedCities();
        if (destroyedCities.size() > 0) {
            Collections.shuffle(destroyedCities);
            for (int i = 0; i < Math.min(destroyedCities.size(), bonusCities); i++) {
                destroyedCities.get(i).isAlive = true;
                bonusCities--;
            }
        }
    }

    /**
     * Hae korkeimmat tulokset tietokannasta
     * @return Array korkeimmista tuloksista
     */
    private String[] getHighScores() {
        int n = 5;
        String[] ret = new String[2 * n];
        
        String sql = "SELECT score, name FROM highscores ORDER BY score DESC LIMIT ?";
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, n);
            ResultSet result = statement.executeQuery();
            int i = 0;
            while (result.next()) {
                ret[i] = String.valueOf(result.getInt("score"));
                ret[i + 1] = result.getString("name");
                i += 2;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    /**
     * Lisää tietokantaan uusi korkein tulos
     * @param name Pelaajan nimi
     */
    public static void setHighScore(String name) {
        String sql = "INSERT INTO highscores (score, name) VALUES (?, ?)";
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, Game.score);
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Näytä loppuruutu
     */
    public void showEndScreen() {
        gfx.drawEndScreen(newHighScore, score);
        nextStateTriggerEnabled = true;
    }
    
    /**
     * Alusta uusi taso
     * @param flag 
     */
    public void setUpLevel(boolean flag) {
        setUpArrays();
        if (level < 2) {
            makeCities();
        }
        makeTurrets();
        drawBackground();
        drawPlayfield();
        gfx.setColors(getLevelColors(level));
        // add bonus from last level
        score += bonus;
        bonus = 0;
        if (flag) {
            nextStateTriggerEnabled = false;
            showBonus = false;
            levelUp = false;
            spawnedMissiles = 0;
            levelMissiles = 10 + (int)((level - 1) * 2. / numDifficulty);
        }
        gfx.showButtons(false);
    }
    
    /**
     * Alusta Arrayt
     */
    private void setUpArrays() {
        missiles = new ArrayList<>();
        enemyMissiles = new ArrayList<>();
        explosions = new ArrayList<>();
        explosionsToAdd = new ArrayList<>();
        explosionsToRemove = new ArrayList<>();
        toRemove = new ArrayList<>();
    }
    
    /**
     * Palauta muuttujille alkuarvot
     */
    public void resetVariables() {
        level = 1;
        score = 0;
        bonus = 0;
        bonusCities = 0;
        extraCityBonusCounter = 0;
        numTurrets = 3;
        numDifficulty = 2;
    }
    
    /**
     * Tilakone pelin eri näkymille
     * @throws SQLException 
     */
    public void stateMachine() throws SQLException {
        if (gotoNextState) {
            gotoNextState = false;
            if (currentState.toLowerCase().equals("start")) {
                showStartScreen();
                resetVariables();
                nextState = "play";
            } else if (currentState.toLowerCase().equals("play")) {
                setUpLevel(true);
                nextState = "bonus";
            } else if (currentState.toLowerCase().equals("bonus")) {
                nextState = showBonusScreen();
            } else if (currentState.toLowerCase().equals("end")) {
                showEndScreen();
                nextState = "start";
            }
            currentState = nextState;
        }
        checkLevelUp();
    }
    
}