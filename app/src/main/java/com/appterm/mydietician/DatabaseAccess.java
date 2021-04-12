package com.appterm.mydietician;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new RecipesDatabase(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_categories", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = new Category();
            category.setCategory_id(cursor.getInt(cursor.getColumnIndex("category_id")));
            category.setCategory_name(cursor.getString(cursor.getColumnIndex("category_name")));


            categories.add(category);
            cursor.moveToNext();
        }
        cursor.close();
        return categories;
    }

    public ArrayList<Recipe> getRecipes(int id) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_recipes where category_id = " + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe recipe = new Recipe();
            recipe.setRecipe_name(cursor.getString(cursor.getColumnIndex("recipe_name")));
            Log.e("Name====>",cursor.getString(cursor.getColumnIndex("recipe_name")));
            recipe.setIngredients(cursor.getString(cursor.getColumnIndex("ingredients")));
            recipe.setSteps(cursor.getString(cursor.getColumnIndex("steps")));
            recipe.setImage(cursor.getString(cursor.getColumnIndex("image")));
            recipes.add(recipe);
            cursor.moveToNext();
        }
        cursor.close();
        return recipes;
    }
}