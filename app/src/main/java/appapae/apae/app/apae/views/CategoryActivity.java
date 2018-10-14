package appapae.apae.app.apae.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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

import appapae.apae.app.apae.R;
import appapae.apae.app.apae.adapters.HidingScrollListener;
import appapae.apae.app.apae.adapters.category.CategoryAdapter;
import appapae.apae.app.apae.dao.CategoryDao;
import appapae.apae.app.apae.dto.CategoryDTO;
import appapae.apae.app.apae.models.Category;
import appapae.apae.app.apae.retrofit.RetrofitInit;
import appapae.apae.app.apae.utils.Utils;
import com.google.firebase.messaging.FirebaseMessaging;

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
    private LinearLayout mToolbarContainer;
    private ArrayList<Category> searchableCategoryList;
    private CategoryDao categoryDao;
    private int mToolbarHeight;

    private CategoryAdapter recyclerAdapter;
    private SwipeRefreshLayout swipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initToolbar();
        swipe =  findViewById(R.id.swipe_category);
        if(verifyConnection()){
            initRetrofit();
            initRecyclerView();
            FirebaseMessaging.getInstance().subscribeToTopic("global_android");
        } else {
            Toast.makeText(CategoryActivity.this, "Por favor, verifique sua internet.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Deslize para baixo para atualizar.", Toast.LENGTH_LONG).show();


        }
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (verifyConnection()){
                    initRetrofit();
                    initRecyclerView();
                    FirebaseMessaging.getInstance().subscribeToTopic("global_android");
                }
                if (!verifyConnection()) {
                    Toast.makeText(CategoryActivity.this, "Por favor, verifique sua internet.", Toast.LENGTH_SHORT).show();
                    swipe.setRefreshing(false);
                }
            }
        });



       /* if (verifyConnection()) {

            swipe.setRefreshing(false);
            swipe.cancelPendingInputEvents();
        }   else {
            Toast.makeText(CategoryActivity.this, "APAE-01 - Verifique sua internet.", Toast.LENGTH_LONG).show();
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (verifyConnection()){
                        swipe.setRefreshing(false);
                    }
                    if (!verifyConnection()) {
                        Toast.makeText(CategoryActivity.this, "APAE-01 - Verifique sua internet.", Toast.LENGTH_LONG).show();
                    } else {
                        initRetrofit();
                        initRecyclerView();
                        FirebaseMessaging.getInstance().subscribeToTopic("global_android");
                    }
                    swipe.setRefreshing(false);
                }
            });
            swipe.setRefreshing(false);
        }
        swipe.setRefreshing(false);
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initToolbar() {
        mToolbarContainer = findViewById(R.id.toolbarContainer);
        toolbar = findViewById(R.id.toolbar);
        searchToolbar = findViewById(R.id.searchFor);
        setSupportActionBar(toolbar);
        setSearchtollbar();
        mToolbarHeight = Utils.getToolbarHeight(this);
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
                Log.e("onResponse ",  "Requisição finalizada com sucesso!" );
                swipe.setRefreshing(false);

            }
            @Override
            public void onFailure(Call<CategoryDTO> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "Desculpe, estamos com problemas no serviço, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                Log.e("onFailure: ",t.getMessage() );
                swipe.setRefreshing(false);
            }
        });
    }
    private void initRecyclerView() {

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int paddingTop = Utils.getToolbarHeight(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setPadding(recyclerView.getPaddingLeft(), paddingTop, recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        categoryDao = new CategoryDao(getApplicationContext());
        recyclerAdapter = new CategoryAdapter(this,categoryDao.getCategoriesDataBase());
        categoryDao.clearCategoriesFromDataBase();
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
            case R.id.action_search:
                    circleReveal(R.id.searchFor,1,true,true);
                    searchToolbar.setVisibility(View.VISIBLE);
                item_search.expandActionView();
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
        searchView.setMaxWidth(Integer.MAX_VALUE);
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

    public  boolean verifyConnection() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected();

    }

}
