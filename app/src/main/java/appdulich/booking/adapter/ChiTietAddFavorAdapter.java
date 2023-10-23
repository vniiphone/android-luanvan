package appdulich.booking.adapter;

import android.content.Context;
import android.icu.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import appdulich.booking.model.Tour;
import thud.helloworld.R;

public class ChiTietAddFavorAdapter extends RecyclerView.Adapter<ChiTietAddFavorAdapter.ViewHoler> {

    private Context context;
    private List<Tour> mtourList = new ArrayList<>();


    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChiTietAddFavorAdapter.ViewHoler(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chi_tiet_add_dat_ve_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        Tour tour = mtourList.get(position);
        long id = tour.getId();
        holder.tentour.setText(tour.getName());
        holder.ngaydi.setText(DateFormat.getDateInstance().format(tour.getNgayGioXuatPhat()));
        holder.ngayve.setText(DateFormat.getDateInstance().format(tour.getNgayVe()));
        holder.giatour.setText(String.valueOf(tour.getGiaThamKhao()));
        Picasso.get().load(tour.getImageUrls()).into(holder.imgAnhTour);
        holder.slot.setText(String.valueOf(tour.getSoLuongVe()));
        holder.soluongvemua.setText("0");

        holder.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return mtourList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        TextView tentour, giatour, ngaydi, ngayve, soluongvemua, slot;
        ImageView imgAnhTour, imgPlus, imgSub;
        Button btnAddCart;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);

          /*  tentour = itemView.findViewById(R.id.item_addtocart_name);
            giatour = itemView.findViewById(R.id.tv_price_tour);
            soluongvemua = itemView.findViewById(R.id.display);
            ngaydi = itemView.findViewById(R.id.item_addtocart_startTrip);
            ngayve = itemView.findViewById(R.id.item_addtocart_endTrip);
            imgAnhTour = itemView.findViewById(R.id.item_addtocart_image);
//            imgPlus = itemView.findViewById(R.id.item_addtocart_plus);
//            imgSub = itemView.findViewById(R.id.item_addtocart_sub);
            slot = itemView.findViewById(R.id.item_addtocart_slot_of_tour);
            btnAddCart = itemView.findViewById(R.id.btn_addtoFavo);*/
        }
    }
}

