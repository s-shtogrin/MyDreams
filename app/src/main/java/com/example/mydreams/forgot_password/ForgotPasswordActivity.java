package com.example.mydreams.forgot_password;

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
import com.example.mydreams.databinding.ActivityForgotPasswordBinding;
import com.example.mydreams.login.LoginActivity;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, ForgotPasswordContract.View {

    ActivityForgotPasswordBinding binding;

    private EditText passwordRecoveryEmail;
    private RelativeLayout signIn, recoverPassword;
    private TextView info;

    private ForgotPasswordPresenter forgotPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        initViews();
    }

    private void initViews() {
        passwordRecoveryEmail = binding.passRecoveryEmail;
        info = binding.passwordInfo;

        signIn = binding.btnBackToSignIn;
        signIn.setOnClickListener(this);
        recoverPassword = binding.btnRecoverPassword;
        recoverPassword.setOnClickListener(this);

        forgotPasswordPresenter = new ForgotPasswordPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recover_password:
                checkPasswordRecoverDetails();
                break;
            case R.id.btn_back_to_sign_in:
                goToSignIn();
                break;
        }
    }

    private void goToSignIn() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void checkPasswordRecoverDetails() {
        String email = passwordRecoveryEmail.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Введите email", Toast.LENGTH_SHORT).show();
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Email введен некорректно", Toast.LENGTH_SHORT).show();
        }
        else {
            initRecover(email);
        }
    }

    private void initRecover(String email) {
        forgotPasswordPresenter.recover(email);
    }

    @Override
    public void onPasswordRecoverSuccess(String message) {
        info.setText("Письмо для восстановления пароля\nотправлено на Ваш email\n\nПросто следуйте инструкциям");
        recoverPassword.setVisibility(View.GONE);
        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordRecoverFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}