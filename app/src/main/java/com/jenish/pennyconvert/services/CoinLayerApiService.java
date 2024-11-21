package com.jenish.pennyconvert.services;

import android.util.Log;

import com.google.gson.Gson;
import com.jenish.pennyconvert.models.CryptoModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoinLayerApiService {
    public CoinLayerApiService(){};
    public CryptoModel fetchData(){

        try{
            URL url = new URL("https://api.coinlayer.com/api/live?access_key=c82cdf2236d09de9df51aa030d4f3813");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            Gson gson = new Gson();
            CryptoModel cryptoModel = gson.fromJson(response.toString(), CryptoModel.class);
            Log.i("Datatata", "Data fetched + " + cryptoModel.rates.size());
            return cryptoModel;

        } catch (Exception e) {
            Log.e("Datatata", "Error fetching data: " + e.getMessage());
        }
        return null;
    }

}
