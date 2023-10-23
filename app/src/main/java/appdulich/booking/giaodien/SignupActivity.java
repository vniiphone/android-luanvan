package appdulich.booking.giaodien;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import appdulich.booking.helpers.StringHelper;
import appdulich.booking.model.User;
import appdulich.booking.model.api_interface.AuthAPI;
import appdulich.booking.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thud.helloworld.R;

public class SignupActivity  extends AppCompatActivity {

    EditText email_, username_, password_;
    Button sign_up_btn;
    String strUsername, strPassword, strEmail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Hook Edt
        username_ = findViewById(R.id.edt_username);
        email_ = findViewById(R.id.edt_email);
        password_ = findViewById(R.id.edt_password);
        sign_up_btn = findViewById(R.id.btn_dangky);


        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    processFormFields();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        /*sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    processFormFields();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });*/


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

    public boolean validateEmail() {
        String email = email_.getText().toString().trim();

        if (email.isEmpty()) {
            email_.setError("Không được trống");
            return false;
        } else if (StringHelper.regexEmailValidationPattern(email)) {
            email_.setError("Vui lòng nhập đúng định dạng");
            return false;
        } else {
            email_.setError(null);
            return true;
        }
    }

    public boolean validatePassWordAndConfirm() {
        String password_ab = password_.getText().toString();

        if (password_ab.isEmpty()) {
            password_.setError("Không được trống");
            return false;
        }
// thêm if check 2 password trùng nhau
         else {
            password_.setError(null);
            return true;
        }
    }


    public void processFormFields() throws JSONException {
        //Check error appear
        if (!validateUsername() || !validateEmail() || !validatePassWordAndConfirm()) {
            return;
        }

        AuthAPI authAPI = RetrofitClient.getClient("http://192.168.1.21:8088/").create(AuthAPI.class);

        final User user = new User(username_.getText().toString(), email_.getText().toString(), password_.getText().toString() );
        retrofit2.Call<User> call = authAPI.signUp(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Handle response here
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(SignupActivity.this, "Đăng ký Thành Công", Toast.LENGTH_SHORT).show();
                }
                if(response.message().equals("size must be between 6 and 40")){
                    Toast.makeText(SignupActivity.this, "Mật khẩu phải từ 6 đến 40 ký tự", Toast.LENGTH_SHORT).show();
                    Log.e("Lỗi khi đăng ký: ", "Lỗi: " + response.toString());
                }
                else {
                    Toast.makeText(SignupActivity.this, "Tài khoản hoặc email đã tồn tại", Toast.LENGTH_SHORT).show();
                    Log.e("Lỗi khi đăng ký: ", "Lỗi: " + response.toString());
                    // Handle error here
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("Lỗi Kết nối: ", "Lỗi: " + t.toString());
            }
        });

    }


    public void GoToHomeLand(View view) {
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }

    public void goToSignIn(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
// Link: https://www.youtube.com/watch?v=CsaFvG2LzhQ&list=PLoYPuzsyna1k-GZfhW8D2UHPKzEQ6bdlW&index=8
// Time: 29:26'

