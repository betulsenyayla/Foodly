package com.example.project1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList food_id, food_name, food_img, food_price, food_piece;

    BasketAdapter(Activity activity, Context context, ArrayList food_id, ArrayList food_name, ArrayList food_img,
                  ArrayList food_price, ArrayList food_piece){
        this.activity = activity;
        this.context = context;
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_img = food_img;
        this.food_price = food_price;
        this.food_piece = food_piece;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.food_id_txt.setText(String.valueOf(food_id.get(position)));
        holder.food_name_txt.setText(String.valueOf(food_name.get(position)));
        holder.food_price_txt.setText(String.valueOf(food_price.get(position))+" TL");
        holder.food_piece_txt.setText(String.valueOf(food_piece.get(position))+" Adet");

        Glide.with(context)
                .load(food_img.get(position))
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return food_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView food_id_txt, food_name_txt, food_price_txt, food_piece_txt;

        ImageView img,delete_button;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            food_id_txt = itemView.findViewById(R.id.food_id_txt);
            food_name_txt = itemView.findViewById(R.id.food_name_txt);
            img = itemView.findViewById(R.id.food_img);
            food_price_txt = itemView.findViewById(R.id.food_price_txt);
            food_piece_txt = itemView.findViewById(R.id.food_piece_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);


            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
