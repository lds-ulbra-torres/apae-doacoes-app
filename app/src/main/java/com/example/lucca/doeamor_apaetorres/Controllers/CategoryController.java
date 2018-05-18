package com.example.lucca.doeamor_apaetorres.controllers;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.lucca.doeamor_apaetorres.callbacks.CategoryCallBack;
import com.example.lucca.doeamor_apaetorres.dao.CategoryDao;
import com.example.lucca.doeamor_apaetorres.models.Category;

import java.util.ArrayList;

public class CategoryController {

    private CategoryDao categoryDao;
    private Context context;
    public CategoryController(Context context){
           categoryDao = new CategoryDao(context);
           this.context = context;
    }

    public void getCategories(CategoryCallBack<ArrayList<Category>> categoryCallBack){

    }

    private boolean verifyConnection() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conectivtyManager != null;
        if ( conectivtyManager.getActiveNetworkInfo() != null )
            if ( conectivtyManager.getActiveNetworkInfo().isAvailable() )
                if ( conectivtyManager.getActiveNetworkInfo().isConnected() ) return true;
        return false;

    }
}
