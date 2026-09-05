package com.gate.mocktest.utils;

import android.content.Context;

import com.gate.mocktest.database.dao.QuestionDao;

/**
 * Backward-compatible entry point. The question bank is now maintained as JSON
 * files under app/src/main/assets/questions/.
 */
public final class SeedDataUtil {
    private SeedDataUtil() {}

    public static void seedQuestions(Context context, QuestionDao dao) {
        JsonQuestionLoader.seedFromAssets(context, dao);
    }
}
