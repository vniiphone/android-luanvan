package appdulich.booking.giaodien;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import appdulich.booking.model.CartItem;
import appdulich.booking.model.GioHang;
import appdulich.booking.model.SessionManager;
import appdulich.booking.model.Tour;
import appdulich.booking.model.api_interface.CartItemAPI;
import appdulich.booking.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thud.helloworld.R;

public class GioHangActivity extends AppCompatActivity {
    final String TAG = "GioHangActivity";
    BottomNavigationView bottomNavigationView;

    RecyclerView cartRecycleView2;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tv_tongtien;
    double tongtienve = 0;
    long id = 0;
    int sove = 0;
    double giave = 0;

    long userId = 0;
    long tourId = 0;
    long bookingId = 0;
    String trangThai = "";

    List<GioHang> gioHangList;
    private String token = "";
    private long user_id = 0;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        gioHangList = new ArrayList<>();
        SessionManager sessionManager = new SessionManager(this);
        token = sessionManager.getToken();
        user_id = sessionManager.getUserId();
        Log.d(TAG, "Token: " + token);

        AnhXaId();
        Listener();
        loadCart();

        bottomNavigationView.setSelectedItemId(R.id.mnu_cart);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnu_cart:
                    return true;
                case R.id.mnu_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.mnu_tools:
                    startActivity(new Intent(getApplicationContext(), ToolsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }

            return false;
        });
/*

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // this method is called
                // when the item is moved.
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Xác Nhận");

                int position = viewHolder.getAdapterPosition();
                RecyclerView.Adapter adapter = cartRecycleView2.getAdapter(); // Lấy adapter từ RecyclerView
                // Hiển thị một dialog xác nhận xóa
                builder.setMessage("Bạn có muốn xóa mục này không?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Xóa item khỏi danh sách dữ liệu
                        // Cập nhật giao diện
//                        adapter.notifyItemRemoved(position);
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Hủy", (dialogInterface, i) -> {
                    // Người dùng đã chọn hủy, không xóa
//                    adapter.notifyItemChanged(position); // Trả lại item vào vị trí ban đầu
                    dialogInterface.cancel();
                });
                builder.create().show();

            }
        }).attachToRecyclerView(cartRecycleView2);
*/
    }


    private void loadCart() {
        RetrofitService retrofitService = new RetrofitService(this);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();

        CartItemAPI api = retrofit.create(CartItemAPI.class);
        api.getBooking(user_id).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                if (response.isSuccessful()) {
//                    List<CartItem> cartItems = response.body();
                    Log.d(TAG, "Body OK");
                    populateListView(response.body());
                    // xử lý dữ liệu ở đây
                } else {
                    Log.d(TAG, "Lỗi Body NULL");
                    // xử lý lỗi ở đây
                }
            }

            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                Log.d(TAG, "Lỗi onFailure " + t.getMessage());

            }
        });

    }

    private void Listener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            loadCart();
        });
    }

    @Override
    protected void onRestart() {
        loadCart();
        super.onRestart();
    }

    public void AnhXaId() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        cartRecycleView2 = findViewById(R.id.recycler_view_giohang);
        cartRecycleView2.setLayoutManager(new LinearLayoutManager(this));
        tv_tongtien = findViewById(R.id.grand_total_cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        cartRecycleView2.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_gio_hang);

//        toolbar = findViewById(R.id.tool_bar_giohang);

    }

    private void populateListView(List<GioHang> body) {
        GioHangAdapterList cartAdapter = new GioHangAdapterList(getApplicationContext(), body);
        cartRecycleView2.setAdapter(cartAdapter);
    }
}


class GioHangAdapterList extends RecyclerView.Adapter<GioHangAdapterList.ViewHolder> {

    private Context context;
    private List<GioHang> mGioHangList = new ArrayList<>();


    public GioHangAdapterList() {
    }


