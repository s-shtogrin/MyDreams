package com.example.mydreams.create_dream;

import android.app.Activity;

import com.example.mydreams.model.Dreams;

public class CreateDreamPresenter implements CreateDreamContract.Presenter, CreateDreamContract.onCreateDreamListener {

    private CreateDreamContract.View createDreamView;
    private CreateDreamInteractor createDreamInteractor;

    public CreateDreamPresenter(CreateDreamContract.View createDreamView) {
        this.createDreamView = createDreamView;
        createDreamInteractor = new CreateDreamInteractor(this);
    }

    @Override
    public void setUpData(String mode) {
        switch (mode) {
            case "create":
                break;
            case "create_from_notification":
                createDreamView.setDreamFromNotification();
                break;
            case "edit":
                createDreamView.setDream();
                break;
        }
    }

    @Override
    public void save(String mode, String id, String title, int evaluation, String content, String tag, long date) {
        Dreams dream = new Dreams(title, evaluation, content, tag, date);

        if (mode.equals("edit"))
            createDreamInteractor.performFirebaseDataUpdate(id, dream);
        else
            createDreamInteractor.performFirebaseAddingData(dream);
    }

    @Override
    public void onSuccess(String message) {
        createDreamView.onSaveDreamSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        createDreamView.onSaveDreamFailure(message);
    }
}
