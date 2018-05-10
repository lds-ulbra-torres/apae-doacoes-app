package com.example.lucca.doeamor_apaetorres.Models.Partners;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lucca.doeamor_apaetorres.Database.DataBaseHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PartnerModel {

    private SQLiteDatabase helper;
    private Context context;
    private ArrayList<Partner> partners;
    private String idCategory;
    public PartnerModel(Context context, String idCategory){
        this.helper = new DataBaseHelper(context).getWritableDatabase();
        this.context = context;
        this.partners = new ArrayList<>();
        this.idCategory = idCategory;
    }

    public void getPartners(final PartnerCallback<ArrayList<Partner>> partnerCallback){
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "http://apaetorres.org.br/doacoes/api/partnerByCategory/"+this.idCategory;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getPartnersFromJson(response);
                        partnerCallback.onSuccess(partners);
                        clearPartnersFromDatabase();
                        savePartners(partners);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                partnerCallback.onError();
                Log.e("ERROR", "Error na response");
            }
        });
        queue.add(stringRequest);
    }

    public void clearPartnersFromDatabase(){
        helper.rawQuery("DELETE FROM PARTNERS",null);
    }

    public void getPartnersFromDatabase(final PartnerCallback<ArrayList<Partner>> partnerCallback){
        partners.clear();
        Cursor cursor = helper.rawQuery("select * from partners", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                partners.add(getPartnerByCursor(cursor));
                cursor.moveToNext();
            }
        }

        partnerCallback.onSuccess(partners);
    }

    private Partner getPartnerByCursor(Cursor cursor){
        Partner partner = new Partner();
        partner.setFantasyNamePartner(cursor.getString(cursor.getColumnIndex("fantasy_name_partner")));
        partner.setCpfCnpjPartner(cursor.getString(cursor.getColumnIndex("cnpj_cpf_partner")));
        partner.setRgInscriptionPartner(cursor.getString(cursor.getColumnIndex("rg_inscription_partner")));
        partner.setCepPartner(cursor.getString(cursor.getColumnIndex("cep_partner")));
        partner.setStreetPartner(cursor.getString(cursor.getColumnIndex("street_partner")));
        partner.setNumberPartner(cursor.getString(cursor.getColumnIndex("number_partner")));
        partner.setNeighborhoodPartner(cursor.getString(cursor.getColumnIndex("neighborhood_partner")));
        partner.setCommerciaPhonePartner(cursor.getString(cursor.getColumnIndex("commercial_phone_partner")));
        partner.setDiscountPartner(cursor.getInt(cursor.getColumnIndex("discount_partner")));
        partner.setIdCityPartner(cursor.getString(cursor.getColumnIndex("id_city")));
        partner.setPhotoPartner(cursor.getString(cursor.getColumnIndex("photo_partner")));
        partner.setCategoryIdPartner(cursor.getInt(cursor.getColumnIndex("category_id_category")));

        return partner;
    }

    private void savePartners(ArrayList<Partner> partners){
        ContentValues values;

        for (Partner p: partners) {
            values = new ContentValues();
            values.put("fantasy_name_partner", p.getFantasyNamePartner());
            values.put("cnpj_cpf_partner", p.getCpfCnpjPartner());
            values.put("rg_inscription_partner", p.getRgInscriptionPartner());
            values.put("cep_partner",p.getCepPartner());
            values.put("street_partner",p.getStreetPartner());
            values.put("number_partner",p.getNumberPartner());
            values.put("neighborhood_partner",p.getNeighborhoodPartner());
            values.put("commercial_phone_partner",p.getCommerciaPhonePartner());
            values.put("discount_partner",p.getDiscountPartner());
            values.put("id_city",p.getIdCityPartner());
            values.put("photo_partner",p.getPhotoPartner());
            values.put("category_id_category",p.getCategoryIdPartner());
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
}
