package appdulich.booking.giaodien;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import appdulich.booking.model.Tour;
import appdulich.booking.model.api_interface.TourAPI;
import appdulich.booking.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thud.helloworld.R;

public class TourActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LoaiTourListAdapter adapter;
    final String TAG = "Activity Tour List";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        anhXaId();
        listener();
        loadTourList();
    }

    public void listener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            loadTourList();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(TourActivity.this, ToolsActivity.class));
        return super.onOptionsItemSelected(item);
    }

    public void anhXaId() {
        recyclerView = findViewById(R.id.tour_recycleview_activity_listTour);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_Tour_Activity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Danh Sách Chuyến Đi");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void loadTourList() {
        RetrofitService retrofitService = new RetrofitService(this);
        TourAPI tourAPI = retrofitService.getRetrofit().create(TourAPI.class);
        tourAPI.getAllTour()
                .enqueue(new Callback<List<Tour>>() {
                    @Override
                    public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                        populateListView(response.body());
                        Log.d(TAG, "List Tour: "+ response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Tour>> call, Throwable t) {
                        Log.d(TAG, "Lỗi: " + t.getMessage());
                        Toast.makeText(TourActivity.this,
                                "Không Lấy Được Tour", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void populateListView(List<Tour> tourList) {
        TourListAdapter tourListAdapter = new TourListAdapter(tourList, getApplicationContext());
        recyclerView.setAdapter(tourListAdapter);
    }


}


class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.ViewHolder> {

    private Context mContext;
    private List<Tour> mTourList;


    public TourListAdapter(List<Tour> tourList, Context context) {
        this.mContext = context;
        mTourList = tourList;
        notifyDataSetChanged();
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.
                from(parent.getContext())
                .inflate(R.layout.item_tour, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Tour tour = mTourList.get(position);

        holder.tv_nameTour.setText(tour.getName());
        holder.tv_ngayGioDi.setText("Xuất Phát: " +tour.getNgayGioXuatPhat());
        holder.tv_ngayVe.setText("Ngày Về: " +tour.getNgayVe());
        holder.tv_noiKhoiHanh.setText("Khởi Hành Tại: " +tour.getNoiKhoiHanh());
        Picasso.get().load(tour.getImageUrls()).into(holder.img_Tour);
        double price = tour.getGiaThamKhao();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
        String formattedPrice = decimalFormat.format(price);
        holder.tv_giaThamKhao.setText("Giá Vé: "+formattedPrice);

        holder.tv_soVeConLai.setText("Sô vé còn lại: "+String.valueOf(tour.getSoLuongVe()));

    }

    @Override
    public int getItemCount() {
        return mTourList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_Tour;
        TextView tv_nameTour, tv_ngayGioDi, tv_ngayVe, tv_giaThamKhao, tv_noiKhoiHanh, tv_soVeConLai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
/*

            img_Tour = itemView.findViewById(R.id.item_image);
            tv_nameTour = itemView.findViewById(R.id.item_tentour);
            tv_ngayGioDi = itemView.findViewById(R.id.item_startTrip);
            tv_ngayVe = itemView.findViewById(R.id.item_endTrip);
            tv_giaThamKhao = itemView.findViewById(R.id.item_price);
            tv_noiKhoiHanh = itemView.findViewById(R.id.item_noiKhoiHanh);
            tv_soVeConLai = itemView.findViewById(R.id.item_slot);

*/

        }

    }
}