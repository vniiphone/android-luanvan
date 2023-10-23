package appdulich.booking.giaodien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import appdulich.booking.adapter.CustomImageAdapter;
import thud.helloworld.R;


public class ToolsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button btnGetToken;
    //    String strToken = "";
    private Integer[] Images = {
            R.mipmap.user, // profile
            R.drawable.destination, // loại tour
            R.drawable.bill,// hoá đơn mua vé
            R.drawable.exiticon}; // Cửa sổ đăng nhập
    private String[] Texts = {
            "Profile",
            "Loại Chuyến Đi",
            "Vé Đã đặt",
            "Thoát"};

   /* Class[] arrClasses = {
            LoginActivity.class,
            CategoryListActivity.class,
            GioHangActivity.class,
            InvoiceActivity.class,
            ProfileActivity.class};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        AnhXaId();
        bottomNavigationView.setSelectedItemId(R.id.mnu_tools);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnu_cart:
                    startActivity(new Intent(getApplicationContext(), GioHangActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.mnu_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.mnu_tools:
                    return true;
            }
            return false;
        });
//        listener();
        GridView gdvMenu = findViewById(R.id.gdv_menu);
        CustomImageAdapter adapter = new CustomImageAdapter(this, R.layout.custom_gridview_cell, Images, Texts);
        gdvMenu.setAdapter(adapter);
        gdvMenu.setOnItemClickListener(new ChonCongViec());
    }

    public void AnhXaId() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    public void dangXuat() {
        Intent intent = new Intent(ToolsActivity.this, LoginActivity.class);
        intent.putExtra("dang_xuat", true); // Gửi cờ đăng xuất
        Toast.makeText(this, "Đăng Xuất", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    private class ChonCongViec implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 3) {
                dangXuat();
                finish();
            } else {
                Intent intent = null;
                switch (i) {
                    case 0:
                        intent = new Intent(ToolsActivity.this, ListProfileActivity.class);
                        break;
                    case 1:
                        intent = new Intent(ToolsActivity.this, CategoryListActivity.class);
                        break;
                    case 2:
                        intent = new Intent(ToolsActivity.this, ListHoaDonActivity.class);
                        break;
                    case 3:
                        intent = new Intent(ToolsActivity.this, LoginActivity.class);

                        break;
                }
                startActivity(intent);
            }
        }
    }
}