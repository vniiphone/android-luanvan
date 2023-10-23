package appdulich.booking.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TourHolder extends RecyclerView.ViewHolder {
    TextView name, startTrip, endTrip, price, description, slot;
    ImageView anhTour;
    public TourHolder(@NonNull View itemView) {
        super(itemView);

   /*     name = itemView.findViewById(R.id.item_tentour);
        startTrip = itemView.findViewById(R.id.item_startTrip);
        endTrip = itemView.findViewById(R.id.item_endTrip);
        price = itemView.findViewById(R.id.item_price);
//        description = itemView.findViewById(R.id.item_description);
        anhTour = itemView.findViewById(R.id.item_image);
        slot = itemView.findViewById(R.id.item_slot);*/
    }
}
