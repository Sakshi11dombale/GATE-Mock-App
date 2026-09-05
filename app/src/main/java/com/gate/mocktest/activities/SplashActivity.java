package com.gate.mocktest.activities;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.gate.mocktest.R;
import com.gate.mocktest.database.AppDatabase;
import com.gate.mocktest.database.entities.User;
public class SplashActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle s){
        super.onCreate(s);setContentView(R.layout.activity_splash);
        TextView logo=findViewById(R.id.tv_logo),tag=findViewById(R.id.tv_tagline);
        AlphaAnimation f=new AlphaAnimation(0,1);f.setDuration(900);f.setFillAfter(true);logo.startAnimation(f);
        AlphaAnimation f2=new AlphaAnimation(0,1);f2.setDuration(900);f2.setStartOffset(400);f2.setFillAfter(true);tag.startAnimation(f2);
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            AppDatabase.databaseWriteExecutor.execute(()->{
                User u=AppDatabase.getInstance(this).userDao().getLoggedInUser();
                runOnUiThread(()->{ 
                    if(u!=null) startActivity(new Intent(this,MainActivity.class));
                    else startActivity(new Intent(this,LoginActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                });
            });
        },2000);
    }
}
