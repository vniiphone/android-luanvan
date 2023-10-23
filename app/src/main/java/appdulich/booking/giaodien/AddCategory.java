package appdulich.booking.giaodien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import thud.helloworld.R;

public class AddCategory extends AppCompatActivity {
    EditText edtNameCate;
    Button btnAdd, btnGoHome;
    private String strNameCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

    /*    edtNameCate = findViewById(R.id.edt_namecate);
        btnGoHome = findViewById(R.id.btn_backtohome);
        btnAdd = findViewById(R.id.btn_saveCate);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usingRetrofit();
            }
        });*/
    }

    public void backtohome(View view) {
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }

/*    private void usingRetrofit() {
        CategoryAPI categoryAPI = RetrofitClient.getClient("http://10.0.2.2:8088/").create(CategoryAPI.class);

        final Category category = new Category(edtNameCate.getText().toString().trim());
        Call<Category> call = categoryAPI.createCategory(category);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    // Handle response here
                    Toast.makeText(AddCategory.this, "Thành Công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCategory.this, "Lỗi Tạo", Toast.LENGTH_SHORT).show();
                    Log.e("Lỗi khi tạo Category: ","Lỗi: "+ response.toString());
                    // Handle error here
                }
            }
            @Override
            public void onFailure(Call<Category> call, Throwable t) {
// Handle error here
                Toast.makeText(AddCategory.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("Lỗi Kết nối: ","Lỗi: "+ t.toString());
            }
        });
    }*/

}