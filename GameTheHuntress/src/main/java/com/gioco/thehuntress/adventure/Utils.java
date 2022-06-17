package com.gioco.thehuntress.adventure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    public static Set<String> loadFileListInSet(File file) throws IOException {
        Set<String> set = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.ready()) {
            set.add(reader.readLine().trim().toLowerCase());
        }
        reader.close();
        return set;
    }

        //restituisce una lista composta da comando oggetto senza stopwords
    public static String[] parseString(String string, Set<String> stopwords) { //string= stringa composta da comando-stopword-oggetto.Classe
        String[] tokens = null;
        int i=0;
        String[] split = string.toLowerCase().trim().split("\\s+");
        for (String t : split) {
            if (!stopwords.contains(t)) {
                tokens[i]="t";
                i++;
            }
        }
        return tokens;  //tokens è la lista di Stringhe composta soltanto da comando-oggetto
    }
}
