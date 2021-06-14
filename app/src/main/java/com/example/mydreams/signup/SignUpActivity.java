package com.example.mydreams.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mydreams.R;
import com.example.mydreams.databinding.ActivitySignUpBinding;
import com.example.mydreams.login.LoginActivity;
import com.example.mydreams.login.LoginPresenter;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, SignUpContract.View {

    ActivitySignUpBinding binding;

    private EditText signUpEmail, signUpPassword;
    private RelativeLayout signIn, signUp;
    private ProgressBar progressBar;

    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        initViews();
    }

    private void initViews() {
        signUpEmail = binding.signUpEmail;
        signUpPassword = binding.signUpPassword;

        signIn = binding.btnGoToSignIn;
        signIn.setOnClickListener(this);
        signUp = binding.btnSignUp;
        signUp.setOnClickListener(this);

        signUpPresenter = new SignUpPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up:
                checkSingUpDetails();
                break;
            case R.id.btn_go_to_sign_in:
                goToSignIn();
                break;
        }
    }

    private void goToSignIn() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void checkSingUpDetails() {
        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Оба поля важны", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 7) {
            Toast.makeText(getApplicationContext(), "Более надежный пароль - не менее 7ми символов", Toast.LENGTH_SHORT).show();
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Email введен некорректно", Toast.LENGTH_SHORT).show();
        }
        else {
            initSignUp(email, password);
        }
    }

    private void initSignUp(String email, String password) {
        signUpPresenter.signUp(email, password);
    }

    @Override
    public void onSignUpSuccess(String message) {
        goToSignIn();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}