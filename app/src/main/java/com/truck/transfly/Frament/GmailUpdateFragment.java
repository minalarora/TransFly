package com.truck.transfly.Frament;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.truck.transfly.Model.RequestEmail;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GmailUpdateFragment extends DialogFragment {

    private FragmentActivity fragmentActivity;
    private boolean isLoading = true;
    private ProgressBar progressBar;
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private EditText mobileNumber;

    public GmailUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        fragmentActivity = (FragmentActivity) context;

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

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        mobileNumber = inflate.findViewById(R.id.textEmail);
        progressBar = inflate.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        isLoading = false;

        mobileNumber.setText(email);

        inflate.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(mobileNumber.getText().toString())) {

                    mobileNumber.setError("Enter Email*");
                    mobileNumber.requestFocus();
                    return;

                } else if (!isValidEmail(mobileNumber.getText().toString())) {

                    mobileNumber.setError("Not Valid Email*");
                    mobileNumber.requestFocus();
                    return;


                }

                RequestEmail requestEmail=new RequestEmail();
                requestEmail.setEmail(mobileNumber.getText().toString());

                updateEmail(PreferenceUtil.getData(fragmentActivity,"token"),requestEmail);


                Log.i("TAG", "onClick: "+PreferenceUtil.getData(fragmentActivity,"token"));

            }
        });

        inflate.findViewById(R.id.clear_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isLoading) {

                    dismiss();

                } else {

                    Toast.makeText(fragmentActivity, "Please Wait!", Toast.LENGTH_SHORT).show();

                }

            }
        });


        return inflate;
    }

    private void updateEmail(String token, RequestEmail email) {

        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);
        mobileNumber.setEnabled(false);

        api.updateEmail(token, email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {

                    dismiss();


                } else {

                    isLoading = false;
                    progressBar.setVisibility(View.GONE);
                    mobileNumber.setEnabled(true);
                    mobileNumber.setError("Unable to change Email! Try Again");
                    mobileNumber.requestFocus();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                isLoading = false;
                progressBar.setVisibility(View.GONE);
                mobileNumber.setEnabled(true);
                mobileNumber.setError("No Internet Connection! Try Again");
                mobileNumber.requestFocus();

            }
        });
    }
}