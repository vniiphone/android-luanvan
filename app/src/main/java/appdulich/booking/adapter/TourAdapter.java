/*
package appdulich.booking.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import appdulich.booking.giaodien.DetailActivity;
import appdulich.booking.model.Tour;
import thud.helloworld.R;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {
    private Context context;
    private List<Tour> mtourList = new ArrayList<>();

    public TourAdapter() {

    }

    public void setData(List<Tour> list) {
        this.mtourList = list;
        notifyDataSetChanged();
    }

    public TourAdapter(Context context, List<Tour> tourList) {
        if (tourList != null) {
            mtourList = tourList;
            notifyDataSetChanged();
        }
        this.context = context;
        this.mtourList = tourList;
    }

    @NonNull
    @Override
    public TourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tour, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TourAdapter.ViewHolder holder, int position) {

        final Tour tour = mtourList.get(position);
        holder.tentour.setText(tour.getName());
//        holder.startTrip.setText(DateFormat.getDateInstance().format(tour.getClass().get));
//        holder.endTrip.setText(DateFormat.getDateInstance().format(tour.getEndTrip()));
        holder.description.setText(tour.getTomTat());

        double price = tour.getGiaThamKhao();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VND");
        String formattedPrice = decimalFormat.format(price);
        holder.price.setText(formattedPrice);
        holder.slot.setText(String.valueOf(tour.getSoLuongVe()));
        //holder.slot.setText(tour.getSlot());
        holder.price.setText(String.valueOf(tour.getGiaThamKhao()));
        Picasso.get().load(tour.getImageUrls()).into(holder.anhTour);

        holder.layoutItem.setOnClickListener(view ->
                onClickGoToDetail(tour));
        holder.btnChiTietTour.setOnClickListener(view ->
                onClickGoToDetail(tour));
    }

    private void onClickGoToDetail(Tour tour) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_tour", tour);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mtourList.size();
    }

    public void release() {
        context = null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tentour, startTrip, endTrip, price, description, slot;
        private ImageView anhTour;
        private LinearLayout layoutItem;
        private Button btnChiTietTour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_itemTour);
            tentour = itemView.findViewById(R.id.item_tentour);
            startTrip = itemView.findViewById(R.id.item_startTrip);
            endTrip = itemView.findViewById(R.id.item_endTrip);
            price = itemView.findViewById(R.id.item_price);
//            description = itemView.findViewById(R.id.item_description);
            anhTour = itemView.findViewById(R.id.item_image_main_tour);
            slot = itemView.findViewById(R.id.item_slot);
            btnChiTietTour = itemView.findViewById(R.id.btn_chitiet);
        }
    }
}
*/
