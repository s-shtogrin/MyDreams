package com.example.mydreams.dream_details;

public class DreamDetailsPresenter implements DreamDetailsContract.Presenter {

    private DreamDetailsContract.View dreamsDetailsView;

    public DreamDetailsPresenter(DreamDetailsContract.View dreamsDetailsView) {
        this.dreamsDetailsView = dreamsDetailsView;
    }

    @Override
    public void setUpData() {
        dreamsDetailsView.setDream();
    }
}
