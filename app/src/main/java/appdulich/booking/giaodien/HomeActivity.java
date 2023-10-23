/*
package appdulich.booking.giaodien;

import static thud.helloworld.R.id.tour_recycleview2_home_activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import appdulich.booking.adapter.TourAdapter;
import appdulich.booking.model.Tour;
import appdulich.booking.model.api_interface.TourAPI;
import appdulich.booking.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thud.helloworld.R;
import thud.helloworld.databinding.ActivityMainBinding;

//  phút thứ 9:03
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavigationView mNavigationView;

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    private RecyclerView tourRecycleView2;
    DrawerLayout drawerLayout;
    SwipeRefreshLayout swipeRefreshLayoutTour;
    private BottomNavigationView mBottomNavigationView;
    private EditText edtSearch;
    private ImageButton imgImageButtonSearch;
    private String urlSearch = "http://10.0.2.2:8088/api/search/tour/";
    private boolean isSearch = false;
    private TourAdapter tourAdapter;
    private TourAdapter.IClickAddToCartListener iClickAddToCartListener;
    public static int cart_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AnhXa();
        ActionBar();
//        ActionViewFlipper();

        tourRecycleView2 = findViewById(R.id.tour_recycleview2_home_activity);
        tourRecycleView2.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayoutTour = findViewById(R.id.swipeRefreshLayout_Tour);
        tourRecycleView2 = findViewById(R.id.tour_recycleview2_home_activity);

        edtSearch = findViewById(R.id.edt_search);
        imgImageButtonSearch = findViewById(R.id.imgbtn_search);
        // Setting the layout as Linear for vertical orientation to have swipe behavior
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        tourRecycleView2.setLayoutManager(linearLayoutManager);


        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        openHomePage();
                        mNavigationView.setCheckedItem(R.id.nav_home);
                        break;
                    case R.id.action_cart:
                        openTools();
                        mNavigationView.setCheckedItem(R.id.nav_cart);
                        break;
                    case R.id.action_business:
                        openTools();
                        mNavigationView.setCheckedItem(R.id.nav_business);
                        break;
                    case R.id.action_category:
                        openTools();
                        mNavigationView.setCheckedItem(R.id.nav_category);
                        break;
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        swipeRefreshLayoutTour.setOnRefreshListener(() -> {
            swipeRefreshLayoutTour.setRefreshing(false);
            loadTour();
        });


        //Handle click, search Tour by api
        edtSearch.setOnClickListener(view -> {
            //Get text from edtSearch
            String query = edtSearch.getText().toString().trim();
            if (TextUtils.isEmpty(query)) {
                loadTour();
            } else {
                searchTour(query);
            }
        });

        imgImageButtonSearch.setOnClickListener(view -> {
            urlSearch = " ";

            //Get text from edtSearch
            String query = edtSearch.getText().toString().trim();
            if (TextUtils.isEmpty(query)) {
                loadTour();
            } else {
                searchTour(query);
            }
        });

    }

    private void openTools() {
    }

    private void openHomePage() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.nav_business:
                startActivity(new Intent(this, ToolsActivity.class));
            case R.id.nav_cart:
                startActivity(new Intent(this, CartActivity.class));
            case R.id.nav_category:
                startActivity(new Intent(this, CategoryListActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

    private void searchTour(String query) {
        isSearch = true;
        RetrofitService retrofitService = new RetrofitService();
        TourAPI tourAPI = retrofitService.getRetrofit().create(TourAPI.class);
        tourAPI.searchTour(query)
                .enqueue(new Callback<List<Tour>>() {
                    @Override
                    public void onResponse(Call<List<Tour>> call,
                                           Response<List<Tour>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Tour>> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, "Failed To Search Tour",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadTour() {

        RetrofitService retrofitService = new RetrofitService();
        TourAPI tourAPI = retrofitService.getRetrofit().create(TourAPI.class);
        tourAPI.getAllTour()
                .enqueue(new Callback<List<Tour>>() {
                    @Override
                    public void onResponse(Call<List<Tour>> call,
                                           Response<List<Tour>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Tour>> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, "Failed To Load List Tour",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Tour> body) {
        TourAdapter tourAdapter = new TourAdapter(this, body);
        tourRecycleView2.setAdapter(tourAdapter);
    }


    private void ActionViewFlipper() {
        Drawable drawable;
        List<String> listQuangCaoLink = new ArrayList<>();

        listQuangCaoLink.add("https://statics.vinpearl.com/du-lich-mien-trung----11_1627291622.jpg");
        listQuangCaoLink.add("https://statics.vinpearl.com/du-lich-mien-trung-12_1627290220.jpg");
        listQuangCaoLink.add("https://statics.vinpearl.com/du-lich-mien-trung-8_1627290496.jpg");
        //Link
        for (int i = 0; i < listQuangCaoLink.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(listQuangCaoLink.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);

    }

    public void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }


    public void AnhXa() {
        toolbar = findViewById(R.id.tool_bar_trangchu);
//        viewFlipper = findViewById(R.id.viewflipper);
        tourRecycleView2 = findViewById(tour_recycleview2_home_activity);
        drawerLayout = findViewById(R.id.drawerlayout);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            mBottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
        } else if (id == R.id.nav_category) {
            mBottomNavigationView.getMenu().findItem(R.id.action_category).setChecked(true);
        } else if (id == R.id.nav_cart) {
            mBottomNavigationView.getMenu().findItem(R.id.action_cart).setChecked(true);
        } else if (id == R.id.nav_business) {
            mBottomNavigationView.getMenu().findItem(R.id.action_business).setChecked(true);
        }
//        setTitleToolbar();
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}*/
