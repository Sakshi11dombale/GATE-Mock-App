package com.gate.mocktest.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.gate.mocktest.database.entities.Question;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuestionDao_Impl implements QuestionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Question> __insertionAdapterOfQuestion;

  public QuestionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuestion = new EntityInsertionAdapter<Question>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `questions` (`id`,`questionText`,`optionA`,`optionB`,`optionC`,`optionD`,`correctAnswer`,`explanation`,`subject`,`topic`,`branch`,`difficulty`,`questionType`,`year`,`marks`,`isPYQ`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Question entity) {
        statement.bindLong(1, entity.id);
        if (entity.questionText == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.questionText);
        }
        if (entity.optionA == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.optionA);
        }
        if (entity.optionB == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.optionB);
        }
        if (entity.optionC == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.optionC);
        }
        if (entity.optionD == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.optionD);
        }
        if (entity.correctAnswer == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.correctAnswer);
        }
        if (entity.explanation == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.explanation);
        }
        if (entity.subject == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.subject);
        }
        if (entity.topic == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.topic);
        }
        if (entity.branch == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.branch);
        }
        if (entity.difficulty == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.difficulty);
        }
        if (entity.questionType == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.questionType);
        }
        statement.bindLong(14, entity.year);
        statement.bindDouble(15, entity.marks);
        final int _tmp = entity.isPYQ ? 1 : 0;
        statement.bindLong(16, _tmp);
      }
    };
  }

  @Override
  public void insertAll(final List<Question> q) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfQuestion.insert(q);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Question> getTechnicalQuestions(final String branch, final int limit) {
    final String _sql = "SELECT * FROM questions WHERE (branch=? OR (?='CSE' AND branch='CS') OR (?='ECE' AND branch='EC')) AND branch!='ALL' ORDER BY RANDOM() LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 2;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 3;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 4;
    _statement.bindLong(_argIndex, limit);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
      final int _cursorIndexOfOptionA = CursorUtil.getColumnIndexOrThrow(_cursor, "optionA");
      final int _cursorIndexOfOptionB = CursorUtil.getColumnIndexOrThrow(_cursor, "optionB");
      final int _cursorIndexOfOptionC = CursorUtil.getColumnIndexOrThrow(_cursor, "optionC");
      final int _cursorIndexOfOptionD = CursorUtil.getColumnIndexOrThrow(_cursor, "optionD");
      final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
      final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
      final int _cursorIndexOfBranch = CursorUtil.getColumnIndexOrThrow(_cursor, "branch");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
      final int _cursorIndexOfIsPYQ = CursorUtil.getColumnIndexOrThrow(_cursor, "isPYQ");
      final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Question _item;
        _item = new Question();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfQuestionText)) {
          _item.questionText = null;
        } else {
          _item.questionText = _cursor.getString(_cursorIndexOfQuestionText);
        }
        if (_cursor.isNull(_cursorIndexOfOptionA)) {
          _item.optionA = null;
        } else {
          _item.optionA = _cursor.getString(_cursorIndexOfOptionA);
        }
        if (_cursor.isNull(_cursorIndexOfOptionB)) {
          _item.optionB = null;
        } else {
          _item.optionB = _cursor.getString(_cursorIndexOfOptionB);
        }
        if (_cursor.isNull(_cursorIndexOfOptionC)) {
          _item.optionC = null;
        } else {
          _item.optionC = _cursor.getString(_cursorIndexOfOptionC);
        }
        if (_cursor.isNull(_cursorIndexOfOptionD)) {
          _item.optionD = null;
        } else {
          _item.optionD = _cursor.getString(_cursorIndexOfOptionD);
        }
        if (_cursor.isNull(_cursorIndexOfCorrectAnswer)) {
          _item.correctAnswer = null;
        } else {
          _item.correctAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _item.explanation = null;
        } else {
          _item.explanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        if (_cursor.isNull(_cursorIndexOfSubject)) {
          _item.subject = null;
        } else {
          _item.subject = _cursor.getString(_cursorIndexOfSubject);
        }
        if (_cursor.isNull(_cursorIndexOfTopic)) {
          _item.topic = null;
        } else {
          _item.topic = _cursor.getString(_cursorIndexOfTopic);
        }
        if (_cursor.isNull(_cursorIndexOfBranch)) {
          _item.branch = null;
        } else {
          _item.branch = _cursor.getString(_cursorIndexOfBranch);
        }
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _item.difficulty = null;
        } else {
          _item.difficulty = _cursor.getString(_cursorIndexOfDifficulty);
        }
        if (_cursor.isNull(_cursorIndexOfQuestionType)) {
          _item.questionType = null;
        } else {
          _item.questionType = _cursor.getString(_cursorIndexOfQuestionType);
        }
        _item.year = _cursor.getInt(_cursorIndexOfYear);
        _item.marks = _cursor.getFloat(_cursorIndexOfMarks);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPYQ);
        _item.isPYQ = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Question> getAptitudeQuestions(final int limit) {
    final String _sql = "SELECT * FROM questions WHERE branch='ALL' AND subject='General Aptitude' ORDER BY RANDOM() LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
      final int _cursorIndexOfOptionA = CursorUtil.getColumnIndexOrThrow(_cursor, "optionA");
      final int _cursorIndexOfOptionB = CursorUtil.getColumnIndexOrThrow(_cursor, "optionB");
      final int _cursorIndexOfOptionC = CursorUtil.getColumnIndexOrThrow(_cursor, "optionC");
      final int _cursorIndexOfOptionD = CursorUtil.getColumnIndexOrThrow(_cursor, "optionD");
      final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
      final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
      final int _cursorIndexOfBranch = CursorUtil.getColumnIndexOrThrow(_cursor, "branch");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
      final int _cursorIndexOfIsPYQ = CursorUtil.getColumnIndexOrThrow(_cursor, "isPYQ");
      final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Question _item;
        _item = new Question();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfQuestionText)) {
          _item.questionText = null;
        } else {
          _item.questionText = _cursor.getString(_cursorIndexOfQuestionText);
        }
        if (_cursor.isNull(_cursorIndexOfOptionA)) {
          _item.optionA = null;
        } else {
          _item.optionA = _cursor.getString(_cursorIndexOfOptionA);
        }
        if (_cursor.isNull(_cursorIndexOfOptionB)) {
          _item.optionB = null;
        } else {
          _item.optionB = _cursor.getString(_cursorIndexOfOptionB);
        }
        if (_cursor.isNull(_cursorIndexOfOptionC)) {
          _item.optionC = null;
        } else {
          _item.optionC = _cursor.getString(_cursorIndexOfOptionC);
        }
        if (_cursor.isNull(_cursorIndexOfOptionD)) {
          _item.optionD = null;
        } else {
          _item.optionD = _cursor.getString(_cursorIndexOfOptionD);
        }
        if (_cursor.isNull(_cursorIndexOfCorrectAnswer)) {
          _item.correctAnswer = null;
        } else {
          _item.correctAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _item.explanation = null;
        } else {
          _item.explanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        if (_cursor.isNull(_cursorIndexOfSubject)) {
          _item.subject = null;
        } else {
          _item.subject = _cursor.getString(_cursorIndexOfSubject);
        }
        if (_cursor.isNull(_cursorIndexOfTopic)) {
          _item.topic = null;
        } else {
          _item.topic = _cursor.getString(_cursorIndexOfTopic);
        }
        if (_cursor.isNull(_cursorIndexOfBranch)) {
          _item.branch = null;
        } else {
          _item.branch = _cursor.getString(_cursorIndexOfBranch);
        }
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _item.difficulty = null;
        } else {
          _item.difficulty = _cursor.getString(_cursorIndexOfDifficulty);
        }
        if (_cursor.isNull(_cursorIndexOfQuestionType)) {
          _item.questionType = null;
        } else {
          _item.questionType = _cursor.getString(_cursorIndexOfQuestionType);
        }
        _item.year = _cursor.getInt(_cursorIndexOfYear);
        _item.marks = _cursor.getFloat(_cursorIndexOfMarks);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPYQ);
        _item.isPYQ = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Question> getMathQuestions(final String branch, final int limit) {
    final String _sql = "SELECT * FROM questions WHERE subject='Engineering Mathematics' AND (branch=? OR (?='CSE' AND branch='CS') OR (?='ECE' AND branch='EC')) ORDER BY RANDOM() LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 2;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 3;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 4;
    _statement.bindLong(_argIndex, limit);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
      final int _cursorIndexOfOptionA = CursorUtil.getColumnIndexOrThrow(_cursor, "optionA");
      final int _cursorIndexOfOptionB = CursorUtil.getColumnIndexOrThrow(_cursor, "optionB");
      final int _cursorIndexOfOptionC = CursorUtil.getColumnIndexOrThrow(_cursor, "optionC");
      final int _cursorIndexOfOptionD = CursorUtil.getColumnIndexOrThrow(_cursor, "optionD");
      final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
      final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
      final int _cursorIndexOfBranch = CursorUtil.getColumnIndexOrThrow(_cursor, "branch");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
      final int _cursorIndexOfIsPYQ = CursorUtil.getColumnIndexOrThrow(_cursor, "isPYQ");
      final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Question _item;
        _item = new Question();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfQuestionText)) {
          _item.questionText = null;
        } else {
          _item.questionText = _cursor.getString(_cursorIndexOfQuestionText);
        }
        if (_cursor.isNull(_cursorIndexOfOptionA)) {
          _item.optionA = null;
        } else {
          _item.optionA = _cursor.getString(_cursorIndexOfOptionA);
        }
        if (_cursor.isNull(_cursorIndexOfOptionB)) {
          _item.optionB = null;
        } else {
          _item.optionB = _cursor.getString(_cursorIndexOfOptionB);
        }
        if (_cursor.isNull(_cursorIndexOfOptionC)) {
          _item.optionC = null;
        } else {
          _item.optionC = _cursor.getString(_cursorIndexOfOptionC);
        }
        if (_cursor.isNull(_cursorIndexOfOptionD)) {
          _item.optionD = null;
        } else {
          _item.optionD = _cursor.getString(_cursorIndexOfOptionD);
        }
        if (_cursor.isNull(_cursorIndexOfCorrectAnswer)) {
          _item.correctAnswer = null;
        } else {
          _item.correctAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _item.explanation = null;
        } else {
          _item.explanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        if (_cursor.isNull(_cursorIndexOfSubject)) {
          _item.subject = null;
        } else {
          _item.subject = _cursor.getString(_cursorIndexOfSubject);
        }
        if (_cursor.isNull(_cursorIndexOfTopic)) {
          _item.topic = null;
        } else {
          _item.topic = _cursor.getString(_cursorIndexOfTopic);
        }
        if (_cursor.isNull(_cursorIndexOfBranch)) {
          _item.branch = null;
        } else {
          _item.branch = _cursor.getString(_cursorIndexOfBranch);
        }
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _item.difficulty = null;
        } else {
          _item.difficulty = _cursor.getString(_cursorIndexOfDifficulty);
        }
        if (_cursor.isNull(_cursorIndexOfQuestionType)) {
          _item.questionType = null;
        } else {
          _item.questionType = _cursor.getString(_cursorIndexOfQuestionType);
        }
        _item.year = _cursor.getInt(_cursorIndexOfYear);
        _item.marks = _cursor.getFloat(_cursorIndexOfMarks);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPYQ);
        _item.isPYQ = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Question> getMockTestQuestions(final String branch, final int limit) {
    final String _sql = "SELECT * FROM questions WHERE (branch=? OR (?='CSE' AND branch='CS') OR (?='ECE' AND branch='EC')) AND branch!='ALL' ORDER BY RANDOM() LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 2;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 3;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 4;
    _statement.bindLong(_argIndex, limit);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
      final int _cursorIndexOfOptionA = CursorUtil.getColumnIndexOrThrow(_cursor, "optionA");
      final int _cursorIndexOfOptionB = CursorUtil.getColumnIndexOrThrow(_cursor, "optionB");
      final int _cursorIndexOfOptionC = CursorUtil.getColumnIndexOrThrow(_cursor, "optionC");
      final int _cursorIndexOfOptionD = CursorUtil.getColumnIndexOrThrow(_cursor, "optionD");
      final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
      final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
      final int _cursorIndexOfBranch = CursorUtil.getColumnIndexOrThrow(_cursor, "branch");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
      final int _cursorIndexOfIsPYQ = CursorUtil.getColumnIndexOrThrow(_cursor, "isPYQ");
      final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Question _item;
        _item = new Question();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfQuestionText)) {
          _item.questionText = null;
        } else {
          _item.questionText = _cursor.getString(_cursorIndexOfQuestionText);
        }
        if (_cursor.isNull(_cursorIndexOfOptionA)) {
          _item.optionA = null;
        } else {
          _item.optionA = _cursor.getString(_cursorIndexOfOptionA);
        }
        if (_cursor.isNull(_cursorIndexOfOptionB)) {
          _item.optionB = null;
        } else {
          _item.optionB = _cursor.getString(_cursorIndexOfOptionB);
        }
        if (_cursor.isNull(_cursorIndexOfOptionC)) {
          _item.optionC = null;
        } else {
          _item.optionC = _cursor.getString(_cursorIndexOfOptionC);
        }
        if (_cursor.isNull(_cursorIndexOfOptionD)) {
          _item.optionD = null;
        } else {
          _item.optionD = _cursor.getString(_cursorIndexOfOptionD);
        }
        if (_cursor.isNull(_cursorIndexOfCorrectAnswer)) {
          _item.correctAnswer = null;
        } else {
          _item.correctAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _item.explanation = null;
        } else {
          _item.explanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        if (_cursor.isNull(_cursorIndexOfSubject)) {
          _item.subject = null;
        } else {
          _item.subject = _cursor.getString(_cursorIndexOfSubject);
        }
        if (_cursor.isNull(_cursorIndexOfTopic)) {
          _item.topic = null;
        } else {
          _item.topic = _cursor.getString(_cursorIndexOfTopic);
        }
        if (_cursor.isNull(_cursorIndexOfBranch)) {
          _item.branch = null;
        } else {
          _item.branch = _cursor.getString(_cursorIndexOfBranch);
        }
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _item.difficulty = null;
        } else {
          _item.difficulty = _cursor.getString(_cursorIndexOfDifficulty);
        }
        if (_cursor.isNull(_cursorIndexOfQuestionType)) {
          _item.questionType = null;
        } else {
          _item.questionType = _cursor.getString(_cursorIndexOfQuestionType);
        }
        _item.year = _cursor.getInt(_cursorIndexOfYear);
        _item.marks = _cursor.getFloat(_cursorIndexOfMarks);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPYQ);
        _item.isPYQ = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Question> getTopicQuestions(final String subject, final String altSubject,
      final String branch, final int limit) {
    final String _sql = "SELECT * FROM questions WHERE (subject=? OR topic=? OR subject=? OR topic=? OR subject LIKE '%' || ? || '%' OR topic LIKE '%' || ? || '%') AND (branch=? OR (?='CSE' AND branch='CS') OR (?='ECE' AND branch='EC')) AND branch!='ALL' ORDER BY RANDOM() LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 10);
    int _argIndex = 1;
    if (subject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subject);
    }
    _argIndex = 2;
    if (subject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subject);
    }
    _argIndex = 3;
    if (altSubject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, altSubject);
    }
    _argIndex = 4;
    if (altSubject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, altSubject);
    }
    _argIndex = 5;
    if (subject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subject);
    }
    _argIndex = 6;
    if (subject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subject);
    }
    _argIndex = 7;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 8;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 9;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 10;
    _statement.bindLong(_argIndex, limit);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
      final int _cursorIndexOfOptionA = CursorUtil.getColumnIndexOrThrow(_cursor, "optionA");
      final int _cursorIndexOfOptionB = CursorUtil.getColumnIndexOrThrow(_cursor, "optionB");
      final int _cursorIndexOfOptionC = CursorUtil.getColumnIndexOrThrow(_cursor, "optionC");
      final int _cursorIndexOfOptionD = CursorUtil.getColumnIndexOrThrow(_cursor, "optionD");
      final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
      final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
      final int _cursorIndexOfBranch = CursorUtil.getColumnIndexOrThrow(_cursor, "branch");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
      final int _cursorIndexOfIsPYQ = CursorUtil.getColumnIndexOrThrow(_cursor, "isPYQ");
      final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Question _item;
        _item = new Question();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfQuestionText)) {
          _item.questionText = null;
        } else {
          _item.questionText = _cursor.getString(_cursorIndexOfQuestionText);
        }
        if (_cursor.isNull(_cursorIndexOfOptionA)) {
          _item.optionA = null;
        } else {
          _item.optionA = _cursor.getString(_cursorIndexOfOptionA);
        }
        if (_cursor.isNull(_cursorIndexOfOptionB)) {
          _item.optionB = null;
        } else {
          _item.optionB = _cursor.getString(_cursorIndexOfOptionB);
        }
        if (_cursor.isNull(_cursorIndexOfOptionC)) {
          _item.optionC = null;
        } else {
          _item.optionC = _cursor.getString(_cursorIndexOfOptionC);
        }
        if (_cursor.isNull(_cursorIndexOfOptionD)) {
          _item.optionD = null;
        } else {
          _item.optionD = _cursor.getString(_cursorIndexOfOptionD);
        }
        if (_cursor.isNull(_cursorIndexOfCorrectAnswer)) {
          _item.correctAnswer = null;
        } else {
          _item.correctAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _item.explanation = null;
        } else {
          _item.explanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        if (_cursor.isNull(_cursorIndexOfSubject)) {
          _item.subject = null;
        } else {
          _item.subject = _cursor.getString(_cursorIndexOfSubject);
        }
        if (_cursor.isNull(_cursorIndexOfTopic)) {
          _item.topic = null;
        } else {
          _item.topic = _cursor.getString(_cursorIndexOfTopic);
        }
        if (_cursor.isNull(_cursorIndexOfBranch)) {
          _item.branch = null;
        } else {
          _item.branch = _cursor.getString(_cursorIndexOfBranch);
        }
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _item.difficulty = null;
        } else {
          _item.difficulty = _cursor.getString(_cursorIndexOfDifficulty);
        }
        if (_cursor.isNull(_cursorIndexOfQuestionType)) {
          _item.questionType = null;
        } else {
          _item.questionType = _cursor.getString(_cursorIndexOfQuestionType);
        }
        _item.year = _cursor.getInt(_cursorIndexOfYear);
        _item.marks = _cursor.getFloat(_cursorIndexOfMarks);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPYQ);
        _item.isPYQ = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Question> getPYQByYear(final int year, final String branch) {
    final String _sql = "SELECT * FROM questions WHERE isPYQ=1 AND year=? AND (branch=? OR (?='CSE' AND branch='CS') OR (?='ECE' AND branch='EC') OR (branch='ALL' AND subject='General Aptitude')) ORDER BY subject";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, year);
    _argIndex = 2;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 3;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 4;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
      final int _cursorIndexOfOptionA = CursorUtil.getColumnIndexOrThrow(_cursor, "optionA");
      final int _cursorIndexOfOptionB = CursorUtil.getColumnIndexOrThrow(_cursor, "optionB");
      final int _cursorIndexOfOptionC = CursorUtil.getColumnIndexOrThrow(_cursor, "optionC");
      final int _cursorIndexOfOptionD = CursorUtil.getColumnIndexOrThrow(_cursor, "optionD");
      final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
      final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
      final int _cursorIndexOfBranch = CursorUtil.getColumnIndexOrThrow(_cursor, "branch");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
      final int _cursorIndexOfIsPYQ = CursorUtil.getColumnIndexOrThrow(_cursor, "isPYQ");
      final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Question _item;
        _item = new Question();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfQuestionText)) {
          _item.questionText = null;
        } else {
          _item.questionText = _cursor.getString(_cursorIndexOfQuestionText);
        }
        if (_cursor.isNull(_cursorIndexOfOptionA)) {
          _item.optionA = null;
        } else {
          _item.optionA = _cursor.getString(_cursorIndexOfOptionA);
        }
        if (_cursor.isNull(_cursorIndexOfOptionB)) {
          _item.optionB = null;
        } else {
          _item.optionB = _cursor.getString(_cursorIndexOfOptionB);
        }
        if (_cursor.isNull(_cursorIndexOfOptionC)) {
          _item.optionC = null;
        } else {
          _item.optionC = _cursor.getString(_cursorIndexOfOptionC);
        }
        if (_cursor.isNull(_cursorIndexOfOptionD)) {
          _item.optionD = null;
        } else {
          _item.optionD = _cursor.getString(_cursorIndexOfOptionD);
        }
        if (_cursor.isNull(_cursorIndexOfCorrectAnswer)) {
          _item.correctAnswer = null;
        } else {
          _item.correctAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _item.explanation = null;
        } else {
          _item.explanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        if (_cursor.isNull(_cursorIndexOfSubject)) {
          _item.subject = null;
        } else {
          _item.subject = _cursor.getString(_cursorIndexOfSubject);
        }
        if (_cursor.isNull(_cursorIndexOfTopic)) {
          _item.topic = null;
        } else {
          _item.topic = _cursor.getString(_cursorIndexOfTopic);
        }
        if (_cursor.isNull(_cursorIndexOfBranch)) {
          _item.branch = null;
        } else {
          _item.branch = _cursor.getString(_cursorIndexOfBranch);
        }
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _item.difficulty = null;
        } else {
          _item.difficulty = _cursor.getString(_cursorIndexOfDifficulty);
        }
        if (_cursor.isNull(_cursorIndexOfQuestionType)) {
          _item.questionType = null;
        } else {
          _item.questionType = _cursor.getString(_cursorIndexOfQuestionType);
        }
        _item.year = _cursor.getInt(_cursorIndexOfYear);
        _item.marks = _cursor.getFloat(_cursorIndexOfMarks);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPYQ);
        _item.isPYQ = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Question> getAllPYQ(final String branch) {
    final String _sql = "SELECT * FROM questions WHERE isPYQ=1 AND (branch=? OR (?='CSE' AND branch='CS') OR (?='ECE' AND branch='EC')) ORDER BY year DESC, subject";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 2;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 3;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfQuestionText = CursorUtil.getColumnIndexOrThrow(_cursor, "questionText");
      final int _cursorIndexOfOptionA = CursorUtil.getColumnIndexOrThrow(_cursor, "optionA");
      final int _cursorIndexOfOptionB = CursorUtil.getColumnIndexOrThrow(_cursor, "optionB");
      final int _cursorIndexOfOptionC = CursorUtil.getColumnIndexOrThrow(_cursor, "optionC");
      final int _cursorIndexOfOptionD = CursorUtil.getColumnIndexOrThrow(_cursor, "optionD");
      final int _cursorIndexOfCorrectAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswer");
      final int _cursorIndexOfExplanation = CursorUtil.getColumnIndexOrThrow(_cursor, "explanation");
      final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
      final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
      final int _cursorIndexOfBranch = CursorUtil.getColumnIndexOrThrow(_cursor, "branch");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfQuestionType = CursorUtil.getColumnIndexOrThrow(_cursor, "questionType");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
      final int _cursorIndexOfIsPYQ = CursorUtil.getColumnIndexOrThrow(_cursor, "isPYQ");
      final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Question _item;
        _item = new Question();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfQuestionText)) {
          _item.questionText = null;
        } else {
          _item.questionText = _cursor.getString(_cursorIndexOfQuestionText);
        }
        if (_cursor.isNull(_cursorIndexOfOptionA)) {
          _item.optionA = null;
        } else {
          _item.optionA = _cursor.getString(_cursorIndexOfOptionA);
        }
        if (_cursor.isNull(_cursorIndexOfOptionB)) {
          _item.optionB = null;
        } else {
          _item.optionB = _cursor.getString(_cursorIndexOfOptionB);
        }
        if (_cursor.isNull(_cursorIndexOfOptionC)) {
          _item.optionC = null;
        } else {
          _item.optionC = _cursor.getString(_cursorIndexOfOptionC);
        }
        if (_cursor.isNull(_cursorIndexOfOptionD)) {
          _item.optionD = null;
        } else {
          _item.optionD = _cursor.getString(_cursorIndexOfOptionD);
        }
        if (_cursor.isNull(_cursorIndexOfCorrectAnswer)) {
          _item.correctAnswer = null;
        } else {
          _item.correctAnswer = _cursor.getString(_cursorIndexOfCorrectAnswer);
        }
        if (_cursor.isNull(_cursorIndexOfExplanation)) {
          _item.explanation = null;
        } else {
          _item.explanation = _cursor.getString(_cursorIndexOfExplanation);
        }
        if (_cursor.isNull(_cursorIndexOfSubject)) {
          _item.subject = null;
        } else {
          _item.subject = _cursor.getString(_cursorIndexOfSubject);
        }
        if (_cursor.isNull(_cursorIndexOfTopic)) {
          _item.topic = null;
        } else {
          _item.topic = _cursor.getString(_cursorIndexOfTopic);
        }
        if (_cursor.isNull(_cursorIndexOfBranch)) {
          _item.branch = null;
        } else {
          _item.branch = _cursor.getString(_cursorIndexOfBranch);
        }
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _item.difficulty = null;
        } else {
          _item.difficulty = _cursor.getString(_cursorIndexOfDifficulty);
        }
        if (_cursor.isNull(_cursorIndexOfQuestionType)) {
          _item.questionType = null;
        } else {
          _item.questionType = _cursor.getString(_cursorIndexOfQuestionType);
        }
        _item.year = _cursor.getInt(_cursorIndexOfYear);
        _item.marks = _cursor.getFloat(_cursorIndexOfMarks);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPYQ);
        _item.isPYQ = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> getSubjectsByBranch(final String branch) {
    final String _sql = "SELECT DISTINCT subject FROM questions WHERE branch=? OR (?='CSE' AND branch='CS') OR (?='ECE' AND branch='EC') ORDER BY subject";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 2;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 3;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final String _item;
        if (_cursor.isNull(0)) {
          _item = null;
        } else {
          _item = _cursor.getString(0);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Integer> getPYQYears(final String branch) {
    final String _sql = "SELECT DISTINCT year FROM questions WHERE isPYQ=1 AND (branch=? OR (?='CSE' AND branch='CS') OR (?='ECE' AND branch='EC') OR (branch='ALL' AND subject='General Aptitude')) ORDER BY year DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 2;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 3;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<Integer> _result = new ArrayList<Integer>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Integer _item;
        if (_cursor.isNull(0)) {
          _item = null;
        } else {
          _item = _cursor.getInt(0);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getTopicQuestionCount(final String subject, final String altSubject,
      final String branch) {
    final String _sql = "SELECT COUNT(*) FROM questions WHERE (subject=? OR topic=? OR subject=? OR topic=? OR subject LIKE '%' || ? || '%' OR topic LIKE '%' || ? || '%') AND (branch=? OR (?='CSE' AND branch='CS') OR (?='ECE' AND branch='EC')) AND branch!='ALL'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 9);
    int _argIndex = 1;
    if (subject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subject);
    }
    _argIndex = 2;
    if (subject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subject);
    }
    _argIndex = 3;
    if (altSubject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, altSubject);
    }
    _argIndex = 4;
    if (altSubject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, altSubject);
    }
    _argIndex = 5;
    if (subject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subject);
    }
    _argIndex = 6;
    if (subject == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subject);
    }
    _argIndex = 7;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 8;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    _argIndex = 9;
    if (branch == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, branch);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getTotalCount() {
    final String _sql = "SELECT COUNT(*) FROM questions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
