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
public class LoginActivity extends AppCompatActivity {
    private EditText etUsername,etPassword;
    private Button btnLogin;
    private TextView tvRegister,tvError;
    private ProgressBar progress;
    @Override protected void onCreate(Bundle s){
        super.onCreate(s);setContentView(R.layout.activity_login);
        etUsername=findViewById(R.id.et_username);etPassword=findViewById(R.id.et_password);
        btnLogin=findViewById(R.id.btn_login);tvRegister=findViewById(R.id.tv_register);
        tvError=findViewById(R.id.tv_error);progress=findViewById(R.id.progress_bar);
        btnLogin.setOnClickListener(v->doLogin());
        tvRegister.setOnClickListener(v->startActivity(new Intent(this,RegisterActivity.class)));
    }
    private void doLogin(){
        String u=etUsername.getText().toString().trim(),p=etPassword.getText().toString().trim();
        if(TextUtils.isEmpty(u)){etUsername.setError("Enter username");return;}
        if(TextUtils.isEmpty(p)){etPassword.setError("Enter password");return;}
        tvError.setVisibility(View.GONE);progress.setVisibility(View.VISIBLE);btnLogin.setEnabled(false);
        AppDatabase.databaseWriteExecutor.execute(()->{
            User user=AppDatabase.getInstance(this).userDao().login(u,p);
            runOnUiThread(()->{
                progress.setVisibility(View.GONE);btnLogin.setEnabled(true);
                if(user!=null){
                    AppDatabase.databaseWriteExecutor.execute(()->{
                        AppDatabase.getInstance(this).userDao().logoutAll();
                        AppDatabase.getInstance(this).userDao().setLoggedIn(user.id);
                    });
                    startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                } else {tvError.setText("Invalid username or password");tvError.setVisibility(View.VISIBLE);}
            });
        });
    }
}
