package com.jenish.pennyconvert.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jenish.pennyconvert.R;
import com.jenish.pennyconvert.adapters.NewsListAdapter;
import com.jenish.pennyconvert.models.NewsModel;
import com.jenish.pennyconvert.services.NewsApiService;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        RecyclerView newsRecyclerView = view.findViewById(R.id.newRecyclerView);

        Thread newsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<NewsModel> newsModelList = NewsApiService.fetchData();
                // Handle the newsModelList here
                // You can update UI elements or perform other operations
                // For example, updating a RecyclerView adapter with the fetched data
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update UI elements or perform other operations
                        // For example, updating a RecyclerView adapter with the fetched data
                        NewsListAdapter adapter = new NewsListAdapter((Context) getActivity(), (ArrayList<NewsModel>) newsModelList);
                        newsRecyclerView.setAdapter(adapter);
                        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        newsRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
        newsThread.start();

        return  view;
    }
}