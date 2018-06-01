package com.example.lucca.doeamor_apaetorres.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import com.example.lucca.doeamor_apaetorres.R;
import com.example.lucca.doeamor_apaetorres.adapters.HidingScrollListener;
import com.example.lucca.doeamor_apaetorres.adapters.category.CategoryAdapter;
import com.example.lucca.doeamor_apaetorres.dao.CategoryDao;
import com.example.lucca.doeamor_apaetorres.dto.CategoryDTO;
import com.example.lucca.doeamor_apaetorres.models.Category;
import com.example.lucca.doeamor_apaetorres.retrofit.RetrofitInit;
import com.example.lucca.doeamor_apaetorres.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    Toolbar searchToolbar;
    Menu search_menu;
    MenuItem item_search;
    private RecyclerView recyclerView;
    private ArrayList<Category> searchableCategoryList;
    private CategoryDao categoryDao;
    private int mToolbarHeight;
    private LinearLayout mToolbarContainer;
    private CategoryAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mToolbarContainer =  findViewById(R.id.toolbarContainer);
        toolbar = findViewById(R.id.toolbar);
        searchToolbar = findViewById(R.id.searchFor);
        setSupportActionBar(toolbar);
        setSearchtollbar();
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbarHeight = Utils.getToolbarHeight(this);
        initRetrofit();

    }


    private void initRecyclerView() {

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        int paddingTop = Utils.getToolbarHeight(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setPadding(recyclerView.getPaddingLeft(), paddingTop, recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerAdapter = new CategoryAdapter(this,categoryDao.getCategoriesDataBase());
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

    public void initRetrofit(){
        Call<CategoryDTO> call= new RetrofitInit().getCategoryService().getCategories();

        call.enqueue(new Callback<CategoryDTO>() {
            @Override
            public void onResponse(@NonNull Call<CategoryDTO> call, @NonNull Response<CategoryDTO> response) {
                CategoryDTO dto = response.body();
                searchableCategoryList = dto.getCategory();
                categoryDao = new CategoryDao(getApplicationContext());
                categoryDao.sync(searchableCategoryList);
                searchableCategoryList.clear();
                initRecyclerView();

                Log.e("onResponse ",  "deu certo" );

            }
            @Override
            public void onFailure(Call<CategoryDTO> call, Throwable t) {
                Log.e("onFailure: ",t.getMessage() );
            }
        });
    }
    protected void onRestart() {
        super.onRestart();

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
                Toast.makeText(this, "Ir para Sobre", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_search:
                    circleReveal(R.id.searchFor,1,true,true);
                    searchToolbar.setVisibility(View.VISIBLE);
                item_search.expandActionView();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Ir para Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setSearchtollbar()
    {

        if ( searchToolbar != null) {
            searchToolbar.inflateMenu(R.menu.menu_search);
            search_menu= searchToolbar.getMenu();

            searchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.searchFor,1,true,false);
                    else
                        searchToolbar.setVisibility(View.GONE);
                }
            });

            item_search = search_menu.findItem(R.id.action_filter_search);

            MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.searchFor,1,true,false);
                    }
                    else
                        searchToolbar.setVisibility(View.GONE);
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
            Log.d("toolbar", "setSearchtollbar: NULL");
    }

    public void initSearchView()
    {
        final SearchView searchView =  (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard

        searchView.setSubmitButtonEnabled(false);

        // Change search close button image

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.close);


        // set hint and the text colors

        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Pesquisar categorias...");
        txtSearch.setHintTextColor(Color.DKGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));


        // set the cursor

        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                recyclerAdapter.getFilter().filter(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                recyclerAdapter.getFilter().filter(newText);
                Log.i("query", "" + newText);
                return true;
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

        int width = myView.getWidth();
        System.out.println(width);
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
