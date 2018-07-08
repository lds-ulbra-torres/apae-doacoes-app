package com.apae.lucca.apae_torres.dto;

import com.apae.lucca.apae_torres.models.Partner;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailDTO {

    @JsonProperty("partners")
    private Partner partner;
    public Partner getDetailPartner(){
        return partner = partner;
    }
}
