package appdulich.booking.giaodien;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import appdulich.booking.model.CartItem;
import appdulich.booking.model.GioHang;
import appdulich.booking.model.Invoice;
import appdulich.booking.model.Profile;
import appdulich.booking.model.SessionManager;
import appdulich.booking.model.api_interface.CartItemAPI;
import appdulich.booking.model.api_interface.InvoiceAPI;
import appdulich.booking.model.api_interface.ProfileUserAPI;
import appdulich.booking.payload.InvoiceRequest;
import appdulich.booking.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thud.helloworld.R;

public class InvoiceActivity extends AppCompatActivity {
    final String TAG = "InvoiceActivity";
    private String token = "";
    private long user_id = 0;
    private String username = "";
    boolean isCreatingCartItem = false;
    boolean isEditingCreate = false;
    boolean isViewingCart = false;
    long cartItem_id = -1;

    long profile_id = -1;
    int slv_dat = 0;
    GioHang gioHang;
    private List<Profile> listProfile;
    TextView tv_tentour, tv_ngayKH, tv_noikhoihanh, tv_giathamkhao, tv_totalprice, tv_soluongveconlai;
    EditText edt_soluongve;
    Spinner spn_profile;
    Button btn_cancel, btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        anhXaId();

        // Lấy giá trị của cờ từ Intent
        isCreatingCartItem = getIntent().getBooleanExtra("isCreatingCartItem", false);
        isEditingCreate = getIntent().getBooleanExtra("isEditingCartItem", false);
        isViewingCart = getIntent().getBooleanExtra("isViewingCartItem", false);

        listProfile = new ArrayList<>();

        if (isCreatingCartItem) {
            Log.d(TAG, "isCreatingCartItem");
            /*
            - Lấy danh sách Profile, hiển thị thông tin tour
            * */
            caseCreatingCartItem();
           /* Button cfButton = findViewById(R.id.btn_thucHienDatVe);
            cfButton.setVisibility(View.VISIBLE); // hiện nút save
            cfButton.setOnClickListener(view -> {
                if (checkValidationInfo()) {
//                    addProfileDetailsAPI();
                } else {
                    Toast.makeText(InvoiceActivity.this, "Vui Lòng Kiểm Tra Lại Dữ Liệu", Toast.LENGTH_SHORT).show();
                }
            });*/

            spn_profile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    profile_id = listProfile.get(i).getId();
                    Log.d(TAG, "profile_id from spinner: " + profile_id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            listener();


        } else if (isEditingCreate) {
            Log.d(TAG, "isEditingCreate");
            listener();
        } else if (isViewingCart) {
            Log.d(TAG, "isViewingCart");
            btn_confirm.setVisibility(View.GONE); // Ẩn button
            listener();
        }
    }

    private boolean checkValidationInfo() {

        return true;
    }

