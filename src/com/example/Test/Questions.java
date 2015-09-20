package com.example.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by САМ on 22.02.2015.
 */
public class Questions {
    String question;
    String [] answers;
    int correct;

    Questions () {}
    public Questions(String question, String[] answers, int correct) {
        this.question = question;
        this.answers = answers;
        this.correct = correct;
    }

    public ArrayList<Questions> getData() throws IOException {
        ArrayList<Questions> questions = new ArrayList<Questions>();
        InputStream is = getClass().getResourceAsStream("/data.txt");
        Scanner scanner = new Scanner(is);
        while (scanner.hasNext()) {
            String q = scanner.nextLine();
            ArrayList<String> ans = new ArrayList<String>();
            String line;
            while (scanner.hasNext() && !((line = scanner.nextLine()).equals(""))) {
                ans.add(line);
            }
            Collections.shuffle(ans);
            String [] a = new String[ans.size()];
            int c = 0, count = 0;
            for (int i = 0; i < ans.size(); i++) {
                count++;
                if (ans.get(i).charAt(0) == '+') c = count;
                a[i] = ans.get(i).substring(2);
            }
            questions.add(new Questions(q, a, c));
        }
        Collections.shuffle(questions);
        return questions;
    }
}
