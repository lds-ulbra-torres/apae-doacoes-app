package laz.appapae.controller;

import android.content.Context;

import laz.appapae.model.PartnerCallback;
import laz.appapae.model.Partner;
import laz.appapae.model.PartnerModel;

import java.util.ArrayList;

/**
 * Created by laz on 21/06/17.
 */

public class PartnerController {

    private PartnerModel partnerModel;

    public PartnerController(Context context){
        partnerModel = new PartnerModel(context);
    }

    public void getPartners(PartnerCallback<ArrayList<Partner>> partnerCallback){
        if(partnerModel.verifyConnection()){
            partnerModel.getPartners(partnerCallback);
        }else{
            partnerModel.getPartnersFromDatabase(partnerCallback);
        }
    }
}
