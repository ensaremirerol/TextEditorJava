import java.util.Iterator;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */

public class WordYielder implements Iterable<Word>{
    private final String text;
    public WordYielder(String text){
        this.text = text;
    } 

    @Override
    public Iterator<Word> iterator() {
        return new WordYielderIterator(text);
    }
    
}

// Verilen text deki bir sonraki kelime bulunur
class WordYielderIterator implements Iterator<Word>{
    // Harfler RegularExpression kullanılarak bulunur
    private static final Pattern ACCEPT_PATTERN = Pattern.compile("[a-zA-ZıİçÇöÖşŞüÜğĞ]");
    private final String text;
    private int currIndex;
    private boolean endOfFile = false;
    public WordYielderIterator(String text){
        this.text = text;
        currIndex = 0;
    }    

    /**
     * @return the currIndex
     */
    public int getCurrIndex() {
        return currIndex;
    }
    
    /**
     *
     * @return a Word Object
     */
    
    // Bir sonraki kelimeyi oluşturur
    @Override
    public Word next() {
        String _word = "";
        String currChar;
        if (endOfFile){
            endOfFile = false;
            currIndex = 0;
            return null;
        }
        while (true){
            if (currIndex >= text.length()){
                int startIndex = getCurrIndex() - _word.length();
                int endIndex = getCurrIndex();
                endOfFile = true;
                return new Word(startIndex, endIndex, _word);
            }
            currChar = String.valueOf(text.charAt(getCurrIndex()));
            if(ACCEPT_PATTERN.matcher(currChar).matches()){
               _word += currChar;
               currIndex++;
            }
            else if (!_word.equals("")){
                int startIndex = getCurrIndex() - _word.length();
                int endIndex = getCurrIndex();
                return new Word(startIndex, endIndex, _word);
            }
            else{
                currIndex++;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return !endOfFile;
    }
}
