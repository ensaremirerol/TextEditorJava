
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */

class CannotInitilazeWordDictionary extends Exception{
    public CannotInitilazeWordDictionary(String message){
        super(message);
    }
}
public class SpellChecker {
    
    private int maxLength = 0;
    
    ArrayList<WordList> wordsList = new ArrayList<>();

    public SpellChecker(String filePath) throws CannotInitilazeWordDictionary {
        FileHandler fileHandler = new FileHandler();
        try{
           String [] wordArray = fileHandler.open(filePath).split("\\r?\\n");
           for (String word : wordArray) {
               int index = word.length() -1;
               if (maxLength >= word.length()){
                   wordsList.get(index).add(word.toLowerCase());
               }
               else{
                   for (int i = 0; i < word.length() - maxLength; i++) {
                       wordsList.add(new WordList(i));
                   }
                   maxLength = word.length();
                   wordsList.get(index).add(word.toLowerCase());
               }
            }
        }
        catch(CannotOpenException e){
            throw new CannotInitilazeWordDictionary(e.getMessage());
        }       
    }
    
    public SpellChecker(String [] wordArray){
        for (String word : wordArray) {
            int index = word.length() -1;
            if (maxLength >= word.length()){
                   wordsList.get(index).add(word.toLowerCase());
            }
            else{
                for (int i = 0; i < word.length() - maxLength; i++) {
                       wordsList.add(new WordList(i));
                   }
                   maxLength = word.length();
                   wordsList.get(index).add(word.toLowerCase());
               }
        }
        
    }
    
    public String checkWord(String word){
        if (word.equals("")) return "";
        
        int index = word.length() -1;
        
        if(wordsList.get(index).contains(word.toLowerCase())) return word;
        
        boolean found;
        
        ArrayList<Integer> typoIndexes = new ArrayList<>();
        
        for(String listWord : wordsList.get(index)){
            String _word = word.toLowerCase();
            typoIndexes.clear();
            found = true;     
            for (int i = 0; i < word.length(); i++) {
                if(_word.charAt(i) != listWord.charAt(i)){
                    try{
                      if(_word.charAt(i+1) == listWord.charAt(i)
                            && listWord.charAt(i+1) == _word.charAt(i)){
                        typoIndexes.add(i);
                        i++;
                        }
                        else{
                            found = false;
                            break;
                        }  
                    }
                    catch (IndexOutOfBoundsException e){
                        found = false;
                        break;
                    }                   
                }
            }
            if(found){
                char [] wordArr = word.toCharArray();
                for(int typoIndex : typoIndexes){
                    char tmp = wordArr[typoIndex+1];
                    wordArr[typoIndex + 1] = wordArr[typoIndex];
                    wordArr[typoIndex] = tmp;
                }
                return String.valueOf(wordArr);
            }
        }
        return "";
    }   
}



