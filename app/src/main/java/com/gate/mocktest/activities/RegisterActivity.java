package com.gate.mocktest.activities;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.gate.mocktest.R;
import com.gate.mocktest.database.AppDatabase;
import com.gate.mocktest.database.entities.User;
public class RegisterActivity extends AppCompatActivity {
    private EditText etName,etEmail,etUsername,etPassword,etConfirm;
    private Spinner spinnerBranch,spinnerYear;
    private Button btnReg;
    private TextView tvLogin,tvError;
    private ProgressBar progress;
    // GATE 2026 has 30 official test papers.
    // The code is stored in the database; the full name is shown to the student.
    private final String[] BRANCHES={
        "AE - Aerospace Engineering",
        "AG - Agricultural Engineering",
        "AR - Architecture & Planning",
        "BM - Biomedical Engineering",
        "BT - Biotechnology",
        "CE - Civil Engineering",
        "CH - Chemical Engineering",
        "CS - Computer Science & Information Technology",
        "CY - Chemistry",
        "DA - Data Science & Artificial Intelligence",
        "EC - Electronics & Communication Engineering",
        "EE - Electrical Engineering",
        "ES - Environmental Science & Engineering",
        "EY - Ecology & Evolution",
        "GE - Geomatics Engineering",
        "GG - Geology & Geophysics",
        "IN - Instrumentation Engineering",
        "MA - Mathematics",
        "ME - Mechanical Engineering",
        "MN - Mining Engineering",
        "MT - Metallurgical Engineering",
        "NM - Naval Architecture & Marine Engineering",
        "PE - Petroleum Engineering",
        "PH - Physics",
        "PI - Production & Industrial Engineering",
        "ST - Statistics",
        "TF - Textile Engineering & Fibre Science",
        "XE - Engineering Sciences",
        "XH - Humanities & Social Sciences",
        "XL - Life Sciences"
    };
    private final String[] BRANCH_CODES={
        "AE","AG","AR","BM","BT","CE","CH","CS","CY","DA",
        "EC","EE","ES","EY","GE","GG","IN","MA","ME","MN",
        "MT","NM","PE","PH","PI","ST","TF","XE","XH","XL"
    };
    private final int[] YEARS={2025,2026,2027};
    @Override protected void onCreate(Bundle s){
        super.onCreate(s);setContentView(R.layout.activity_register);
        etName=findViewById(R.id.et_name);etEmail=findViewById(R.id.et_email);
        etUsername=findViewById(R.id.et_username);etPassword=findViewById(R.id.et_password);
        etConfirm=findViewById(R.id.et_confirm_password);spinnerBranch=findViewById(R.id.spinner_branch);
        spinnerYear=findViewById(R.id.spinner_year);btnReg=findViewById(R.id.btn_register);
        tvLogin=findViewById(R.id.tv_login);tvError=findViewById(R.id.tv_error);
        progress=findViewById(R.id.progress_bar);
        ArrayAdapter<String> ba=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,BRANCHES);
        ba.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);spinnerBranch.setAdapter(ba);
        String[] ys=new String[YEARS.length];for(int i=0;i<YEARS.length;i++)ys[i]="GATE "+YEARS[i];
        ArrayAdapter<String> ya=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,ys);
        ya.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);spinnerYear.setAdapter(ya);
        btnReg.setOnClickListener(v->doRegister());
        tvLogin.setOnClickListener(v->finish());
    }
    private void doRegister(){
        String name=etName.getText().toString().trim(),email=etEmail.getText().toString().trim();
        String username=etUsername.getText().toString().trim(),password=etPassword.getText().toString().trim();
        String confirm=etConfirm.getText().toString().trim();
        String branch=BRANCH_CODES[spinnerBranch.getSelectedItemPosition()];
        int year=YEARS[spinnerYear.getSelectedItemPosition()];
        if(TextUtils.isEmpty(name)){etName.setError("Enter your name");return;}
        if(TextUtils.isEmpty(email)||!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){etEmail.setError("Enter valid email");return;}
        if(TextUtils.isEmpty(username)||username.length()<4){etUsername.setError("Min 4 characters");return;}
        if(TextUtils.isEmpty(password)||password.length()<6){etPassword.setError("Min 6 characters");return;}
        if(!password.equals(confirm)){etConfirm.setError("Passwords don't match");return;}
        tvError.setVisibility(View.GONE);progress.setVisibility(View.VISIBLE);btnReg.setEnabled(false);
        AppDatabase.databaseWriteExecutor.execute(()->{
            AppDatabase db=AppDatabase.getInstance(this);
            User eu=db.userDao().findByUsername(username),ee=db.userDao().findByEmail(email);
            runOnUiThread(()->{
                if(eu!=null){progress.setVisibility(View.GONE);btnReg.setEnabled(true);tvError.setText("Username already taken");tvError.setVisibility(View.VISIBLE);return;}
                if(ee!=null){progress.setVisibility(View.GONE);btnReg.setEnabled(true);tvError.setText("Email already registered. Login instead.");tvError.setVisibility(View.VISIBLE);return;}
                AppDatabase.databaseWriteExecutor.execute(()->{
                    User user=new User();user.name=name;user.email=email;user.username=username;
                    user.password=password;user.branch=branch;user.targetYear=year;
                    user.registeredAt=System.currentTimeMillis();user.isLoggedIn=true;
                    long id=db.userDao().insert(user);
                    runOnUiThread(()->{
                        progress.setVisibility(View.GONE);
                        if(id>0){Toast.makeText(this,"Welcome, "+name+"! 🎉",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));finish();
                        } else {btnReg.setEnabled(true);tvError.setText("Registration failed. Try again.");tvError.setVisibility(View.VISIBLE);}
                    });
                });
            });
        });
    }
}
