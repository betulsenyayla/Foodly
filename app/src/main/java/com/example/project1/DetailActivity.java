package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    Button sepete_ekle;

    List<FoodModelClass> foodList;
    String imageUrl, imageName, foodprice, foodId,piece;

    TextView foodPiece;
    ImageView positive, negative;

    String adet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sepete_ekle = findViewById(R.id.sepete_ekle);
        foodPiece = findViewById(R.id.foodPiece);

        foodList = new ArrayList<>();

        positive = findViewById(R.id.positive);
        negative = findViewById(R.id.negative);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               adet=String.valueOf(Integer.parseInt(foodPiece.getText().toString())+1);
               foodPiece.setText(adet);
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(foodPiece.getText().toString()) > 1) {
                    adet=String.valueOf(Integer.parseInt(foodPiece.getText().toString())-1);
                    foodPiece.setText(adet);
                }

            }
        });

        getIncomingIntent();

        sepete_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(DetailActivity.this);
                myDB.addBook(imageName,
                        imageUrl,
                        foodprice,
                        foodPiece.getText().toString());
            }
        });

    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("food_url") && getIntent().hasExtra("food_name")){

            imageUrl = getIntent().getStringExtra("food_url");
            imageName = getIntent().getStringExtra("food_name");
            foodprice = getIntent().getStringExtra("food_price");
            foodId = getIntent().getStringExtra("food_id");

            setImage(imageUrl, imageName,foodprice,piece);
        }
    }

    private void setImage(String imageUrl, String imageName,String foodprice,String piece){

        TextView name = findViewById(R.id.image_description);
        name.setText(imageName);

        TextView food_price = findViewById(R.id.food_price);
        food_price.setText(foodprice+" TL");

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

}