package com.percimal.singaporeairlines;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.percimal.singaporeairlines.data.DaoMaster;
import com.percimal.singaporeairlines.data.DaoSession;

/**
 * Created by Peter on 5/10/2016.
 */

public class PercimalApplication extends Application {
    private DaoSession daoSession;
    private static PercimalApplication instance;

    public static PercimalApplication getInstance() {
        if(instance == null) {
            instance = new PercimalApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setupDatabase();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "db.sqlite", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
