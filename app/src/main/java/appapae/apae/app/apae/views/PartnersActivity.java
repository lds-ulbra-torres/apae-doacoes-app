package appapae.apae.app.apae.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import appapae.apae.app.apae.R;
import appapae.apae.app.apae.adapters.HidingScrollListener;
import appapae.apae.app.apae.adapters.partner.PartnerAdapter;
import appapae.apae.app.apae.dao.PartnerDao;
import appapae.apae.app.apae.dto.PartnerDTO;
import appapae.apae.app.apae.models.Partner;
import appapae.apae.app.apae.retrofit.RetrofitInit;
import appapae.apae.app.apae.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartnersActivity extends AppCompatActivity {
    public static String name;

    Toolbar toolbar, searchtollbar;
    Menu search_menu;
    MenuItem item_search;
    private LinearLayout mToolbarContainer;
    private ArrayList<Partner> searchablePartnerList = new ArrayList<>();
    private PartnerDao partnerDao;
    private PartnerAdapter recyclerAdapter;
    private int mToolbarHeight;
    private String idCat;
    private SwipeRefreshLayout swipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);
        toolbar = findViewById(R.id.toolbar);
        mToolbarHeight = Utils.getToolbarHeight(this);
        mToolbarContainer = findViewById(R.id.toolbarContainerPartner);
        setSearchtollbar();
        Intent intent = getIntent();
        idCat = intent.getStringExtra("id");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(intent.getStringExtra("name"));
        getSupportActionBar().setSubtitle("Categorias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipe = findViewById(R.id.swipe_partners);
        if(verifyConnection()){
            initRetrofit(idCat);
            initRecyclerView();
        } else {
            Toast.makeText(this, "Por favor, verifique sua internet.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Deslize para baixo para atualizar.", Toast.LENGTH_LONG).show();


        }
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (verifyConnection()){
                    initRetrofit(idCat);
                    initRecyclerView();
                }
                if (!verifyConnection()) {
                    Toast.makeText(PartnersActivity.this, "Por favor, verifique sua internet.", Toast.LENGTH_SHORT).show();

                    swipe.setRefreshing(false);
                }
            }
        });

    }

    public void initRetrofit(final String idCat) {
        Call<PartnerDTO> call = new RetrofitInit().getPartnerService().getPartners(idCat);

        call.enqueue(new Callback<PartnerDTO>() {
            @Override
            public void onResponse(@NonNull Call<PartnerDTO> call, @NonNull Response<PartnerDTO> response) {
                PartnerDTO dto = response.body();
                partnerDao = new PartnerDao(getApplicationContext());
                searchablePartnerList = dto.getPartners();
                partnerDao.sync(searchablePartnerList);
                searchablePartnerList.clear();
                initRecyclerView();
                Log.e("onResponse ", "requisição concluída!");
                swipe.setRefreshing(false);


            }

            @Override
            public void onFailure(Call<PartnerDTO> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
                Toast.makeText(PartnersActivity.this, "APAE-02 - Erro ao buscar parceiros.", Toast.LENGTH_SHORT).show();
                swipe.setRefreshing(false);
            }
        });
    }

    private void initRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.recyclerViewPartners);
        int paddingTop = Utils.getToolbarHeight(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setPadding(recyclerView.getPaddingLeft(), paddingTop, recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        partnerDao = new PartnerDao(getApplicationContext());
        recyclerAdapter = new PartnerAdapter(this, partnerDao.getPartnersDataBase());
        partnerDao.clearPartnersFromDatabase();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnScrollListener(new HidingScrollListener(this) {
            @Override
            public void onMoved(int distance) {
                mToolbarContainer.setTranslationY(-distance);
                search_menu.close();
            }

            @Override
            public void onShow() {
                mToolbarContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void onHide() {
                mToolbarContainer.animate().translationY(-mToolbarHeight).setInterpolator(new AccelerateInterpolator(4)).start();
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
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_search:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.searchFor, 1, true, true);
                else
                    searchtollbar.setVisibility(View.VISIBLE);

                item_search.expandActionView();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setSearchtollbar() {
        searchtollbar = findViewById(R.id.searchFor);
        if (searchtollbar != null) {
            searchtollbar.inflateMenu(R.menu.menu_search);
            search_menu = searchtollbar.getMenu();

            searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.toolbar, 1, true, false);
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
                        circleReveal(R.id.searchFor, 1, true, false);
                    } else
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

    public void initSearchView() {

        final android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard
        searchView.setMaxWidth(Integer.MAX_VALUE);

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
                recyclerAdapter.getFilter().filter(query);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }

            public void callSearch(String query) {
                //Do searching
                Log.i("query", "" + query);

            }

        });

    }

    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
        final View myView = findViewById(viewID);

        int width = myView.getWidth();

        if (posFromRight > 0)
            width -= (posFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);
        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx = width;
        int cy = myView.getHeight() / 2;

        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);

        anim.setDuration((long) 220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if (isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();


    }

    public boolean verifyConnection() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();

    }

}
