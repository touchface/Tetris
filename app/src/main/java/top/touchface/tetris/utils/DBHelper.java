package top.touchface.tetris.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import top.touchface.tetris.model.entry.Score;

public class DBHelper extends SQLiteOpenHelper {

    //类没有实例化,是不能用作父类构造器的参数,必须声明为静态

    private static final String name = "TETRISDB"; //数据库名称

    private static final int version = 1; //数据库版本

    public DBHelper(Context context) {
        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super(context, name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Score (s_id integer primary key autoincrement, s_name varchar(20), s_score integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE Score ADD xxx VARCHAR(12)"); //往表中增加一列
    }

    public Score getMaxScore() {
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) {
            return null;
        }
        Cursor cursor = db.rawQuery("select* from Score order by s_score desc", null);
        Score score = new Score();
        cursor.moveToNext();
        score.setS_id(cursor.getInt(0));
        score.setS_name(cursor.getString(1));
        score.setS_score(cursor.getInt(2));
        cursor.close();
        return score;
    }

    public long insertScore(Score score) {
        SQLiteDatabase db = getReadableDatabase();
        long i;
        if (db == null) {
            return 0;
        }
        ContentValues contentValues = new ContentValues();
        i = db.insert("Score", null, contentValues);
        contentValues.put("s_name", score.getS_name());
        contentValues.put("s_score", score.getS_score());
        return i;
    }

} 