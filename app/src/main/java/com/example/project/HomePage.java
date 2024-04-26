package com.example.project;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CourseDetailFragment;
import com.DTO;
import com.DTO1;
import com.QuizFragment;
import com.Recycler_View_Adapter;
import com.TaskFragment;
import com.WebsiteFragment;
import com.example.signup_fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lectures;
import com.lectureslist;
import com.login_fragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String name,course,fname,cnic;
    TextView wb,gd,viewall;
    FrameLayout frameLayout;
    RelativeLayout lectures,profile,our_team,site;
    Dialog courseslist;
    LinearLayout task,Quiz,web,graphic;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        courseslist = new Dialog(this);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        View headerView = navigationView.getHeaderView(0);
        TextView nametv = headerView.findViewById(R.id.nametv);
        TextView fnametv = headerView.findViewById(R.id.fnametv);
        TextView emailtv = headerView.findViewById(R.id.emailtv);
        TextView cnictv = headerView.findViewById(R.id.cnictv);
        TextView hiname = findViewById(R.id.Hiname);
        wb = (TextView) findViewById(R.id.webdevelopment);
        gd = (TextView) findViewById(R.id.graphicdesign);
        viewall = (TextView) findViewById(R.id.viewcourses);
        ImageView imageView = headerView.findViewById(R.id.profile);
        our_team = findViewById(R.id.my_favourite);
        site = findViewById(R.id.our_site);
        lectures = findViewById(R.id.my_course);
        task = findViewById(R.id.Task);
        Quiz = findViewById(R.id.Quiz);
        web = findViewById(R.id.web);
        graphic = findViewById(R.id.gd);
        profile = findViewById(R.id.my_profile);
        frameLayout = (FrameLayout)findViewById(R.id.framelayout1);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        DocumentReference docRef = db.collection("students").document(email);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    name = documentSnapshot.getString("Name");
                    fname = documentSnapshot.getString("Fname");
                    cnic = documentSnapshot.getString("CNIC");
                    String imageUrl = documentSnapshot.getString("ImageURL");
                    course = documentSnapshot.getString("Courses");

                    Picasso.get().load(imageUrl).into(imageView);
                    nametv.setText(name);
                    fnametv.setText(fname);
                    emailtv.setText(email);
                    cnictv.setText(cnic);
                    hiname.setText("Hi, "+name);
//                    Bundle args = new Bundle();
//                    args.putString("Course", course);
//                    lectureslist fragment = new lectureslist();
//                    fragment.setArguments(args);

                } else {

                }
            }
        });
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCoursesDialog();
            }
        });
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coursedetail(wb.getText().toString());
            }
        });
        graphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coursedetail(gd.getText().toString());
            }
        });
        lectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lectureslist newFragment = new lectureslist();
                Bundle args = new Bundle();
                args.putString("Course", course);
                newFragment.setArguments(args);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.framelayout1, newFragment);
                transaction.addToBackStack(null); // Add the transaction to the back stack
                transaction.commit();
            }
        });
        Quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.framelayout1,new QuizFragment());
                transaction.commit();
            }
        });
task.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        TaskFragment NewFragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString("Course", course);
        args.putString("Name", name);
        NewFragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout1, NewFragment);
        transaction.addToBackStack(null); // Add the transaction to the back stack
        transaction.commit();
    }
});
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile();
            }
        });
        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.framelayout1,new WebsiteFragment());
                transaction.commit();
            }
        });
        our_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.framelayout1,new OurTeam());
                transaction.commit();
            }
        });
    }
public void profile(){
    if(drawerLayout.isDrawerOpen(GravityCompat.START)){
        drawerLayout.closeDrawer(GravityCompat.START);
    }else{
        drawerLayout.openDrawer(GravityCompat.START);
    }
    }

    private void openCoursesDialog() {
        // Initialize the dialog
        Dialog coursesDialog = new Dialog(this);
        coursesDialog.setContentView(R.layout.newcourse);

        // Find the ListView in the dialog layout
        ListView listView = coursesDialog.findViewById(R.id.list_item);

        // Fetch data from Firestore and populate the ListView
        db.collection("Descriptions")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<String> courseNames = new ArrayList<>();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String courseTitle = document.getString("Title");

                            if (courseTitle != null) {
                                courseNames.add(courseTitle);
                            }
                        }

                        // Create an ArrayAdapter and set it to the ListView
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(HomePage.this, android.R.layout.simple_list_item_1, courseNames);
                        listView.setAdapter(adapter);

                        // Set item click listener if needed
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selectedCourse = courseNames.get(position);
                                coursedetail(selectedCourse);
                                coursesDialog.dismiss(); // Dismiss the dialog after an item is clicked
                            }
                        });

                        // Show the dialog
                        coursesDialog.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors
                        Toast.makeText(HomePage.this, "Error fetching data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void coursedetail(String cname){
        CourseDetailFragment NewFragment = new CourseDetailFragment();

        Bundle args = new Bundle();
        args.putString("name", cname);
        NewFragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout1, NewFragment);
        transaction.addToBackStack(null); // Add the transaction to the back stack
        transaction.commit();
    }
}
