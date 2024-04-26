package com;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class TaskFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private static final int PICK_FILE_REQUEST_CODE = 3;

    TextView taskTextView;
    String name, fileUrl;
    private Button btnPickPDF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        String course = getArguments().getString("Course");
        name = getArguments().getString("Name");
        taskTextView = view.findViewById(R.id.Task);
        btnPickPDF = view.findViewById(R.id.pick_pdf_button);
        btnPickPDF.setOnClickListener(v -> checkAndRequestPermission());

        db.collection(course + " Task")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (DocumentSnapshot document : task.getResult()) {
                            taskTextView.setText(document.getString("Task"));
                        }
                    }
                });

        return view;
    }

    private void checkAndRequestPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            startFilePicker();
        } else {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_FILE_REQUEST_CODE);
        }
    }

    private void startFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf"); // Set the MIME type to filter the kind of files to be displayed
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PICK_FILE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startFilePicker();
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                // Get the selected file URI
                Uri fileUri = data.getData();

                // Upload the file to Firebase Storage
                uploadFileToFirebaseStorage(fileUri);
            }
        }
    }

    private void uploadFileToFirebaseStorage(Uri fileUri) {
        // Create a storage reference with a unique name (e.g., using the current timestamp)
        String fileName = "file_" + System.currentTimeMillis() + ".pdf";
        StorageReference fileRef = storageRef.child("Tasks").child(fileName);

        // Upload file to Firebase Storage
        UploadTask uploadTask = fileRef.putFile(fileUri);

        // Register observers to listen for when the upload is successful or if it fails
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Handle successful upload
            Toast.makeText(requireContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();

            // Get the download URL of the uploaded file
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                fileUrl = uri.toString();

                // You can now use the download URL as needed
                // For example, you might store it in the database along with other information.
                // You can also display the download URL, share it, etc.

                // Update the Firestore document with the file URL
                updateFirestoreDocument(fileUrl);
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(requireContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void updateFirestoreDocument(String fileUrl) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("Assignment", fileUrl);
        userData.put("Task", taskTextView.getText().toString()); // Get the task text
        userData.put("Name", name);

        // Use your specific document reference instead of document() if needed
        db.collection("Assignment Submission")
                .add(userData)
                .addOnSuccessListener(documentReference -> {
                    // Document added successfully
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    Toast.makeText(requireContext(), "Error uploading data to Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}