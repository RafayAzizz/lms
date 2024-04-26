package com;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.List;

import io.grpc.Context;

public class LectureListAdapter extends RecyclerView.Adapter<LectureListAdapter.ViewHolder> {
    private List<DTO> lecturesList;
    private OnItemClickListener itemClickListener;
    public LectureListAdapter(List<DTO> lecturesList, OnItemClickListener itemClickListener) {
        this.lecturesList = lecturesList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }
    public interface OnItemClickListener {
        void onItemClick(int position, String link);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DTO lectureslist = lecturesList.get(position);

        // Bind the data to the ViewHolder

        holder.TopicTextView.setText(lectureslist.getTopic());
        holder.linkTextView.setText(lectureslist.getLink());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position,lectureslist.getLink());
            }
        });

    }
    @Override
    public int getItemCount() {
        return lecturesList.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView TopicTextView,linkTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TopicTextView = itemView.findViewById(R.id.TopicTextView);
            linkTextView = itemView.findViewById(R.id.LinkTextView);
        }
    }

}

