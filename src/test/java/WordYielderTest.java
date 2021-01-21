/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ensar
 */
public class WordYielderTest {
    
    String text = "Merhaba, dir ekibi bunu yapmıştır! Ege-ege";
    
    public WordYielderTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of next method, of class WordYielder.
     */
    @Test
    public void testNext() {
        System.out.println("next");
        WordYielder instance = new WordYielder(text);
        Iterator iterator = instance.iterator();
        String [] expResult = {"Merhaba", "dir", "ekibi", "bunu", "yapmıştır", "Ege", "ege"};
        String [] result = new String [expResult.length];
        for (int i = 0; i < expResult.length; i++) {
            Word w = (Word)iterator.next();
            result[i] = w.getWord();
        }
        assertArrayEquals(expResult, result);
    }
    
}
