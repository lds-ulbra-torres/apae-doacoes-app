package appapae.apae.app.apae.adapters.category;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import com.bumptech.glide.Glide;
import appapae.apae.app.apae.R;
import appapae.apae.app.apae.adapters.ItemClickListener;
import appapae.apae.app.apae.adapters.category.viewholder.RecyclerCategoryViewHolder;
import appapae.apae.app.apae.models.Category;
import appapae.apae.app.apae.views.PartnersActivity;

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
                .load("http://doacoes.apaetorres.org.br" + category.getPhotoCat())
                .error(R.color.colorError)
                .into(holder.getImgView());

        holder.setItemClickListener(new ItemClickListener(){
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(mContext, PartnersActivity.class);
                PartnersActivity.name = String.valueOf(mCategories.get(pos).getNameCat());
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
