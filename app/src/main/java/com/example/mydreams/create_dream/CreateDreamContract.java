package com.example.mydreams.create_dream;

import android.app.Activity;

import com.example.mydreams.model.Dreams;

public class CreateDreamContract {

    interface View {
        void setDream();
        void setDreamFromNotification();

        void onSaveDreamSuccess(String message);
        void onSaveDreamFailure(String message);
    }

    interface Presenter {
        void setUpData(String mode);

        void save(String mode, String id, String title, int evaluation, String content, String tag, long date);
    }

    interface Interactor {
        void performFirebaseAddingData(Dreams dream);
        void performFirebaseDataUpdate(String id, Dreams dream);
    }

    interface onCreateDreamListener {
        void onSuccess(String message);
        void onFailure(String message);
    }
}
