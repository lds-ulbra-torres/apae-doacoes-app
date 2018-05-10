package com.example.lucca.doeamor_apaetorres.Models.Categories;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lucca.doeamor_apaetorres.Database.DataBaseHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryModel {
    private SQLiteDatabase helper;
    private Context context;
    private ArrayList<Category> categories;

    public CategoryModel(Context context){
        this.helper = new DataBaseHelper(context).getWritableDatabase();
        this.context = context;
        this.categories = new ArrayList<>();
    }

    public void getCategories(final CategoryCallBack<ArrayList<Category>> categoryCallBack) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://apaetorres.org.br/doacoes/api/category";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getCategoriesFromJson(response);
                        categoryCallBack.onSuccess(categories);
                        clearCategoriesFromDataBase();
                        saveCategories(categories);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        categoryCallBack.onError();
                        Log.e("ERROR", "Error na response");
                    }
                }

        );
        queue.add(stringRequest);
    }
        public void clearCategoriesFromDataBase(){
            helper.rawQuery("DELETE FROM CATEGORIES", null);
        }

        public void getCategoriesFromDataBase(final CategoryCallBack<ArrayList<Category>> categoryCallBack){
            categories.clear();
            Cursor cursor = helper.rawQuery("select * from CATEGORIES", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    categories.add(getCategoriesByCursor(cursor));
                    cursor.moveToNext();
                }
            }

            categoryCallBack.onSuccess(categories);
        }

        private Category getCategoriesByCursor(Cursor cursor){
            Category category = new Category();

            category.setNameCat(cursor.getString(cursor.getColumnIndex("name_category")));
            category.setDescription(cursor.getString(cursor.getColumnIndex("description_category")));
            category.setPhotoCat(cursor.getString(cursor.getColumnIndex("photo_category")));

            return category;
        }

        private void saveCategories(ArrayList<Category> categories) {
            ContentValues values;

            for (Category c: categories){
                values = new ContentValues();

                values.put("name_category",c.getNameCat());
                values.put("description_category",c.getDescription());
                values.put("photo_category",c.getPhotoCat());

                helper.insert("CATEGORIES",null,values);
            }
        }

        private void getCategoriesFromJson(String json){
            categories.clear();
            Gson gson = new Gson();
            JSONObject object = null;
            JSONArray categoriesJson = null;

            try {
                object = new JSONObject(json);
                categoriesJson = object.getJSONArray("category");

            }  catch (JSONException e){
                e.printStackTrace();
            }
            if (categoriesJson == null){
                Toast.makeText(context,"Ocorreu um erro, por favor, feche e abra o aplicativo", Toast.LENGTH_SHORT).show();
            }   else {
                    for (int x=0; x < categoriesJson.length();x++){
                        try {
                            Category category = gson.fromJson(categoriesJson.get(x).toString(), Category.class);
                            categories.add(category);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
        }

    public  boolean verifyConnection() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();

    }


}
