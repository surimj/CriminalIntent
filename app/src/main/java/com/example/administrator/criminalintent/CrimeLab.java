package com.example.administrator.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.CrimeDbSchame.CrimeBaseHelper;
import database.CrimeDbSchame.CrimeDbSchema;
import database.CrimeDbSchame.CrimeDbSchema.CrimeTable;

/**
 * Created by Administrator on 2016/11/21.
 * 单例类，一个单例类仅允许创建一个实例
 * 应用在内存存在多久，单例就存在多久，从内存移除应用时，单例对象就不复存在
 * 单例适合于存储控制模型层对象
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private SQLiteDatabase mDatabase;
    private Context mContext;

   //context对象?
    //私有GET方法，其他类无法创建CrimeLAB对象
    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context){

        mContext = context.getApplicationContext();
        //打开数据库SQLiteDataBase
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        //创建一个空的List来保存Crime对象


    }

    public List<Crime> getCrimes() {
        return new ArrayList<Crime>();
    }

    //返回带有制定ID的Crime对象
    public Crime getCrime(UUID id){

        return null;
    }

    //增加Crime
    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeTable.NAME,null,values);

    }

    //将crime记录转换成ContentValues,用于写入和更新SQLite数据
    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID,crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE,crime.getTitle());
        values.put(CrimeTable.Cols.DATE,crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED,crime.isSolved()? 1:0);
        return values;

    }
}
