package com.example.mydreams.forgot_password;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordInteractor implements ForgotPasswordContract.Interactor {

    private ForgotPasswordContract.onPasswordRecoverListener onPasswordRecoverListener;

    public ForgotPasswordInteractor(ForgotPasswordContract.onPasswordRecoverListener onPasswordRecoverListener) {
        this.onPasswordRecoverListener = onPasswordRecoverListener;
    }

    @Override
    public void performFirebasePasswordRecover(String email) {
        FirebaseAuth.getInstance()
            .sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        onPasswordRecoverListener.onSuccess("Письмо для восстановления пароля отправлено");
                    }
                    else {
                        onPasswordRecoverListener.onFailure("Проверьте адрес электронной почты или зарегестрируйтесь");
                    }
                }
            });
    }
}
