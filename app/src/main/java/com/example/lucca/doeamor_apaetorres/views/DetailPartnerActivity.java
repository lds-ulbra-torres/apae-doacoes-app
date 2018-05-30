package com.example.lucca.doeamor_apaetorres.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lucca.doeamor_apaetorres.R;
import com.example.lucca.doeamor_apaetorres.models.Partner;

public class DetailPartnerActivity extends AppCompatActivity {
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

        name.setText(partner.getFantasy_name_partner());
        phone.setText(partner.getCommercial_phone_partner());
        street.setText(partner.getStreet_partner()+", ");
        number.setText(partner.getNumber_partner());

        Glide.with(this)
                .load("http://doacoes.apaetorres.org.br/" + partner.getPhoto_partner())
                .into(photo);
    }


    private void getAllExtras() {
        partner = new Partner();
        Intent intent = getIntent();
        partner.setFantasy_name_partner(intent.getStringExtra("name"));
        partner.setPhoto_partner(intent.getStringExtra("partnerPhoto"));
        partner.setCommercial_phone_partner(intent.getStringExtra("partnerPhone"));
        partner.setStreet_partner(intent.getStringExtra("partnerStreet"));
        partner.setNumber_partner(intent.getStringExtra("partnerNumber"));
        partner.setCep_partner(intent.getStringExtra("partnerState"));
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
