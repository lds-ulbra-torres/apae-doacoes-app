package com.example.lucca.doeamor_apaetorres.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.lucca.doeamor_apaetorres.callbacks.PartnerCallback;
import com.example.lucca.doeamor_apaetorres.database.DataBaseHelper;
import com.example.lucca.doeamor_apaetorres.models.Partner;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PartnerDao {

    private SQLiteDatabase helper;
    private Context context;
    private ArrayList<Partner> partners;
    public PartnerDao(Context context){
        this.helper = new DataBaseHelper(context).getWritableDatabase();
        this.context = context;
        this.partners = new ArrayList<>();
    }

    public void clearPartnersFromDatabase(){
        helper.delete("PARTNERS", null,null);
        helper.execSQL("DELETE FROM PARTNERS");
    }

    public void getPartnersFromDatabase(final PartnerCallback<ArrayList<Partner>> partnerCallback){
        Cursor cursor = helper.rawQuery("select * from PARTNERS inner join CATEGORIES on PARTNERS.category_id_category = CATEGORIES._id_category order by discount_partner desc  ;", null);
        while (cursor.moveToNext()) {
                partners.add(getPartnerByCursor(cursor));
        }
        cursor.close();
        partnerCallback.onSuccess(partners);
    }

    private Partner getPartnerByCursor(Cursor cursor){
        Partner partner = new Partner();
        partner.setId_partner(cursor.getInt(cursor.getColumnIndex("_id_partner")));
        partner.setFantasy_name_partner(cursor.getString(cursor.getColumnIndex("fantasy_name_partner")));
        partner.setCnpj_cpf_partner(cursor.getString(cursor.getColumnIndex("cnpj_cpf_partner")));
        partner.setRg_inscription_partner(cursor.getString(cursor.getColumnIndex("rg_inscription_partner")));
        partner.setCep_partner(cursor.getString(cursor.getColumnIndex("cep_partner")));
        partner.setStreet_partner(cursor.getString(cursor.getColumnIndex("street_partner")));
        partner.setNumber_partner(cursor.getString(cursor.getColumnIndex("number_partner")));
        partner.setNeighborhood_partner(cursor.getString(cursor.getColumnIndex("neighborhood_partner")));
        partner.setCommercial_phone_partner(cursor.getString(cursor.getColumnIndex("commercial_phone_partner")));
        partner.setDiscount_partner(cursor.getInt(cursor.getColumnIndex("discount_partner")));
        partner.setId_city(cursor.getString(cursor.getColumnIndex("id_city")));
        partner.setPhoto_partner(cursor.getString(cursor.getColumnIndex("photo_partner")));
        partner.setCategory_id_category(cursor.getInt(cursor.getColumnIndex("category_id_category")));
        return partner;
    }

    private void savePartners(ArrayList<Partner> partners){
        ContentValues values;

        for (Partner p: partners) {
            values = new ContentValues();
            values.put("fantasy_name_partner", p.getFantasy_name_partner());
            values.put("cnpj_cpf_partner", p.getCnpj_cpf_partner());
            values.put("rg_inscription_partner", p.getRg_inscription_partner());
            values.put("cep_partner",p.getCep_partner());
            values.put("street_partner",p.getStreet_partner());
            values.put("number_partner",p.getNumber_partner());
            values.put("neighborhood_partner",p.getNeighborhood_partner());
            values.put("commercial_phone_partner",p.getCommercial_phone_partner());
            values.put("discount_partner",p.getDiscount_partner());
            values.put("id_city",p.getId_city());
            values.put("photo_partner",p.getPhoto_partner());
            values.put("category_id_category",p.getCategory_id_category());
            helper.insert("PARTNERS", null, values);
        }
    }

    private void getPartnersFromJson(String json){
        partners.clear();
        Gson gson = new Gson();
        JSONObject object=null;
        JSONArray partnersJson=null;
        JSONArray error = null;

        try {
            object = new JSONObject(json);
            partnersJson = object.getJSONArray("partnersByCategory");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(partnersJson ==null){
            Toast.makeText(context,"Ainda não existem parceiros para esta categoria!", Toast.LENGTH_LONG).show();


        } else{
            for (int x=0; x < partnersJson.length(); x++){
                try {
                    Partner partner = gson.fromJson(partnersJson.get(x).toString(), Partner.class);
                    partners.add(partner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public  boolean verifyConnection() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();

    }





















    public void insert(ArrayList<Partner> partners) {
        for (Partner partner: partners){
            insertPartner(partner);
        }
    }

    private void updatePartner(Partner partner) {
        ContentValues data = getDataPartners(partner);
        String[] params = {String.valueOf(partner.getId_partner())};
        helper.update("PARTNERS",data,"_id_partner = ?", params);
    }

    private boolean exists(Partner partner){
        String exists = "SELECT _id_partner FROM PARTNERS WHERE _id_partner = ? LIMIT 1";
        Cursor cursor = helper.rawQuery(exists, new String[]{String.valueOf(partner.getId_partner())});
        int count = cursor.getCount();
        return count > 0;
    }

    private void insertPartner(Partner partner) {
        ContentValues data = getDataPartners(partner);
        helper.insert("PARTNERS", null,data);
    }

    private ContentValues getDataPartners(Partner p) {
        ContentValues data = new ContentValues();
        data.put("_id_partner",p.getId_partner());
        data.put("fantasy_name_partner", p.getFantasy_name_partner());
        data.put("cnpj_cpf_partner", p.getCnpj_cpf_partner());
        data.put("rg_inscription_partner", p.getRg_inscription_partner());
        data.put("cep_partner",p.getCep_partner());
        data.put("street_partner",p.getStreet_partner());
        data.put("number_partner",p.getNumber_partner());
        data.put("neighborhood_partner",p.getNeighborhood_partner());
        data.put("commercial_phone_partner",p.getCommercial_phone_partner());
        data.put("discount_partner",p.getDiscount_partner());
        data.put("id_city",p.getId_city());
        data.put("photo_partner",p.getPhoto_partner());
        data.put("category_id_category",p.getCategory_id_category());
        helper.insert("PARTNERS", null, data);
        return data;
    }

    public ArrayList<Partner> getPartnersDataBase(){
        partners.clear();
        Cursor c = helper.rawQuery("select * from PARTNERS inner join CATEGORIES on PARTNERS.category_id_category = CATEGORIES._id_category order by discount_partner desc;", null);

        while(c.moveToNext()){
            partners.add(getPartnerByCursor(c));
        }
        c.close();
        return partners;
    }
}
