package laz.appapae.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import laz.appapae.R;
import laz.appapae.adapters.PartnerAdapter;
import laz.appapae.controller.PartnerController;
import laz.appapae.model.Partner;
import laz.appapae.model.PartnerCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private ListView lvPartners;
    public static int counterColor = 0;
    private PartnerAdapter adapter;
    private ArrayList<Partner> searchablePartnerList;

    private PartnerController partnerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        partnerController = new PartnerController(MainActivity.this);
        searchablePartnerList = new ArrayList<>();

        lvPartners = (ListView) findViewById(R.id.lvPartners);

        lvPartners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailPartnerActivity.class);
                Partner partner = (Partner) lvPartners.getAdapter().getItem(position);
                intent.putExtra("name", partner.getFantasyName());
                intent.putExtra("phone", partner.getPhone());
                intent.putExtra("neighborhood", partner.getNeighborhood());
                intent.putExtra("number", partner.getStreetNumber());
                intent.putExtra("street", partner.getStreetName());
                intent.putExtra("discount", String.valueOf(partner.getDiscount()));
                intent.putExtra("photo", partner.getPhoto());
                intent.putExtra("state", partner.getNameState());
                intent.putExtra("city", partner.getNameCity());
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ApaeTorres");
        actionBar.setSubtitle("Parceiros");

        loadListPartners();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadListPartners();
    }

    private void loadListPartners(){
        partnerController.getPartners(new PartnerCallback<ArrayList<Partner>>() {
            @Override
            public void onSuccess(ArrayList<Partner> partners) {
                adapter = new PartnerAdapter(MainActivity.this, partners);
                searchablePartnerList = partners;
                lvPartners.setAdapter(adapter);
            }
            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "Ocorreu um erro, por favor, feche e abra o aplicativo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SearchView searchView = new SearchView(MainActivity.this);
        //searchView.setBackgroundColor(ContextCompat.getColor(this,android.R.color.white));
        //searchView.setForegroundTintMode();
        searchView.setOnQueryTextListener(new PartnerFilter());

        EditText editText  = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);


        ImageView closeBtn = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        closeBtn.setColorFilter(Color.WHITE);


        MenuItem search = menu.add(0, 0, 0, "Search");

        search.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        search.setActionView(searchView);

        return true;
    }

    private class PartnerFilter implements SearchView.OnQueryTextListener{

        @Override
        public boolean onQueryTextSubmit(String query) {

            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            ArrayList<Partner> searchable = new ArrayList<>();

            for (Partner p: searchablePartnerList) {
                if(p.getFantasyName().toLowerCase().contains(newText.toLowerCase())){
                    searchable.add(p);
                }
            }

            adapter = new PartnerAdapter(MainActivity.this, searchable);
            lvPartners.setAdapter(adapter);

            return false;
        }
    }

}
