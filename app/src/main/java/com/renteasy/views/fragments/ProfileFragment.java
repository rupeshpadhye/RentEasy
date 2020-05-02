package com.renteasy.views.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.entity.User;
import com.renteasy.presenters.UserLifeCyclePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RUPESH on 8/6/2016.
 */
public class ProfileFragment extends Fragment {

    @Inject
    UserLifeCyclePresenter userLifeCyclePresenter;
    @BindView(R.id.email_id)
    TextView email;
    private User mUser;

    @BindView(R.id.mobile_no)
    TextView mobileNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent(getActivity()).inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        if (!userLifeCyclePresenter.identifyUserIsAnonymous()) {
            rootView = inflater.inflate(R.layout.profile_details, container, false);
            ButterKnife.bind(this, rootView);
            mUser = userLifeCyclePresenter.getUser();
            email.setText(mUser.getEmail());
            if (mUser.getMobileNo() != null) {
                mobileNo.setText(mUser.getMobileNo());
            }

        } else {
            rootView = inflater.inflate(R.layout.google_login, container, false);
        }


        initializeToolbar();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @OnClick(R.id.edit_number)
    public void onEditMobile() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View mView = layoutInflaterAndroid.inflate(R.layout.edit_mobile, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.new_mobile_no);
        final TextInputLayout textInputLayout = (TextInputLayout) mView.findViewById(R.id.textInputLayout);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        if (userInputDialogEditText.getText() != null || !userInputDialogEditText.getText().toString().isEmpty()) {

                            String number = userInputDialogEditText.getText().toString();
                            mUser.setMobileNo(number);
                            userLifeCyclePresenter.updateUser(mUser);
                            mobileNo.setText(number);

                        } else {
                            textInputLayout.setError(getString(R.string.mandatory_field));
                        }


                    }
                })

                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();


    }


    private void initializeToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.profile);
    }
}
