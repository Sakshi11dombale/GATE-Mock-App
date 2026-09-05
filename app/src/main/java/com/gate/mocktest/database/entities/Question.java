package com.gate.mocktest.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName="questions")
public class Question {
    @PrimaryKey(autoGenerate=true) public int id;
    public String questionText,optionA,optionB,optionC,optionD;
    public String correctAnswer,explanation,subject,topic,branch,difficulty,questionType;
    public int year;
    public float marks;
    public boolean isPYQ;
    public Question(){}
    public Question(String qt,String a,String b,String c,String d,String ans,String exp,
                    String sub,String top,String br,String diff,int yr,String type,float m,boolean pyq){
        questionText=qt;optionA=a;optionB=b;optionC=c;optionD=d;correctAnswer=ans;
        explanation=exp;subject=sub;topic=top;branch=br;difficulty=diff;year=yr;
        questionType=type;marks=m;isPYQ=pyq;
    }
}
