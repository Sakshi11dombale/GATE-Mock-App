package com.gate.mocktest.models;
import com.gate.mocktest.database.entities.Question;
import java.util.*;
public class ExamSession {
    public static final String TYPE_MOCK="MOCK",TYPE_TOPIC="TOPIC",TYPE_PYQ="PYQ";
    public static final int NOT_VISITED=0,NOT_ANSWERED=1,ANSWERED=2,MARKED=3,ANSWERED_MARKED=4;
    private List<Question> questions;
    private Map<Integer,String> answers=new HashMap<>();
    private Map<Integer,Integer> status=new HashMap<>();
    private int currentIndex=0;
    private long durationMillis,startTime;
    private String testName,testType,branch,subject;
    public ExamSession(List<Question> q,long dur,String name,String type,String branch,String subject){
        questions=q;durationMillis=dur;testName=name;testType=type;this.branch=branch;this.subject=subject;
        startTime=System.currentTimeMillis();
        for(int i=0;i<q.size();i++) status.put(i,NOT_VISITED);
        if(!q.isEmpty()) status.put(0,NOT_ANSWERED);
    }
    public Question getCurrentQuestion(){
        if(questions==null || questions.isEmpty()) return null;
        if(currentIndex<0 || currentIndex>=questions.size()) return null;
        return questions.get(currentIndex);
    }
    public void selectAnswer(String opt){
        answers.put(currentIndex,opt);
        int s=status.getOrDefault(currentIndex,NOT_ANSWERED);
        status.put(currentIndex,(s==MARKED||s==ANSWERED_MARKED)?ANSWERED_MARKED:ANSWERED);
    }
    public void clearAnswer(){
        answers.remove(currentIndex);
        int s=status.getOrDefault(currentIndex,NOT_ANSWERED);
        status.put(currentIndex,(s==ANSWERED_MARKED)?MARKED:NOT_ANSWERED);
    }
    public void markForReview(){
        int s=status.getOrDefault(currentIndex,NOT_ANSWERED);
        status.put(currentIndex,(s==ANSWERED||s==ANSWERED_MARKED)?ANSWERED_MARKED:MARKED);
    }
    public void navigateTo(int i){
        if(i>=0&&i<questions.size()){currentIndex=i;
            if(status.getOrDefault(i,NOT_VISITED)==NOT_VISITED) status.put(i,NOT_ANSWERED);}
    }
    public boolean canGoNext(){return currentIndex<questions.size()-1;}
    public boolean canGoPrev(){return currentIndex>0;}
    public void goNext(){if(canGoNext())navigateTo(currentIndex+1);}
    public void goPrev(){if(canGoPrev())navigateTo(currentIndex-1);}
    public String getCurrentAnswer(){return answers.get(currentIndex);}
    public int getQuestionStatus(int i){return status.getOrDefault(i,NOT_VISITED);}
    public float calculateScore(){
        float s=0;
        for(int i=0;i<questions.size();i++){
            String sel=answers.get(i);
            if(sel!=null){
                if(sel.equals(questions.get(i).correctAnswer)) s+=questions.get(i).marks;
                else s-=questions.get(i).marks/3f;
            }
        }
        return Math.max(0,s);
    }
    public float getMaxScore(){float m=0;for(Question q:questions)m+=q.marks;return m;}
    public int getCorrectCount(){int c=0;for(int i=0;i<questions.size();i++){String s=answers.get(i);if(s!=null&&s.equals(questions.get(i).correctAnswer))c++;}return c;}
    public int getWrongCount(){int c=0;for(int i=0;i<questions.size();i++){String s=answers.get(i);if(s!=null&&!s.equals(questions.get(i).correctAnswer))c++;}return c;}
    public int getAttemptedCount(){return answers.size();}
    public int getNotAttemptedCount(){return questions.size()-answers.size();}
    public long getTimeTaken(){return System.currentTimeMillis()-startTime;}
    public List<Question> getQuestions(){return questions;}
    public int getCurrentIndex(){return currentIndex;}
    public int getTotalQuestions(){return questions.size();}
    public long getDurationMillis(){return durationMillis;}
    public String getTestName(){return testName;}
    public String getTestType(){return testType;}
    public String getBranch(){return branch;}
    public String getSubject(){return subject;}
    public Map<Integer,String> getAnswers(){return answers;}
}
