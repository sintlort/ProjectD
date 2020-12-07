package com.example.projectd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectd.Model.project;

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
        holder._id.setText(_id);
        if(project.getStatus_project().matches("1")){
            holder.nama_project.setText(nama_project.concat(" (Active)"));
        } else {
            holder.nama_project.setText(nama_project.concat(" (InActive)"));
        }
        holder.start_project.setText(start_date);
        holder.end_project.setText(end_date);
        holder.max_orang.setText(max_orang);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItem.ClickedProject(project);
            }
        });

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
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardo);
            _id = itemView.findViewById(R.id._id_project);
            nama_project = itemView.findViewById(R.id.project_name);
            start_project = itemView.findViewById(R.id.start_date);
            end_project = itemView.findViewById(R.id.end_date);
            max_orang = itemView.findViewById(R.id.maksimal_orang);
        }
    }
}
