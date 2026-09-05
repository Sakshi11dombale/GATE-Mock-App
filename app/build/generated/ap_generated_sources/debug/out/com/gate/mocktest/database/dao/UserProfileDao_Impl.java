package com.gate.mocktest.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.gate.mocktest.database.entities.UserProfile;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserProfileDao_Impl implements UserProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserProfile> __insertionAdapterOfUserProfile;

  private final EntityDeletionOrUpdateAdapter<UserProfile> __updateAdapterOfUserProfile;

  private final SharedSQLiteStatement __preparedStmtOfIncrementTestCount;

  private final SharedSQLiteStatement __preparedStmtOfAddQuestionsSolved;

  public UserProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserProfile = new EntityInsertionAdapter<UserProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_profile` (`id`,`name`,`email`,`branch`,`avatarInitials`,`targetYear`,`totalTestsTaken`,`totalQuestionsSolved`,`currentStreak`,`maxStreak`,`globalRank`,`totalStudyTimeMillis`,`lastActiveDate`,`overallAccuracy`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final UserProfile entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.email == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.email);
        }
        if (entity.branch == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.branch);
        }
        if (entity.avatarInitials == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.avatarInitials);
        }
        statement.bindLong(6, entity.targetYear);
        statement.bindLong(7, entity.totalTestsTaken);
        statement.bindLong(8, entity.totalQuestionsSolved);
        statement.bindLong(9, entity.currentStreak);
        statement.bindLong(10, entity.maxStreak);
        statement.bindLong(11, entity.globalRank);
        statement.bindLong(12, entity.totalStudyTimeMillis);
        statement.bindLong(13, entity.lastActiveDate);
        statement.bindDouble(14, entity.overallAccuracy);
      }
    };
    this.__updateAdapterOfUserProfile = new EntityDeletionOrUpdateAdapter<UserProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_profile` SET `id` = ?,`name` = ?,`email` = ?,`branch` = ?,`avatarInitials` = ?,`targetYear` = ?,`totalTestsTaken` = ?,`totalQuestionsSolved` = ?,`currentStreak` = ?,`maxStreak` = ?,`globalRank` = ?,`totalStudyTimeMillis` = ?,`lastActiveDate` = ?,`overallAccuracy` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final UserProfile entity) {
        statement.bindLong(1, entity.id);
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.email == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.email);
        }
        if (entity.branch == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.branch);
        }
        if (entity.avatarInitials == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.avatarInitials);
        }
        statement.bindLong(6, entity.targetYear);
        statement.bindLong(7, entity.totalTestsTaken);
        statement.bindLong(8, entity.totalQuestionsSolved);
        statement.bindLong(9, entity.currentStreak);
        statement.bindLong(10, entity.maxStreak);
        statement.bindLong(11, entity.globalRank);
        statement.bindLong(12, entity.totalStudyTimeMillis);
        statement.bindLong(13, entity.lastActiveDate);
        statement.bindDouble(14, entity.overallAccuracy);
        statement.bindLong(15, entity.id);
      }
    };
    this.__preparedStmtOfIncrementTestCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_profile SET totalTestsTaken=totalTestsTaken+1 WHERE id=1";
        return _query;
      }
    };
    this.__preparedStmtOfAddQuestionsSolved = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_profile SET totalQuestionsSolved=totalQuestionsSolved+? WHERE id=1";
        return _query;
      }
    };
  }

  @Override
  public void insert(final UserProfile p) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUserProfile.insert(p);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final UserProfile p) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUserProfile.handle(p);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void incrementTestCount() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementTestCount.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfIncrementTestCount.release(_stmt);
    }
  }

  @Override
  public void addQuestionsSolved(final int c) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfAddQuestionsSolved.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, c);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfAddQuestionsSolved.release(_stmt);
    }
  }

  @Override
  public LiveData<UserProfile> getProfile() {
    final String _sql = "SELECT * FROM user_profile WHERE id=1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"user_profile"}, false, new Callable<UserProfile>() {
      @Override
      @Nullable
      public UserProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfBranch = CursorUtil.getColumnIndexOrThrow(_cursor, "branch");
          final int _cursorIndexOfAvatarInitials = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarInitials");
          final int _cursorIndexOfTargetYear = CursorUtil.getColumnIndexOrThrow(_cursor, "targetYear");
          final int _cursorIndexOfTotalTestsTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTestsTaken");
          final int _cursorIndexOfTotalQuestionsSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestionsSolved");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfMaxStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "maxStreak");
          final int _cursorIndexOfGlobalRank = CursorUtil.getColumnIndexOrThrow(_cursor, "globalRank");
          final int _cursorIndexOfTotalStudyTimeMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStudyTimeMillis");
          final int _cursorIndexOfLastActiveDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActiveDate");
          final int _cursorIndexOfOverallAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "overallAccuracy");
          final UserProfile _result;
          if (_cursor.moveToFirst()) {
            _result = new UserProfile();
            _result.id = _cursor.getInt(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfName)) {
              _result.name = null;
            } else {
              _result.name = _cursor.getString(_cursorIndexOfName);
            }
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _result.email = null;
            } else {
              _result.email = _cursor.getString(_cursorIndexOfEmail);
            }
            if (_cursor.isNull(_cursorIndexOfBranch)) {
              _result.branch = null;
            } else {
              _result.branch = _cursor.getString(_cursorIndexOfBranch);
            }
            if (_cursor.isNull(_cursorIndexOfAvatarInitials)) {
              _result.avatarInitials = null;
            } else {
              _result.avatarInitials = _cursor.getString(_cursorIndexOfAvatarInitials);
            }
            _result.targetYear = _cursor.getInt(_cursorIndexOfTargetYear);
            _result.totalTestsTaken = _cursor.getInt(_cursorIndexOfTotalTestsTaken);
            _result.totalQuestionsSolved = _cursor.getInt(_cursorIndexOfTotalQuestionsSolved);
            _result.currentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            _result.maxStreak = _cursor.getInt(_cursorIndexOfMaxStreak);
            _result.globalRank = _cursor.getInt(_cursorIndexOfGlobalRank);
            _result.totalStudyTimeMillis = _cursor.getLong(_cursorIndexOfTotalStudyTimeMillis);
            _result.lastActiveDate = _cursor.getLong(_cursorIndexOfLastActiveDate);
            _result.overallAccuracy = _cursor.getFloat(_cursorIndexOfOverallAccuracy);
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
