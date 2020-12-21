/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */

// WordYilder bu objeyi döndürür
public class Word {
    private final int startIndex;
    private final int endIndex;
    private final String word;
    public Word(int startIndex, int endIndex, String word){
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.word = word;
    }

    /**
     * @return the startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * @return the endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }
    
}
