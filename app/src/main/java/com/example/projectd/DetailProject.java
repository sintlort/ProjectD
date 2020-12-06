package com.example.projectd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.projectd.Model.project;
import org.w3c.dom.Text;

public class DetailProject extends AppCompatActivity {
    TextView nama, start, end, descP;
    project Project;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);
        nama = findViewById(R.id.nama);
        start = findViewById(R.id.tanggalstart);
        end = findViewById(R.id.tanggalend);
        descP = findViewById(R.id.description_project);

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            Project = (project) intent.getSerializableExtra("data");
            String namaproject = Project.getNama_project();
            String startdate = Project.getStart_project();
            String enddate = Project.getEnd_project();
            String desc = Project.getDesc_project();
            nama.setText(namaproject);
            start.setText(startdate);
            end.setText(enddate);
            descP.setText(desc);

        }
    }
}