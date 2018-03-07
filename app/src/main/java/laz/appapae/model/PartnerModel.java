package laz.appapae.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import laz.appapae.database.DatabaseHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by laz on 21/06/17.
 */

public class PartnerModel {

    private SQLiteDatabase helper;
    private Context context;
    private ArrayList<Partner> partners;

    public PartnerModel(Context context){
        this.helper = new DatabaseHelper(context).getWritableDatabase();
        this.context = context;
        this.partners = new ArrayList<>();
    }

    public void getPartners(final PartnerCallback<ArrayList<Partner>> partnerCallback){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://doacoes.apaetorres.org.br/v1/partner/get";

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
        Cursor  cursor = helper.rawQuery("select * from partners", null);

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
        partner.setFantasyName(cursor.getString(cursor.getColumnIndex("fantasyName")));
        partner.setCpfCnpj(cursor.getString(cursor.getColumnIndex("cpfCnpj")));
        partner.setRgInscription(cursor.getString(cursor.getColumnIndex("rgInscription")));
        partner.setCep(cursor.getString(cursor.getColumnIndex("cep")));
        partner.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        partner.setStreetName(cursor.getString(cursor.getColumnIndex("streetName")));
        partner.setDiscount(cursor.getInt(cursor.getColumnIndex("discount")));
        partner.setStreetNumber(cursor.getString(cursor.getColumnIndex("streetNumber")));
        partner.setNeighborhood(cursor.getString(cursor.getColumnIndex("neighborhood")));
        partner.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
        partner.setNameState(cursor.getString(cursor.getColumnIndex("nameState")));
        partner.setNameCity(cursor.getString(cursor.getColumnIndex("nameCity")));

        return partner;
    }

    private void savePartners(ArrayList<Partner> partners){
        ContentValues values;

        for (Partner p: partners) {
            values = new ContentValues();
            values.put("fantasyName", p.getFantasyName());
            values.put("cpfCnpj", p.getCpfCnpj());
            values.put("rgInscription", p.getRgInscription());
            values.put("cep", p.getCep());
            values.put("phone", p.getPhone());
            values.put("discount", p.getDiscount());
            values.put("streetNumber", p.getStreetNumber());
            values.put("neighborhood", p.getNeighborhood());
            values.put("photo", p.getPhoto());
            values.put("nameState", p.getNameState());
            values.put("nameCity", p.getNameCity());

            helper.insert("PARTNERS", null, values);
        }
    }

    private void getPartnersFromJson(String json){
        partners.clear();
        Gson gson = new Gson();
        JSONObject object=null;
        JSONArray partnersJson=null;

        try {
            object = new JSONObject(json);
            partnersJson = object.getJSONArray("partners");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int x=0; x < partnersJson.length(); x++){
            try {
                Partner partner = gson.fromJson(partnersJson.get(x).toString(), Partner.class);
                partners.add(partner);
            } catch (JSONException e) {
                e.printStackTrace();
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
