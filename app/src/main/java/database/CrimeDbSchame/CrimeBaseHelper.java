package database.CrimeDbSchame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.CrimeDbSchame.CrimeDbSchema.CrimeTable;

/**
 * Created by Administrator on 2016/12/10.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //拼接sql语句的时候引用变量的两端要加上空格
        sqLiteDatabase.execSQL("create table " + CrimeTable.NAME + "("
                + " _id integer primary key autoincrement, " + CrimeTable.Cols.UUID + ", "
                + CrimeTable.Cols.TITLE + ", " + CrimeTable.Cols.DATE + ", "
                + CrimeTable.Cols.SOLVED + " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
