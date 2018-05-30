package com.example.lucca.doeamor_apaetorres.adapters.category;

import android.widget.Filter;

import com.example.lucca.doeamor_apaetorres.models.Category;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    RecyclerAdapter adapter;
    ArrayList<Category> filterList;


    public CustomFilter(ArrayList<Category> filterList, RecyclerAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Category> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNameCat().toUpperCase().contains(constraint))
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
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.mCategories= (ArrayList<Category>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
