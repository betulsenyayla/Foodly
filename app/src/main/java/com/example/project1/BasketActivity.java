package com.example.project1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView alldelete;
    Button odemeButton;

    int total;
    TextView toplam;

    MyDatabaseHelper myDB;
    ArrayList<String> food_id, food_name, food_img, food_price,food_piece;
    BasketAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        recyclerView = findViewById(R.id.recyclerView);

        toplam = findViewById(R.id.toplam);

        alldelete = findViewById(R.id.delete);

        odemeButton = findViewById(R.id.buttonOde);

        myDB = new MyDatabaseHelper(BasketActivity.this);
        food_id = new ArrayList<>();
        food_name = new ArrayList<>();
        food_img = new ArrayList<>();
        food_price = new ArrayList<>();
        food_piece = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new BasketAdapter(BasketActivity.this,this, food_id, food_name, food_img,
                food_price,food_piece);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BasketActivity.this));

        alldelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BasketActivity.this);
                builder.setTitle("Sepettekileri Sil");
                builder.setMessage("Sepettekileri Silmek İstediğinize Emin Misiniz?");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyDatabaseHelper myDB = new MyDatabaseHelper(BasketActivity.this);
                        myDB.deleteAllData();
                        //Refresh Activity
                        Intent intent = new Intent(BasketActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
            }
        });


        odemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BasketActivity.this, LottieActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Sepet Boş", Toast.LENGTH_LONG).show();
        }else{
            while (cursor.moveToNext()){
                food_id.add(cursor.getString(0));
                food_name.add(cursor.getString(1));
                food_img.add(cursor.getString(2));
                food_price.add(cursor.getString(3));
                food_piece.add(cursor.getString(4));
            }

            for (int i=0;i<food_id.size();i++) {
                total=total+(Integer.parseInt(food_price.get(i))*Integer.parseInt(food_piece.get(i)));
            }

        }


        toplam.setText("Toplam Fiyat: "+String.valueOf(total)+" ₺");
    }
}
