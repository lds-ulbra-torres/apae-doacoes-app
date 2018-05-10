package com.example.lucca.doeamor_apaetorres.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lucca.doeamor_apaetorres.Models.Partners.Partner;
import com.example.lucca.doeamor_apaetorres.R;

public class DetailPartner extends AppCompatActivity {
    private Partner partner;

    private TextView name;
    private ImageView photo;
    private TextView phone;
    private TextView street;
    private TextView number;
    private TextView cep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_partner1);
        getAllExtras();
        bindViews();
        name.setText(partner.getFantasyNamePartner());
        phone.setText(partner.getCommerciaPhonePartner());
        street.setText(partner.getStreetPartner()+", ");
        number.setText(partner.getNumberPartner());

        Glide.with(this)
                .load("http://doacoes.apaetorres.org.br/" + partner.getPhotoPartner())
                .diskCacheStrategy( DiskCacheStrategy.ALL )
                .error(R.drawable.ic_image_off)
                .into(photo);
    }


    private void getAllExtras() {
        partner = new Partner();
        Intent intent = getIntent();
        partner.setFantasyNamePartner(intent.getStringExtra("name"));
        partner.setPhotoPartner(intent.getStringExtra("partnerPhoto"));
        partner.setCommerciaPhonePartner(intent.getStringExtra("partnerPhone"));
        partner.setStreetPartner(intent.getStringExtra("partnerStreet"));
        partner.setNumberPartner(intent.getStringExtra("partnerNumber"));
        partner.setCepPartner(intent.getStringExtra("partnerState"));
    }

    private void bindViews(){

        name = findViewById(R.id.tvTituloDetailPartner);
        photo = findViewById(R.id.imageDetailPartner);
        phone = findViewById(R.id.partnerDetailPhone);
        street = findViewById(R.id.partnerDetailStreet);
        cep = findViewById(R.id.partnerState);
        number = findViewById(R.id.partnerDetailNumber);


    }

}
