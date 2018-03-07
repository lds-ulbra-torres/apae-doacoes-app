package laz.appapae.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laz on 11/05/17.
 */

public class Partner {

    @SerializedName("id_partner")
    private int id;
    @SerializedName("fantasy_name_partner")
    private String fantasyName;
    @SerializedName("cnpj_cpf_partner")
    private String cpfCnpj;
    @SerializedName("rg_inscription_partner")
    private String rgInscription;
    @SerializedName("cep_partner")
    private String cep;
    @SerializedName("commercial_phone_partner")
    private String phone;
    @SerializedName("discount_partner")
    private int discount;
    @SerializedName("number_partner")
    private String streetNumber;
    @SerializedName("street_partner")
    private String streetName;
    @SerializedName("neighborhood_partner")
    private String neighborhood;
    @SerializedName("photo_partner")
    private String photo;
    @SerializedName("uf_state")
    private String nameState;
    @SerializedName("name_city")
    private String nameCity;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getRgInscription() {
        return rgInscription;
    }

    public void setRgInscription(String rgInscription) {
        this.rgInscription = rgInscription;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNameState() {
        return nameState;
    }

    public void setNameState(String nameState) {
        this.nameState = nameState;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }
}
