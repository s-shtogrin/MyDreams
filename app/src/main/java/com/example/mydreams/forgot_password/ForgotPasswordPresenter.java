package com.example.mydreams.forgot_password;

import android.app.Activity;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter, ForgotPasswordContract.onPasswordRecoverListener {

    private ForgotPasswordContract.View forgotPasswordView;
    private ForgotPasswordInteractor forgotPasswordInteractor;

    public ForgotPasswordPresenter(ForgotPasswordContract.View view) {
        this.forgotPasswordView = view;
        forgotPasswordInteractor = new ForgotPasswordInteractor(this);
    }

    @Override
    public void recover(String email) {
        forgotPasswordInteractor.performFirebasePasswordRecover(email);
    }

    @Override
    public void onSuccess(String message) {
        forgotPasswordView.onPasswordRecoverSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        forgotPasswordView.onPasswordRecoverFailure(message);
    }

    //    public void detachView() {
//        view = null;
//    }
}
