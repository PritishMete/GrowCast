package com.example.growcast;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class utility {

    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReferenceForNotes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            return FirebaseFirestore.getInstance().collection("notes")
                    .document(userEmail).collection("my_notes");
        } else {
            // Handle the case when the current user is null (not signed in)
            // For example, you can return a default collection reference or show an error message.
            return FirebaseFirestore.getInstance().collection("default_notes");
        }
    }

    static String timestampToString(Timestamp timestamp) {
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }
}
