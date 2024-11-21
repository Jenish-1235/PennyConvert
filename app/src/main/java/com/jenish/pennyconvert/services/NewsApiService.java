package com.jenish.pennyconvert.services;

import android.util.Log;

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

    public static List<NewsModel> fetchData() {
        List<NewsModel> newsModelList = new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://finnhub.io/api/v1/news?category=general&token=csvj9r1r01qo7odcsjb0csvj9r1r01qo7odcsjbg");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                System.out.println("Raw Response: " + response);

                // Parse JSON array
                JSONArray jsonArray = new JSONArray(response.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject newsObject = jsonArray.getJSONObject(i);

                    // Create a NewsItem object and populate its fields
                    NewsModel news = new NewsModel();
                    news.category = newsObject.optString("category");
                    news.datetime = newsObject.optLong("datetime");
                    news.headline = newsObject.optString("headline");
                    news.newsId = newsObject.optInt("id");
                    news.image = newsObject.optString("image");
                    news.source = newsObject.optString("source");
                    news.summary = newsObject.optString("summary");
                    news.url = newsObject.optString("url");

                    // Add to list
                    newsModelList.add(news);
                }
            } else {
                System.err.println("Unexpected Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return newsModelList;
    }
}