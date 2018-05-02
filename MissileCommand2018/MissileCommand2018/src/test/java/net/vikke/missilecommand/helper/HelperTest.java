/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vikke.missilecommand.helper;

import net.vikke.missilecommand.helper.Helper;
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
public class HelperTest {
    
    Helper helper;
    
    @Before
    public void setUp() {
        helper = new Helper();
    }
    
    @Test
    public void testaaSquare() {
        assertTrue(Helper.square(10) == 10*10);
    }

    @Test
    public void testaaNormSquared() {
        assertTrue(Helper.normSquared(3, 4) == (3 * 3 + 4 * 4));
    }

    @Test
    public void testaaNorm() {
        assertTrue(Helper.norm(3, 4) == Math.sqrt(3 * 3 + 4 * 4));
    }
}
