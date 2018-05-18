package com.example.lucca.doeamor_apaetorres.callbacks;

import com.example.lucca.doeamor_apaetorres.models.Partner;

import java.util.ArrayList;

public interface PartnerCallback<S>{

    void onSuccess(ArrayList<Partner> partners);

    void onError();
}
