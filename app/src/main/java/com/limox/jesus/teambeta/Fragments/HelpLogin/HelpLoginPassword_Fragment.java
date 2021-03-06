package com.limox.jesus.teambeta.Fragments.HelpLogin;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;

public class HelpLoginPassword_Fragment extends Fragment {

    EditText mEdtEmail;
    Button mBtnNext;
    String mEmail;
    View.OnClickListener mListenerOnClick;
    private OnHelpLoginPasswordFragmentListener mCallback;

    public interface OnHelpLoginPasswordFragmentListener{
        void startHelpLoginFinalFragment(Bundle args);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnHelpLoginPasswordFragmentListener)
            mCallback = (OnHelpLoginPasswordFragmentListener) activity;
        else {
            throw new ClassCastException(activity.toString() + " must implements OnHelpLoginPasswordFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_help_login_password,container,false);
        // Inicializate
        mEdtEmail = (EditText) rootView.findViewById(R.id.hlp_edtEmail);
        mBtnNext = (Button) rootView.findViewById(R.id.hlp_btnSend);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializateOnClick();
        mBtnNext.setOnClickListener(mListenerOnClick);
    }



    void initializateOnClick(){
        mListenerOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the email introducced
                mEmail = mEdtEmail.getText().toString();

                if (Users_Repository.get().getUserByEmail(mEmail) != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("email",mEmail);
                    mCallback.startHelpLoginFinalFragment(bundle);
                }else{
                    mEdtEmail.setError(getString(R.string.message_error_email_not_registered));
                }
            }
        };
    }
}
