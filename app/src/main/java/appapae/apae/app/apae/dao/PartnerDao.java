package appapae.apae.app.apae.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import appapae.apae.app.apae.Database.DataBaseHelper;
import appapae.apae.app.apae.models.Partner;
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

    public void sync(ArrayList<Partner> categories) {
        for (Partner partner: categories){

            if ( exists(partner) ){
                updatePartner(partner);
            }   else {
                insertPartner(partner);
            }

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
        data.put("card_discount_partner",p.getCard_discount_partner());
        data.put("term_discount_partner",p.getTerm_discount_partner());
        data.put("category_id_category",p.getCategory_id_category());
        return data;
    }

    public ArrayList<Partner> getPartnersDataBase(){
        partners.clear();
        Cursor c = helper.rawQuery("select * from PARTNERS  order by discount_partner desc;", null);

        while(c.moveToNext()){
            partners.add(getPartnerByCursor(c));
        }
        c.close();
        return partners;
    }

    public void clearPartnersFromDatabase(){
        helper.delete("PARTNERS", null,null);
        helper.execSQL("DELETE FROM PARTNERS");
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
        partner.setCard_discount_partner(cursor.getString(cursor.getColumnIndex("card_discount_partner")));
        partner.setTerm_discount_partner(cursor.getString(cursor.getColumnIndex("term_discount_partner")));
        return partner;
    }

}
