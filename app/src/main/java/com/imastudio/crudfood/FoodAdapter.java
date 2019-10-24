package com.imastudio.crudfood;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.imastudio.crudfood.model.DataItem;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    Context context;
    List<DataItem> dataItems;

    public FoodAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {

        final String id = dataItems.get(position).getMenuId();
        final String nama = dataItems.get(position).getMenuNama();
        final String harga = dataItems.get(position).getMenuHarga();
        final String url = dataItems.get(position).getMenuGambar();

        holder.tvName.setText(dataItems.get(position).getMenuNama());
        holder.tvPrice.setText(dataItems.get(position).getMenuHarga());
        Glide.with(context).load(dataItems.get(position).getMenuGambar()).into(holder.imgFood);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateDeleteActivity.class);
                intent.putExtra(UpdateDeleteActivity.KEY_ID, id);
                intent.putExtra(UpdateDeleteActivity.KEY_NAMA, nama);
                intent.putExtra(UpdateDeleteActivity.KEY_HARGA, harga);
                intent.putExtra(UpdateDeleteActivity.KEY_URL, url);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView tvName, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.item_image);
            tvName = itemView.findViewById(R.id.item_name);
            tvPrice = itemView.findViewById(R.id.item_price);

        }
    }
}
