package com.hroniko.jload.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QuestionSaver {

    private Map<String, String> questionAndAnswerMap = new HashMap<>();

    public QuestionSaver() {
    }

    public void onlyOnePrint(String question){
        if (!questionAndAnswerMap.containsKey(question)){
            System.out.println(question);
            questionAndAnswerMap.put(question, "yes");
        }
    }

//    public void addAnswer(String question, String answer){
//        questionAndAnswerMap.put(question, answer);
//    }

    public String onlyOneAddAnswer(String question, String messageForYes, String messageForNot){
        if (!questionAndAnswerMap.containsKey(question)){
            System.out.println(question);
            Scanner in = new Scanner(System.in);
            String answer = in.nextLine();
            if (!(answer == null || answer.length() == 0 || answer.toLowerCase().equals("y") || answer.toLowerCase().equals("yes") )){
                if (messageForYes != null) System.out.println(messageForYes);
                questionAndAnswerMap.put(question, "yes");
            } else {
                if (messageForNot != null) System.out.println(messageForNot);
                questionAndAnswerMap.put(question, "no");
            }

            return "yes";
        }
        return "no";
    }

    public String getAnswer(String question){
        if (!questionAndAnswerMap.containsKey(question)){
            return questionAndAnswerMap.get(question);
        } else return "no";
    }

    public void setAnswer(String question, String answer){
        if (!questionAndAnswerMap.containsKey(question)){
            questionAndAnswerMap.put(question, answer);
        }
    }

    public Boolean isExist(String question){
        return questionAndAnswerMap.containsKey(question);
    }

    public Boolean isNotExist(String question){
        return !questionAndAnswerMap.containsKey(question);
    }


}
