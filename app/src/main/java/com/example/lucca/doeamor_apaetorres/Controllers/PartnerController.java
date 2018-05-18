package com.example.lucca.doeamor_apaetorres.controllers;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.lucca.doeamor_apaetorres.callbacks.PartnerCallback;
import com.example.lucca.doeamor_apaetorres.dao.PartnerDao;
import com.example.lucca.doeamor_apaetorres.models.Partner;

import java.util.ArrayList;


public class PartnerController {

    private PartnerDao partnerDao;
    private Context context;

    public PartnerController(Context context){
        partnerDao = new PartnerDao(context);
        this.context = context;
    }

    public void getPartners(PartnerCallback<ArrayList<Partner>> partnerCallback) {

    }
    private boolean verifyConnection() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conectivtyManager != null;
        if ( conectivtyManager.getActiveNetworkInfo() != null )
            if ( conectivtyManager.getActiveNetworkInfo().isAvailable() )
                if ( conectivtyManager.getActiveNetworkInfo().isConnected() ) return true;
        return false;

    }
}
