package com.jenish.pennyconvert.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jenish.pennyconvert.R;
import com.jenish.pennyconvert.models.NewsModel;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private Context context;
    private ArrayList<NewsModel> newsList;

    public NewsListAdapter(Context context, ArrayList<NewsModel> newsList){
        this.context = context;
        this.newsList = newsList;
    }

  public class NewsViewHolder extends RecyclerView.ViewHolder{
        ImageView newsImage = itemView.findViewById(R.id.newsImage);
        TextView newsHeadline = itemView.findViewById(R.id.newsHeadLine);
        TextView newsSummary = itemView.findViewById(R.id.newsSummary);

      public NewsViewHolder(@NonNull View itemView) {
          super(itemView);
      }
  }






    @NonNull
    @Override
    public NewsListAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.NewsViewHolder holder, int position) {
        holder.newsHeadline.setText(newsList.get(position).headline);
        holder.newsSummary.setText(newsList.get(position).summary);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
