package com.example.projectd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectd.Model.progressImageModel;
import com.example.projectd.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProgressImageAdapter extends RecyclerView.Adapter<ProgressImageAdapter.MyViewHolder> {
    private Context context;
    private List<progressImageModel> progressImageModels;

    public ProgressImageAdapter(List<progressImageModel> progressImageModels) {
        this.progressImageModels = progressImageModels;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_progress_project, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        progressImageModel progressImageModel = progressImageModels.get(position);
        String url = "https://000projectd.000webhostapp.com/progress_images/"+progressImageModel.getImage();
        Picasso.get().load("https://000projectd.000webhostapp.com/progress_images/"+progressImageModel.getImage()).into(holder.imageView);
        holder.textView.setText(progressImageModel.getCreated_at());

    }

    @Override
    public int getItemCount() {
        return progressImageModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.progress_image);
            textView = itemView.findViewById(R.id.created_at_text);
        }
    }
}
