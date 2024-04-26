package com;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class CourseDetailFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView title,tutor,description,lectures,quiz,task;
    String name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        name = getArguments().getString("name");
        View view = inflater.inflate(R.layout.fragment_course_detail, container, false);
        title = (TextView) view.findViewById(R.id.title);
        tutor = (TextView) view.findViewById(R.id.tutor);
        description = (TextView) view.findViewById(R.id.description);
        lectures = (TextView) view.findViewById(R.id.lecturesNo);
        quiz = (TextView) view.findViewById(R.id.QuizNo);
        task = (TextView) view.findViewById(R.id.totalTask);
        title.setText(name);
        db.collection("Descriptions").document(name)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        tutor.setText(documentSnapshot.getString("Tutor"));
                        task.setText(documentSnapshot.getString("TotalTask"));
                        quiz.setText(documentSnapshot.getString("TotalQuiz"));
                        lectures.setText(documentSnapshot.getString("TotalLectures"));
                        description.setText(documentSnapshot.getString("Description"));
                    }
                });
        return view;
    }
}