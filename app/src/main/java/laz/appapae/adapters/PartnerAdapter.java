package laz.appapae.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import laz.appapae.R;
import laz.appapae.model.Partner;

import java.util.ArrayList;

import static laz.appapae.view.MainActivity.counterColor;

/**
 * Created by laz on 11/05/17.
 */

public class PartnerAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Partner> partners;

    public PartnerAdapter(Context context,ArrayList<Partner> partners) {
        super(context, 0, partners);

        this.context = context;
        this.partners = partners;
    }

    @Override
    public int getCount() {
        return partners.size();
    }

    @Override
    public Partner getItem(int position) {
        return partners.get(position);
    }

    @Override
    public long getItemId(int position) {
        return partners.get(position).getId();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    @NonNull
    public View getView(int position,View convertView, ViewGroup parent) {
        View view;
        TextView tvNamePartner;
        TextView tvDiscount;
        RelativeLayout rlItemLv;

        if(convertView == null){
            view = LayoutInflater.from(this.context).inflate(R.layout.partners_list_item, null);

            rlItemLv = (RelativeLayout) view.findViewById(R.id.rlItemLv);
            tvNamePartner = (TextView) view.findViewById(R.id.tvName);
            tvDiscount = (TextView) view.findViewById(R.id.tvDiscount);
        }else{
            view = convertView;
            rlItemLv = (RelativeLayout) view.findViewById(R.id.rlItemLv);
            tvNamePartner = (TextView) view.findViewById(R.id.tvName);
            tvDiscount = (TextView) view.findViewById(R.id.tvDiscount);
        }

        switch (counterColor){
            case 0:{
                rlItemLv.setBackgroundResource(R.drawable.list_item_background_1);
                counterColor++;
                break;
            }
            case 1:{
                rlItemLv.setBackgroundResource(R.drawable.list_item_background_2);
                counterColor++;
                break;
            }
            case 2:{
                rlItemLv.setBackgroundResource(R.drawable.list_item_background_3);
                counterColor++;
                break;
            }
            case 3:{
                rlItemLv.setBackgroundResource(R.drawable.list_item_background_4);
                counterColor++;
                break;
            }
            case 4:{
                rlItemLv.setBackgroundResource(R.drawable.list_item_background_5);
                counterColor = 0;
                break;
            }
        }

        Partner partner = partners.get(position);
        tvNamePartner.setText(partner.getFantasyName());
        tvDiscount.setText(" -" + String.valueOf(partner.getDiscount())+"%");

        return view;
    }
}
