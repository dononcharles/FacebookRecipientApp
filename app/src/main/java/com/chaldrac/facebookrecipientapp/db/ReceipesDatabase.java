package com.chaldrac.facebookrecipientapp.db;

import com.dbflow5.annotation.Database;
import com.dbflow5.config.DBFlowDatabase;

@Database(version = ReceipesDatabase.VERSION)
public abstract class ReceipesDatabase extends DBFlowDatabase {
    public static final  int VERSION = 1;
   // public static final  String NAME = "Recipes";
}
