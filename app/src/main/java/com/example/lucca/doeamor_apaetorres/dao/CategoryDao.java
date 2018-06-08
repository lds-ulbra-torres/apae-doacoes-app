package com.example.lucca.doeamor_apaetorres.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lucca.doeamor_apaetorres.callbacks.CategoryCallBack;
import com.example.lucca.doeamor_apaetorres.database.DataBaseHelper;
import com.example.lucca.doeamor_apaetorres.models.Category;

import java.util.ArrayList;

public class CategoryDao {
    private SQLiteDatabase helper;
    private Context context;
    private ArrayList<Category> categories;

    public CategoryDao(Context context){
        this.helper = new DataBaseHelper(context).getWritableDatabase();
        this.context = context;
        this.categories = new ArrayList<>();
    }

    public void sync(ArrayList<Category> categories) {
        for (Category category: categories){

            if ( exists(category) ){
                updateCat(category);
            }   else {
                insertCat(category);
            }

        }
    }

    private void updateCat(Category category) {
        ContentValues data = getDataCategories(category);
        String[] params = {String.valueOf(category.getId())};
        helper.update("CATEGORIES",data,"_id_category = ?", params);
    }

    private boolean exists(Category category){
        String exists = "SELECT _id_category FROM CATEGORIES WHERE _id_category = ? LIMIT 1";
        Cursor cursor = helper.rawQuery(exists, new String[]{String.valueOf(category.getId())});
        int count = cursor.getCount();
        return count > 0;
    }

    private void insertCat(Category category) {
        ContentValues data = getDataCategories(category);
        helper.insert("CATEGORIES", null,data);
    }

    private ContentValues getDataCategories(Category category) {
        ContentValues data = new ContentValues();
        data.put("_id_category",category.getId());
        data.put("name_category",category.getNameCat());
        data.put("description_category",category.getDescription_category());
        data.put("photo_category",category.getPhotoCat());
        return data;
    }

    public ArrayList<Category> getCategoriesDataBase(){
        String sql = "SELECT * FROM CATEGORIES ORDER BY name_category";
        Cursor c = helper.rawQuery(sql,null);

        while(c.moveToNext()){
            Category category = new Category();
            category.setId_category(c.getLong(c.getColumnIndex(String.valueOf("_id_category"))));
            category.setNameCat(c.getString(c.getColumnIndex("name_category")));
            category.setDescription_category((c.getString(c.getColumnIndex("description_category"))));
            category.setPhotoCat(c.getString(c.getColumnIndex("photo_category")));
            categories.add(category);
        }
        c.close();
        return categories;
    }
}
