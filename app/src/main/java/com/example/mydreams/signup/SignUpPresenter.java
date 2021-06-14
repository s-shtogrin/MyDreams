package com.example.mydreams.signup;

public class SignUpPresenter implements SignUpContract.Presenter, SignUpContract.onSignUpListener {

    private SignUpContract.View signUpView;
    private SignUpInteractor signUpInteractor;

    public SignUpPresenter(SignUpContract.View signUpView){
        this.signUpView = signUpView;
        signUpInteractor = new SignUpInteractor(this);
    }

    @Override
    public void signUp(String email, String password) {
        signUpInteractor.performFirebaseSignUp(email, password);
    }

    @Override
    public void onSuccess(String message) {
        signUpView.onSignUpSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        signUpView.onSignUpFailure(message);
    }

//    public void detachView() {
//        view = null;
//    }
}
