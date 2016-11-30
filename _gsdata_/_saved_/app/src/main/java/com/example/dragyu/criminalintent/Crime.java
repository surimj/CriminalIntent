package com.example.dragyu.criminalintent;

import java.util.UUID;

/**
 * Created by dragyu on 2016/11/18.
 */

public class Crime {
    private UUID mId;
    private String mTitle;

    public Crime (){
        mId = UUID.randomUUID();
    }

    public UUID getmId() {
        return mId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }
}
