package com.example.lucca.doeamor_apaetorres.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucca.doeamor_apaetorres.R;
import com.example.lucca.doeamor_apaetorres.adapters.PartnerAdapter;
import com.example.lucca.doeamor_apaetorres.callbacks.PartnerCallback;
import com.example.lucca.doeamor_apaetorres.controllers.PartnerController;
import com.example.lucca.doeamor_apaetorres.dao.PartnerDao;
import com.example.lucca.doeamor_apaetorres.dto.PartnerDTO;
import com.example.lucca.doeamor_apaetorres.models.Partner;
import com.example.lucca.doeamor_apaetorres.retrofit.RetrofitInit;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartnersActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar, searchtollbar;
    Menu search_menu;
    MenuItem item_search;
    private ExpandableHeightGridView lvPartners;
    private PartnerAdapter adapter;
    private ArrayList<Partner> searchablePartnerList= new ArrayList<>();
    private PartnerController partnerController;
    private PartnerDao partnerDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);
        Intent intent = getIntent();
        String idCat = intent.getStringExtra("id");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getActionBar();
        setSearchtollbar();
        lvPartners =  findViewById(R.id.lvPartners);
        lvPartners.setExpanded(true);

        lvPartners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(PartnersActivity.this, DetailPartner.class);
                Partner partner = (Partner) lvPartners.getAdapter().getItem(position);
                intent.putExtra("name", partner.getFantasy_name_partner());
                intent.putExtra("partnerPhoto", partner.getPhoto_partner());
                intent.putExtra("partnerPhone", partner.getCommercial_phone_partner());
                intent.putExtra("partnerStreet",partner.getStreet_partner());
                intent.putExtra("partnerNumber", partner.getNumber_partner());
                intent.putExtra("partnerState",partner.getCep_partner());
                startActivity(intent);
            }
        });

        retrofitInit(idCat);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void loadListPartners(){
        partnerController.getPartners(new PartnerCallback<ArrayList<Partner>>() {
            @Override
            public void onSuccess(ArrayList<Partner> partners) {
                adapter = new PartnerAdapter(PartnersActivity.this, partners);
                searchablePartnerList = partners;
                lvPartners.setAdapter(adapter);

            }
            @Override
            public void onError() {
                Toast.makeText(PartnersActivity.this, "Ocorreu um erro, por favor, feche e abra o aplicativo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrofitInit(final String idCat){
        Call <PartnerDTO> call= new RetrofitInit().getPartnerService().getPartners(idCat);

        call.enqueue(new Callback<PartnerDTO>() {
            @Override
            public void onResponse(@NonNull Call<PartnerDTO> call, @NonNull Response<PartnerDTO> response) {
                PartnerDTO dto = response.body();
                partnerDao = new PartnerDao(getApplicationContext());
                searchablePartnerList = dto.getPartners();
                partnerDao.insert(searchablePartnerList);
                adapter = new PartnerAdapter(PartnersActivity.this, partnerDao.getPartnersDataBase());
                lvPartners.setAdapter(adapter);
                partnerDao.clearPartnersFromDatabase();
                Log.e("onResponse ",  "deu certo" );

            }
            @Override
            public void onFailure(Call<PartnerDTO> call, Throwable t) {
                Log.e("onFailure: ",t.getMessage() );
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_status:
                Toast.makeText(this, "Home Status Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_search:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.searchtoolbar,1,true,true);
                else
                    searchtollbar.setVisibility(View.VISIBLE);

                item_search.expandActionView();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onSupportNavigateUp(){

        finish();
        return true;
    }
    public void setSearchtollbar()
    {
        searchtollbar =  findViewById(R.id.searchtoolbar);
        if (searchtollbar != null) {
            searchtollbar.inflateMenu(R.menu.menu_search);
            search_menu=searchtollbar.getMenu();

            searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.searchtoolbar,1,true,false);
                    else
                        searchtollbar.setVisibility(View.GONE);
                }
            });

            item_search = search_menu.findItem(R.id.action_filter_search);

            MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.searchtoolbar,1,true,false);
                    }
                    else
                        searchtollbar.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Do something when expanded
                    return true;
                }
            });

            initSearchView();


        } else
            Log.d("MyToolbar", "setSearchtollbar: NULL");
    }

    public void initSearchView()
    {

        final android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard

        searchView.setSubmitButtonEnabled(false);

        // Change search close button image

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.close);

        // set hint and the text colors

        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Pesquisar Parceiros...");
        txtSearch.setHintTextColor(Color.DKGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.black));


        // set the cursor

        AutoCompleteTextView searchTextView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Partner> searchable = new ArrayList<>();
                for (Partner p: searchablePartnerList) {
                    if(p.getFantasy_name_partner().toLowerCase().contains(query.toLowerCase())){
                        searchable.add(p);
                    }
                }
                adapter = new PartnerAdapter(PartnersActivity.this, searchable);
                lvPartners.setAdapter(adapter);
                lvPartners.setExpanded(true);
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Partner> searchable = new ArrayList<>();
                for (Partner p: searchablePartnerList) {
                    if(p.getFantasy_name_partner().toLowerCase().contains(newText.toLowerCase())){
                        searchable.add(p);
                    }
                }

                adapter = new PartnerAdapter(PartnersActivity.this, searchable);
                lvPartners.setAdapter(adapter);
                lvPartners.setExpanded(true);

                return false;
            }

            public void callSearch(String query) {
                //Do searching
                Log.i("query", "" + query);

            }

        });

    }

    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow)
    {
        final View myView = findViewById(viewID);

        int width=myView.getWidth();

        if(posFromRight>0)
            width-=(posFromRight*getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material))-(getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)/ 2);
        if(containsOverflow)
            width-=getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx=width;
        int cy=myView.getHeight()/2;

        Animator anim;
        if(isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0,(float)width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float)width, 0);

        anim.setDuration((long)220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isShow)
                {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if(isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();


    }



}
