package com.example.administrator.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by dragyu on 2016/11/23.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
