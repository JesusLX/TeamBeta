package com.limox.jesus.monksresurrection.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.limox.jesus.monksresurrection.Fragments.GenericPosts.DashPost_Fragment;
import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Singleton.Posts_Singleton;
import com.limox.jesus.monksresurrection.Singleton.Users_Singleton;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

/**
 * Created by jesus on 24/12/16.
 */
public class ProfilePostTabsAdapter extends FragmentStatePagerAdapter {
    String[] mTabNames;
    FragmentManager mSuppFM;
    int mIdUser;
    public ProfilePostTabsAdapter(Context context, FragmentManager supportFragmentManager, int idUser) {
        super(supportFragmentManager);
        mTabNames = context.getResources().getStringArray(R.array.uprofile_post_tabs);
        mIdUser = idUser;
    }

    @Override
    public int getCount() {
        return mTabNames.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (position){
            case 0:
                args.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Singleton.get().getPostsByUser(mIdUser, Post.ALL));
                fragment = DashPost_Fragment.newInstance(args);
                break;
            case 1:
                args.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Singleton.get().getPostsByUser(mIdUser, Post.PUBLISHED));
                fragment = DashPost_Fragment.newInstance(args);
                break;
            case 2:
                args.putParcelableArrayList(AllConstants.ARRAYLIST_POST_PARCELABLE_KEY, Posts_Singleton.get().getPostsByUser(Users_Singleton.get().getCurrentUser().getIdUser(), Post.PUBLISHED));
                fragment = DashPost_Fragment.newInstance(args);
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return mTabNames[position];
    }
}