    public void anhXaId() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Chi Tiết Vé Tour");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tv_tentour = findViewById(R.id.invoice_tv_tentour);
        tv_ngayKH = findViewById(R.id.invoice_tv_ngayxp);
        tv_noikhoihanh = findViewById(R.id.invoice_tv_noikhoihanh);
        tv_giathamkhao = findViewById(R.id.invoice_tv_giavetour);
        tv_totalprice = findViewById(R.id.invoice_tv_tongsotien);
        tv_soluongveconlai = findViewById(R.id.invoice_tv_soluongveconlai);
        edt_soluongve = findViewById(R.id.invoice_edt_soluongve);
        spn_profile = findViewById(R.id.invoice_spn_profiles);
        btn_cancel = findViewById(R.id.invoice_btn_cancel);
        btn_confirm = findViewById(R.id.invoice_btn_confirm_datve);
    }

    private void caseCreatingCartItem() {
        Log.d(TAG, "Creating Cart Item");
        SessionManager sessionManager = new SessionManager(this);
        token = sessionManager.getToken();
        user_id = sessionManager.getUserId();

        getCartItemFromGioHang();
        getProfiles();
        // Xử lý khi cờ là false,
        // có thể không hiển thị nút save hoặc thực hiện hành động khác
//        Button saveButton = findViewById(R.id.profile_btn_save_info);
//        saveButton.setVisibility(View.GONE); // ẩn nút save
//        getCartItemDetail();
    }

    private void getCartItemFromGioHang() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Log.d(TAG, "Bundle not found");
        } else {
//            gioHang = (GioHang) bundle.getSerializable("object_cartItem");
            cartItem_id = (long) bundle.getLong("CartItem_id", -1);
//            setGioHangInfo(gioHang);
            Log.d(TAG, "cartItem_id: " + cartItem_id);
            getGioHangItem();
        }
    }

    private void getGioHangItem() {
        RetrofitService retrofitService = new RetrofitService(this);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();
        CartItemAPI api = retrofit.create(CartItemAPI.class);
        Log.d(TAG, "GioHangID: " + cartItem_id);
        api.getBookingSingle(cartItem_id).enqueue(new Callback<GioHang>() {
            @Override
            public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Success to get getBookingSingle: " + response.body());
                    setGioHangInfo(response.body());
                } else {
                    Log.d(TAG, "Null to get getBookingSingle: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<GioHang> call, Throwable t) {
                Log.d(TAG, "onFailure to get getBookingSingle: " + t.getMessage());

            }
        });

    }
    private void onClickConfirmDatVe(){
        SessionManager sessionManager = new SessionManager(this);
        String token = sessionManager.getToken();
        RetrofitService retrofitService = new RetrofitService(this);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();

//        final CartItem cartItem = new CartItem(user_id, tour_id, value);
        long tourid = gioHang.getTour().getId();
        Log.d(TAG, "TourID: " +tourid );
        Log.d(TAG, "userId: " + user_id);
        Log.d(TAG, "profile_id: " + profile_id);
        Log.d(TAG, "cartItem_id: " + cartItem_id);
        Log.d(TAG, "accessToken: " + token);

        InvoiceAPI api = retrofit.create(InvoiceAPI.class);
        InvoiceRequest invoiceRequest = new InvoiceRequest(user_id, cartItem_id, profile_id, tourid);
        api.createInvoice(invoiceRequest).enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "success: " + response.body());
                    Toast.makeText(InvoiceActivity.this, "Đặt Tour Thành Công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InvoiceActivity.this, ListHoaDonActivity.class);
                    // Thêm FLAG_ACTIVITY_NEW_TASK trước khi mở Activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d(TAG, "false: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void listener() {
        btn_cancel.setOnClickListener(view -> finish());
        if(isCreatingCartItem){
            btn_confirm.setOnClickListener(view -> {
                Log.d(TAG, "Confirm profileID: " + profile_id + "Cart Item ID: " + cartItem_id);
                onClickConfirmDatVe();
            });
            edt_soluongve.addTextChangedListener(new TextWatcher() {
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
                            putUpdateSoluongVe(soLuongVe);

                            // Tính lại tổng số tiền vé và cập nhật TextView
                            double totalVe = gioHang.getTour().getGiaThamKhao() * soLuongVe;
                            DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
                            String formattedPrice = decimalFormat.format(totalVe);
                            tv_totalprice.setText(formattedPrice);
                            edt_soluongve.clearFocus();
                        }
                    } catch (NumberFormatException e) {
                        // Xử lý khi chuỗi không thể chuyển đổi thành số nguyên
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    private void putUpdateSoluongVe(int value) {
        SessionManager sessionManager = new SessionManager(this);
        String token = sessionManager.getToken();
        RetrofitService retrofitService = new RetrofitService(this);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();

//        final CartItem cartItem = new CartItem(user_id, tour_id, value);
        Log.d("DebugCallUpdate", "CartID: " + cartItem_id);
        long tourid = gioHang.getTour().getId();
        Log.d("DebugCallUpdate", "TourID: " +tourid );
        Log.d("DebugCallUpdate", "userId: " + user_id);
        Log.d("DebugCallUpdate", "soLuongVeDat: " + value);
        Log.d("DebugCallUpdate", "accessToken: " + token);

        CartItemAPI api = retrofit.create(CartItemAPI.class);
        api.updateItem(cartItem_id, user_id, tourid, value).enqueue(new Callback<CartItem>() {
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


        private void setGioHangInfo(GioHang mgioHang) {
        gioHang = mgioHang;
        String strTenTour = gioHang.getTour().getName();
        String strNgayXP = "Ngày Khởi Hành: " + gioHang.getTour().getNgayGioXuatPhat();
        String strNoiKhoiHanh = "Nơi Khởi Hành: " + gioHang.getTour().getNoiKhoiHanh();

        Double totalVe = gioHang.getTour().getGiaThamKhao();
        double price = totalVe;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
        String strFormatPrice = "Giá Vé: " + decimalFormat.format(price);

        String strSoluongVe = "Số Lượng Vé Còn Lại: " + gioHang.getTour().getSoLuongVe();
        String strSoLuongVeDat = String.valueOf(gioHang.getSoLuongVeDat());

        double totalTien = price * mgioHang.getSoLuongVeDat();
        String strFormatTotalPrice = "Tổng Số Tiền Vé: " + decimalFormat.format(totalTien);

        tv_tentour.setText(strTenTour);
        tv_noikhoihanh.setText(strNoiKhoiHanh);
        tv_ngayKH.setText(strNgayXP);
        tv_giathamkhao.setText(strFormatPrice);
        tv_soluongveconlai.setText(strSoluongVe);
        edt_soluongve.setText(strSoLuongVeDat);
        tv_totalprice.setText(strFormatTotalPrice);

    }

    private void setListProfiles(List<Profile> profiles) {
        listProfile = profiles;
        List<String> profileNames = new ArrayList<>();
        for (Profile profile : profiles) {
            String fullName = profile.getLastName() + " " + profile.getName();
            profileNames.add(fullName);
        }

        // Sử dụng danh sách tên để liên kết với Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, profileNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(() -> {
            spn_profile.setAdapter(adapter); // Đảm bảo rằng bạn đang chạy trên UI thread
        });
    }

    private void getProfiles() {
        Log.d(TAG, "getProfiles: " + user_id);
        RetrofitService retrofitService = new RetrofitService(this);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();
        ProfileUserAPI api = retrofit.create(ProfileUserAPI.class);
//        CompletableFuture<List<Profile>> future = new CompletableFuture<>();
        api.getAllProfiles(user_id).enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Success to get profile: " + response.body());
//                    List<Profile> profiles = response.body();
//                    future.complete(profiles);
                    setListProfiles(response.body());
                } else {
                    Log.d(TAG, "Failed to get profile: " + response.message());
//                    future.completeExceptionally(new RuntimeException(response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
                Log.d(TAG, "Failed to get profile: " + t.getMessage());
//                future.completeExceptionally(t);
            }
        });
//        return future;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        startActivity(new Intent(InvoiceActivity.this, ToolsActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_on_hoadon, menu);
        return super.onCreateOptionsMenu(menu);
    }
}