package com.example.mydreams.login;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.onLoginListener {

    private LoginContract.View loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginContract.View loginView){
        this.loginView = loginView;
        loginInteractor = new LoginInteractor(this);
    }

    @Override
    public void login(String email, String password) {
        loginInteractor.performFirebaseLogin(email, password);
    }

    @Override
    public void onSuccess(String message) {
        loginView.onLoginSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        loginView.onLoginFailure(message);
    }

//    public void detachView() {
//        view = null;
//    }
}
