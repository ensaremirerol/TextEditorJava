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
public class SpellCheckerTest {
    
    public SpellCheckerTest() {
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
     * Test of checkWord method, of class SpellChecker.
     */
    @Test
    public void testCheckWordTypo() {
        System.out.println("checkWordTypo");
        String word = "Ahemt";
        String [] wordArr = {"ahmet", "ali", "fatma"};
        SpellChecker instance = new SpellChecker(wordArr);
        String expResult = "Ahmet";
        String result = instance.checkWord(word);
        assertEquals(expResult, result);
        word = "lai";
        expResult = "ali";
        result = instance.checkWord(word);
        assertEquals(expResult, result);
    }
    @Test
    public void testCheckWordTypo2() {
        System.out.println("checkWordTypo");
        String word = "benim";
        String [] wordArr = {"being"};
        SpellChecker instance = new SpellChecker(wordArr);
        String expResult = "";
        String result = instance.checkWord(word);
        assertEquals(expResult, result);
    }
    @Test
    public void testCheckWordNotInList() {
        System.out.println("checkWordNotInList");
        String word = "Ensar";
        String [] wordArr = {"ahmet", "ali", "fatma"};
        SpellChecker instance = new SpellChecker(wordArr);
        String expResult = "";
        String result = instance.checkWord(word);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCheckWordCorrectWord(){
        System.out.println("checkWordCorrecWord");
        String word = "Ali";
        String [] wordArr = {"ahmet", "ali", "fatma"};
        SpellChecker instance = new SpellChecker(wordArr);
        String expResult = "Ali";
        String result = instance.checkWord(word);
        assertEquals(expResult, result);
    }
    
}
