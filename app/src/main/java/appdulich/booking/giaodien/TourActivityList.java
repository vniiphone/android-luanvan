/*
package appdulich.booking.giaodien;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

//import appdulich.booking.adapter.TourAdapter;
import appdulich.booking.model.Tour;
import appdulich.booking.model.api_interface.TourAPI;
import appdulich.booking.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thud.helloworld.R;

//https://www.youtube.com/watch?v=IZ1sz5Jm5sQ 
public class TourActivityList extends AppCompatActivity {
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    long id_cate = 0;
    final String TAG = "TourActivityList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);

        recyclerView = findViewById(R.id.tour_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_Tour);
        recyclerView = findViewById(R.id.tour_recycleview);

        // Setting the layout as Linear for vertical orientation to have swipe behavior
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // SetOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                loadTour();

            }
        });
        Intent intent = getIntent();
        id_cate = intent.getLongExtra("KEY_ID_CATE", 0);
        loadTour();
    }

    private void loadTour() {
        RetrofitService retrofitService = new RetrofitService();
        TourAPI tourAPI = retrofitService.getRetrofit().create(TourAPI.class);
        tourAPI.getToursByCate(id_cate)
                .enqueue(new Callback<List<Tour>>() {
                    @Override
                    public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                        List<Tour> tourList = response.body();
                        populateListView(tourList);
                        Log.d(TAG, "Tour List: " + tourList);
                    }

                    @Override
                    public void onFailure(Call<List<Tour>> call, Throwable t) {
                        Toast.makeText(TourActivityList.this, "Failed To Load List Tour",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Tour> tourList) {
        TourAdapter tourAdapter = new TourAdapter(this, tourList);
        recyclerView.setAdapter(tourAdapter);
    }

}*/
