package com.example.lucca.doeamor_apaetorres.Models.Partners;

import java.util.ArrayList;

public interface PartnerCallback<S>{

    void onSuccess(ArrayList<Partner> partners);

    void onError();
}
