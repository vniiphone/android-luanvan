package appdulich.booking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import appdulich.booking.model.CartItem;
import thud.helloworld.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<CartItem> list;
    long id = 0;
    Context context;
    double tongtienve = 0;
    int sove = 0;
    double giatour;

    public CartAdapter(List<CartItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Set đối tượng layout cho recycleView Cart
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cartr, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = list.get(position);

        /*Picasso.get().load(cartItem.getTour().getImageUrls()).into(holder.imageView);
        holder.nameTour.setText(list.get(position).getTour().getName());
        holder.soLuongVeMua.setText(String.valueOf(list.get(position).getQuantity()));

        DecimalFormat decimalFormat = new DecimalFormat("#,###.## VND");
        double price = cartItem.getTour().getGiaThamKhao();
        String priceString = decimalFormat.format(price);
        holder.giaTien.setText("Giá vé: " + priceString);

        sove = cartItem.getQuantity();
        giatour = cartItem.getTour().getGiaThamKhao();
*/
       /* for (int i = 0; i <= list.size(); i++) {
            tongtienve += cartItem.getTour().getPrice();
        }

        double tongTienOrder = tongtienve;
        String totalString = decimalFormat.format(tongTienOrder);
        holder.tvtongtien.setText("Giá vé: " + totalString);*/

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameTour, giaTien, chiTiet, soLuongVeMua;
        ImageView increase, decrease, deleteItem;
        TextView tvtongtien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
// anh xa id den layout item_cartr
            imageView = itemView.findViewById(R.id.item_image_cart);
            nameTour = itemView.findViewById(R.id.item_tentour_cartr);
            giaTien = itemView.findViewById(R.id.cart_price_item);
            chiTiet = itemView.findViewById(R.id.item_chitiet);
            increase = itemView.findViewById(R.id.item_cart_increment);
            decrease = itemView.findViewById(R.id.item_cart_decrement);
            deleteItem = itemView.findViewById(R.id.delete_item_from_cart);
            soLuongVeMua = itemView.findViewById(R.id.item_so_luong_ve_mua);
//            tvtongtien = itemView.findViewById(R.id.grand_total_cart);
        }
    }
}
