package appdulich.booking.giaodien;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import appdulich.booking.model.Profile;
import appdulich.booking.model.SessionManager;
import appdulich.booking.model.api_interface.ProfileUserAPI;
import appdulich.booking.payload.ProfileRequest;
import appdulich.booking.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thud.helloworld.R;

public class ProfileActivity extends AppCompatActivity {
    final String TAG = "ProfileActivity";
    private String token = "";
    private long user_id = 0;
    private String username = "";
    boolean isAddingProfile = false;
    boolean isEditingProfile = false;
    long profile_id = -1;

    ImageView img_avt;
    EditText edt_lastName,
            edt_name,
            edt_sdt,
            edt_cccd,
            edt_age,
            edt_diaChiNha,
            edt_phuongXa,
            edt_huyenThi,
            edt_tinhThanh;
    Profile profile;
    TextView tv_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Lấy giá trị của cờ từ Intent
        isAddingProfile = getIntent().getBooleanExtra("isAddingProfile", false);
        isEditingProfile = getIntent().getBooleanExtra("isEditingProfile", false);

        anhXaId();
        //Trường hợp CREATE PROFILE
        if (isAddingProfile) {
            caseCreateProfile();
            // Xử lý khi cờ là true, hiển thị nút save và khởi tạo Activity trống
            Button saveButton = findViewById(R.id.profile_btn_save_info);
            saveButton.setVisibility(View.VISIBLE); // hiện nút save
            saveButton.setOnClickListener(view -> {
                if (checkValidationInfo()) {
                    addProfileDetailsAPI();
                } else {
                    Toast.makeText(ProfileActivity.this, "Vui Lòng Kiểm Tra Lại Dữ Liệu", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //Trường hợp EDIT PROFILE
        else if (isEditingProfile) {
            caseEdtProfile();
            // Xử lý khi cờ là true, hiển thị nút save và khởi tạo Activity trống
            Button saveButton = findViewById(R.id.profile_btn_save_info);
            saveButton.setVisibility(View.VISIBLE); // hiện nút save
            saveButton.setOnClickListener(view -> {
                if (checkValidationInfo()) {
                    edtProfileDetailsAPI();
                } else {
                    Toast.makeText(ProfileActivity.this, "Vui Lòng Kiểm Tra Lại Dữ Liệu", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //Trường Hợp create
        else {
            caseViewProfile();
        }

    }

    private void caseCreateProfile() {
        Log.d(TAG, "CREATE Profile");
        SessionManager sessionManager = new SessionManager(this);
        token = sessionManager.getToken();
        user_id = sessionManager.getUserId();
        profile = new Profile();

    }

    private void caseViewProfile() {
        Log.d(TAG, "Xem Profile");
        SessionManager sessionManager = new SessionManager(this);
        token = sessionManager.getToken();
        user_id = sessionManager.getUserId();
        // Xử lý khi cờ là false,
        // có thể không hiển thị nút save hoặc thực hiện hành động khác
        Button saveButton = findViewById(R.id.profile_btn_save_info);
        saveButton.setVisibility(View.GONE); // ẩn nút save
        getProfileDetail();

        edt_lastName.setEnabled(false);
        edt_name.setEnabled(false);
        edt_age.setEnabled(false);
        edt_sdt.setEnabled(false);
        edt_cccd.setEnabled(false);
        edt_diaChiNha.setEnabled(false);
        edt_phuongXa.setEnabled(false);
        edt_huyenThi.setEnabled(false);
        edt_tinhThanh.setEnabled(false);
    }

    private void caseEdtProfile() {
        Log.d(TAG, "EDIT Profile");
        SessionManager sessionManager = new SessionManager(this);
        token = sessionManager.getToken();
        user_id = sessionManager.getUserId();
        profile = new Profile();
        getProfileDetail();
    }


    public void getProfileDetail() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Log.d(TAG, "Bundle not found");
        } else {
            profile = (Profile) bundle.getSerializable("object_profile");
            profile_id = (long) bundle.getLong("profile_id", -1);
            setProfileDetail(profile);
        }
    }

    public void setProfileDetail(Profile profile) {

        SessionManager sessionManager = new SessionManager(this);
        username = sessionManager.getUsername();
        user_id = sessionManager.getUserId();
        tv_username.setText("@" + username);

        if (profile != null) {
            edt_lastName.setText(profile.getLastName());
            edt_name.setText(profile.getName());
            edt_sdt.setText(profile.getPhoneNumber());
            edt_cccd.setText(profile.getSoCCCD());
            edt_age.setText(String.valueOf(profile.getAge()));
            edt_diaChiNha.setText(profile.getDiaChiNha());
            edt_phuongXa.setText(profile.getPhuongXa());
            edt_huyenThi.setText(profile.getHuyenThi());
            edt_tinhThanh.setText(profile.getTinhThanh());
        } else {

        }
    }

    public void anhXaId() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edt_lastName = findViewById(R.id.profile_edt_lastName);
        edt_name = findViewById(R.id.profile_edt_name);
        edt_sdt = findViewById(R.id.profile_edt_phone);
        edt_cccd = findViewById(R.id.profile_edt_cccd);
        edt_age = findViewById(R.id.profile_edt_age);
        edt_diaChiNha = findViewById(R.id.profile_edt_diaChi);
        edt_phuongXa = findViewById(R.id.profile_edt_phuongXa);
        edt_huyenThi = findViewById(R.id.profile_edt_huyenThi);
        edt_tinhThanh = findViewById(R.id.profile_edt_tinhThanh);

        tv_username = findViewById(R.id.tv_activity_profile_username);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_on_hoadon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void edtProfileDetailsAPI() {
        SessionManager sessionManager = new SessionManager(this);
        long userid = sessionManager.getUserId();
        RetrofitService retrofitService = new RetrofitService(this);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();
        ProfileUserAPI api = retrofit.create(ProfileUserAPI.class);

        // GET edittext
        String name = edt_name.getText().toString();
        String lastName = edt_lastName.getText().toString();
        int age = Integer.parseInt(edt_age.getText().toString());
        String phoneNumber = edt_sdt.getText().toString();
        String soCCCD = edt_cccd.getText().toString();
        String diaChiNha = edt_diaChiNha.getText().toString();
        String phuongXa = edt_phuongXa.getText().toString();
        String huyenThi = edt_huyenThi.getText().toString();
        String tinhThanh = edt_tinhThanh.getText().toString();

        Log.d(TAG, "Profile_id: " + profile_id);
        //Gọi API Interface
        ProfileRequest profileRequest = new ProfileRequest(
                name, lastName, age, phoneNumber, soCCCD,
                diaChiNha, phuongXa, huyenThi, tinhThanh,
                userid);

        api.updateProfile(profile_id, profileRequest).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Success to edt profile: " + response.body());
                    finish();
                } else {
                    Log.d(TAG, "Failed to edt profile: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d(TAG, "onFailure to edt profile: " + t.getMessage());

            }
        });

    }

    public void addProfileDetailsAPI() {
        SessionManager sessionManager = new SessionManager(this);
        long userid = sessionManager.getUserId();
        RetrofitService retrofitService = new RetrofitService(this);
        String newToken = token;
        retrofitService.updateToken(newToken);
        Retrofit retrofit = retrofitService.getRetrofit();
        ProfileUserAPI api = retrofit.create(ProfileUserAPI.class);
        String name = edt_name.getText().toString();
        String lastName = edt_lastName.getText().toString();
        int age = Integer.parseInt(edt_age.getText().toString());
        String phoneNumber = edt_sdt.getText().toString();
        String soCCCD = edt_cccd.getText().toString();
        String diaChiNha = edt_diaChiNha.getText().toString();
        String phuongXa = edt_phuongXa.getText().toString();
        String huyenThi = edt_huyenThi.getText().toString();
        String tinhThanh = edt_tinhThanh.getText().toString();


        ProfileRequest profileRequest = new ProfileRequest(
                name, lastName, age, phoneNumber, soCCCD,
                diaChiNha, phuongXa, huyenThi, tinhThanh,
                userid);


        api.createProfile(profileRequest).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Success to create profile: " + response.body());
                    finish();
                } else {
                    Log.d(TAG, "Failed to create profile: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d(TAG, "onFailure to create profile: " + t.getMessage());

            }
        });
    }


    public Boolean checkValidationInfo() {
        String lastName = edt_lastName.getText().toString();
        String name = edt_name.getText().toString();

        String ageStr = edt_age.getText().toString();
//        if(ageStr.isEmpty() && ageStr.length() == 0){
//            int age = Integer.parseInt(ageStr);
//        }
        String phoneNumber = edt_sdt.getText().toString();
        String soCCCD = edt_cccd.getText().toString();
        String diaChiNha = edt_diaChiNha.getText().toString();
        String phuongXa = edt_phuongXa.getText().toString();
        String huyenThi = edt_huyenThi.getText().toString();
        String tinhThanh = edt_tinhThanh.getText().toString();

        if (lastName.length() == 0 && lastName.isEmpty()) {
            edt_lastName.setError("Chưa Nhập Tên Lót");
            edt_lastName.requestFocus();
            return false;
        } else {
            edt_lastName.setError(null); // Xóa thông báo lỗi
        }
        if (name.length() == 0 && name.isEmpty()) {
            edt_name.setError("Chưa Nhập Tên");
            edt_name.requestFocus();
            return false;
        } else {
            edt_name.setError(null); // Xóa thông báo lỗi
        }
        if (phoneNumber.isEmpty()) {
            edt_sdt.setError("Chưa Nhập SDT");
            edt_sdt.requestFocus();
            return false;
        } else if (phoneNumber.length() < 10 || phoneNumber.length() > 11) {
            edt_sdt.setError("SDT Phải có 10 số");
            edt_sdt.requestFocus();
            return false;
        } else {
            edt_sdt.setError(null); // Xóa thông báo lỗi
        }
        if (ageStr.length() == 0 && ageStr.isEmpty()) {
            edt_age.setError("Không Hợp Lệ");
            edt_age.requestFocus();
            return false;
        } else {
            edt_age.setError(null); // Xóa thông báo lỗi
        }

        if (soCCCD.isEmpty()) {
            edt_cccd.setError("Chưa Nhập CCCD");
            edt_cccd.requestFocus();
            return false;
        } else if (soCCCD.length() < 12 || soCCCD.length() > 13) {
            edt_cccd.setError("CCCD phải có 12 chữ số");
            edt_cccd.requestFocus();
            return false;
        } else {
            edt_cccd.setError(null);
        }
        if (diaChiNha.length() == 0 && diaChiNha.isEmpty()) {
            edt_diaChiNha.setError("Nhập Địa Chỉ Nhà");
            edt_diaChiNha.requestFocus();
            return false;
        } else {
            edt_diaChiNha.setError(null);
        }

        if (phuongXa.isEmpty() && phuongXa.length() == 0) {
            edt_phuongXa.setError("Nhập Phường - Xã");
            edt_phuongXa.requestFocus();
            return false;
        } else {
            edt_phuongXa.setError(null);
        }

        if (huyenThi.isEmpty() && huyenThi.length() == 0) {
            edt_huyenThi.setError("Nhập Huyện - Thị");
            edt_huyenThi.requestFocus();
            return false;
        } else {
            edt_huyenThi.setError(null);
        }

        if (tinhThanh.isEmpty() && tinhThanh.length() == 0) {
            edt_tinhThanh.setError("Nhập Thành Phố - Tỉnh");
            edt_tinhThanh.requestFocus();
            return false;
        } else {
            edt_tinhThanh.setError(null);
        }

        return true;


    }
}