package sample;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DictionaryManagement {

    public Dictionary insertFromFile() throws IOException {
        Dictionary a = new Dictionary();
        FileReader file = new FileReader("source.txt");
        Scanner sc= new Scanner(file);

        while(true) {
            String a1 = sc.next();

            if(a1.equals("end")) break;

            String a2 = sc.nextLine();

            if(a2.indexOf('/') != -1) {
                a1 += a2.substring(0,a2.indexOf('/'));
            }

            StringBuilder a3 = new StringBuilder();
            String n = "d";

            while (!n.equals("")) {
                n = sc.nextLine();
                if(!n.equals("")) {
                    a3.append(n);
                    a3.append("\n");
                }
            }

            Word w =new Word(a1.substring(1,a1.length()-1),a2, a3.toString());
            a.target.add(a1.substring(1,a1.length()-1));
            a.words.put(a1.substring(1,a1.length()-1), w);
        }
        sc.close();
        return a;
    }
}
