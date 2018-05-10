package com.example.lucca.doeamor_apaetorres.Models.Partners;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laz on 11/05/17.
 */

public class Partner {

    @SerializedName("id_partner")
    private int id;
    @SerializedName("owner_name_partner")
    private String ownerNamePartner;
    @SerializedName("fantasy_name_partner")
    private String fantasyNamePartner;
    @SerializedName("email_partner")
    private String emailPartner;
    @SerializedName("cnpj_cpf_partner")
    private String cpfCnpjPartner;
    @SerializedName("rg_inscription_partner")
    private String rgInscriptionPartner;
    @SerializedName("cep_partner")
    private String cepPartner;
    @SerializedName("street_partner")
    private String streetPartner;
    @SerializedName("number_partner")
    private String numberPartner;
    @SerializedName("neighborhood_partner")
    private String neighborhoodPartner;
    @SerializedName("commercial_phone_partner")
    private String commerciaPhonePartner;
    @SerializedName("discount_partner")
    private int discountPartner;
    @SerializedName("id_city")
    private String idCityPartner;
    @SerializedName("photo_partner")
    private String photoPartner;
    @SerializedName("category_id_category")
    private int categoryIdPartner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerNamePartner() {
        return ownerNamePartner;
    }

    public void setOwnerNamePartner(String ownerNamePartner) {
        this.ownerNamePartner = ownerNamePartner;
    }

    public String getFantasyNamePartner() {
        return fantasyNamePartner;
    }

    public void setFantasyNamePartner(String fantasyNamePartner) {
        this.fantasyNamePartner = fantasyNamePartner;
    }

    public String getEmailPartner() {
        return emailPartner;
    }

    public void setEmailPartner(String emailPartner) {
        this.emailPartner = emailPartner;
    }

    public String getCpfCnpjPartner() {
        return cpfCnpjPartner;
    }

    public void setCpfCnpjPartner(String cpfCnpjPartner) {
        this.cpfCnpjPartner = cpfCnpjPartner;
    }

    public String getRgInscriptionPartner() {
        return rgInscriptionPartner;
    }

    public void setRgInscriptionPartner(String rgInscriptionPartner) {
        this.rgInscriptionPartner = rgInscriptionPartner;
    }

    public String getCepPartner() {
        return cepPartner;
    }

    public void setCepPartner(String cepPartner) {
        this.cepPartner = cepPartner;
    }

    public String getStreetPartner() {
        return streetPartner;
    }

    public void setStreetPartner(String streetPartner) {
        this.streetPartner = streetPartner;
    }

    public String getNumberPartner() {
        return numberPartner;
    }

    public void setNumberPartner(String numberPartner) {
        this.numberPartner = numberPartner;
    }

    public String getNeighborhoodPartner() {
        return neighborhoodPartner;
    }

    public void setNeighborhoodPartner(String neighborhoodPartner) {
        this.neighborhoodPartner = neighborhoodPartner;
    }

    public String getCommerciaPhonePartner() {
        return commerciaPhonePartner;
    }

    public void setCommerciaPhonePartner(String commerciaPhonePartner) {
        this.commerciaPhonePartner = commerciaPhonePartner;
    }

    public int getDiscountPartner() {
        return discountPartner;
    }

    public void setDiscountPartner(int discountPartner) {
        this.discountPartner = discountPartner;
    }

    public String getIdCityPartner() {
        return idCityPartner;
    }

    public void setIdCityPartner(String idCityPartner) {
        this.idCityPartner = idCityPartner;
    }

    public String getPhotoPartner() {
        return photoPartner;
    }

    public void setPhotoPartner(String photoPartner) {
        this.photoPartner = photoPartner;
    }

    public int getCategoryIdPartner() {
        return categoryIdPartner;
    }

    public void setCategoryIdPartner(int categoryIdPartner) {
        this.categoryIdPartner = categoryIdPartner;
    }
}