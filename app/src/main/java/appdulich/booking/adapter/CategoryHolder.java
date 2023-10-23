package appdulich.booking.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import thud.helloworld.R;

public class CategoryHolder extends RecyclerView.ViewHolder {

    TextView name, id;

    public CategoryHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.category_listItem_name);

    }
}
