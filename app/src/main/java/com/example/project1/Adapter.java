package com.example.project1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.GenericArrayType;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context mContext;
    private List<FoodModelClass> mData;

    public Adapter(Context mContext, List<FoodModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v=inflater.inflate(R.layout.food_item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.price.setText(mData.get(position).getPrice()+" TL");
        holder.name.setText(mData.get(position).getName());

        Glide.with(mContext)
                .load(mData.get(position).getImg())
                .into(holder.img);

        holder.food_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("food_name", mData.get(position).getName());
                intent.putExtra("food_url", mData.get(position).getImg());
                intent.putExtra("food_price",mData.get(position).getPrice());
                intent.putExtra("food_id",mData.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        ImageView img;
        LinearLayout food_list;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            price=itemView.findViewById(R.id.food_price);
            name=itemView.findViewById(R.id.food_name);
            img = itemView.findViewById(R.id.food_image);
            food_list=itemView.findViewById(R.id.food_list);
        }
    }
}
