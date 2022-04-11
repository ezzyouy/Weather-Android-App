package com.example.weather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListItemModel extends ArrayAdapter<MeteoList> {

    private List<MeteoList> meteoLists;
    private int resource;
    public static Map<String, Integer> images= new HashMap<>();
    static {
        images.put("Clear", R.drawable.clear);
        images.put("Clouds", R.drawable.cloud);
        images.put("Rain", R.drawable.rain);
        images.put("thunderstromspng", R.drawable.thunderstromspng);
    }

    public ListItemModel(@NonNull Context context, int resource, @NonNull List<MeteoList> data) {
        super(context,resource,data);
        Log.i("Mylog", String.valueOf(data.size()));
        this.meteoLists=data;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i("Mylog", "............."+position);
        View listItem=convertView;
        if(listItem==null){
            listItem= LayoutInflater.from(getContext()).inflate(resource,parent,false);
            ImageView imageView=listItem.findViewById(R.id.imageView);
            TextView textViewTM =listItem.findViewById(R.id.textView5);
            TextView textViewTMN =listItem.findViewById(R.id.textView3);
            TextView textViewP =listItem.findViewById(R.id.textView7);
            TextView textViewH =listItem.findViewById(R.id.textView9);
            TextView textViewD =listItem.findViewById(R.id.textView);
            String key=meteoLists.get(position).image;
            if(key!=null){
                imageView.setImageResource(images.get(key));
            }
            textViewTM.setText(String.valueOf(meteoLists.get(position).tempMax)+" C°");
            textViewTMN.setText(String.valueOf(meteoLists.get(position).tempMin)+" C°");
            textViewP.setText(String.valueOf(meteoLists.get(position).pression)+" hPa");
            textViewH.setText(String.valueOf(meteoLists.get(position).humidite)+" %");
            textViewD.setText(String.valueOf(meteoLists.get(position).date));

        }
        return listItem;
       // return super.getView(position, convertView, parent);
    }
}
