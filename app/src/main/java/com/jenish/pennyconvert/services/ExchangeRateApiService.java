package com.jenish.pennyconvert.services;

import com.google.gson.Gson;
import com.jenish.pennyconvert.models.CurrencyModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Currency;

public class ExchangeRateApiService {
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/46eaa92b38df6d8e61037b38/latest/USD";

    public CurrencyModel fetchData(){
        CurrencyModel currencyModel = new CurrencyModel();

        try{
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            Gson gson = new Gson();
            currencyModel = gson.fromJson(response.toString(), CurrencyModel.class);



        }catch (Exception e){
            e.printStackTrace();
        }

        return currencyModel;
    }

}
