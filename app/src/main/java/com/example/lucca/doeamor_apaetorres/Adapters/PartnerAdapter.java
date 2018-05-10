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
import com.example.lucca.doeamor_apaetorres.Models.Partners.Partner;
import com.example.lucca.doeamor_apaetorres.R;

import java.util.ArrayList;

public class PartnerAdapter extends ArrayAdapter<Partner> {
    private TextView tvTituloPartner;
    private TextView tvDiscountPartner;
    private ImageView imgBackground;


    public PartnerAdapter(@NonNull Context context, @NonNull ArrayList<Partner> partner) {
        super(context, 0, partner);
    }

    @Nullable
    @Override
    public Partner getItem(int position) {
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
            gridView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_partner,parent,false);
        }

        Partner partner = getItem(position);

        tvTituloPartner = gridView.findViewById(R.id.partnerTittle);
        imgBackground = gridView.findViewById(R.id.partnerPhoto);
        tvDiscountPartner = gridView.findViewById(R.id.partnerDiscount);
        tvDiscountPartner.setText(String.valueOf(partner.getDiscountPartner())+"%");
        tvTituloPartner.setText(partner.getFantasyNamePartner());
        loadPhotoPartner(partner);
        imgBackground.setBackgroundResource(R.drawable.border);
        return gridView;

    }

    private void loadPhotoPartner(Partner partner) {
        Glide
                .with(getContext())
                .load("http://apaetorres.org.br/doacoes" + partner.getPhotoPartner())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgBackground);
    }
}
