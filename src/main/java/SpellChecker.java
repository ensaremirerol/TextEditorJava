
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
// Yazım hatalarını denetler
public class SpellChecker {
    
    public static SpellChecker instance;
    
    // İçerdeki en uzun kelime
    private int maxLength = 0;
    
    // Aynı uzunluktaki kelimeler bir listeye atılır
    // Ondan sonra o listeler bu listeye atılır
    /*
        0           1              2            ...
        length 1    length 2       length 3     ...
    */
    ArrayList<ArrayList<String>> wordsList = new ArrayList<>();
    
    // Dosya okunarak wordsList oluşturulur
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
                       wordsList.add(new ArrayList());
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
    
    // Array ile wordsList oluşturulur
    public SpellChecker(String [] wordArray){
        for (String word : wordArray) {
            int index = word.length() -1;
            if (maxLength >= word.length()){
                   wordsList.get(index).add(word.toLowerCase());
            }
            else{
                for (int i = 0; i < word.length() - maxLength; i++) {
                       wordsList.add(new ArrayList());
                   }
                   maxLength = word.length();
                   wordsList.get(index).add(word.toLowerCase());
               }
        }
        
    }
    
    public static void initialize(String filePath) throws CannotInitilazeWordDictionary{
        instance = new SpellChecker(filePath);
    }
    
    
    // Kelime kontrolü
    /*
        a  h  m  e  t   Listedeki kelime
        |  |  \ /   | 
        |  |  / \   |
        a  h  e  m  t   Kontrol edilen kelime
    
        Listeden bir kelime seçilir
        Kelime karakter karakter taranır
        Bir indexdeki karakter eşleşmez ise yukardaki gibi çapraz eşleme yapmaya
            çalışılır
        Çapraz eşleme başarılı ise
            - Karakter karakter taramaya devam edilir
        Değil ise
            - Listedeki bir sonraki kelime seçilir
         
        Son karakter yada karakterler(Çapraz eşleme) taramadana geçrse
        Kontrol edilen kelimenin büyük harf küçük harf durumuna göre 
        Doğru kelime geri döndürülür
    
    
    */
    public String checkWord(String word){
        // Kelime boş ise
        if (word.equals("")) return "";
        
        // Kelimenin hangi listede aranacağı seçilir
        int index = word.length() -1;
        
        // Eğerki kelime zaten listede ise kelime geri döndürlür
        if(wordsList.get(index).contains(word.toLowerCase())) return word;
        
        // Doğruluk için kullanılır
        boolean found;
        
        // Hataların bulunduğu indexleri tutar
        ArrayList<Integer> typoIndexes = new ArrayList<>();
        
        String _word = word.toLowerCase();
        
        // Listeden tek tek kelime seçer
        for(String listWord : wordsList.get(index)){
            typoIndexes.clear();
            found = true;
            // Karakter karakter tarama işlemi
            for (int i = 0; i < word.length(); i++) {
                // Eğerki karakterler eşleşirse devam edilir
                if(_word.charAt(i) != listWord.charAt(i)){
                    // Değilse çapraz eşleme yapılır
                    // Çapraz eşlemenin index dışına çıkma ihtimali vardır
                    try{
                      if(_word.charAt(i+1) == listWord.charAt(i)
                            && listWord.charAt(i+1) == _word.charAt(i)){
                        typoIndexes.add(i);
                        // Index burda 1 artırılır for döngüsü kendisi 1 daha atlar
                        // Toplamda 2 indeks geçilir
                        i++;
                        }
                        else{
                            found = false;
                            break;
                        }  
                    }
                    // Index dışına çıkarsa
                    catch (IndexOutOfBoundsException e){
                        found = false;
                        break;
                    }                   
                }
            }
            // Kelime bulunursa orjinal kelimenin Büyük/küçük harf
            // durumuna göre kelime yeniden yazılır
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
        // Eğerki bulunamazsa boş string döndürülür
        return "";
    }   
}



