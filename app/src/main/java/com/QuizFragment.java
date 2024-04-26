package com;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.project.HomePage;
import com.example.project.R;

import java.util.ArrayList;

public class QuizFragment extends Fragment {


    ArrayList<DTO1> Questions = new ArrayList<>();
    RecyclerView recyclerView;
    Recycler_View_Adapter recycler_view_adapter;
    public Button submit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_quiz, container, false);
        submit = (Button) view.findViewById(R.id.submit);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recycler_view_adapter = new Recycler_View_Adapter(getContext(),Questions);
        recyclerView.setAdapter(recycler_view_adapter);
        Questions.add(new DTO1("Which programming language is often used for web development?",
                "Java",
                "C++",
                "JavaScript",
                "C"));
        Questions.add(new DTO1("Which data structure uses Last In, First Out (LIFO) order?",
                "Queue",
                "Stack",
                "Array",
                "Linked List"));
        Questions.add(new DTO1("Which of the following is an example of an object-oriented programming language?",
                "C",
                "FORTRAN",
                "Assembly",
                "Java"));
        Questions.add(new DTO1("What is the chemical symbol for gold?",
                "Gd",
                "Au",
                "Fe",
                "Ag"));
        Questions.add(new DTO1("Which continent is the largest by land area?",
                "Asia",
                "Africa",
                "N America",
                "Europe"));
        Questions.add(new DTO1("What is the value of π (pi) to two decimal places?",
                "3.14",
                "3.15",
                "3.16",
                "3.17"));
        Questions.add(new DTO1("In what year did the Titanic sink?",
                "1910",
                "1911",
                "1912",
                "1913"));
        Questions.add(new DTO1("Which country won the FIFA World Cup in 2018?",
                "Brazil",
                "Spain",
                "France",
                "India"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Your Response Has been Recorded", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}