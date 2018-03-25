/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

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
public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(0);
    }
    
    @Test
    public void tarkistaLuodunKassapaatteenSaldo() {
        assertEquals(100000,kassa.kassassaRahaa());
    }

    @Test
    public void tarkistaLuodunKassapaatteenMyydytLounaat() {
        int edulliset = kassa.edullisiaLounaitaMyyty();
        int maukkaat = kassa.maukkaitaLounaitaMyyty();
        assertTrue(edulliset==0 && maukkaat==0);
    }
    
    @Test
    public void tarkistaKateismaksuEdullinenLounasTasaraha() {
        int vaihtoraha = kassa.syoEdullisesti(240);
        assertEquals(0, vaihtoraha);
    }

    @Test
    public void tarkistaKateismaksuEdullinenLounasYliraha() {
        int vaihtoraha = kassa.syoEdullisesti(1000);
        assertEquals(760, vaihtoraha);
    }

    @Test
    public void tarkistaKateismaksuEdullinenLounasAliraha() {
        int vaihtoraha = kassa.syoEdullisesti(100);
        assertEquals(100, vaihtoraha);
    }

    @Test
    public void tarkistaKateismaksuEdullinenLounasMyyntiOnnistui() {
        int myydyt = kassa.edullisiaLounaitaMyyty();
        int vaihtoraha = kassa.syoEdullisesti(1000);
        assertEquals(myydyt+1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void tarkistaKateismaksuEdullinenLounasMyyntiEpaonnistui() {
        int myydyt = kassa.edullisiaLounaitaMyyty();
        int vaihtoraha = kassa.syoEdullisesti(100);
        assertEquals(myydyt, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void tarkistaKateismaksuMaukkaastiLounasTasaraha() {
        int vaihtoraha = kassa.syoMaukkaasti(400);
        assertEquals(0, vaihtoraha);
    }

    @Test
    public void tarkistaKateismaksuMaukkaastiLounasYliraha() {
        int vaihtoraha = kassa.syoMaukkaasti(1000);
        assertEquals(600, vaihtoraha);
    }

    @Test
    public void tarkistaKateismaksuMaukkaastiLounasAliraha() {
        int vaihtoraha = kassa.syoMaukkaasti(100);
        assertEquals(100, vaihtoraha);
    }

    @Test
    public void tarkistaKateismaksuMaukkaastiLounasMyyntiOnnistui() {
        int myydyt = kassa.maukkaitaLounaitaMyyty();
        int vaihtoraha = kassa.syoMaukkaasti(1000);
        assertEquals(myydyt + 1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void tarkistaKateismaksuMaukkaastiLounasMyyntiEpaonnistui() {
        int myydyt = kassa.maukkaitaLounaitaMyyty();
        int vaihtoraha = kassa.syoMaukkaasti(100);
        assertEquals(myydyt, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void tarkistaKorttimaksuEdullinenLounasTasaraha() {
        kortti.lataaRahaa(240);
        assertTrue(kassa.syoEdullisesti(kortti));
    }

    @Test
    public void tarkistaKorttimaksuEdullinenLounasYliraha() {
        kortti.lataaRahaa(1000);
        assertTrue(kassa.syoEdullisesti(kortti));
    }

    @Test
    public void tarkistaKorttimaksuEdullinenLounasAliraha() {
        kortti.lataaRahaa(100);
        assertTrue(!kassa.syoEdullisesti(kortti));
    }

    @Test
    public void tarkistaKorttimaksuEdullinenLounasMyyntiOnnistui() {
        kortti.lataaRahaa(1000);
        int myydyt = kassa.edullisiaLounaitaMyyty();
        kassa.syoEdullisesti(kortti);
        assertEquals(myydyt + 1, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void tarkistaKorttimaksuEdullinenLounasMyyntiEpaonnistui() {
        kortti.lataaRahaa(100);
        int myydyt = kassa.edullisiaLounaitaMyyty();
        kassa.syoEdullisesti(kortti);
        assertEquals(myydyt, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void tarkistaKorttimaksuMaukkaastiLounasTasaraha() {
        kortti.lataaRahaa(400);
        assertTrue(kassa.syoMaukkaasti(kortti));
    }

    @Test
    public void tarkistaKorttimaksuMaukkaastiLounasYliraha() {
        kortti.lataaRahaa(1000);
        assertTrue(kassa.syoMaukkaasti(kortti));
    }

    @Test
    public void tarkistaKorttimaksuMaukkaastiLounasAliraha() {
        kortti.lataaRahaa(100);
        assertTrue(!kassa.syoMaukkaasti(kortti));
    }

    @Test
    public void tarkistaKorttimaksuMaukkaastiLounasMyyntiOnnistui() {
        kortti.lataaRahaa(1000);
        int myydyt = kassa.maukkaitaLounaitaMyyty();
        kassa.syoMaukkaasti(kortti);
        assertEquals(myydyt + 1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void tarkistaKorttimaksuMaukkaastiLounasMyyntiEpaonnistui() {
        kortti.lataaRahaa(100);
        int myydyt = kassa.maukkaitaLounaitaMyyty();
        kassa.syoMaukkaasti(kortti);
        assertEquals(myydyt, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void tarkistaKassanSaldoLadatessaKortilleOnnistuu() {
        int alku = kassa.kassassaRahaa();
        kassa.lataaRahaaKortille(kortti, 10000);
        int loppu = kassa.kassassaRahaa();
        assertEquals(10000,loppu - alku);
    }

    @Test
    public void tarkistaKassanSaldoLadatessaKortilleEpaonnistuu() {
        int alku = kassa.kassassaRahaa();
        kassa.lataaRahaaKortille(kortti, -10000);
        int loppu = kassa.kassassaRahaa();
        assertEquals(0, loppu - alku);
    }
}
