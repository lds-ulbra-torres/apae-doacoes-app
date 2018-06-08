package com.example.lucca.doeamor_apaetorres.views;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lucca.doeamor_apaetorres.R;
import com.example.lucca.doeamor_apaetorres.models.Category;
import com.example.lucca.doeamor_apaetorres.models.Partner;
import com.example.lucca.doeamor_apaetorres.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class DetailPartnerActivity extends FragmentActivity implements OnMapReadyCallback{
    private Partner partner;

    private TextView name;
    private ImageView photo;
    private TextView phone;
    private TextView street;
    private TextView number;
    private TextView cep;
    private Toolbar toolbar;
    private int mToolbarHeight;
    private LinearLayout mToolbarContainer;
    private String idcat;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_partner);
        toolbar = findViewById(R.id.toolbar);
        getAllExtras();

        toolbar.setTitle(partner.getFantasy_name_partner());
        toolbar.setSubtitle("Parceiros");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bindViews();
        name.setText(partner.getFantasy_name_partner());
        phone.setText(partner.getCommercial_phone_partner());
        street.setText(partner.getStreet_partner() + ", ");
        number.setText(partner.getNumber_partner());

        Glide.with(this)
                .load("http://doacoes.apaetorres.org.br/" + partner.getPhoto_partner())
                .into(photo);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);
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
        idcat = intent.getStringExtra("cat");
    }

    private void bindViews() {
        name = findViewById(R.id.tvTituloDetailPartner);
        photo = findViewById(R.id.imageDetailPartner);
        phone = findViewById(R.id.partnerDetailPhone);
        street = findViewById(R.id.partnerDetailStreet);
        cep = findViewById(R.id.partnerState);
        number = findViewById(R.id.partnerDetailNumber);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.867, 151.206);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        googleMap.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));
    }
}
