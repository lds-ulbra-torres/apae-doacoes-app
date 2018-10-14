package appapae.apae.app.apae.dto;

import appapae.apae.app.apae.models.Partner;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PartnerDTO {

    @JsonProperty("partnersByCategory")
    private ArrayList<Partner> partnersByCategory;

    public ArrayList<Partner> getPartners() {
        return partnersByCategory;
    }
}