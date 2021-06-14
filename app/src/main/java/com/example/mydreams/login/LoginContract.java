package com.example.mydreams.login;

public interface LoginContract {

    interface View {
        void onLoginSuccess(String message);
        void onLoginFailure(String message);
    }

    interface Presenter {
        void login(String email, String password);
    }

    interface Interactor {
        void performFirebaseLogin(String email, String password);
    }

    interface onLoginListener {
        void onSuccess(String message);
        void onFailure(String message);
    }
}
