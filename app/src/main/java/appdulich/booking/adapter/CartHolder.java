package appdulich.booking.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartHolder extends RecyclerView.ViewHolder {

    TextView name, imgThumb, price;
    ImageView giam, tang, xoa;


    public CartHolder(@NonNull View itemView) {
        super(itemView);
/*
        name = itemView.findViewById(R.id.item_tentour);
        imgThumb = itemView.findViewById(R.id.item_image);
        price = itemView.findViewById(R.id.cart_price_item);
        giam = itemView.findViewById(R.id.cart_decrement);
        tang = itemView.findViewById(R.id.cart_increment);
        xoa = itemView.findViewById(R.id.delete_item_from_cart);*/

    }
}
