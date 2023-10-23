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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import appdulich.booking.datalocal.AppUtils;
import appdulich.booking.model.DiemDen;
import appdulich.booking.model.GioHang;
import appdulich.booking.model.LichTrinhTour;
import appdulich.booking.model.SessionManager;
import appdulich.booking.model.Tour;
import appdulich.booking.model.api_interface.CartItemAPI;
import appdulich.booking.model.api_interface.LichTrinhTourAPI;
import appdulich.booking.model.api_interface.TourAPI;
import appdulich.booking.retrofit.RetrofitService;
import appdulich.booking.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thud.helloworld.R;

public class DetailActivity extends AppCompatActivity {

    Utils utils;

    final String TAG = "DetailActivity";
    TextView
            tenTour, giaThamKhao, tomTat, ngayGioXp, noiKhoiHanh, ngayVe, soVeConLai, // Tour Detail
            tenKhachSan, giaPhong;// Khách Sạn
//            tenDiemDen, diaChiDiemDen, giaVeThamQuanDiemDen,

    RecyclerView rcl_lichtrinhtour, rcl_diemden;
    Toolbar toolbar;

    ImageView imgAnhTour;
    Button btnAddCart;


    Context context;
    String strName, strImageURL;
    int intSlot;
    double intPrice;
    String dateNgaydi, dateNgayve;
    long tourid = 0;
    AppUtils appUtils;
    private String token = "";
    private long user_id = 0;
    private long tour_id;
    String soVeEDT = "";
    String soLuongVeTour = "";

    private Tour tour;

