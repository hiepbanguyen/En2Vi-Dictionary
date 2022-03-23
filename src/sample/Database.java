package sample;

import java.io.IOException;

public class Database {

    protected static DictionaryManagement DM = new DictionaryManagement();
    protected static Dictionary d;

    static {
        try {
            d = DM.insertFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMeaning(String word) {
        word = word.toLowerCase();
        if(d.words.get(word) != null) {
            return d.words.get(word).getWord_target()+"\n"+d.words.get(word).getWord_phonetic()+"\n"+d.words.get(word).getWord_explain();
        }
        else{ return "Không tồn tại từ trong từ điển.";}
    }

    public static void deleteWord(String word) {
        word = word.toLowerCase();
        d.words.remove(word);
    }

    public static void addWord(String word, String meaning) {
        word = word.toLowerCase();
        Word a = new Word(word,"",meaning);
        d.words.put(word,a);
    }

    public static void editWord(String word, String newMeaning) {
        word = word.toLowerCase();
        d.words.get(word).setWord_explain(newMeaning);
    }
}
