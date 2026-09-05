package com.gate.mocktest.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName="test_attempts")
public class TestAttempt {
    @PrimaryKey(autoGenerate=true) public int id;
    public String testName,testType,branch,subject,rank;
    public int totalQuestions,attempted,correct,wrong,notAttempted,userId;
    public float score,maxScore,accuracy;
    public long timeTakenMillis,attemptDate;
}
