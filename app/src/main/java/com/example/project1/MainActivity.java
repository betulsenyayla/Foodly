package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String JSON_URL = "http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php";

    List<FoodModelClass> foodList;
    RecyclerView recyclerView;
    ImageView basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        foodList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);

        basket = findViewById(R.id.basket);

        basketLogin();

        GetData getData = new GetData();
        getData.execute();

    }

    public void basketLogin() {
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,BasketActivity.class);
                startActivity(intent);
            }
        });
    }

    public class GetData extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();


                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {

                        current += (char) data;
                        data = isr.read();

                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            }catch (Exception e) {
                e.printStackTrace();
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("yemekler");

                for (int i=0;i < jsonArray.length();i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    FoodModelClass model = new FoodModelClass();
                    model.setId(jsonObject1.getString("yemek_id"));
                    model.setName(jsonObject1.getString("yemek_adi"));
                    model.setPrice(jsonObject1.getString("yemek_fiyat"));
                    model.setImg("http://kasimadalan.pe.hu/yemekler/resimler/"+jsonObject1.getString("yemek_resim_adi"));

                    foodList.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(foodList);
        }
    }

    private void PutDataIntoRecyclerView(List<FoodModelClass> foodList) {

        Adapter adapter = new Adapter(this,foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
    }


}