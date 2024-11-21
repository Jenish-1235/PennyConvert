package com.jenish.pennyconvert.services;

import com.google.gson.Gson;
import com.jenish.pennyconvert.models.NewsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsApiService {

    public static List<NewsModel> fetchData(){
        List<NewsModel> newsModelList = new ArrayList<>();
        try{
            URL url = new URL("https://finnhub.io/api/v1/news?category=general&token=csvj9r1r01qo7odcsjb0csvj9r1r01qo7odcsjbg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Parse the JSON
                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Extract fields
                    String headline = jsonObject.getString("headline");
                    String summary = jsonObject.getString("summary");
                    String source = jsonObject.getString("source");
                    String urlStr = jsonObject.getString("url");
                    String image = jsonObject.getString("image");

                    // Create and add NewsItem to the list
                    newsModelList.add(new NewsModel(headline, summary, source, urlStr, image));
                }
            } else {
                System.err.println("HTTP Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;

    }

}
