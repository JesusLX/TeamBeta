package com.limox.jesus.teambeta.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.limox.jesus.teambeta.Model.Chat;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;

import java.util.ArrayList;

/**
 * Created by usuario on 12/01/17.
 */

public class NavDrawerUtils {
    private NavigationView.OnNavigationItemSelectedListener NavListener;
    private DrawerLayout mDrawerLayout;
    private OnNavDrawerListener mCallback;

    public interface OnNavDrawerListener{
        void startUserProfile(Bundle user);
        void startSelectProject();
        void startBugForum();
        void startAdminZone();
        void startSettings();
        void startHelp();

        void startChats(ArrayList<Chat> chats);
    }

    public NavDrawerUtils(Context context, DrawerLayout drawerLayout) {
        if (context instanceof OnNavDrawerListener)
            mCallback = (OnNavDrawerListener) context;
        else
            throw new ClassCastException(context.toString()+ "must implement OnNavDrawerListener");
        mDrawerLayout =drawerLayout;
        createNavListener();
    }
    public int getMenu(){
        if (Users_Repository.get().getCurrentUser().getForumsAdmin().contains(Users_Repository.get().getCurrentForum().getKey()) || Users_Repository.get().getCurrentUser().getForumsOwn().contains(Users_Repository.get().getCurrentForum().getKey())) {
            return R.menu.menu_nav_admins;
        } else
            return R.menu.menu_nav;
    }


    public NavigationView.OnNavigationItemSelectedListener getNavListener() {
        return NavListener;
    }

    private void createNavListener(){
        NavListener = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle user;
                switch (item.getItemId()){
                    case R.id.action_profile:
                        user = new Bundle();
                        user.putString(AllConstants.Keys.SimpleBundle.ID_USER_KEY, Users_Repository.get().getCurrentUser().getId());
                        mCallback.startUserProfile(user);
                        break;
                    case R.id.action_chats:
                        mCallback.startChats(Users_Repository.get().getCurrentUser().optChats(Users_Repository.get().getCurrentForum().getKey()));
                        break;
                    case R.id.action_projects:
                        mCallback.startSelectProject();
                        break;
                    case R.id.action_bugs:
                        mCallback.startBugForum();
                        break;
                    case R.id.action_admins:
                        mCallback.startAdminZone();
                        break;
                    case R.id.action_settings:
                        mCallback.startSettings();
                        break;
                    case R.id.action_help:
                        mCallback.startHelp();
                        break;

                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        };
    }
}
