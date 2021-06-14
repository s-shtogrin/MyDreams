package com.example.mydreams.signup;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpInteractor implements SignUpContract.Interactor {

    private SignUpContract.onSignUpListener onSignUpListener;

    public SignUpInteractor(SignUpContract.onSignUpListener onSignUpListener) {
        this.onSignUpListener = onSignUpListener;
    }

    @Override
    public void performFirebaseSignUp(String email, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendEmailVerification();
                            onSignUpListener.onSuccess("Регистрация проведена успешно");
                        }
                        else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            onSignUpListener.onFailure("Пользователь с таким email уже существует");
                        }
                        else {
                            //onSignUpListener.onFailure(task.getException().getMessage());
                            onSignUpListener.onFailure("Ошибка при регистрации");
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    onSignUpListener.onSuccess("Войдите после подтверждения адреса электронной почты");

                    FirebaseAuth.getInstance().signOut();
                }
            });
        }
        else {
            onSignUpListener.onFailure("Ошибка при запросе подтверждения адреса электронной почты");
        }
    }
}
