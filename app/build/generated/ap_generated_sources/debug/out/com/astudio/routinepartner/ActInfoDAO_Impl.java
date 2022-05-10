package com.astudio.routinepartner;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ActInfoDAO_Impl implements ActInfoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ActInfo> __insertionAdapterOfActInfo;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByActId;

  private final SharedSQLiteStatement __preparedStmtOfUpdateData;

  public ActInfoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfActInfo = new EntityInsertionAdapter<ActInfo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `actInfo` (`id`,`category`,`year`,`month`,`date`,`startHour`,`startMinute`,`endHour`,`endMinute`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ActInfo value) {
        stmt.bindLong(1, value.id);
        if (value.getCategory() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCategory());
        }
        stmt.bindLong(3, value.getYear());
        stmt.bindLong(4, value.getMonth());
        stmt.bindLong(5, value.getDate());
        stmt.bindLong(6, value.getStartHour());
        stmt.bindLong(7, value.getStartMinute());
        stmt.bindLong(8, value.getEndHour());
        stmt.bindLong(9, value.getEndMinute());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM actInfo";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByActId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM actInfo WHERE ? = actInfo.id";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateData = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE actInfo SET Year = ?, Month = ?, Date = ?, StartHour = ?, StartMinute = ?, EndHour = ?, EndMinute = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final ActInfo actInfo) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfActInfo.insert(actInfo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void deleteByActId(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByActId.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByActId.release(_stmt);
    }
  }

  @Override
  public void updateData(final int id, final int CYear, final int CMonth, final int CDate,
      final int CShour, final int CSminute, final int CEhour, final int CEminute) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateData.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, CYear);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, CMonth);
    _argIndex = 3;
    _stmt.bindLong(_argIndex, CDate);
    _argIndex = 4;
    _stmt.bindLong(_argIndex, CShour);
    _argIndex = 5;
    _stmt.bindLong(_argIndex, CSminute);
    _argIndex = 6;
    _stmt.bindLong(_argIndex, CEhour);
    _argIndex = 7;
    _stmt.bindLong(_argIndex, CEminute);
    _argIndex = 8;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateData.release(_stmt);
    }
  }

  @Override
  public ActInfo[] getAll() {
    final String _sql = "SELECT * FROM actInfo";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfStartHour = CursorUtil.getColumnIndexOrThrow(_cursor, "startHour");
      final int _cursorIndexOfStartMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "startMinute");
      final int _cursorIndexOfEndHour = CursorUtil.getColumnIndexOrThrow(_cursor, "endHour");
      final int _cursorIndexOfEndMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "endMinute");
      final ActInfo[] _result = new ActInfo[_cursor.getCount()];
      int _index = 0;
      while(_cursor.moveToNext()) {
        final ActInfo _item;
        _item = new ActInfo();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        _item.setYear(_tmpYear);
        final int _tmpMonth;
        _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
        _item.setMonth(_tmpMonth);
        final int _tmpDate;
        _tmpDate = _cursor.getInt(_cursorIndexOfDate);
        _item.setDate(_tmpDate);
        final int _tmpStartHour;
        _tmpStartHour = _cursor.getInt(_cursorIndexOfStartHour);
        _item.setStartHour(_tmpStartHour);
        final int _tmpStartMinute;
        _tmpStartMinute = _cursor.getInt(_cursorIndexOfStartMinute);
        _item.setStartMinute(_tmpStartMinute);
        final int _tmpEndHour;
        _tmpEndHour = _cursor.getInt(_cursorIndexOfEndHour);
        _item.setEndHour(_tmpEndHour);
        final int _tmpEndMinute;
        _tmpEndMinute = _cursor.getInt(_cursorIndexOfEndMinute);
        _item.setEndMinute(_tmpEndMinute);
        _result[_index] = _item;
        _index ++;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ActInfo[] getItemByDate(final int Syear, final int Smonth, final int Sdate,
      final int Eyear, final int Emonth, final int Edate) {
    final String _sql = "SELECT * FROM actInfo WHERE (((? * 10000 + ? * 100 + ?) <= (actInfo.year * 10000 + actInfo.month * 100 + actInfo.date)) AND ((? * 10000 + ? * 100 + ?) >= (actInfo.year * 10000 + actInfo.month * 100 + actInfo.date)))";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 6);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, Syear);
    _argIndex = 2;
    _statement.bindLong(_argIndex, Smonth);
    _argIndex = 3;
    _statement.bindLong(_argIndex, Sdate);
    _argIndex = 4;
    _statement.bindLong(_argIndex, Eyear);
    _argIndex = 5;
    _statement.bindLong(_argIndex, Emonth);
    _argIndex = 6;
    _statement.bindLong(_argIndex, Edate);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfStartHour = CursorUtil.getColumnIndexOrThrow(_cursor, "startHour");
      final int _cursorIndexOfStartMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "startMinute");
      final int _cursorIndexOfEndHour = CursorUtil.getColumnIndexOrThrow(_cursor, "endHour");
      final int _cursorIndexOfEndMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "endMinute");
      final ActInfo[] _result = new ActInfo[_cursor.getCount()];
      int _index = 0;
      while(_cursor.moveToNext()) {
        final ActInfo _item;
        _item = new ActInfo();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        _item.setYear(_tmpYear);
        final int _tmpMonth;
        _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
        _item.setMonth(_tmpMonth);
        final int _tmpDate;
        _tmpDate = _cursor.getInt(_cursorIndexOfDate);
        _item.setDate(_tmpDate);
        final int _tmpStartHour;
        _tmpStartHour = _cursor.getInt(_cursorIndexOfStartHour);
        _item.setStartHour(_tmpStartHour);
        final int _tmpStartMinute;
        _tmpStartMinute = _cursor.getInt(_cursorIndexOfStartMinute);
        _item.setStartMinute(_tmpStartMinute);
        final int _tmpEndHour;
        _tmpEndHour = _cursor.getInt(_cursorIndexOfEndHour);
        _item.setEndHour(_tmpEndHour);
        final int _tmpEndMinute;
        _tmpEndMinute = _cursor.getInt(_cursorIndexOfEndMinute);
        _item.setEndMinute(_tmpEndMinute);
        _result[_index] = _item;
        _index ++;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ActInfo[] getItemByCategory(final String category) {
    final String _sql = "SELECT * FROM actInfo WHERE ? = actInfo.category";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfStartHour = CursorUtil.getColumnIndexOrThrow(_cursor, "startHour");
      final int _cursorIndexOfStartMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "startMinute");
      final int _cursorIndexOfEndHour = CursorUtil.getColumnIndexOrThrow(_cursor, "endHour");
      final int _cursorIndexOfEndMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "endMinute");
      final ActInfo[] _result = new ActInfo[_cursor.getCount()];
      int _index = 0;
      while(_cursor.moveToNext()) {
        final ActInfo _item;
        _item = new ActInfo();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        _item.setYear(_tmpYear);
        final int _tmpMonth;
        _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
        _item.setMonth(_tmpMonth);
        final int _tmpDate;
        _tmpDate = _cursor.getInt(_cursorIndexOfDate);
        _item.setDate(_tmpDate);
        final int _tmpStartHour;
        _tmpStartHour = _cursor.getInt(_cursorIndexOfStartHour);
        _item.setStartHour(_tmpStartHour);
        final int _tmpStartMinute;
        _tmpStartMinute = _cursor.getInt(_cursorIndexOfStartMinute);
        _item.setStartMinute(_tmpStartMinute);
        final int _tmpEndHour;
        _tmpEndHour = _cursor.getInt(_cursorIndexOfEndHour);
        _item.setEndHour(_tmpEndHour);
        final int _tmpEndMinute;
        _tmpEndMinute = _cursor.getInt(_cursorIndexOfEndMinute);
        _item.setEndMinute(_tmpEndMinute);
        _result[_index] = _item;
        _index ++;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
