package sample;

import java.util.*;

class Spelling {

    static HashMap<String, Integer> nWords = new HashMap<>();
    static {
        int i = 0;
        for(String in : Database.d.target) {
            nWords.put(in,i);
            i++;
        }
    }

    public Spelling(){}


    private ArrayList<String> edits(String word) {
        ArrayList<String> result = new ArrayList<>();
        for(int i=0; i < word.length(); ++i) result.add(word.substring(0, i) + word.substring(i+1));
        for(int i=0; i < word.length()-1; ++i) result.add(word.substring(0, i) + word.charAt(i+1) + word.charAt(i) + word.substring(i+2));
        for(int i=0; i < word.length(); ++i) for(char c='a'; c <= 'z'; ++c) result.add(word.substring(0, i) + c + word.substring(i+1));
        for(int i=0; i <= word.length(); ++i) for(char c='a'; c <= 'z'; ++c) result.add(word.substring(0, i) + c + word.substring(i));
        return result;
    }

    public final String correct(String word) {
        if(nWords.containsKey(word)) return word;
        ArrayList<String> list = edits(word);
        HashMap<Integer, String> candidates = new HashMap<>();
        for(String s : list) if(nWords.containsKey(s)) candidates.put(nWords.get(s),s);
        if(candidates.size() > 0) return candidates.get(Collections.max(candidates.keySet()));
        for(String s : list) for(String w : edits(s)) if(nWords.containsKey(w)) candidates.put(nWords.get(w),w);
        return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : word;
    }

}