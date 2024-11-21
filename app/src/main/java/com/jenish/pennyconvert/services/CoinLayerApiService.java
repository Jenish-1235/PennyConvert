package com.jenish.pennyconvert.services;

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
    private static final String BASE_URL = "http://api.coinlayer.com/live?access_key=d0b5a02f4300892deddee7f02f414906";

    public CryptoModel fetchData(){

        try{
            URL url = new URL(BASE_URL);
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
            return cryptoModel;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
