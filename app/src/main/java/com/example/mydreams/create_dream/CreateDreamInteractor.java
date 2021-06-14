package com.example.mydreams.create_dream;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.mydreams.model.Dreams;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateDreamInteractor implements CreateDreamContract.Interactor {

    private CreateDreamContract.onCreateDreamListener onCreateDreamListener;

    public CreateDreamInteractor(CreateDreamContract.onCreateDreamListener onCreateDreamListener) {
        this.onCreateDreamListener = onCreateDreamListener;
    }

    @Override
    public void performFirebaseAddingData(Dreams dream) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = FirebaseFirestore.getInstance()
                .collection("dreams")
                .document(firebaseUser.getUid()).collection("myDreams")
                .document();

        documentReference.set(dream)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onCreateDreamListener.onSuccess("Запись добавлена");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onCreateDreamListener.onFailure(e.getMessage());
                        //onCreateDreamListener.onFailure("Ошибка при создании новой записи");
                    }
                });
    }

    @Override
    public void performFirebaseDataUpdate(String id, Dreams dream) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = FirebaseFirestore.getInstance()
                .collection("dreams")
                .document(firebaseUser.getUid())
                .collection("myDreams")
                //.document(data.getStringExtra("dreamId"));
                .document(id);

        documentReference.set(dream)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        onCreateDreamListener.onSuccess("Запись обновлена");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onCreateDreamListener.onFailure(e.getMessage());
                    }
                });
    }
}
