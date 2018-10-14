package appapae.apae.app.apae.dto;

import appapae.apae.app.apae.models.Partner;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailDTO {

    @JsonProperty("partners")
    private Partner partner;
    public Partner getDetailPartner(){
        return partner = partner;
    }
}
