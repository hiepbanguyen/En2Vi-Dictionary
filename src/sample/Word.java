package sample;

public class Word {
    private String word_target;
    private String word_explain;
    private  String word_phonetic;

    public Word() {}

    public Word (String word_target, String word_phonetic, String word_explain) {
        this.word_target = word_target;
        this.word_phonetic = word_phonetic;
        this.word_explain = word_explain;
    }
    public Word(String word_target, String word_explain) {
         this.word_target = word_target;
         this.word_explain = word_explain;
         this.word_phonetic = "";
    }
    public String getWord_phonetic() {
        return word_phonetic;
    }

    public void setWord_phonetic(String word_phonetic) {
        this.word_phonetic = word_phonetic;
    }

    public String getWord_target() {
        return this.word_target;
    }

    public String getWord_explain() {
        return this.word_explain;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
}
