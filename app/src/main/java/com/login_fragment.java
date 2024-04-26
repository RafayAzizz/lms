package com;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project.HomePage;
import com.example.project.R;
import com.example.signup_fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class login_fragment extends Fragment {
EditText email,password;
TextView signuptv;
Button loginbtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<String> loginEmails = new ArrayList<>();
    ArrayList<String> loginPasswords = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = (EditText) view.findViewById(R.id.et_email);
        password = (EditText) view.findViewById(R.id.et_pass);
        signuptv =(TextView) view.findViewById(R.id.signuptv);
        loginbtn = (Button) view.findViewById(R.id.loginbtn);

        db.collection("students")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                loginEmails.add(document.getString("Email"));
                                loginPasswords.add(document.getString("Pass"));
                            }
                            }

                    }
                });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = email.getText().toString();
                String enteredPassword = password.getText().toString();

                boolean found = false;

                if (TextUtils.isEmpty(enteredEmail) || TextUtils.isEmpty(enteredPassword)) {
                    Toast.makeText(getActivity(), "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < loginEmails.size(); i++) {
                    if (enteredEmail.equals(loginEmails.get(i)) && enteredPassword.equals(loginPasswords.get(i))) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), HomePage.class);
                    intent.putExtra("email", enteredEmail);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Incorrect Password or Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.framelayout,new signup_fragment());
                transaction.commit();

            }
        });
        return view;
    }
}