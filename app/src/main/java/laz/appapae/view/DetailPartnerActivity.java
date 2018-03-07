package laz.appapae.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import laz.appapae.R;
import laz.appapae.model.Partner;

public class DetailPartnerActivity extends AppCompatActivity {

    private Partner partner;

    private TextView tvPhone;
    private ImageView ivPhoto;
    private TextView tvDiscount;
    private TextView tvStreet;
    private TextView tvTitle;
    //private Button btnShare;
    private TextView tvCity;
    private TextView tvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_partner);

        this.bindViews();
        this.getAllExtras();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("ApaeTorres");
        actionBar.setSubtitle(partner.getFantasyName());

        tvPhone.setText(partner.getPhone());
        tvStreet.setText(partner.getStreetName()+", "+partner.getStreetNumber());
        tvDiscount.setText(String.valueOf(partner.getDiscount())+"%");
        tvTitle.setText(partner.getFantasyName());
        tvCity.setText(partner.getNameCity());
        tvState.setText(partner.getNameState());


        Glide.with(this)
                .load("http://doacoes.apaetorres.org.br/" + partner.getPhoto())
                .diskCacheStrategy( DiskCacheStrategy.ALL )
                .into(ivPhoto);
    }

    public  boolean verifyConnection() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void bindViews(){
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvDiscount = (TextView) findViewById(R.id.tvDiscount);
        tvStreet = (TextView) findViewById(R.id.tvStreet);
        ivPhoto = (ImageView) findViewById(R.id.ivDetail);
        //btnShare = (Button) findViewById(R.id.btnShare);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvState = (TextView) findViewById(R.id.tvState);
    }

    private void getAllExtras(){
        partner = new Partner();
        Intent intent = getIntent();
        partner.setFantasyName(intent.getStringExtra("name"));
        partner.setPhone(intent.getStringExtra("phone"));
        partner.setNeighborhood(intent.getStringExtra("neighborhood"));
        partner.setPhoto(intent.getStringExtra("photo"));
        partner.setStreetNumber(intent.getStringExtra("number"));
        partner.setStreetName(intent.getStringExtra("street"));
        partner.setDiscount(Integer.parseInt(intent.getStringExtra("discount")));
        partner.setNameCity(intent.getStringExtra("city"));
        partner.setNameState(intent.getStringExtra("state"));
    }


}
