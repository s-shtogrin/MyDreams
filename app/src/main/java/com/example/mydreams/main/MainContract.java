package com.example.mydreams.main;

import android.app.Activity;

import com.example.mydreams.model.Dreams;

public interface MainContract {

    interface View {
        void userIdentified();
        void userNotIdentified();

        void showDreamDetails(Dreams dream);
        void showDialog(String message);

        void notificationOn();
        void notificationOff();

        void onDeleteDataSuccess(String message);
        void onDeleteDataFailure(String message);
        void userLoggedOut();
    }

    interface Presenter {
        void userIdentification();

        void setUpNotificationChannels(Activity activity);
        void checkNotificationStatus();

        void delete();

        void logout();
    }

    interface Interactor {
        void performFirebaseUserIdentification();

        void performFirebaseDataDeletion(String docId);
        void performFirebaseGettingData(String docId);

        void performFirebaseLogOut();
    }

    interface onMainListener {
        void onSuccess(String message);
        void onFailure(String message);

        void onGetDataSuccess(Dreams dream);

        void onUserIdentificationSuccess();
        void onUserIdentificationFailure();

        void onItemClicked(String docId);
        void onItemDeleteClicked(String docId);

        void onLoggedOut();
    }
}
