package com.example.administrator.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/11/21.
 * 单例类，一个单例类仅允许创建一个实例
 * 应用在内存存在多久，单例就存在多久，从内存移除应用时，单例对象就不复存在
 * 单例适合于存储控制模型层对象
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

   //context对象?
    //私有GET方法，其他类无法创建CrimeLAB对象
    private static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context){
        //创建一个空的List来保存Crime对象
        mCrimes = new ArrayList<>();
        for (int i =0;i <100; i++){

        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    //返回带有制定ID的Crime对象
    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
