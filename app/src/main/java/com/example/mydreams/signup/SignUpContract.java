package com.example.mydreams.signup;

public interface SignUpContract {

    interface View {
        void onSignUpSuccess(String message);
        void onSignUpFailure(String message);
    }

    interface Presenter {
        void signUp(String email, String password);
    }

    interface Interactor {
        void performFirebaseSignUp(String email, String password);
    }

    interface onSignUpListener {
        void onSuccess(String message);
        void onFailure(String message);
    }
}
