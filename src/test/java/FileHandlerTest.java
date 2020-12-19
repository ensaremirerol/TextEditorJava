/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ensar
 */
public class FileHandlerTest {
    
    public FileHandlerTest() {
    }
      
    /**
     * Test of open method, of class FileHandler.
     */
    @Test
    public void testSaveandOpen() throws Exception {
        System.out.println("save and open");
        String filePath = "./test";
        FileHandler instance = new FileHandler();
        String textToWrite = "adkdllsdk\ndjjasldasıo5486";
        instance.save(filePath, textToWrite);
        String result = instance.open(filePath);
        assertEquals(textToWrite, result);
    }
    
}
