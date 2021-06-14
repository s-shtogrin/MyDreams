package com.example.mydreams.forgot_password;

import android.app.Activity;

public interface ForgotPasswordContract {

    interface View {
        void onPasswordRecoverSuccess(String message);
        void onPasswordRecoverFailure(String message);
    }

    interface Presenter {
        void recover(String email);
    }

    interface Interactor {
        void performFirebasePasswordRecover(String email);
    }

    interface onPasswordRecoverListener {
        void onSuccess(String message);
        void onFailure(String message);
    }
}
