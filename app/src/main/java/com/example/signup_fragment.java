package com.example;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.HomePage;
import com.example.project.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.login_fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class signup_fragment extends Fragment {


FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private static final int PICK_IMAGE = 1;
    Button signupbtn;
    EditText name, fname,cnic,email,pass;
    AutoCompleteTextView textViewSelectedCourse;
    TextInputLayout spinnerCourses;

    ImageView pickimage,pickimagebtn;
    String imageUrl ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        pickimage = (ImageView) view.findViewById(R.id.pickimage);
        name = (EditText)view.findViewById(R.id.et_name);
        fname = (EditText)view.findViewById(R.id.et_fname);
        cnic = (EditText)view.findViewById(R.id.et_CNIC);
        email = (EditText)view.findViewById(R.id.et_signup_email);
        pass = (EditText)view.findViewById(R.id.et_signup_pass);
        pickimagebtn =  view.findViewById(R.id.pickimagebtn);
        signupbtn = (Button) view.findViewById(R.id.signupbtn);

        textViewSelectedCourse = view.findViewById(R.id.select_course);
        spinnerCourses = view.findViewById(R.id.spinner);


        String[] coursess= {"App Development", "Web Development", "Digital Marketing","Cyber Security","Graphic Design"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, coursess);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



//        AutoCompleteTextView professionAutoCompleteTextView = findViewById(R.id.select_course);
        textViewSelectedCourse.setAdapter(adapter);

        textViewSelectedCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedProfession = (String) parent.getItemAtPosition(position);

                }
        });

        pickimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = name.getText().toString();
                String userfname = fname.getText().toString();
                String usercnic = cnic.getText().toString();
                String useremail = email.getText().toString();
                String userpass = pass.getText().toString();
                String userCourses = textViewSelectedCourse.getText().toString();
                Map<String, Object> userData = new HashMap<>();
                userData.put("Name", userName);
                userData.put("Fname", userfname);
                userData.put("CNIC", usercnic);
                userData.put("Pass", userpass);
                userData.put("Email", useremail);
                userData.put("ImageURL", imageUrl);
                userData.put("Courses", userCourses);
                db.collection("students")
                        .document(useremail)
                        .set(userData)
                        .addOnSuccessListener(aVoid -> {
                            // Document added successfully

                            Toast.makeText(getActivity(), "Record Added Successfully " , Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.framelayout,new login_fragment());
                            transaction.commit();
                        })
                        .addOnFailureListener(e -> {
                            // Handle any errors
                            Toast.makeText(getActivity(), "Error uploading data to Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
        setUponBackPressed();
        return view;

    }
    private void openGallery() {
        ImagePicker.with(this)
                .crop()
               // .compress(1024)			//Final image size will be less than 1 MB(Optional)
               // .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (data != null) {
                Uri imageUri = data.getData();
                pickimage.setImageURI(imageUri);

                // Create a reference to store the image in Firebase Storage
                StorageReference imageRef = storageRef.child("images/" + UUID.randomUUID().toString());

                // Upload the image to Firebase Storage
                UploadTask uploadTask = imageRef.putFile(imageUri);

                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully, get the download URL
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        imageUrl = uri.toString();
                    });
                }).addOnFailureListener(e -> {
                });
            }


    } public void setUponBackPressed(){
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    setEnabled(false);
                    requireActivity().onBackPressed();
                }
            }
        });
    }

}