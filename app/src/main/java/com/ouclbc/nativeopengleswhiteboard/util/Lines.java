package com.ouclbc.nativeopengleswhiteboard.util;

import android.view.MotionEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by libaocheng on 2017/5/17.
 */

public class Lines {
    private static final Lines ourInstance = new Lines();

    List<PointArray> mLinePointLists = new CopyOnWriteArrayList<PointArray>();
    PointArray mPointArray = new PointArray();
    public  static Lines getInstance() {
        return ourInstance;
    }

    private Lines() {
    }

    public List<PointArray> getLinePointLists() {
        return mLinePointLists;
    }
    public PointArray getLine() {
        return mPointArray;
    }

    public void touchProcess(MotionEvent event) {
        switch (event.getAction()& MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                mPointArray = new PointArray();
                Lines.getInstance().getLinePointLists().add(mPointArray);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                //Lines.getInstance().getLinePointLists().add(mPointArray);
                break;
            case MotionEvent.ACTION_POINTER_UP:

                break;
        }
    }
}
