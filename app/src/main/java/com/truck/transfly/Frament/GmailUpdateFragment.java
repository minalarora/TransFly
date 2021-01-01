package com.truck.transfly.Frament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.truck.transfly.Activty.OtpValidation;
import com.truck.transfly.R;

public class GmailUpdateFragment extends DialogFragment {

    private FragmentActivity fragmentActivity;
    private boolean isLoading=true;
    private ProgressBar progressBar;
    private EditText mobileNumber;

    public GmailUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        fragmentActivity= (FragmentActivity) context;

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_gmail_update, container, false);

        Bundle arguments = getArguments();
        String email = arguments.getString("email");

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_dialog_min);
        }

        mobileNumber = inflate.findViewById(R.id.textEmail);
        progressBar = inflate.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        isLoading=false;

        mobileNumber.setText(email);

        inflate.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(mobileNumber.getText().toString())){

                    mobileNumber.setError("Enter Email*");
                    mobileNumber.requestFocus();
                    return;

                } else if(!isValidEmail(mobileNumber.getText().toString())){

                    mobileNumber.setError("Not Valid Email*");
                    mobileNumber.requestFocus();
                    return;


                }

                sendGmailByUpdate();

            }
        });

        inflate.findViewById(R.id.clear_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isLoading) {

                    dismiss();

                } else {

                    Toast.makeText(fragmentActivity, "Please Wait!", Toast.LENGTH_SHORT).show();

                }

            }
        });


        return inflate;
    }

    private void sendGmailByUpdate() {

          isLoading=true;
          progressBar.setVisibility(View.VISIBLE);
          mobileNumber.setEnabled(false);

    }
}