package com.limox.jesus.teambeta.Fragments.UserProfile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.limox.jesus.teambeta.Adapters.TabsAdapter.ProfileForumsTabsAdapter;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.ExternalUtils;



public class UserExternalProfile_Fragment extends Fragment implements UserManagerPresenter.View {

    private User mUser;

    private OnUserExternalProfileFragmentListener mCallback;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TextView mTxvUserName;
    private TextView mTxvEmail;
    private ProfileForumsTabsAdapter mAdapter;
    private UserManagerPresenterImpl mPresenter;
    private String mIdUser;
    private ImageView mIvProfileImage;

    public UserExternalProfile_Fragment() {
        // Required empty public constructor
    }


    public static UserExternalProfile_Fragment newInstance(Bundle iduser) {
        UserExternalProfile_Fragment fragment = new UserExternalProfile_Fragment();
        fragment.setArguments(iduser);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(AllConstants.Keys.Parcelables.USER_PARCELABLE_KEY);
            if (mUser == null) {
                mIdUser = getArguments().getString(AllConstants.Keys.SimpleBundle.ID_USER_KEY);
            } else {
                if (Users_Repository.get().getCurrentUser().getId().equals(mUser.getId())) {
                    mUser = Users_Repository.get().getCurrentUser();
                } else
                    mIdUser = mUser.getId();
            }
        }

        setHasOptionsMenu(true);
        setRetainInstance(true);
        mAdapter = new ProfileForumsTabsAdapter(getContext(), getChildFragmentManager(), getArguments());
        mPresenter = new UserManagerPresenterImpl(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Users_Repository.get().setCurrentForum(null);
        if (mUser != null) {
            fillView();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_user_external_profile, container, false);

        mIvProfileImage = (ImageView) rootView.findViewById(R.id.materialup_profile_image);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.up_tlTabs);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mViewPager = (ViewPager) rootView.findViewById(R.id.up_vpContainer);
        mTxvUserName = (TextView) rootView.findViewById(R.id.txvUserName);
        mTxvEmail = (TextView) rootView.findViewById(R.id.txvEmail);
        if (mIdUser != null)
            mPresenter.getUser(mIdUser);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // mPresenter.getUser(mIdUser);
        mViewPager.setAdapter(mAdapter);
        mToolbar.inflateMenu(R.menu.menu_user_profile);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        mCallback.startSettings();
                        break;
                }
                return false;
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mTxvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExternalUtils.sendEmail(getContext(), mTxvEmail.getText().toString(), getString(R.string.email_subject));
            }
        });

        if (mUser != null) {

            fillView();

        }
        if (Users_Repository.get().getCurrentUser() != null) {
            if (Users_Repository.get().getCurrentUser().getToken() == null) {
                String userToken = FirebaseInstanceId.getInstance().getToken();
                Users_Repository.get().setNewUserToken(userToken);
            }
        }
        //  UIUtils.loadImage(getContext(), mUser.getProfilePicture(), mIvProfileImage)
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserExternalProfileFragmentListener) {
            mCallback = (OnUserExternalProfileFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUserExternalProfileFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onUserCreated() {

    }

    @Override
    public void onUserObtained(User tryUser) {
        fillView();

    }

    @Override
    public void onError(Exception exception) {

    }

    public interface OnUserExternalProfileFragmentListener {

        void startSettings();
    }


    private void fillView() {
        //Picasso.with(getContext()).load(mUser.getProfilePicture()).into(mIvProfileImage);
        Glide.with(getContext()).load(mUser.getProfilePicture()).into(mIvProfileImage);
        mTxvUserName.setText(mUser.getName());
        mTxvEmail.setText(mUser.getEmail());
    }
}