    public static ArrayList<LichTrinhTour> lichTrinhTourList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);

        SessionManager sessionManager = new SessionManager(this);
        token = sessionManager.getToken();
        user_id = sessionManager.getUserId();
        Log.d(TAG, "Token: " + token);
        tour = new Tour();
        AnhXaId();

        lichTrinhTourList = new ArrayList<>();

        getTourDetail();

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddToCartDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_on_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void AnhXaId() {
//        toolbar = findViewById(R.id.tool_bar_trangchu);
        toolbar = findViewById(R.id.tool_bar_detail);
        setSupportActionBar(toolbar);
//        setTitle("Loại Tour");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tenTour = findViewById(R.id.txt_nameTour);
        giaThamKhao = findViewById(R.id.tv_giathamkhao);
        ngayGioXp = findViewById(R.id.txt_ngaygioXP);
        ngayVe = findViewById(R.id.tv_ngayVe);
        soVeConLai = findViewById(R.id.tv_soVeConlai);
        imgAnhTour = findViewById(R.id.img_chiTiet);
        noiKhoiHanh = findViewById(R.id.tv_noikhoihanh);
        btnAddCart = findViewById(R.id.btn_addtocart);
        rcl_lichtrinhtour = findViewById(R.id.recycle_lichtrinhtour);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcl_lichtrinhtour.setLayoutManager(new LinearLayoutManager(this));

//        MaterialButton btnAdd = findViewById(R.id.btn_done_addTourToCart);
    }

    private void showAddToCartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_addtocart, null);
        builder.setView(dialogView);

        final EditText editTextQuantity = dialogView.findViewById(R.id.edt_soLuongVeDat);

        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            int soVeDat = Integer.parseInt(editTextQuantity.getText().toString());
            if (soVeDat != 0) {
                if (soVeDat <= tour.getSoLuongVe()) {
                    soVeEDT = editTextQuantity.getText().toString();
//                    Log.d(TAG, "SoVe dat: " + soVeEDT);
//                    Toast.makeText(DetailActivity.this, "Vé " + soVeEDT, Toast.LENGTH_SHORT).show();
                    callApiToAddToCart(tour_id, user_id, Integer.parseInt(soVeEDT), token);
                } else {
                    Log.d(TAG, "SoVe dat nho ho so luong ve con lại ");
                    Toast.makeText(DetailActivity.this, "Số vé phải ít hơn vé còn lại", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DetailActivity.this, "Vui lòng nhập số vé hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });


        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.create().show();

    }

    public void callApiToAddToCart(long tourId, long userId, int soLuongVeDat, String accessToken) {
        Log.d(TAG, "TourID: " + tourId);
        Log.d(TAG, "userId: " + userId);
        Log.d(TAG, "soLuongVeDat: " + soLuongVeDat);
        Log.d(TAG, "accessToken: " + accessToken);

        if (accessToken != null) {
            RetrofitService retrofitService = new RetrofitService(this);
            String newToken = token;
            retrofitService.updateToken(newToken);
            Retrofit retrofit = retrofitService.getRetrofit();
            CartItemAPI api = retrofit.create(CartItemAPI.class);
            api.createCartItem(
                    userId, tour_id, soLuongVeDat
            ).enqueue(new Callback<GioHang>() {
                @Override
                public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                    if (response.body() != null) {
                        Toast.makeText(DetailActivity.this, "Thành Công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d(TAG, "Lỗi khi call api new Booking");
                        Toast.makeText(DetailActivity.this, "Body Null", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GioHang> call, Throwable t) {
                    Log.d(TAG, "Lỗi khi call api new Booking" + t.getMessage());
                    Toast.makeText(DetailActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Log.d(TAG, "Lỗi Token NULL");
            Toast.makeText(DetailActivity.this, "Chưa Có Dữ Liệu Đăng Nhập", Toast.LENGTH_SHORT).show();
        }

    }


    public void getTourDetail() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Log.d(TAG, "Bundle not found");
            return;
        } else {
            Long id = (Long) bundle.get("object_id");
            tour_id = (Long) bundle.get("object_id");
            Log.d(TAG, "ID Tour: " + id);
            callAPITourDetailByID(id);
            callAPILichTrinhTourByTourID(id);
        }
    }


    private void callAPITourDetailByID(Long id) {
        RetrofitService retrofitService = new RetrofitService(this);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();

        TourAPI tourAPI = retrofit.create(TourAPI.class);
        tourAPI.getTourDetailByID(id)
                .enqueue(new Callback<Tour>() {
                    @Override
                    public void onResponse(Call<Tour> call, Response<Tour> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "onResponse Tour: " + response.body());
                            // Tiếp tục xử lý thông tin tour sau khi kiểm tra là response không null
                            setTourDetail(response.body());
                            setParametersTour(response.body());
                        } else {
                            Log.d(TAG, "Response không hợp lệ hoặc response.body() là null: " + call.toString());
//                            Log.d(TAG, "Response không hợp lệ hoặc response.body() là null: "+response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Tour> call, Throwable t) {
                        Log.d(TAG, "onFailure Tour: " + t.getMessage());
                    }
                });
    }

    public void setParametersTour(Tour tourSet) {
        if (tourSet != null) {
            this.tour = tourSet;

        } else {
            Log.d(TAG, "Tour Set is NULL");
        }
    }

    private void callAPILichTrinhTourByTourID(Long tourID) {
        // Khởi tạo Retrofit và ApiService ở một vị trí chung hoặc Application class
        RetrofitService retrofitService = new RetrofitService(this);

        // Get the token from your SessionManager
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();

        LichTrinhTourAPI api = retrofit.create(LichTrinhTourAPI.class);
        api.getLichTrinhTourByTourID(tourID)
                .enqueue(new Callback<List<LichTrinhTour>>() {
                    @Override
                    public void onResponse(Call<List<LichTrinhTour>> call, Response<List<LichTrinhTour>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d(TAG, "onResponse LichTrinhTour: " + response.body());
                            populateListView(response.body());
//                            lichTrinhTourList(response.body());
                        } else {
                            Log.d(TAG, "Response không hợp lệ hoặc response.body() là null callAPILichTrinhTourByTourID: " + call.toString());
//                            Log.d(TAG, "Response không hợp lệ hoặc response.body() là null: "+response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LichTrinhTour>> call, Throwable t) {
                        Log.d(TAG, "onFailure" + t.getMessage());

                    }
                });
    }

    private void populateListView(List<LichTrinhTour> listLichTrinh) {
        ListLichTrinhAdapter listLichTrinhAdapter = new ListLichTrinhAdapter(getApplicationContext(), listLichTrinh);
        rcl_lichtrinhtour.setAdapter(listLichTrinhAdapter);
    }


    public void setTourDetail(Tour tourDetail) {
        Log.d(TAG, "setTourDetail: " + tourDetail.getNoiKhoiHanh());
        tenTour.setText(tourDetail.getName());
        noiKhoiHanh.setText(tourDetail.getNoiKhoiHanh());


        Utils utils = new Utils();

//        String strDateTimeXP = tourDetail.getNgayGioXuatPhat();
//        String cvDateTimeNgayGioXP = utils.convertTimeStampToDateTime(strDateTimeXP);
//
//        String strNgayVe = tourDetail.getNgayVe();
//        String cvNgayVe = utils.convertTimeStampToDate(strNgayVe);

//        DateFormat.getDateInstance().format(tour.getNgayGioXuatPhat())
        ngayGioXp.setText(tourDetail.getNgayGioXuatPhat());
//        ngayGioXp.setText(DateFormat.getDateInstance().format(tourDetail.getNgayGioXuatPhat()));
//        ngayVe.setText(DateFormat.getDateInstance().format(tourDetail.getNgayVe()));
        ngayVe.setText(tourDetail.getNgayVe());
        soVeConLai.setText(String.valueOf(tourDetail.getSoLuongVe()));
        Picasso.get().load(tourDetail.getImageUrls()).into(imgAnhTour);

        double price = tourDetail.getGiaThamKhao();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
        String formattedPrice = decimalFormat.format(price);
        giaThamKhao.setText(formattedPrice);

    }


    public void onAddToCartTour(Long tour_id, Long user_id, Long soVe) {


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_home:
                startActivity(new Intent(DetailActivity.this, ToolsActivity.class));
                break;
            case R.id.mnu_back:
                finish();
//                startActivity(new Intent(DetailActivity.this, MainActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}


class ListLichTrinhAdapter extends RecyclerView.Adapter<ListLichTrinhAdapter.ViewHolder> {
    private Context context;
    private List<LichTrinhTour> mLichTrinhTourList = new ArrayList<>();


    public ListLichTrinhAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lichtrinh, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LichTrinhTour lichTrinhTour = mLichTrinhTourList.get(position);
        holder.tv_tenLichTrinh.setText(lichTrinhTour.getTenLichTrinh());
        holder.tv_sttLichTrinh.setText(String.valueOf(lichTrinhTour.getSttLichTrinh()));
        holder.tv_ghiChu.setText(lichTrinhTour.getGhiChu());
        holder.tv_phuongTien.setText(lichTrinhTour.getPhuongTien());
        holder.tv_lichTrinhChiTiet.setText(lichTrinhTour.getLichTrinhChiTiet());

        List<DiemDen> diemDenList = lichTrinhTour.getDiemDen();


        // Tạo adapter cho RecyclerView DiemDen
        DiemDenAdapter diemDenAdapter = new DiemDenAdapter(diemDenList);
        holder.rclViewDiemDen.setAdapter(diemDenAdapter);

// Cài đặt layout cho RecyclerView (LinearLayoutManager)
        LinearLayoutManager layoutManager = new LinearLayoutManager(context); // context là một tham số trong constructor của adapter
        holder.rclViewDiemDen.setLayoutManager(layoutManager);

// Cài đặt KhachSan
//        if (lichTrinhTour.getKhachSan() != null) {
//            KhachSan khachSan = lichTrinhTour.getKhachSan();
        holder.tv_tenKhachSan.setText("Khách Sạn " + lichTrinhTour.getNameKhachSan());
        holder.tv_diaChiKhachSan.setText("Địa Chỉ: " + lichTrinhTour.getDiaChiKhachSan());
        holder.tv_phoneKhachSan.setText("Điện Thoại: " + lichTrinhTour.getPhoneKhachSan());

        double price = lichTrinhTour.getGiaPhongKhachSan();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
        String formattedPrice = decimalFormat.format(price);
        holder.tv_giaPhong.setText("Giá Phòng: " + formattedPrice);


//        } else {
//            holder.tv_tenKhachSan.setText("Chưa có thông tin nơi nghỉ");
//            holder.tv_giaPhong.setText("");
//            holder.tv_diaChiKhachSan.setText("");
//            holder.tv_phoneKhachSan.setText("");
//        }
    }

    public ListLichTrinhAdapter(Context context, List<LichTrinhTour> mLichTrinhTourList) {
        this.context = context;
        this.mLichTrinhTourList = mLichTrinhTourList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mLichTrinhTourList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Khai báo view
        TextView tv_tenLichTrinh, tv_sttLichTrinh, tv_ghiChu, tv_phuongTien, tv_lichTrinhChiTiet;

        //Diem Den
        RelativeLayout relativeLayout;
        RecyclerView rclViewDiemDen;

        //Khach Sạn
        TextView tv_tenKhachSan, tv_giaPhong, tv_diaChiKhachSan, tv_phoneKhachSan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_tenLichTrinh = itemView.findViewById(R.id.tv_tenlichtrinh);
            tv_sttLichTrinh = itemView.findViewById(R.id.sttlichtrinh);
            tv_ghiChu = itemView.findViewById(R.id.tv_ghichu);
            tv_phuongTien = itemView.findViewById(R.id.tv_phuongtien);
            tv_lichTrinhChiTiet = itemView.findViewById(R.id.tv_lichtrinhchitiet);
            relativeLayout = itemView.findViewById(R.id.layout_item_lichtrinh);
            rclViewDiemDen = itemView.findViewById(R.id.recycle_diemden);
            tv_tenKhachSan = itemView.findViewById(R.id.tv_tenkhachsan);
            tv_giaPhong = itemView.findViewById(R.id.tv_giaPhongKhachSan);
            tv_diaChiKhachSan = itemView.findViewById(R.id.tv_diaChiKhachSan);
            tv_phoneKhachSan = itemView.findViewById(R.id.tv_phoneKhachSan);
        }
    }

}

class DiemDenAdapter extends RecyclerView.Adapter<DiemDenAdapter.ViewHolder> {
    private List<DiemDen> diemDenList;

    public DiemDenAdapter(List<DiemDen> diemDenList) {
        this.diemDenList = diemDenList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diemden, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiemDen diemDen = diemDenList.get(position);
        holder.tvTenDiemDen.setText(diemDen.getName());
        holder.tvDiaChi.setText(diemDen.getDiaChi());
        holder.tvNoiDung.setText(diemDen.getNoiDung());
        holder.tvGiaVe.setText(String.valueOf(diemDen.getGiaVeThamQuan()));
    }

    @Override
    public int getItemCount() {
        return diemDenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenDiemDen, tvDiaChi, tvNoiDung, tvGiaVe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDiemDen = itemView.findViewById(R.id.tv_tendiemden);
            tvDiaChi = itemView.findViewById(R.id.tv_diachidiemden);
            tvNoiDung = itemView.findViewById(R.id.tv_noidungdiemden);
            tvGiaVe = itemView.findViewById(R.id.tv_giavethamquan);
        }
    }

}

