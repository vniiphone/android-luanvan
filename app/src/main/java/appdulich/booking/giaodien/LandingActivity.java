package appdulich.booking.giaodien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import thud.helloworld.R;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

    }
    public void goToTour(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }public void goToCate(View view){
        Intent intent = new Intent(this, CategoryListActivity.class);
        startActivity(intent);
    }
    public void goToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}