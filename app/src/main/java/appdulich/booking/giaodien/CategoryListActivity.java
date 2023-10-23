package appdulich.booking.giaodien;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import appdulich.booking.model.Category;
import appdulich.booking.model.Tour;
import appdulich.booking.model.api_interface.CategoryAPI;
import appdulich.booking.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thud.helloworld.R;

public class CategoryListActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LoaiTourListAdapter adapter;
    final String TAG = "LoaiTour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        anhXaId();
        listener();
        loadCategory();
    }

    public void listener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
//            loadCategory();
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(CategoryListActivity.this, ToolsActivity.class));
        finish();
        return super.onOptionsItemSelected(item);

    }

    public void anhXaId() {
        recyclerView = findViewById(R.id.recyclerview_loaiTour);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_Category);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Loại Tour");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void loadCategory() {
        RetrofitService retrofitService = new RetrofitService(this);
        CategoryAPI categoryAPI = retrofitService.getRetrofit().create(CategoryAPI.class);
        categoryAPI.getAllCategory()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.d(TAG, "Lỗi: " + t.getMessage());
                        Toast.makeText(CategoryListActivity.this,
                                "Không Lấy Được Loại Tour", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void populateListView(List<Category> categoryList) {
        LoaiTourListAdapter loaiTourListAdapter = new LoaiTourListAdapter(categoryList, getApplicationContext());
        recyclerView.setAdapter(loaiTourListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_on_cate, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

class LoaiTourListAdapter extends RecyclerView.Adapter<LoaiTourListAdapter.ViewHolder> {
    private Context context;
    private List<Category> mCategoryList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_loaitour, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category category = mCategoryList.get(position);

        holder.tenLoaiTour.setText(category.getName());
        holder.noiDung.setText(category.getNoiDung());
    }

    public LoaiTourListAdapter(List<Category> categoryList, Context context) {
//        if (categoryList != null) {
        mCategoryList = categoryList;

//        }
        this.context = context;
        notifyDataSetChanged();
//        this.mCategoryList = categoryList;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    private void onClickGoToTourByLoaiTour(Tour tour) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable("object_tour", tour);
//        intent.putExtras(bundle);
//        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public void setData(List<Category> list) {
        this.mCategoryList = list;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tenLoaiTour, noiDung;
        private TextView loaiTourId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tenLoaiTour = itemView.findViewById(R.id.item_loaiTour_tenLoaiTour);
            noiDung = itemView.findViewById(R.id.item_loaiTour_noiDung);

        }
    }
}