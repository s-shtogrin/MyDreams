package com.example.mydreams.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mydreams.R;

import java.util.Objects;

public class AppDialogFragment extends DialogFragment {

    public interface DialogListener {
        public void onDialogPositiveClick();
    }

    private static final String ARG_TEXT = "text";

    private String text;

    public static AppDialogFragment newInstance(String text) {
        AppDialogFragment fragment = new AppDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    DialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            text = getArguments().getString(ARG_TEXT);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = View.inflate(getActivity(), R.layout.fragment_dialog, null);

        TextView dialogText = view.findViewById(R.id.dialog_text);
        dialogText.setText(text);

        builder.setView(view)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AppDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        Button positive = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
        positive.setTextColor(getResources().getColor(R.color.grey, null));
    }

    @Override
    public void onResume() {
        super.onResume();

        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();

        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height =  ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;

        getDialog().getWindow().setAttributes(params);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_design);
    }
}
