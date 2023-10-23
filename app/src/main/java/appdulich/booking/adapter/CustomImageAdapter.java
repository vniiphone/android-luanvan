package appdulich.booking.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import thud.helloworld.R;

public class CustomImageAdapter extends BaseAdapter {
    private Activity context;
    private int layoutId;
    private Integer[] Images;
    private String[] Texts;
    public CustomImageAdapter(Activity context, int layoutId, Integer[] images, String[] texts){
        this.context = context;
        this.layoutId = layoutId;
        this.Images = images;
        this.Texts = texts; }
    public void setDataSource(Integer[] images, String[] texts) {
        Images = images; Texts = texts;
    }

    @Override
    public int getCount() {
        return Images.length;
    }

    @Override
    public Object getItem(int i)  {
        return Images[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, parent, false);
        Holder holder = new Holder();
        holder.txtText = convertView.findViewById(R.id.txt_Text);
        holder.imgImage = convertView.findViewById(R.id.img_ImageIconTool);
        convertView.setTag(holder); holder.txtText.setText(Texts[position]);
        holder.imgImage.setImageResource(Images[position]); return convertView;

    }
    private class Holder{
        TextView txtText;
        ImageView imgImage;
    }
}
