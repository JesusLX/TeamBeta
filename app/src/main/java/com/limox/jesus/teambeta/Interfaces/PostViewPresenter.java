package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;
import android.support.annotation.IntDef;

import com.limox.jesus.teambeta.Model.Post;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jesus on 30/12/16.
 */

public interface PostViewPresenter {
    void deletePost(String idPost);
    void changePostOfList(Post post, @Post.STATE int newState);

    void likePost(String idPost, Post post);
    interface View {
        Context getContext();

        @IntDef({TO_NOT_PUBLISHED, TO_PUBLISHED, TO_FIXED, DELETE})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ACTION {
        }

        public static final int TO_NOT_PUBLISHED = 0;
        public static final int TO_PUBLISHED = 1;
        public static final int TO_FIXED = 2;
        public static final int DELETE = 3;

        void onDestroy();
    }


}
