/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class WordListTest {
    
    public WordListTest() {
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
     * Test of compareTo method, of class WordList.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        WordList obj = new WordList("Ali");
        WordList instance = new WordList("Ahmet");
        int expResult = 2;
        int result = instance.compareTo(obj);
        assertEquals(expResult, result);

    }

    /**
     * Test of getWordsLength method, of class WordList.
     */
    @Test
    public void testGetWordsLength() {
        System.out.println("getWordsLength");
        WordList instance = new WordList("Merhaba");
        int expResult = 7;
        int result = instance.getWordsLength();
        assertEquals(expResult, result);
    }


    
}
