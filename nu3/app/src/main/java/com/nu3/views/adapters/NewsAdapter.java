package com.nu3.views.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nu3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.News;

/**
 * Created by carlostriana on 3/29/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<News> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public TextView mDescriptionTextView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.newRowTitle);
            mImageView = (ImageView) v.findViewById(R.id.newRowImage);
            mDescriptionTextView = (TextView) v.findViewById(R.id.newRowDescription);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapter(ArrayList<News> news) {
        mDataset = news;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.mImageView.getContext();
        String IMAGE_URL = "http://cms.maksuco.com/uploads/7/items/" + mDataset.get(position).getImageURL();
        holder.mTextView.setText(mDataset.get(position).getName());
        holder.mDescriptionTextView.setText(Html.fromHtml(mDataset.get(position).getDescriptionSpanish()));
        Picasso.with(context).load(IMAGE_URL).into(holder.mImageView);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
