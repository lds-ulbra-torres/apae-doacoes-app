package com.example.lucca.apae_torres.dto;

import com.example.lucca.apae_torres.models.Partner;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailDTO {

    @JsonProperty("partners")
    private Partner partner;
    public Partner getDetailPartner(){
        return partner;
    }
}
