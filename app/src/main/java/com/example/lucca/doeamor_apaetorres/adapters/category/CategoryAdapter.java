package com.example.lucca.doeamor_apaetorres.adapters.category;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.example.lucca.doeamor_apaetorres.R;
import com.example.lucca.doeamor_apaetorres.adapters.ItemClickListener;
import com.example.lucca.doeamor_apaetorres.adapters.category.viewholder.RecyclerCategoryViewHolder;
import com.example.lucca.doeamor_apaetorres.models.Category;
import com.example.lucca.doeamor_apaetorres.views.PartnersActivity;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private final Context mContext;
    ArrayList<Category> mCategories, filterList;
    CustomFilter filter;
    public CategoryAdapter(Context context, ArrayList<Category> categories){
        mCategories = categories;
        mContext = context;
        this.filterList=categories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_categories, parent, false);
        return RecyclerCategoryViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final RecyclerCategoryViewHolder holder = (RecyclerCategoryViewHolder) viewHolder;
        final Category category = mCategories.get(position);
        holder.setText(category.getNameCat());

        Glide
                .with(mContext)
                .load("http://apaetorres.org.br/doacoes" + category.getPhotoCat())
                .error(R.drawable.ic_image_off)
                .into(holder.getImgView());

        holder.setItemClickListener(new ItemClickListener(){
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(mContext, PartnersActivity.class);
                PartnersActivity.name = String.valueOf(mCategories.get(pos).getNameCat());
                System.out.println(mCategories.get(pos).getId());
                intent.putExtra("id", String.valueOf(mCategories.get(pos).getId()));
                intent.putExtra("name",mCategories.get(pos).getNameCat());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories == null ? 0 : mCategories.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }
        return filter;
    }
}
