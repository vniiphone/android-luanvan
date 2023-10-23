package appdulich.booking.giaodien;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.checkbox.MaterialCheckBox;

import appdulich.booking.model.SessionManager;
import appdulich.booking.model.Signin;
import appdulich.booking.model.User;
import appdulich.booking.model.api_interface.AuthAPI;
import appdulich.booking.retrofit.RetrofitClient;
import thud.helloworld.R;

public class LoginActivity extends AppCompatActivity {
    MaterialCheckBox chkRemember;
    EditText username_, password_;
    Button signin_btn;
    String strUsername = "", strPassword = "";
    SharedPreferences sharedPreferences;
    Boolean isCheckRemember = false;
    private boolean isSucess = false;
    boolean hasID = false, hasPwd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        AnhXa();

        // Kiểm tra xem có cờ đăng xuất không
        if (getIntent().getBooleanExtra("dang_xuat", false)) {
            dangXuat();
        }

        //Khởi tạo sharedPref
        sharedPreferences = getSharedPreferences("DataUser", MODE_PRIVATE);

        if (sharedPreferences.contains("username")) {
            strUsername = sharedPreferences.getString("username", " ");
            Log.e("username", strUsername);
//            Toast.makeText(this, "Binding User", Toast.LENGTH_SHORT).show();
            username_.setText(sharedPreferences.getString("username", ""));
            hasID = true;
        }
        if (sharedPreferences.contains("password")) {
            strPassword = sharedPreferences.getString("password", " ");
//            Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
            Log.e("password", strPassword);
            password_.setText(sharedPreferences.getString("password", ""));
            hasPwd = true;
        }


        signin_btn.setOnClickListener(view -> usingRetrofit());


        chkRemember.setOnCheckedChangeListener((compoundButton, b) -> {
            if (chkRemember.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username_.getText().toString());
                editor.putString("password", password_.getText().toString().trim());
                editor.commit();

            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
//                Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void dangXuat() {
        // Xóa thông tin người dùng khi đăng xuất
        SessionManager session = new SessionManager(getApplicationContext());
        session.dangXuat();

        // Khởi động lại ứng dụng từ LoginActivity
        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (client != null) {
//            client.close();
//        }
    }
    public void AnhXa() {

        username_ = findViewById(R.id.edt_username);
        password_ = findViewById(R.id.edt_password);
        signin_btn = findViewById(R.id.btn_signin);
        chkRemember = findViewById(R.id.check_remberpwd);
    }

    public void listener() {


    }

    private void usingRetrofit() {
        AuthAPI authAPI = RetrofitClient.getClient("http://10.0.2.2:8088/").create(AuthAPI.class);
        final User user = new User(username_.getText().toString(), password_.getText().toString());
        final Signin signin = new Signin(username_.getText().toString(), password_.getText().toString());
        retrofit2.Call<User> call = authAPI.signIn(user);
        call.enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(retrofit2.Call<User> call, retrofit2.Response<User> response) {
                if (response.isSuccessful()) {
                    // Handle response here
                    String token = response.body().getToken();
                    Long userId = response.body().getId();
                    String email = response.body().getEmail();
                    String username = response.body().getUsername();
                    SessionManager session = new SessionManager(getApplicationContext());
                    session.saveToken(token);
                    session.saveUserId(userId);
                    session.saveEmail(email);
                    session.saveUserName(username);

                    Log.e("token", token.toString());
                    Log.e("userId", userId.toString());
                    Log.e("email", email.toString());

                    //Chuyển hướng đến Main Page
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    isSucess = true;
//                    Toast.makeText(LoginActivity.this, "Đăng nhập Thành Công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    isSucess = false;
                    Toast.makeText(LoginActivity.this, "Sai Mật khẩu hoặc Tài khoản", Toast.LENGTH_SHORT).show();
                    Log.e("Lỗi khi đăng nhập: ", "Lỗi: " + response.toString());
                    // Handle error here
                }
            }

            @Override
            public void onFailure(retrofit2.Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("Lỗi Kết nối: ", "Lỗi: " + t.toString());
                isSucess = false;
            }
        });

    }

/*
    public void processFormFields() throws JSONException {
        //Check error appear
        if (!validateUsername() || !validatePassWordAndConfirm()) {
            return;
        }
        JSONObject dulieu = new JSONObject();
        dulieu.put("username", username_.toString());
        dulieu.put("password", password_.toString());
        System.out.println(dulieu.toString());

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), dulieu.toString());
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8088/api/auth/signin")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println(e.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,
                                "Lỗi kết nối, vui lòng thử lại sau" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                // Xử lý kết quả trả về từ server
                System.out.println(response.toString());
                if (!response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,
                                    "Lỗi phản hồi từ server, vui lòng thử lại sau"
                                            + response.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                System.out.println(response.toString());

            }
        });
    }*/

    public void GoToSignUp(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        finish();
        startActivity(intent);
    }

    public void GoToHomeLand(View view) {
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }

    public boolean validateUsername() {
        String username = username_.getText().toString().trim();

        if (username.isEmpty()) {
            username_.setError("Không được trống");
            return false;
        } else {
            username_.setError(null);
            return true;
        }
    }

    public boolean validatePassWordAndConfirm() {
        String password_ab = password_.getText().toString();

        if (password_ab.isEmpty()) {
            password_.setError("Không được trống");
            return false;
        } else {
            password_.setError(null);
            return true;
        }
    }


}