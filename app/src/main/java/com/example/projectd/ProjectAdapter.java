package com.example.projectd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectd.Model.project;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {

    private List<project> projectData;
    private Context context;
    private ClickedItem clickedItem;

    public ProjectAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(List<project> projectsData){
        this.projectData = projectsData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.project_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.MyViewHolder holder, int position) {

        project project = projectData.get(position);
        String _id = project.getId();
        String nama_project = project.getNama_project();
        String start_date = project.getStart_project();
        String end_date = project.getEnd_project();
        String max_orang = project.getMax_orang();
        Picasso.get().load("https://www.nusabali.com/event_images/78/bkft53-blaze-of-solidarity-2018-10-06-033630_0.jpg").into(holder.imageView);
        if(project.getStatus_project().matches("1")){
            holder.nama_project.setText(nama_project.concat(" (Active)"));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedItem.ClickedProject(project);
                }
            });
        } else {
            holder.nama_project.setText(nama_project.concat(" (InActive)"));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Project tidak aktif", Toast.LENGTH_SHORT).show();
                }
            });
        }
        holder.start_project.setText(start_date);
        holder.end_project.setText(end_date);
        holder.max_orang.setText(max_orang);

    }

    public interface ClickedItem{
        public void ClickedProject(project projectData);
    }

    @Override
    public int getItemCount() {
        return projectData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView _id, nama_project, start_project, end_project, max_orang;
        ImageView imageView;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardo);
            nama_project = itemView.findViewById(R.id.project_name);
            start_project = itemView.findViewById(R.id.start_date);
            end_project = itemView.findViewById(R.id.end_date);
            max_orang = itemView.findViewById(R.id.maksimal_orang);
            imageView = itemView.findViewById(R.id.project_image_view);
        }
    }
}
