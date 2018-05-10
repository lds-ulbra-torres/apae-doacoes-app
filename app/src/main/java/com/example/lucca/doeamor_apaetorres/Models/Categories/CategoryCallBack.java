package com.example.lucca.doeamor_apaetorres.Models.Categories;

import java.util.ArrayList;

public interface CategoryCallBack<S>{

    void onSuccess(ArrayList<Category> category);

    void onError();
}
