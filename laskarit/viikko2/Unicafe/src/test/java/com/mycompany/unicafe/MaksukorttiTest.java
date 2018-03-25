package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        String str = kortti.toString();
        assertEquals("saldo: 0.10", str);
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(99);
        String str = kortti.toString();
        assertEquals("saldo: 1.09", str);
    }
    
    @Test
    public void rahaOttaminenSaldoVaheneeJosTarpeeksi() {
        kortti.otaRahaa(9);
        String str = kortti.toString();
        assertEquals("saldo: 0.01", str);
    }
    @Test
    public void rahaOttaminenSaldoEiMuutuJosEiTarpeeksi() {
        kortti.otaRahaa(11);
        String str = kortti.toString();
        assertEquals("saldo: 0.10", str);
    }

    @Test
    public void rahaOttaminenPalauttaaTrueJosOnnistuu() {
        boolean b = kortti.otaRahaa(9);
        assertTrue(b);
    }

    @Test
    public void rahaOttaminenPalauttaaFalseJosEiOnnistuu() {
        boolean b = kortti.otaRahaa(11);
        assertTrue(!b);
    }
    
    @Test
    public void tarkistaSaldo() {
        int saldo = kortti.saldo();
        assertEquals(10, saldo);
    }
}
