package com.example.lucca.doeamor_apaetorres.callbacks;

import com.example.lucca.doeamor_apaetorres.models.Category;

import java.util.ArrayList;

public interface CategoryCallBack<S>{

    void onSuccess(ArrayList<Category> category);

    void onError();
}
