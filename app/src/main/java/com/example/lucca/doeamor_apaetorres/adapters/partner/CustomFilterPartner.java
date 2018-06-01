package com.example.lucca.doeamor_apaetorres.adapters.partner;

import android.widget.Filter;

import com.example.lucca.doeamor_apaetorres.adapters.partner.PartnerAdapter;
import com.example.lucca.doeamor_apaetorres.models.Partner;

import java.util.ArrayList;

public class CustomFilterPartner extends Filter {
    private PartnerAdapter adapter;
    private ArrayList<Partner> filterList;


    public CustomFilterPartner(ArrayList<Partner> filterList, PartnerAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results=new Filter.FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Partner> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getFantasy_name_partner().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {

        adapter.mPartners= (ArrayList<Partner>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
