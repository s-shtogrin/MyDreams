package com.example.mydreams.main;

import android.app.Activity;
import android.os.Build;

import com.example.mydreams.model.Dreams;
import com.example.mydreams.notifications.AppNotificationManager;
import com.example.mydreams.notifications.NotificationContract;

public class MainPresenter implements MainContract.Presenter, MainContract.onMainListener {

    private MainContract.View mainView;
    private MainInteractor mainInteractor;

    private String dreamId;

    private AppNotificationManager notificationManager;

    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
        mainInteractor = new MainInteractor(this);

        notificationManager = AppNotificationManager.getInstance();
        notificationManager.setListener(new NotificationContract.onNotificationListener() {
            @Override
            public void onStatusChanged() {
                checkNotificationStatus();
            }
        });
    }

    @Override
    public void userIdentification() {
        mainInteractor.performFirebaseUserIdentification();
    }

    @Override
    public void setUpNotificationChannels(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.setUpNotificationChannels(activity);
        }
    }

    @Override
    public void checkNotificationStatus() {
        if (notificationManager.getNotificationStatus()) {
            mainView.notificationOn();
        } else {
            mainView.notificationOff();
        }
    }

    @Override
    public void delete() {
        mainInteractor.performFirebaseDataDeletion(dreamId);
    }

    @Override
    public void logout() {
        mainInteractor.performFirebaseLogOut();
    }

    @Override
    public void onSuccess(String message) {
        mainView.onDeleteDataSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mainView.onDeleteDataFailure(message);
    }

    @Override
    public void onGetDataSuccess(Dreams dream) {
        mainView.showDreamDetails(dream);
    }

    @Override
    public void onUserIdentificationSuccess() {
        mainView.userIdentified();
    }

    @Override
    public void onUserIdentificationFailure() {
        mainView.userNotIdentified();
    }

    @Override
    public void onItemClicked(String docId) {
        mainInteractor.performFirebaseGettingData(docId);
    }

    @Override
    public void onItemDeleteClicked(String docId) {
        dreamId = docId;
        mainView.showDialog("Точно удалить?");
    }

    @Override
    public void onLoggedOut() {
        mainView.userLoggedOut();
    }
}
