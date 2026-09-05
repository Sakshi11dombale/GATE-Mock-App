package com.gate.mocktest.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.gate.mocktest.database.entities.TestAttempt;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TestAttemptDao_Impl implements TestAttemptDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TestAttempt> __insertionAdapterOfTestAttempt;

  public TestAttemptDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTestAttempt = new EntityInsertionAdapter<TestAttempt>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `test_attempts` (`id`,`testName`,`testType`,`branch`,`subject`,`rank`,`totalQuestions`,`attempted`,`correct`,`wrong`,`notAttempted`,`userId`,`score`,`maxScore`,`accuracy`,`timeTakenMillis`,`attemptDate`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TestAttempt entity) {
        statement.bindLong(1, entity.id);
        if (entity.testName == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.testName);
        }
        if (entity.testType == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.testType);
        }
        if (entity.branch == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.branch);
        }
        if (entity.subject == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.subject);
        }
        if (entity.rank == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.rank);
        }
        statement.bindLong(7, entity.totalQuestions);
        statement.bindLong(8, entity.attempted);
        statement.bindLong(9, entity.correct);
        statement.bindLong(10, entity.wrong);
        statement.bindLong(11, entity.notAttempted);
        statement.bindLong(12, entity.userId);
        statement.bindDouble(13, entity.score);
        statement.bindDouble(14, entity.maxScore);
        statement.bindDouble(15, entity.accuracy);
        statement.bindLong(16, entity.timeTakenMillis);
        statement.bindLong(17, entity.attemptDate);
      }
    };
  }

  @Override
  public long insert(final TestAttempt a) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTestAttempt.insertAndReturnId(a);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<TestAttempt>> getUserAttempts(final int uid) {
    final String _sql = "SELECT * FROM test_attempts WHERE userId=? ORDER BY attemptDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, uid);
    return __db.getInvalidationTracker().createLiveData(new String[] {"test_attempts"}, false, new Callable<List<TestAttempt>>() {
      @Override
      @Nullable
      public List<TestAttempt> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTestName = CursorUtil.getColumnIndexOrThrow(_cursor, "testName");
          final int _cursorIndexOfTestType = CursorUtil.getColumnIndexOrThrow(_cursor, "testType");
          final int _cursorIndexOfBranch = CursorUtil.getColumnIndexOrThrow(_cursor, "branch");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfRank = CursorUtil.getColumnIndexOrThrow(_cursor, "rank");
          final int _cursorIndexOfTotalQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestions");
          final int _cursorIndexOfAttempted = CursorUtil.getColumnIndexOrThrow(_cursor, "attempted");
          final int _cursorIndexOfCorrect = CursorUtil.getColumnIndexOrThrow(_cursor, "correct");
          final int _cursorIndexOfWrong = CursorUtil.getColumnIndexOrThrow(_cursor, "wrong");
          final int _cursorIndexOfNotAttempted = CursorUtil.getColumnIndexOrThrow(_cursor, "notAttempted");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfMaxScore = CursorUtil.getColumnIndexOrThrow(_cursor, "maxScore");
          final int _cursorIndexOfAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracy");
          final int _cursorIndexOfTimeTakenMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "timeTakenMillis");
          final int _cursorIndexOfAttemptDate = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptDate");
          final List<TestAttempt> _result = new ArrayList<TestAttempt>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TestAttempt _item;
            _item = new TestAttempt();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfTestName)) {
              _item.testName = null;
            } else {
              _item.testName = _cursor.getString(_cursorIndexOfTestName);
            }
            if (_cursor.isNull(_cursorIndexOfTestType)) {
              _item.testType = null;
            } else {
              _item.testType = _cursor.getString(_cursorIndexOfTestType);
            }
            if (_cursor.isNull(_cursorIndexOfBranch)) {
              _item.branch = null;
            } else {
              _item.branch = _cursor.getString(_cursorIndexOfBranch);
            }
            if (_cursor.isNull(_cursorIndexOfSubject)) {
              _item.subject = null;
            } else {
              _item.subject = _cursor.getString(_cursorIndexOfSubject);
            }
            if (_cursor.isNull(_cursorIndexOfRank)) {
              _item.rank = null;
            } else {
              _item.rank = _cursor.getString(_cursorIndexOfRank);
            }
            _item.totalQuestions = _cursor.getInt(_cursorIndexOfTotalQuestions);
            _item.attempted = _cursor.getInt(_cursorIndexOfAttempted);
            _item.correct = _cursor.getInt(_cursorIndexOfCorrect);
            _item.wrong = _cursor.getInt(_cursorIndexOfWrong);
            _item.notAttempted = _cursor.getInt(_cursorIndexOfNotAttempted);
            _item.userId = _cursor.getInt(_cursorIndexOfUserId);
            _item.score = _cursor.getFloat(_cursorIndexOfScore);
            _item.maxScore = _cursor.getFloat(_cursorIndexOfMaxScore);
            _item.accuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            _item.timeTakenMillis = _cursor.getLong(_cursorIndexOfTimeTakenMillis);
            _item.attemptDate = _cursor.getLong(_cursorIndexOfAttemptDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<TestAttempt>> getRecentAttempts(final int uid) {
    final String _sql = "SELECT * FROM test_attempts WHERE userId=? ORDER BY attemptDate DESC LIMIT 5";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, uid);
    return __db.getInvalidationTracker().createLiveData(new String[] {"test_attempts"}, false, new Callable<List<TestAttempt>>() {
      @Override
      @Nullable
      public List<TestAttempt> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTestName = CursorUtil.getColumnIndexOrThrow(_cursor, "testName");
          final int _cursorIndexOfTestType = CursorUtil.getColumnIndexOrThrow(_cursor, "testType");
          final int _cursorIndexOfBranch = CursorUtil.getColumnIndexOrThrow(_cursor, "branch");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfRank = CursorUtil.getColumnIndexOrThrow(_cursor, "rank");
          final int _cursorIndexOfTotalQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestions");
          final int _cursorIndexOfAttempted = CursorUtil.getColumnIndexOrThrow(_cursor, "attempted");
          final int _cursorIndexOfCorrect = CursorUtil.getColumnIndexOrThrow(_cursor, "correct");
          final int _cursorIndexOfWrong = CursorUtil.getColumnIndexOrThrow(_cursor, "wrong");
          final int _cursorIndexOfNotAttempted = CursorUtil.getColumnIndexOrThrow(_cursor, "notAttempted");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfMaxScore = CursorUtil.getColumnIndexOrThrow(_cursor, "maxScore");
          final int _cursorIndexOfAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracy");
          final int _cursorIndexOfTimeTakenMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "timeTakenMillis");
          final int _cursorIndexOfAttemptDate = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptDate");
          final List<TestAttempt> _result = new ArrayList<TestAttempt>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TestAttempt _item;
            _item = new TestAttempt();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfTestName)) {
              _item.testName = null;
            } else {
              _item.testName = _cursor.getString(_cursorIndexOfTestName);
            }
            if (_cursor.isNull(_cursorIndexOfTestType)) {
              _item.testType = null;
            } else {
              _item.testType = _cursor.getString(_cursorIndexOfTestType);
            }
            if (_cursor.isNull(_cursorIndexOfBranch)) {
              _item.branch = null;
            } else {
              _item.branch = _cursor.getString(_cursorIndexOfBranch);
            }
            if (_cursor.isNull(_cursorIndexOfSubject)) {
              _item.subject = null;
            } else {
              _item.subject = _cursor.getString(_cursorIndexOfSubject);
            }
            if (_cursor.isNull(_cursorIndexOfRank)) {
              _item.rank = null;
            } else {
              _item.rank = _cursor.getString(_cursorIndexOfRank);
            }
            _item.totalQuestions = _cursor.getInt(_cursorIndexOfTotalQuestions);
            _item.attempted = _cursor.getInt(_cursorIndexOfAttempted);
            _item.correct = _cursor.getInt(_cursorIndexOfCorrect);
            _item.wrong = _cursor.getInt(_cursorIndexOfWrong);
            _item.notAttempted = _cursor.getInt(_cursorIndexOfNotAttempted);
            _item.userId = _cursor.getInt(_cursorIndexOfUserId);
            _item.score = _cursor.getFloat(_cursorIndexOfScore);
            _item.maxScore = _cursor.getFloat(_cursorIndexOfMaxScore);
            _item.accuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            _item.timeTakenMillis = _cursor.getLong(_cursorIndexOfTimeTakenMillis);
            _item.attemptDate = _cursor.getLong(_cursorIndexOfAttemptDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Float> getOverallAccuracy(final int uid) {
    final String _sql = "SELECT AVG(accuracy) FROM test_attempts WHERE userId=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, uid);
    return __db.getInvalidationTracker().createLiveData(new String[] {"test_attempts"}, false, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getTotalTestCount(final int uid) {
    final String _sql = "SELECT COUNT(*) FROM test_attempts WHERE userId=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, uid);
    return __db.getInvalidationTracker().createLiveData(new String[] {"test_attempts"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Long> getTotalStudyTime(final int uid) {
    final String _sql = "SELECT SUM(timeTakenMillis) FROM test_attempts WHERE userId=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, uid);
    return __db.getInvalidationTracker().createLiveData(new String[] {"test_attempts"}, false, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
