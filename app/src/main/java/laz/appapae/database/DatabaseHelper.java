package laz.appapae.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by laz on 21/06/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "APAE_DB";
    private static final int DATABASE_VERSION = 7;
    private static String TABLE_PARTNERS = "CREATE TABLE PARTNERS" +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "fantasyName TEXT," +
            "cpfCnpj TEXT," +
            "rgInscription TEXT," +
            "cep TEXT," +
            "phone TEXT," +
            "discount INTEGER," +
            "streetName TEXT," +
            "streetNumber TEXT," +
            "neighborhood TEXT," +
            "photo TEXT," +
            "nameState TEXT," +
            "nameCity TEXT);";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_PARTNERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE PARTNERS");
        onCreate(db);
    }
}
