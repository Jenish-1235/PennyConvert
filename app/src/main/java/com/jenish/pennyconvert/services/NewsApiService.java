package com.jenish.pennyconvert.services;

import com.google.gson.Gson;
import com.jenish.pennyconvert.models.NewsModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsApiService {

    public NewsModel fetchData(){
        NewsModel newsModel = new NewsModel();

        try{
            URL url = new URL("https://finnhub.io/api/v1/news?category=general&token=csvj9r1r01qo7odcsjb0csvj9r1r01qo7odcsjbg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
            }

            newsModel = new Gson().fromJson(response.toString(), NewsModel.class);

            return newsModel;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

}
