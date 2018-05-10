package com.example.lucca.doeamor_apaetorres.Controllers;

import android.content.Context;

import com.example.lucca.doeamor_apaetorres.Models.Partners.Partner;
import com.example.lucca.doeamor_apaetorres.Models.Partners.PartnerCallback;
import com.example.lucca.doeamor_apaetorres.Models.Partners.PartnerModel;

import java.util.ArrayList;


public class PartnerController {

    private PartnerModel partnerModel;

    public PartnerController(Context context, String idCategory){
        partnerModel = new PartnerModel(context,idCategory);
    }

    public void getPartners(PartnerCallback<ArrayList<Partner>> partnerCallback){
        if(partnerModel.verifyConnection()){
            partnerModel.getPartners(partnerCallback);
        }else{
            partnerModel.getPartnersFromDatabase(partnerCallback);
        }
    }
}
