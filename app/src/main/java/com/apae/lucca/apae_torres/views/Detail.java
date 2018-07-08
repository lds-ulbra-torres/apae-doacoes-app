package com.apae.lucca.apae_torres.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.apae.lucca.apae_torres.R;
import com.apae.lucca.apae_torres.dto.DetailDTO;
import com.apae.lucca.apae_torres.models.Partner;
import com.apae.lucca.apae_torres.retrofit.RetrofitInit;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity implements OnMapReadyCallback{
    private Partner partner = new Partner();
    private LatLng latLng;
    private String idDetail;
    private String sPhoto;
    private String sName;
    private ImageView imageView;
    private TextView tvStreet;
    private TextView tvNumber;
    private Button btnMaps;
    private FloatingActionButton btnCall;
    public static CameraPosition cameraPosition;
    private TextView tvState;
    private TextView tvCity;
    private TextView tvDiscountCard;
    private TextView tvDiscountDeadline;
    private TextView tvDiscount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getAllExtras();
        initRetrofit(idDetail);
        initToolbar();

        btnMaps = findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+partner.getStreet_partner()+" "+partner.getNumber_partner()+","+partner.getName_city()+" "+partner
                .getName_state());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        btnCall = findViewById(R.id.btnCall);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Detail.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Detail.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_DIAL);
                    intentLigar.setData(Uri.parse("tel:" + partner.getCommercial_phone_partner()));
                    startActivity(intentLigar);
                }
            }
            });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarTest);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(sName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void getAllExtras() {
        Intent intent = getIntent();
        idDetail = intent.getStringExtra("idPartner");
        sPhoto = intent.getStringExtra("photo");
        sName = intent.getStringExtra("name");
    }

    private void bindViews() {
        imageView = findViewById(R.id.myImage);
        tvStreet = findViewById(R.id.detailStreet);
        //tvNumber = findViewById(R.id.detailNumber);
        tvCity = findViewById(R.id.detailCity);
        tvState = findViewById(R.id.detailState);
        TextView card = findViewById(R.id.card);
        TextView deadline = findViewById(R.id.deadline);
        TextView money = findViewById(R.id.money);

        tvStreet.setText(partner.getStreet_partner() + ", Nº"+partner.getNumber_partner());
        //tvNumber.setText(partner.getNumber_partner());
        tvState.setText(partner.getName_state());
        tvCity.setText(partner.getName_city());
        card.setText(""+partner.getCard_discount_partner()+"%");
        deadline.setText(""+partner.getTerm_discount_partner()+"%");
        money.setText(" "+partner.getDiscount_partner()+"%");

        Glide.with(this)
                .load("http://doacoes.apaetorres.org.br" + sPhoto)
                .error(R.color.colorError)
                .into(imageView);
    }
    public void initRetrofit(String idDetail) {
        Call<DetailDTO> call = new RetrofitInit().getDetailService().getDetailPartners(idDetail);
        call.enqueue(new Callback<DetailDTO>() {

            @Override
            public void onResponse(@NonNull Call<DetailDTO> call, @NonNull Response<DetailDTO> response) {
                DetailDTO dto = response.body();
                partner = dto.getDetailPartner();
                bindViews();
                latLng = getLatLng(partner.getStreet_partner() + " " + partner.getNumber_partner() + " " + partner.getName_city() + "-" + partner.getName_state());
                Log.e("onResponse ", "requisição detalhes concluída!");
                initFragment();

            }

            @Override
            public void onFailure(Call<DetailDTO> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
                Toast.makeText(Detail.this, "Erro na requisição de detalhes.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(Detail.this);
    }

    private void getPosition(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(latLng)
                .title(partner.getFantasy_name_partner()));
    }

    private LatLng getLatLng(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext());
            List<Address> results = geocoder.getFromLocationName(endereco, 1);
            if (!results.isEmpty()) {
                LatLng position = new LatLng(results.get(0).getLatitude(), results.get(0).getLongitude());
                return position;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        getPosition(googleMap);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude,latLng.longitude))
                .zoom(18.f)
                .bearing(300)
                .tilt(30)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),3000,null);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
