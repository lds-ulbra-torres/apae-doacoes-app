package com.example.lucca.doeamor_apaetorres.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

/**
 * Created by laz on 11/05/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Partner implements Serializable {
    @JsonDeserialize
    private int id_partner;
    @JsonDeserialize
    private String owner_name_partner;
    @JsonDeserialize
    private String fantasy_name_partner;
    @JsonDeserialize
    private String email_partner;
    @JsonDeserialize
    private String cnpj_cpf_partner;
    @JsonDeserialize
    private String rg_inscription_partner;
    @JsonDeserialize
    private String cep_partner;
    @JsonDeserialize
    private String street_partner;
    @JsonDeserialize
    private String number_partner;
    @JsonDeserialize
    private String neighborhood_partner;
    @JsonDeserialize
    private String commercial_phone_partner;
    @JsonDeserialize
    private int discount_partner;
    @JsonDeserialize
    private String id_city;
    @JsonDeserialize
    private String photo_partner;
    @JsonDeserialize
    private int category_id_category;

    public int getId_partner() {
        return id_partner;
    }

    public void setId_partner(int id_partner) {
        this.id_partner = id_partner;
    }

    public String getOwner_name_partner() {
        return owner_name_partner;
    }

    public void setOwner_name_partner(String owner_name_partner) {
        this.owner_name_partner = owner_name_partner;
    }

    public String getFantasy_name_partner() {
        return fantasy_name_partner;
    }

    public void setFantasy_name_partner(String fantasy_name_partner) {
        this.fantasy_name_partner = fantasy_name_partner;
    }

    public String getEmail_partner() {
        return email_partner;
    }

    public void setEmail_partner(String email_partner) {
        this.email_partner = email_partner;
    }

    public String getCnpj_cpf_partner() {
        return cnpj_cpf_partner;
    }

    public void setCnpj_cpf_partner(String cnpj_cpf_partner) {
        this.cnpj_cpf_partner = cnpj_cpf_partner;
    }

    public String getRg_inscription_partner() {
        return rg_inscription_partner;
    }

    public void setRg_inscription_partner(String rg_inscription_partner) {
        this.rg_inscription_partner = rg_inscription_partner;
    }

    public String getCep_partner() {
        return cep_partner;
    }

    public void setCep_partner(String cep_partner) {
        this.cep_partner = cep_partner;
    }

    public String getStreet_partner() {
        return street_partner;
    }

    public void setStreet_partner(String street_partner) {
        this.street_partner = street_partner;
    }

    public String getNumber_partner() {
        return number_partner;
    }

    public void setNumber_partner(String number_partner) {
        this.number_partner = number_partner;
    }

    public String getNeighborhood_partner() {
        return neighborhood_partner;
    }

    public void setNeighborhood_partner(String neighborhood_partner) {
        this.neighborhood_partner = neighborhood_partner;
    }

    public String getCommercial_phone_partner() {
        return commercial_phone_partner;
    }

    public void setCommercial_phone_partner(String commercial_phone_partner) {
        this.commercial_phone_partner = commercial_phone_partner;
    }

    public int getDiscount_partner() {
        return discount_partner;
    }

    public void setDiscount_partner(int discount_partner) {
        this.discount_partner = discount_partner;
    }

    public String getId_city() {
        return id_city;
    }

    public void setId_city(String id_city) {
        this.id_city = id_city;
    }

    public String getPhoto_partner() {
        return photo_partner;
    }

    public void setPhoto_partner(String photo_partner) {
        this.photo_partner = photo_partner;
    }

    public int getCategory_id_category() {
        return category_id_category;
    }

    public void setCategory_id_category(int category_id_category) {
        this.category_id_category = category_id_category;
    }
}