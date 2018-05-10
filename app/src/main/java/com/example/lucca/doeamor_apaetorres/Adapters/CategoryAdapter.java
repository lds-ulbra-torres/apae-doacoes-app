package com.example.lucca.doeamor_apaetorres.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lucca.doeamor_apaetorres.Models.Categories.Category;
import com.example.lucca.doeamor_apaetorres.R;

import java.util.ArrayList;

/**
 * Created by lucca on 09/04/2018.
 */

public class CategoryAdapter extends ArrayAdapter <Category> {
    private TextView tvTitulo;
    private ImageView imgBackground;

    public CategoryAdapter(@NonNull Context context, @NonNull ArrayList<Category> category) {
        super(context, 0, category);
    }

    @Nullable
    @Override
    public Category getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View gridView = convertView;

        if(gridView == null){
            gridView = LayoutInflater.from(getContext()).inflate(R.layout.activity_categories,parent,false);
        }
        Category category = getItem(position);

        tvTitulo = gridView.findViewById(R.id.tvTitulo);
        imgBackground = gridView.findViewById(R.id.imgBackground);
        String name = category.getNameCat();
        tvTitulo.setText(name);
        loadPhotoCategories(category);
        return gridView;
    }

    private void loadPhotoCategories(Category category) {
        Glide
                .with(getContext())
                .load("http://apaetorres.org.br/doacoes" + category.getPhotoCat())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgBackground);
    }
}
