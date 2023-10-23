package appdulich.booking.giaodien;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import appdulich.booking.datalocal.DatalocalManager;
import appdulich.booking.model.SessionManager;
import appdulich.booking.model.Tour;
import appdulich.booking.model.api_interface.TourAPI;
import appdulich.booking.retrofit.RetrofitService;
import appdulich.booking.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thud.helloworld.R;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LoaiTourListAdapter adapter;
    BottomNavigationView bottomNavigationView;

    public static ArrayList<Tour> toursListArray;
    DrawerLayout drawerLayout;

    Toolbar toolbar;
    private EditText edtSearch;
    private ImageButton imgImageButtonSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager sessionManager = new SessionManager(this);
        String token = sessionManager.getToken();
        toursListArray = new ArrayList<>(); // Khởi tạo List Tour

        AnhXaId();

        bottomNavigationView.setSelectedItemId(R.id.mnu_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnu_cart:
                    startActivity(new Intent(getApplicationContext(), GioHangActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.mnu_home:
                    return true;
                case R.id.mnu_tools:
                    startActivity(new Intent(getApplicationContext(), ToolsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
        listener();
        loadTourList();

    }

    public void listener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            loadTourList();
        });
    }

    private long getCurrentUserId() {
        // Khởi tạo DatalocalManager
        DatalocalManager.init(this);
        // Lấy token từ SharedPreferences
        String token = DatalocalManager.getInstance().mySharedPreferences.getStringValue("token");

        Long userId = DatalocalManager.getUser().getId();
        // Trả về user id
        return userId;
    }

    public void AnhXaId() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_Main);
        edtSearch = findViewById(R.id.edt_search);
        imgImageButtonSearch = findViewById(R.id.imgbtn_search);

        toolbar = findViewById(R.id.tool_bar_trangchu);

        recyclerView = findViewById(R.id.recycle_main_tour);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

    }

    private void loadTourList() {
        RetrofitService retrofitService = new RetrofitService(this);
        TourAPI tourAPI = retrofitService.getRetrofit().create(TourAPI.class);
        tourAPI.getAllTour()
                .enqueue(new Callback<List<Tour>>() {
                    @Override
                    public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                        if (!response.body().isEmpty()) {
                            populateListView(response.body());
                        } else {
                            Log.d(TAG, "Lỗi: " + response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Tour>> call, Throwable t) {
                        Log.d(TAG, "Lỗi: " + t.getMessage());
                        Toast.makeText(MainActivity.this,
                                "Không Lấy Được Tour", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Tour> tourList) {
        ListTourAdapter tourListAdapter = new ListTourAdapter(getApplicationContext(), tourList);
        recyclerView.setAdapter(tourListAdapter);
    }

}

class ListTourAdapter extends RecyclerView.Adapter<ListTourAdapter.ViewHolder> {
    private Context context;
    private List<Tour> mTourList = new ArrayList<>();

    private AdapterView.OnItemClickListener listener;

    public ListTourAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tour, parent, false));
    }

    public ListTourAdapter(Context context, List<Tour> tourList) {
        if (!tourList.isEmpty()) {
            this.mTourList = tourList;
            notifyDataSetChanged();
            this.context = context;

        }else {
            this.context = context;
        }

    }

    private void onClickGoToDetail(Tour tour) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_tour", tour);
        bundle.putLong("object_id", tour.getId());
        Log.d("onClickGoToDetail", "onClickGoToDetail id: " + tour.getId());
        intent.putExtras(bundle);
        // Thêm FLAG_ACTIVITY_NEW_TASK trước khi mở Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Tour tour = mTourList.get(position);

        Utils utils = new Utils();

//        String strDateTimeXP = tour.getNgayGioXuatPhat();
//        String cvDateTimeNgayGioXP = utils.convertTimeStampToDateTime(strDateTimeXP);
//
//        String strNgayVe = tour.getNgayVe();
//        String cvNgayVe = utils.convertTimeStampToDate(strNgayVe);
//

        holder.tv_nameTour.setText(tour.getName());
        holder.tv_ngayGioDi.setText("Xuất Phát: " + tour.getNgayGioXuatPhat());
        holder.tv_ngayVe.setText("Ngày Về: " + tour.getNgayVe());
        holder.tv_noiKhoiHanh.setText("Khởi Hành Tại: " + tour.getNoiKhoiHanh());
        Picasso.get().load(tour.getImageUrls()).into(holder.img_Tour);
        double price = tour.getGiaThamKhao();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
        String formattedPrice = decimalFormat.format(price);
        holder.tv_giaThamKhao.setText("Giá Vé: " + formattedPrice);

        holder.tv_soVeConLai.setText("Sô vé còn lại: " + String.valueOf(tour.getSoLuongVe()));

        holder.linearLayout.setOnClickListener(view ->
                onClickGoToDetail(tour));
        holder.btn_chiTiet.setOnClickListener(view ->
                onClickGoToDetail(tour));
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTourList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_Tour;
        TextView tv_nameTour, tv_ngayGioDi, tv_ngayVe, tv_giaThamKhao, tv_noiKhoiHanh, tv_soVeConLai;
        Button btn_chiTiet;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Tour = itemView.findViewById(R.id.item_image_main_tour);
            tv_nameTour = itemView.findViewById(R.id.item_tentour);
            tv_ngayGioDi = itemView.findViewById(R.id.item_startTrip);
            tv_ngayVe = itemView.findViewById(R.id.item_endTrip);
            tv_giaThamKhao = itemView.findViewById(R.id.item_price);
            tv_noiKhoiHanh = itemView.findViewById(R.id.item_noiKhoiHanh);
            tv_soVeConLai = itemView.findViewById(R.id.item_slot);
            linearLayout = itemView.findViewById(R.id.layout_itemTour);
            btn_chiTiet = itemView.findViewById(R.id.btn_chiTietTour);
        }
    }
}