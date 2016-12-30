package com.limox.jesus.monksresurrection.Fragments.DashPost;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Adapters.PostTabsAdapter;
import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Repositories.Users_Repository;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeDashPosts_Fragment extends Fragment  {

    private Toolbar mToolbar;
    private CircleImageView mCiProfilePicture;
    private TextView mTxvProfileName;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PostTabsAdapter mAdapter;
    private OnHomeDashPostFragmentListener mCallback;

  

    public interface OnHomeDashPostFragmentListener {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAdapter = new PostTabsAdapter(getChildFragmentManager(),getResources().getStringArray(R.array.dash_post_tabs));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_home_dash_posts,container,false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.hdp_tbTitle);
        mTabLayout= (TabLayout) rootView.findViewById(R.id.hdp_tabLayout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.hdp_vpContainer);
        mCiProfilePicture = (CircleImageView) rootView.findViewById(R.id.hdp_civProfilePicture);
        mTxvProfileName = (TextView) rootView.findViewById(R.id.hdp_txvProfileName);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*  mViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                DrawFromBackTransformer trans = new DrawFromBackTransformer();
                trans.transformPage(page,position);
            }
        });*/
        mViewPager.setAdapter(mAdapter);
      //  mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
       // mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setupWithViewPager(mViewPager);
        mCiProfilePicture.setImageResource(Users_Repository.get().getCurrentUser().getProfilePicture());
        mTxvProfileName.setText(Users_Repository.get().getCurrentUser().getName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mToolbar = null;
        mTabLayout = null;
        mViewPager = null;
    }

}
