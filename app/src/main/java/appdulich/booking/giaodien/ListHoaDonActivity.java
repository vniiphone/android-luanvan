package appdulich.booking.giaodien;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import appdulich.booking.model.Invoice;
import appdulich.booking.model.SessionManager;
import appdulich.booking.model.api_interface.InvoiceAPI;
import appdulich.booking.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thud.helloworld.R;

public class ListHoaDonActivity extends AppCompatActivity {
    final String TAG = "ListHoaDonActivity";

    RecyclerView hoadonRecycleView;
    SwipeRefreshLayout swipeRefreshLayout;
    private String token = "";
    private long user_id = 0;


    public static List<Invoice> hoaDonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hoa_don);


        SessionManager sessionManager = new SessionManager(this);
        token = sessionManager.getToken();
        user_id = sessionManager.getUserId();
        Log.d(TAG, "Token: " + token);

        hoaDonList = new ArrayList<>(); // Khởi tạo List hoaDonList

        anhXaId();
        listener();
        loadHoaDonList();
    }

    private void anhXaId() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_list_hoadon);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Danh Sách Vé Tour");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        hoadonRecycleView = findViewById(R.id.recyclerview_hoadon_list);
        hoadonRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onRestart() {
        loadHoaDonList();
        super.onRestart();
    }

    public void listener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            loadHoaDonList();
        });
    }

    private void loadHoaDonList() {
        RetrofitService retrofitService = new RetrofitService(this);
        String newToken = token;
        long id = user_id;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();
        InvoiceAPI api = retrofit.create(InvoiceAPI.class);
        api.getAllInvoiceByUserId(id).enqueue(new Callback<List<Invoice>>() {
            @Override
            public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Success to get Invoice: " + response.body());
                    populateListView(response.body());
                } else {
                    Log.d(TAG, "Failed to get Invoice: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Invoice>> call, Throwable t) {
                Log.d(TAG, "Failed to get Invoice: " + t.getMessage());

            }
        });

    }

    private void populateListView(List<Invoice> invoices) {
        ListHoaDonApdater listHoaDonApdater = new ListHoaDonApdater(getApplicationContext(), invoices);
        hoadonRecycleView.setAdapter(listHoaDonApdater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnu_back)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_on_hoadon, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

class ListHoaDonApdater extends RecyclerView.Adapter<ListHoaDonApdater.ViewHolder> {
    private Context context;
    private List<Invoice> mInvoice = new ArrayList<>();

    public ListHoaDonApdater() {
    }

    public ListHoaDonApdater(Context context, List<Invoice> mInvoice) {
        if (!mInvoice.isEmpty()) {
            this.context = context;
            this.mInvoice = mInvoice;
            notifyDataSetChanged();
        } else {
            this.context = context;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListHoaDonApdater.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hoadon, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Invoice invoice =  mInvoice.get(position);
        String strTenTour = invoice.getTour().getName();
        String strNgayGioXP = invoice.getTour().getNgayGioXuatPhat();
        String strNoiKH = invoice.getTour().getNoiKhoiHanh();
        String strSoluongve = String.valueOf(invoice.getSoLuongVeDaDat());
        Boolean wasPay = invoice.isWasPay();
        String strPay = "Chưa Xác Nhận";
        if (wasPay) {
            strPay = "Hoàn Thành";
        }
        String url = invoice.getTour().getImageUrls();

        Double totalVe = invoice.getTotalPrice();
        double price = totalVe;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
        String formattedPrice = decimalFormat.format(price);

        holder.tv_tenTour.setText(strTenTour);
        holder.tv_status.setText(strPay);
        holder.tv_ngayXP.setText("Ngày Xuất Phát: "+strNgayGioXP);
        holder.tv_noiKH.setText("Nơi Khởi Hành: "+strNoiKH);
        holder.tv_soluongvedat.setText("Số Lượng Vé Đã Đặt: "+strSoluongve);
        holder.tv_totalprice.setText("Tổng Cộng: " + formattedPrice);
        Picasso.get().load(url).into(holder.img_tour);

        holder.linearLayout.setOnClickListener(view ->{
            getOnDetailHoaDon(invoice);
        });
        holder.btn_xemthem.setOnClickListener(view ->{
            getOnDetailHoaDon(invoice);
        });

    }
    private void getOnDetailHoaDon(Invoice invoice) {

        Intent intent = new Intent(context, InvoiceActivity.class);
//        intent.putExtra("isEditingProfile", true);
//        Bundle bundle = new Bundle();
//        bundle.putLong("invoice_id", invoice.getId());
//        bundle.putSerializable("invoice_profile", invoice);
//        intent.putExtras(bundle);
        // Thêm FLAG_ACTIVITY_NEW_TASK trước khi mở Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mInvoice.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenTour, tv_status, tv_ngayXP, tv_noiKH, tv_soluongvedat, tv_totalprice;
        ImageView img_tour;
        Button btn_xemthem;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_tenTour = itemView.findViewById(R.id.item_hoadon_tentour);
            tv_status = itemView.findViewById(R.id.item_hoadon_status);
            tv_ngayXP = itemView.findViewById(R.id.item_hoadon_ngayxp);
            tv_noiKH = itemView.findViewById(R.id.item_hoadon_noikhoihanh);
            tv_soluongvedat = itemView.findViewById(R.id.item_hoadon_soluongvedat);
            tv_totalprice = itemView.findViewById(R.id.item_hoadon_totalprice);
            btn_xemthem = itemView.findViewById(R.id.item_hoadon_btn_xemthem);
            img_tour = itemView.findViewById(R.id.item_hoadon_image_tour);
            linearLayout = itemView.findViewById(R.id.linear_hoadon_item);
        }
    }
}