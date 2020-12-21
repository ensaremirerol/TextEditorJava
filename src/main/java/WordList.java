
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

// Aynı uzunluktaki kelimelerin tutulduğu liste
public class WordList extends ArrayList<String> implements Comparable<WordList>{
       private final int wordsLength;
       
        public WordList(int wordsLength) {
            this.wordsLength = wordsLength;          
        }
        
        public WordList(String word) {
            this.wordsLength = word.length();   
            add(word);
            
        }
        
        @Override
        public int compareTo(WordList obj){
            return this.getWordsLength() - obj.getWordsLength();
        }

        /**
         * @return the wordsLength
         */
        public int getWordsLength() {
            return wordsLength;
        }

}
