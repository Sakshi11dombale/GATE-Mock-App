package com.gate.mocktest.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gate.mocktest.database.dao.QuestionDao;
import com.gate.mocktest.database.dao.TestAttemptDao;
import com.gate.mocktest.database.dao.UserDao;
import com.gate.mocktest.database.dao.UserProfileDao;
import com.gate.mocktest.database.entities.Question;
import com.gate.mocktest.database.entities.TestAttempt;
import com.gate.mocktest.database.entities.User;
import com.gate.mocktest.database.entities.UserProfile;
import com.gate.mocktest.utils.SeedDataUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {Question.class, TestAttempt.class, UserProfile.class, User.class},
        version = 6,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract QuestionDao questionDao();
    public abstract TestAttemptDao testAttemptDao();
    public abstract UserProfileDao userProfileDao();
    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static AppDatabase getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    Context appContext = ctx.getApplicationContext();
                    INSTANCE = Room.databaseBuilder(appContext, AppDatabase.class, "gate.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(androidx.sqlite.db.SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    databaseWriteExecutor.execute(() ->
                                            SeedDataUtil.seedQuestions(appContext, INSTANCE.questionDao()));
                                }

                                @Override
                                public void onOpen(androidx.sqlite.db.SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    // Safety net for an older/empty installation. New installs are
                                    // populated from JSON in onCreate; existing installations with
                                    // an empty question table are populated automatically here.
                                    databaseWriteExecutor.execute(() -> {
                                        if (INSTANCE != null && INSTANCE.questionDao().getTotalCount() == 0) {
                                            SeedDataUtil.seedQuestions(appContext, INSTANCE.questionDao());
                                        }
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
