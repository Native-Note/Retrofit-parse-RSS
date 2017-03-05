package com.nativenote.movielist_retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nativenote.movielist_retrofit.R;
import com.nativenote.movielist_retrofit.model.Item;

import java.util.List;

/**
 * Created by imtiaz on 3/2/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private List<Item> movies;

    public MovieAdapter(Context context, List<Item> movies) {
        this.context = context;
        this.movies = movies;
    }

    public void setMovies(List<Item> movies) {
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item contact = movies.get(position);
        holder.title.setText(contact.getTitle());
        holder.date.setText(contact.getPubDate());
        holder.description.setText(contact.getDescription());
        holder.link.setText(contact.getLink());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, date, link, description;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            link = (TextView) view.findViewById(R.id.link);
            description = (TextView) view.findViewById(R.id.description);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Clicked: " + movies.get(getAdapterPosition()).getTitle(), Toast.LENGTH_LONG).show();
        }
    }
}
