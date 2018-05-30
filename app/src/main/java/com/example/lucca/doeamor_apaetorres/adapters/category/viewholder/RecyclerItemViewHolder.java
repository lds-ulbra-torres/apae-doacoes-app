package com.example.lucca.doeamor_apaetorres.adapters.category.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucca.doeamor_apaetorres.R;
import com.example.lucca.doeamor_apaetorres.adapters.partner.PartnerAdapter;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView title;
    ImageView imgView;

    PartnerAdapter.ItemClickListener itemClickListener;

    public RecyclerItemViewHolder(final View parent, ImageView img, TextView titulo) {
        super(parent);
        title = titulo;
        imgView = img;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());

    }
    public void setItemClickListener(PartnerAdapter.ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }

        public static RecyclerItemViewHolder newInstance(View parent) {

            ImageView imgView =  parent.findViewById(R.id.imgBackground);
            TextView tvTitle = parent.findViewById(R.id.tvTitulo);

            return new RecyclerItemViewHolder(parent,imgView, tvTitle);
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


