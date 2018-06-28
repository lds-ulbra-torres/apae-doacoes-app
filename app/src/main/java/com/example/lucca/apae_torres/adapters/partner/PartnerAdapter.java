package com.example.lucca.apae_torres.adapters.partner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.example.lucca.apae_torres.R;
import com.example.lucca.apae_torres.adapters.ItemClickListener;
import com.example.lucca.apae_torres.adapters.partner.viewholder.RecyclerPartnerViewHolder;
import com.example.lucca.apae_torres.models.Partner;
import com.example.lucca.apae_torres.views.Detail;

import java.util.ArrayList;

public class PartnerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private final Context mContext;

     ArrayList<Partner> mPartners, filterList;
    private CustomFilterPartner filter;

    public PartnerAdapter(@NonNull Context context, @NonNull ArrayList<Partner> partners) {
        mContext = context;
        mPartners = partners;
        this.filterList = partners;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_partners, parent, false);
        return RecyclerPartnerViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final RecyclerPartnerViewHolder holder = (RecyclerPartnerViewHolder) viewHolder;
        final Partner partner = mPartners.get(position);
        holder.setText(partner.getFantasy_name_partner());
        CharSequence c = String.valueOf(partner.getDiscount_partner());
        holder.setDiscount("-"+c + "%");
        Glide
                .with(mContext)
                .load("http://doacoes.apaetorres.org.br" + partner.getPhoto_partner())
                .error(R.color.colorError)
                .into(holder.getImgView());

        holder.setItemClickListener(new ItemClickListener(){
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent1 = new Intent(v.getContext(), Detail.class);
                intent1.putExtra("name", partner.getFantasy_name_partner());
                intent1.putExtra("idPartner", String.valueOf(partner.getId_partner()));
                intent1.putExtra("photo", partner.getPhoto_partner());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(),v.findViewById(R.id.partnerPhoto),"imageTransition");
                v.getContext().startActivity(intent1, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPartners == null ? 0 : mPartners.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilterPartner(filterList,this);
        }
        return filter;
    }

}
