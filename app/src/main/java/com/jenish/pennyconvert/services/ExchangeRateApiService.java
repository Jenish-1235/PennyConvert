package com.jenish.pennyconvert.services;

import android.util.Log;

import com.google.gson.Gson;
import com.jenish.pennyconvert.models.CurrencyModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Currency;

public class ExchangeRateApiService {
    public ExchangeRateApiService(){};

    public CurrencyModel fetchData(){

        try{
            URL url = new URL("https://v6.exchangerate-api.com/v6/46eaa92b38df6d8e61037b38/latest/USD");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
            Gson gson = new Gson();
            CurrencyModel currencyModel = gson.fromJson(response.toString(), CurrencyModel.class);
//            Log.i("Datatata", "Data fetched + " + currencyModel.conversion_rates.size());
            return currencyModel;

        }catch (Exception e){
//            Log.e("Datatata", "Error fetching data: " + e.getMessage());
        }

        return null;
    }

}
