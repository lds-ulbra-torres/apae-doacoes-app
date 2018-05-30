package com.example.lucca.doeamor_apaetorres.adapters.partner;

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
import com.example.lucca.doeamor_apaetorres.R;
import com.example.lucca.doeamor_apaetorres.models.Partner;

import java.util.ArrayList;

public class PartnerAdapter extends ArrayAdapter<Partner> {
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

        TextView tvTituloPartner = gridView.findViewById(R.id.partnerTittle);
        imgBackground = gridView.findViewById(R.id.partnerPhoto);
        TextView tvDiscountPartner = gridView.findViewById(R.id.partnerDiscount);
        tvDiscountPartner.setText("-"+String.valueOf(partner.getDiscount_partner())+"%");
        tvTituloPartner.setText(partner.getFantasy_name_partner());
        loadPhotoPartner(partner);

        return gridView;

    }

    private void loadPhotoPartner(Partner partner) {
        Glide
                .with(getContext())
                .load("http://apaetorres.org.br/doacoes" + partner.getPhoto_partner())
                .into(imgBackground);
    }

    public static interface ItemClickListener {

        void onItemClick(View v, int pos);

    }
}