    public GioHangAdapterList(Context context, List<GioHang> mGioHangList) {
        this.context = context;
        if (!mGioHangList.isEmpty()) {
            this.mGioHangList = mGioHangList;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Tour tour = mGioHangList.get(position).getTour();
        final GioHang gioHang = mGioHangList.get(position);
//       final GioHang booking = mGioHangList.get(position).getBooking();
        long bookingId = gioHang.getId();


        holder.tv_tenTour.setText(gioHang.getTour().getName());
        holder.edt_tv_soVe.setText(String.valueOf(gioHang.getSoLuongVeDat()));
        Picasso.get().load(gioHang.getTour().getImageUrls()).into(holder.img_anhTour);

        holder.tv_idTour.setText(String.valueOf("TourID: " + gioHang.getTour().getId()));

        Double totalVe = tour.getGiaThamKhao() * gioHang.getSoLuongVeDat();

        double price = totalVe;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
        String formattedPrice = decimalFormat.format(price);
        holder.tv_tongTienVe.setText(formattedPrice + " VND");


        holder.edt_tv_soVe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("DebugTextSoVe", "SoVe: " + charSequence);
                try {
                    int soLuongVe = Integer.parseInt(charSequence.toString());
                    if (soLuongVe != gioHang.getSoLuongVeDat()) {
                        // Gửi dữ liệu lên server và nhận kết quả
                        putUpdateSoluongVe(soLuongVe, gioHang.getTour().getId(), bookingId);

                        // Tính lại tổng số tiền vé và cập nhật TextView
                        double totalVe = tour.getGiaThamKhao() * soLuongVe;
                        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
                        String formattedPrice = decimalFormat.format(totalVe);
                        holder.tv_tongTienVe.setText(formattedPrice);
                        holder.edt_tv_soVe.clearFocus();
                    }
                } catch (NumberFormatException e) {
                    // Xử lý khi chuỗi không thể chuyển đổi thành số nguyên
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
               /* try {
                    int soLuongVe = Integer.parseInt(editable.toString());
                    Log.d("DebugTextSoVe", "SoVe editable: " + editable);
                    if (soLuongVe != gioHang.getSoLuongVeDat()) {
                        putUpdateSoluongVe(soLuongVe, gioHang.getTour().getId(), bookingId);
                    }
                } catch (NumberFormatException e) {
                    // Xử lý khi chuỗi không thể chuyển đổi thành số nguyên
                }*/
//                try {
//                    int soLuongVe = Integer.parseInt(editable.toString());
//                    if (soLuongVe != gioHang.getSoLuongVeDat()) {
//                        // Gửi dữ liệu lên server và nhận kết quả
//                        putUpdateSoluongVe(Integer.parseInt(editable.toString()), gioHang.getTour().getId(), bookingId);
//                        // Tính lại tổng số tiền vé và cập nhật TextView
//                        double totalVe = tour.getGiaThamKhao() * soLuongVe;
//                        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
//                        String formattedPrice = decimalFormat.format(totalVe);
//                        holder.tv_tongTienVe.setText(formattedPrice);
                        holder.edt_tv_soVe.clearFocus();
//                    }
//                } catch (NumberFormatException e) {
//                    // Xử lý khi chuỗi không thể chuyển đổi thành số nguyên
//                }
//                Toast.makeText(context, editable, Toast.LENGTH_SHORT).show();
            }
        });

        holder.img_delete.setOnClickListener(view -> {
            onClickDeleteCart(bookingId);
        });

        holder.btn_datve.setOnClickListener(view -> {
            goToDatVeActivity(gioHang);
        });
    }

    public void goToDatVeActivity(GioHang item) {

        Intent intent = new Intent(context, InvoiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_cartItem", item);
        bundle.putLong("CartItem_id", item.getId());
        Log.d("goToDatVeActivity", "goToDatVeActivity id: " + item.getId());
        intent.putExtra("isCreatingCartItem", true);
        intent.putExtras(bundle);
        // Thêm FLAG_ACTIVITY_NEW_TASK trước khi mở Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    private void onClickDeleteCart(long id_cart) {
        SessionManager sessionManager = new SessionManager(context);
        String token = sessionManager.getToken();
        long user_id = sessionManager.getUserId();

        RetrofitService retrofitService = new RetrofitService(context);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();

//        final CartItem cartItem = new CartItem(user_id, tour_id, value);
        Log.d("DebugCallUpdate", "CartID: " + id_cart);
        Log.d("DebugCallUpdate", "userId: " + user_id);
        Log.d("DebugCallUpdate", "accessToken: " + token);

        CartItemAPI api = retrofit.create(CartItemAPI.class);

        api.deleteBooking(id_cart).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Thành Công", Toast.LENGTH_SHORT).show();
                    Log.d("DebugCallUpdate", "success: ");
                } else {
                    Log.d("DebugCallUpdate", "false: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("DebugCallUpdate", "onFailure: " + t.getMessage());

            }
        });
    }

    private void putUpdateSoluongVe(int value, long tour_id, long id_cart) {
        SessionManager sessionManager = new SessionManager(context);
        String token = sessionManager.getToken();
        long user_id = sessionManager.getUserId();

        RetrofitService retrofitService = new RetrofitService(context);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();

//        final CartItem cartItem = new CartItem(user_id, tour_id, value);
        Log.d("DebugCallUpdate", "CartID: " + id_cart);
        Log.d("DebugCallUpdate", "TourID: " + tour_id);
        Log.d("DebugCallUpdate", "userId: " + user_id);
        Log.d("DebugCallUpdate", "soLuongVeDat: " + value);
        Log.d("DebugCallUpdate", "accessToken: " + token);

        CartItemAPI api = retrofit.create(CartItemAPI.class);

        api.updateItem(id_cart, user_id, tour_id, value).enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(Call<CartItem> call, Response<CartItem> response) {
                if (response.isSuccessful()) {
                    Log.d("DebugCallUpdate", "success: " + response.body());
                } else {
                    Log.d("DebugCallUpdate", "false: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CartItem> call, Throwable t) {
                Log.d("DebugCallUpdate", "onFailure: " + t.getMessage());

            }
        });
    }


    @Override
    public int getItemCount() {
        return mGioHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenTour, tv_ngayDi, tv_tongTienVe, tv_idTour;
        EditText edt_tv_soVe;
        ImageView img_anhTour, img_delete;
        Button btn_datve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            edt_tv_soVe = itemView.findViewById(R.id.id_edt_soluongve);
            tv_tenTour = itemView.findViewById(R.id.cardview_giohang_tentour);
            img_anhTour = itemView.findViewById(R.id.item_giohang_image);
            tv_tongTienVe = itemView.findViewById(R.id.tv_tongSoTien_item);
            tv_idTour = itemView.findViewById(R.id.tv_idTour_item);
            img_delete = itemView.findViewById(R.id.img_btn_delete_cartItem);
            btn_datve = itemView.findViewById(R.id.btn_thucHienDatVe);
        }
    }
}