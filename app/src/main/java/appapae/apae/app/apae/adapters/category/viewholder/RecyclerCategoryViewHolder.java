package appapae.apae.app.apae.adapters.category.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import appapae.apae.app.apae.R;
import appapae.apae.app.apae.adapters.ItemClickListener;

public class RecyclerCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView title;
    private final ImageView imgView;

    private ItemClickListener itemClickListener;

    public RecyclerCategoryViewHolder(final View parent, ImageView img, TextView titulo) {
        super(parent);
        title = titulo;
        imgView = img;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());

    }
    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }

        public static RecyclerCategoryViewHolder newInstance(View parent) {
            ImageView imgView =  parent.findViewById(R.id.imgBackground);
            TextView tvTitle = parent.findViewById(R.id.tvTitulo);
            return new RecyclerCategoryViewHolder(parent,imgView, tvTitle);
        }



    public TextView getTitle() {
        return title;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setText(CharSequence text) {
            title.setText(text);
    }
}


