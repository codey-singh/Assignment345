package com.example.assignment345.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment345.R;
import com.example.assignment345.datastore.models.Order;

import java.util.ArrayList;

public class OrdersArrayAdapter extends ArrayAdapter<Order> {
    private ArrayList<Order> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView mealName;
        TextView total;
        TextView qty;
    }

    public OrdersArrayAdapter(ArrayList<Order> data, Context context) {
        super(context, R.layout.row, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Order o = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row, parent, false);
            viewHolder.mealName = (TextView) convertView.findViewById(R.id.mealName);
            viewHolder.total = (TextView) convertView.findViewById(R.id.total);
            viewHolder.qty = (TextView) convertView.findViewById(R.id.qty);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        Log.i("Adapter", "getView: "+ o.toString());
        viewHolder.mealName.setText(o.getMeal_name());
        viewHolder.qty.setText("" + o.getQuantity());
        viewHolder.total.setText("" + o.getTotalPrice());
        // Return the completed view to render on screen
        return convertView;
    }
}
