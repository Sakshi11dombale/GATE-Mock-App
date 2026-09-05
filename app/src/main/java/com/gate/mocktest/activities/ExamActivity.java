package com.gate.mocktest.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gate.mocktest.R;
import com.gate.mocktest.adapters.OptionAdapter;
import com.gate.mocktest.adapters.QuestionPaletteAdapter;
import com.gate.mocktest.database.entities.Question;
import com.gate.mocktest.models.ExamSession;
import com.gate.mocktest.viewmodels.ExamViewModel;
public class ExamActivity extends AppCompatActivity {
    public static final String EXTRA_TEST_TYPE="test_type",EXTRA_BRANCH="branch",EXTRA_SUBJECT="subject",
        EXTRA_TOPIC_QUERY="topic_query",EXTRA_YEAR="year",EXTRA_Q_COUNT="question_count",EXTRA_DURATION="duration_millis",EXTRA_USER_ID="user_id";
    private ExamViewModel viewModel;
    private QuestionPaletteAdapter paletteAdapter;
    private OptionAdapter optionAdapter;
    private TextView tvQNum,tvSubject,tvNeg,tvTimer,tvDiff,btnToggle;
    private LinearLayout paletteLayout;
    private Button btnPrev,btnNext,btnClear,btnMark,btnSubmit;
    private View progressBar,contentLayout;
    private int userId;
    @Override protected void onCreate(Bundle s){
        super.onCreate(s);setContentView(R.layout.activity_exam);
        viewModel=new ViewModelProvider(this).get(ExamViewModel.class);
        userId=getIntent().getIntExtra(EXTRA_USER_ID,0);
        bindViews();setupRecyclers();setupListeners();observe();
        String type=getIntent().getStringExtra(EXTRA_TEST_TYPE);
        String branch=getIntent().getStringExtra(EXTRA_BRANCH);
        String subject=getIntent().getStringExtra(EXTRA_SUBJECT);
        String topicQuery=getIntent().getStringExtra(EXTRA_TOPIC_QUERY);
        int year=getIntent().getIntExtra(EXTRA_YEAR,0);
        int count=getIntent().getIntExtra(EXTRA_Q_COUNT,30);
        long dur=getIntent().getLongExtra(EXTRA_DURATION,3600000L);
        if(ExamSession.TYPE_MOCK.equals(type)) viewModel.startMockTest(branch,count,dur);
        else if(ExamSession.TYPE_TOPIC.equals(type)) viewModel.startTopicTest(subject,topicQuery,branch,count,dur);
        else if(ExamSession.TYPE_PYQ.equals(type)) viewModel.startPYQTest(branch,year,dur);
        else viewModel.startMockTest(branch!=null?branch:"CS",count,dur);
    }
    private void bindViews(){
        tvQNum=findViewById(R.id.tv_question_number);tvSubject=findViewById(R.id.tv_subject);
        tvNeg=findViewById(R.id.tv_neg_marking);tvTimer=findViewById(R.id.tv_timer);
        tvDiff=findViewById(R.id.tv_difficulty);btnToggle=findViewById(R.id.btn_toggle_palette);
        paletteLayout=findViewById(R.id.palette_layout);progressBar=findViewById(R.id.progress_bar);
        contentLayout=findViewById(R.id.content_layout);
        btnPrev=findViewById(R.id.btn_prev);btnNext=findViewById(R.id.btn_next);
        btnClear=findViewById(R.id.btn_clear);btnMark=findViewById(R.id.btn_mark_review);btnSubmit=findViewById(R.id.btn_submit);
    }
    private void setupRecyclers(){
        paletteAdapter=new QuestionPaletteAdapter(i->viewModel.navigateTo(i));
        RecyclerView rvP=findViewById(R.id.rv_palette);
        rvP.setLayoutManager(new GridLayoutManager(this,8));rvP.setAdapter(paletteAdapter);
        optionAdapter=new OptionAdapter(opt->viewModel.selectAnswer(opt));
        RecyclerView rvO=findViewById(R.id.rv_options);
        rvO.setLayoutManager(new LinearLayoutManager(this));rvO.setAdapter(optionAdapter);
    }
    private void setupListeners(){
        btnPrev.setOnClickListener(v->viewModel.goPrev());
        btnNext.setOnClickListener(v->viewModel.goNext());
        btnClear.setOnClickListener(v->viewModel.clearAnswer());
        btnMark.setOnClickListener(v->viewModel.markForReview());
        btnSubmit.setOnClickListener(v->showSubmitDialog());
        btnToggle.setOnClickListener(v->togglePalette());
        findViewById(R.id.btn_back).setOnClickListener(v->showExitDialog());
    }
    private void observe(){
        viewModel.getLoading().observe(this,l->{progressBar.setVisibility(l?View.VISIBLE:View.GONE);contentLayout.setVisibility(l?View.GONE:View.VISIBLE);});
        viewModel.getSessionLive().observe(this,session->{if(session==null)return;renderQuestion(session);paletteAdapter.update(session);});
        viewModel.getTimeRemainingMillis().observe(this,ms->{tvTimer.setText(ExamViewModel.formatTime(ms));if(ms<600000)tvTimer.setTextColor(0xFFEF4444);});
        viewModel.getTimeUp().observe(this,up->{if(Boolean.TRUE.equals(up)){Toast.makeText(this,"Time up!",Toast.LENGTH_SHORT).show();submitTest();}});
        viewModel.getError().observe(this,msg->{ if(msg!=null && !msg.isEmpty()){ Toast.makeText(this,msg,Toast.LENGTH_LONG).show(); finish(); }});
    }
    private void renderQuestion(ExamSession session){
        if(session==null || session.getTotalQuestions()==0){
            Toast.makeText(this,"No questions are available for this test yet.",Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Question q=session.getCurrentQuestion();
        if(q==null){ finish(); return; }
        tvQNum.setText("Q "+(session.getCurrentIndex()+1)+" / "+session.getTotalQuestions());
        tvSubject.setText(q.subject+" · "+q.marks+" mark"+(q.marks>1?"s":""));
        tvNeg.setText(q.marks==1?"−0.33 penalty":"−0.66 penalty");
        tvDiff.setText(q.difficulty);
        ((TextView)findViewById(R.id.tv_question_text)).setText(q.questionText);
        optionAdapter.setOptions(new String[]{"A","B","C","D"},new String[]{q.optionA,q.optionB,q.optionC,q.optionD},session.getCurrentAnswer());
        btnPrev.setEnabled(session.canGoPrev());btnNext.setEnabled(session.canGoNext());
    }
    private void togglePalette(){
        boolean visible = paletteLayout.getVisibility() == View.VISIBLE;
        if (visible) {
            paletteLayout.animate().translationY(paletteLayout.getHeight()).alpha(0f).setDuration(180)
                    .withEndAction(() -> paletteLayout.setVisibility(View.GONE)).start();
            btnToggle.setText("Question palette");
        } else {
            paletteLayout.setAlpha(0f);
            paletteLayout.setTranslationY(80f);
            paletteLayout.setVisibility(View.VISIBLE);
            paletteLayout.animate().translationY(0f).alpha(1f).setDuration(220).start();
            btnToggle.setText("Hide palette");
        }
    }
    private void showSubmitDialog(){
        ExamSession s=viewModel.getSession();if(s==null)return;
        new AlertDialog.Builder(this).setTitle("Submit Test?")
            .setMessage("Attempted: "+s.getAttemptedCount()+" / "+s.getTotalQuestions()+"\nNot attempted: "+s.getNotAttemptedCount()+"\n\nSubmit the test?")
            .setPositiveButton("Submit",(d,w)->submitTest()).setNegativeButton("Continue",null).show();
    }
    private void showExitDialog(){
        new AlertDialog.Builder(this).setTitle("Exit Test?").setMessage("Your progress will be lost.")
            .setPositiveButton("Exit",(d,w)->finish()).setNegativeButton("Stay",null).show();
    }
    private void submitTest(){
        viewModel.pauseTimer();ExamSession s=viewModel.getSession();if(s==null){finish();return;}
        Intent i=new Intent(this,ResultActivity.class);
        i.putExtra(ResultActivity.EXTRA_SCORE,s.calculateScore());i.putExtra(ResultActivity.EXTRA_MAX_SCORE,s.getMaxScore());
        i.putExtra(ResultActivity.EXTRA_CORRECT,s.getCorrectCount());i.putExtra(ResultActivity.EXTRA_WRONG,s.getWrongCount());
        i.putExtra(ResultActivity.EXTRA_NOT_ATTEMPTED,s.getNotAttemptedCount());i.putExtra(ResultActivity.EXTRA_TOTAL,s.getTotalQuestions());
        i.putExtra(ResultActivity.EXTRA_TIME_TAKEN,s.getTimeTaken());i.putExtra(ResultActivity.EXTRA_TEST_NAME,s.getTestName());
        i.putExtra(ResultActivity.EXTRA_TEST_TYPE,s.getTestType());i.putExtra(ResultActivity.EXTRA_BRANCH,s.getBranch());
        i.putExtra(ResultActivity.EXTRA_SUBJECT,s.getSubject());i.putExtra(ResultActivity.EXTRA_USER_ID,userId);
        startActivity(i);finish();
    }
    @Override public void onBackPressed(){showExitDialog();}
}
