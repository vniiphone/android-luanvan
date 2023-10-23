package appdulich.booking.giaodien;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import appdulich.booking.adapter.CartAdapter;
import appdulich.booking.model.Tour;
import thud.helloworld.R;

public class CartActivity extends AppCompatActivity {

    public static TextView grandTotal;
    public static int grandTotalplus;

    public static ArrayList<Tour> tempListTour;
    RecyclerView cartRCV;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }

}