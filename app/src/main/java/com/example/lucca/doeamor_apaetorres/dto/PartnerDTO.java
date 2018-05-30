package com.example.lucca.doeamor_apaetorres.dto;

import com.example.lucca.doeamor_apaetorres.models.Partner;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PartnerDTO {

    @JsonProperty("partnersByCategory")
    private ArrayList<Partner> partnersByCategory;

    public ArrayList<Partner> getPartners() {
        return partnersByCategory;
    }
}