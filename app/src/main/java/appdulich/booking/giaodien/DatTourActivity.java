package appdulich.booking.giaodien;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import thud.helloworld.R;

public class DatTourActivity extends AppCompatActivity {
    final String TAG = "DatTourActivity";
    private String token = "";
    private long user_id = 0;
    private String username = "";
    private long booking_id = -1;
    long profile_id = -1;

    /*
     Để thực hiện đặt tour: User cần chọn 1 trong profile họ nhận vé và làm thông tin chính
     - sau đó gửi booking_id, profile_id, user_id đến server
     - mặc định
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_tour);

        anhXaId();
    }


    public void anhXaId() {

    }
}