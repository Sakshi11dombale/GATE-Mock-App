package com.gate.mocktest.activities;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.gate.mocktest.R;
import com.gate.mocktest.database.entities.TestAttempt;
import com.gate.mocktest.viewmodels.DashboardViewModel;
public class ResultActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE="score",EXTRA_MAX_SCORE="max_score",EXTRA_CORRECT="correct",
        EXTRA_WRONG="wrong",EXTRA_NOT_ATTEMPTED="not_attempted",EXTRA_TOTAL="total",
        EXTRA_TIME_TAKEN="time_taken",EXTRA_TEST_NAME="test_name",EXTRA_TEST_TYPE="test_type",
        EXTRA_BRANCH="branch",EXTRA_SUBJECT="subject",EXTRA_USER_ID="user_id";
    @Override protected void onCreate(Bundle s){
        super.onCreate(s);setContentView(R.layout.activity_result);
        DashboardViewModel vm=new ViewModelProvider(this).get(DashboardViewModel.class);
        float score=getIntent().getFloatExtra(EXTRA_SCORE,0),max=getIntent().getFloatExtra(EXTRA_MAX_SCORE,100);
        int correct=getIntent().getIntExtra(EXTRA_CORRECT,0),wrong=getIntent().getIntExtra(EXTRA_WRONG,0);
        int notAttempted=getIntent().getIntExtra(EXTRA_NOT_ATTEMPTED,0),total=getIntent().getIntExtra(EXTRA_TOTAL,0);
        long time=getIntent().getLongExtra(EXTRA_TIME_TAKEN,0);
        String testName=getIntent().getStringExtra(EXTRA_TEST_NAME),testType=getIntent().getStringExtra(EXTRA_TEST_TYPE);
        String branch=getIntent().getStringExtra(EXTRA_BRANCH),subject=getIntent().getStringExtra(EXTRA_SUBJECT);
        int uid=getIntent().getIntExtra(EXTRA_USER_ID,0);
        float pct=(max>0)?(score/max)*100f:0f,acc=(total>0)?(correct/(float)total)*100f:0f;
        ((TextView)findViewById(R.id.tv_test_name)).setText(testName);
        ((TextView)findViewById(R.id.tv_score)).setText(String.format("%.2f",score));
        ((TextView)findViewById(R.id.tv_max_score)).setText("/ "+String.format("%.0f",max)+" marks");
        ((TextView)findViewById(R.id.tv_percentage)).setText(String.format("%.1f%%",pct));
        ((TextView)findViewById(R.id.tv_correct)).setText(String.valueOf(correct));
        ((TextView)findViewById(R.id.tv_wrong)).setText(String.valueOf(wrong));
        ((TextView)findViewById(R.id.tv_not_attempted)).setText(String.valueOf(notAttempted));
        ((TextView)findViewById(R.id.tv_time_taken)).setText(fmtTime(time));
        ((TextView)findViewById(R.id.tv_accuracy)).setText(String.format("%.1f%%",acc));
        ((TextView)findViewById(R.id.tv_grade)).setText(grade(pct));
        ((TextView)findViewById(R.id.tv_est_rank)).setText("Est. Rank: "+rank(pct));
        ((ProgressBar)findViewById(R.id.progress_ring)).setProgress((int)pct);
        TestAttempt a=new TestAttempt();a.testName=testName;a.testType=testType;a.branch=branch;a.subject=subject;
        a.totalQuestions=total;a.attempted=correct+wrong;a.correct=correct;a.wrong=wrong;
        a.notAttempted=notAttempted;a.score=score;a.maxScore=max;a.timeTakenMillis=time;
        a.attemptDate=System.currentTimeMillis();a.accuracy=acc;a.rank=rank(pct);a.userId=uid;
        vm.saveAttempt(a);
        findViewById(R.id.btn_home).setOnClickListener(v->{startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));finish();});
        findViewById(R.id.btn_retake).setOnClickListener(v->finish());
        findViewById(R.id.btn_share).setOnClickListener(v->{
            Intent si=new Intent(Intent.ACTION_SEND);si.setType("text/plain");
            si.putExtra(Intent.EXTRA_TEXT,"I scored "+String.format("%.2f",score)+"/"+String.format("%.0f",max)+" ("+String.format("%.1f%%",pct)+") in "+testName+" on GATE MockTest!");
            startActivity(Intent.createChooser(si,"Share result"));
        });
    }
    private String grade(float p){if(p>=90)return"Outstanding 🏆";if(p>=75)return"Excellent ⭐";if(p>=60)return"Good 👍";if(p>=45)return"Average 📈";return"Needs Improvement 💪";}
    private String rank(float p){if(p>=90)return"~Top 500";if(p>=80)return"~Top 2,000";if(p>=70)return"~Top 5,000";if(p>=60)return"~Top 10,000";return"~Top 50,000+";}
    private String fmtTime(long ms){long s=ms/1000,h=s/3600,m=(s%3600)/60,sec=s%60;return h>0?h+"h "+m+"m":m+"m "+sec+"s";}
}
