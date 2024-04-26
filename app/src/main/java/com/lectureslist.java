package com;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class lectureslist extends Fragment implements LectureListAdapter.OnItemClickListener{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private LectureListAdapter adapter;
    private List<DTO> lectureList;
//    TextView tv;
    FrameLayout frameLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lectureslist, container, false);
        String Course = getArguments().getString("Course");
        recyclerView = view.findViewById(R.id.recyclerView);
        frameLayout = (FrameLayout) view.findViewById(R.id.framelayout2);
        lectureList = new ArrayList<>(); // Initialize lectureList here
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LectureListAdapter(lectureList, this);
        recyclerView.setAdapter(adapter);
        CollectionReference collectionRef = db.collection(Course);
        collectionRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            if (documentSnapshot.exists()) {
                              String  Topic = documentSnapshot.getString("Topic");
                                String Link = documentSnapshot.getString("Link");
                                DTO lectureslist = new DTO(Topic, Link);
                                lectureList.add(lectureslist);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        return view;
    }

    @Override
    public void onItemClick(int position,String link) {
        // Handle item click here

        replaceFragment(link);
    }

    private void replaceFragment(String link) {
        // Create a new instance of the fragment you want to navigate to
        // You might need to pass some data to the new fragment based on the selected item
        lectures newFragment = new lectures();

        // Use a Bundle to pass data to the new fragment if needed
        Bundle args = new Bundle();
        args.putString("link", link);
        // Replace with your actual data
        newFragment.setArguments(args);

        // Replace the current fragment with the new one
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout2, newFragment);
        transaction.addToBackStack(null); // Add the transaction to the back stack
        transaction.commit();
    }
}