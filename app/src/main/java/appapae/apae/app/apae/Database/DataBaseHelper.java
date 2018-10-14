package appapae.apae.app.apae.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "APAE_DB";
    private static int DATABASE_VERSION = 45;

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static String TABLE_PARTNERS = "CREATE TABLE PARTNERS" +
            "(_id_partner INTEGER PRIMARY KEY AUTOINCREMENT," +
            "owner_name_partner TEXT," +
            "fantasy_name_partner TEXT," +
            "email_partner TEXT," +
            "cnpj_cpf_partner TEXT," +
            "rg_inscription_partner TEXT," +
            "cep_partner TEXT," +
            "street_partner TEXT,"+
            "number_partner TEXT," +
            "neighborhood_partner TEXT," +
            "commercial_phone_partner TEXT," +
            "discount_partner INTEGER," +
            "id_city INTEGER," +
            "photo_partner TEXT," +
            "card_discount_partner TEXT," +
            "term_discount_partner TEXT," +
            "category_id_category INTEGER," +
            "CONSTRAINT id_cat FOREIGN KEY (category_id_category) REFERENCES CATEGORIES (_id_category));";

    private static String TABLE_CATEGORIES = "CREATE TABLE CATEGORIES" +
            "(_id_category INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "name_category TEXT," +
            "description_category TEXT," +
            "photo_category TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CATEGORIES);
        db.execSQL(TABLE_PARTNERS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE CATEGORIES");
        db.execSQL("DROP TABLE PARTNERS");
        onCreate(db);
    }

}
