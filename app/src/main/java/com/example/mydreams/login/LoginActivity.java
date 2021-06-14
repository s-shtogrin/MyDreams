package com.example.mydreams.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydreams.R;
import com.example.mydreams.databinding.ActivityLoginBinding;
import com.example.mydreams.forgot_password.ForgotPasswordActivity;
import com.example.mydreams.main.MainActivity;
import com.example.mydreams.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContract.View{

    ActivityLoginBinding binding;

    private EditText loginEmail, loginPassword;
    private RelativeLayout signIn, signUp;
    private TextView forgotPassword;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        loginEmail = binding.loginEmail;
        loginPassword = binding.loginPassword;

        signIn = binding.btnSignIn;
        signIn.setOnClickListener(this);
        signUp = binding.btnGoToSignUp;
        signUp.setOnClickListener(this);
        forgotPassword = binding.goToForgotPassword;
        forgotPassword.setOnClickListener(this);

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                checkLoginDetails();
                break;
            case R.id.btn_go_to_sign_up:
                goToSignUp();
                break;
            case R.id.go_to_forgot_password:
                goToForgotPassword();
                break;
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void goToSignUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    private void goToForgotPassword() {
        Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void checkLoginDetails() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Оба поля важны", Toast.LENGTH_SHORT).show();
        }
        else {
            initLogin(email, password);
        }
    }

    private void initLogin(String email, String password) {
        loginPresenter.login(email, password);
    }

    @Override
    public void onLoginSuccess(String message) {
        goToMainActivity();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}