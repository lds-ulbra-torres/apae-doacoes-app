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
import com.example.lucca.doeamor_apaetorres.adapters.partner.PartnerAdapter;
import com.example.lucca.doeamor_apaetorres.adapters.category.viewholder.RecyclerItemViewHolder;
import com.example.lucca.doeamor_apaetorres.models.Category;
import com.example.lucca.doeamor_apaetorres.views.PartnersActivity;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private final Context mContext;
    ArrayList<Category> mCategories, filterList;
    CustomFilter filter;
    public RecyclerAdapter (Context context, ArrayList<Category> categories){
        mCategories = categories;
        mContext = context;
        this.filterList=categories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_categories, parent, false);
        return RecyclerItemViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
        final Category category = mCategories.get(position);
        String title = category.getNameCat();
        holder.setText(title);

        Glide
                .with(mContext)
                .load("http://apaetorres.org.br/doacoes" + category.getPhotoCat())
                .error(R.drawable.ic_image_off)
                .into(holder.getImgView());

        holder.setItemClickListener(new PartnerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(mContext, PartnersActivity.class);
                System.out.println(mCategories.get(pos).getId());
                intent.putExtra("id", String.valueOf(mCategories.get(pos).getId()));
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
