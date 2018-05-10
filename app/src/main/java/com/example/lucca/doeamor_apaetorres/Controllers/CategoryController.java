package com.example.lucca.doeamor_apaetorres.Controllers;

import android.content.Context;

import com.example.lucca.doeamor_apaetorres.Models.Categories.Category;
import com.example.lucca.doeamor_apaetorres.Models.Categories.CategoryModel;
import com.example.lucca.doeamor_apaetorres.Models.Categories.CategoryCallBack;

import java.util.ArrayList;

public class CategoryController {

    private CategoryModel categoryModel;

    public CategoryController(Context context){
           categoryModel = new CategoryModel(context);
    }

    public void getCategories(CategoryCallBack<ArrayList<Category>> categoryCallBack){
        if(categoryModel.verifyConnection()){
            categoryModel.getCategories(categoryCallBack);
        }   else {
            categoryModel.getCategoriesFromDataBase(categoryCallBack);
        }
    }
}
