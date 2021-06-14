package com.example.mydreams.main;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.mydreams.model.Dreams;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainInteractor implements MainContract.Interactor {

    private MainContract.onMainListener onMainListener;

    public MainInteractor(MainContract.onMainListener onMainListener) {
        this.onMainListener = onMainListener;
    }

    @Override
    public void performFirebaseUserIdentification() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            onMainListener.onUserIdentificationSuccess();
        }
        else {
            onMainListener.onUserIdentificationFailure();
        }
    }

    @Override
    public void performFirebaseDataDeletion(String id) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = FirebaseFirestore.getInstance()
                .collection("dreams")
                .document(firebaseUser.getUid())
                .collection("myDreams")
                .document(id);

        documentReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onMainListener.onSuccess("Удалено");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onMainListener.onFailure(e.getMessage());
                    }
                });
    }

    @Override
    public void performFirebaseGettingData(String id) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = FirebaseFirestore.getInstance()
                .collection("dreams")
                .document(firebaseUser.getUid())
                .collection("myDreams")
                .document(id);

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Dreams dream = documentSnapshot.toObject(Dreams.class);
                        onMainListener.onGetDataSuccess(dream);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onMainListener.onFailure(e.getMessage());
                    }
                });
    }

    @Override
    public void performFirebaseLogOut() {
        FirebaseAuth.getInstance().signOut();
        onMainListener.onLoggedOut();
    }
}
