package com.example.lucca.apae_torres.dto;

import com.example.lucca.apae_torres.models.Partner;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PartnerDTO {

    @JsonProperty("partnersByCategory")
    private ArrayList<Partner> partnersByCategory;

    public ArrayList<Partner> getPartners() {
        return partnersByCategory;
    }
}