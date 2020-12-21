import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */

class CannotSaveException extends Exception{
    public CannotSaveException(String message) {
        super(message);
    }
}

class CannotOpenException extends Exception{
    public CannotOpenException(String message){
        super(message);
    }
}

// Dosya okuma/yazma işlemleri için sınıf
public class FileHandler {
    
    
    private File file;
    private FileWriter fileWriter;
    private BufferedWriter bufferWriter;
    private FileReader fileReader;
    private BufferedReader bufferReader;
    
    

    public FileHandler(){
    }
    
    // Dosyayı kaydetme işlemi
    public void save(String filePath, String text) throws CannotSaveException{
        try {
            if(!filePath.endsWith(".txt")){
            filePath = filePath + ".txt";
            }
            file = new File(filePath);
            // Create a file writer 
            fileWriter = new FileWriter(file, StandardCharsets.UTF_8 ,false); 
  
            // Create buffered writer to write 
            bufferWriter = new BufferedWriter(fileWriter); 
  
            // Write
            bufferWriter.write(text); 
  
            bufferWriter.flush(); 
            bufferWriter.close();
            } 
        catch (IOException e) {
            throw new CannotSaveException(e.getMessage());
            }
    }
    
    // Dosyayı açma işlemi
    public String open(String filePath) throws CannotOpenException{
        if(!filePath.endsWith(".txt")){
            filePath = filePath + ".txt";
            }
        file = new File(filePath);  
        try 
        { 
            // String 
            String text = "", line = ""; 
  
            // File reader 
            fileReader = new FileReader(file, StandardCharsets.UTF_8); 
  
            // Buffered reader 
            bufferReader = new BufferedReader(fileReader); 
  
            // Initilize line 
            text = bufferReader.readLine(); 
  
            // Take the input from the file 
            while ((line = bufferReader.readLine()) != null) { 
                    text = text + "\n" + line; 
            }
            
            bufferReader.close();
  
            // Set the text 
            return text;
        } 
        catch(IOException e){
            throw new CannotOpenException(e.getMessage());
        }          
    }
    
}
