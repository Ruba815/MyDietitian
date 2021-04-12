package com.appterm.mydietician;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class RecipesDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "db_recipes.db";
    private static final int DATABASE_VERSION = 1;

    public RecipesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


}
