package com.limox.jesus.monksresurrection.Singleton;

import com.limox.jesus.monksresurrection.Model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesus on 11/11/16.
 */

public class Posts_Singleton {
    private static Posts_Singleton mPosts_Singleton;
    private ArrayList<Post> mPosts;
    private ArrayList<Post> mPostsPublished;
    private ArrayList<Post> mPostsNotPublished;
    private ArrayList<Post> mPostsFixed;

    public static Posts_Singleton get() {
        if (mPosts_Singleton == null) {
            mPosts_Singleton = new Posts_Singleton();
        }
        return mPosts_Singleton;
    }

    private Posts_Singleton() {
        mPosts = new ArrayList<Post>();
        mPostsPublished = new ArrayList<Post>();
        mPostsNotPublished = new ArrayList<Post>();
        mPostsFixed = new ArrayList<Post>();


        createExamplePosts();
        sortLists();
    }

    private void createExamplePosts() {
        mPosts.add(new Post("Error1", 0, "Se ha rotoblablalbalablablablalbablablalba", "prueba,error,fallo", 0));
        mPosts.add(new Post("Error2", 0, "Se ha rotoblablalbalablablablalbablablalba", "prueba,error,fallo", 1));
        mPosts.add(new Post("Error3", 1, "Se ha rotoblablalbalablablablalbablablalba", "prueba,pj,fallo", 2));
        mPosts.add(new Post("Error4", 2, "Se ha rotoblablalbalablablablalbablablalba", "prueba,error,noche", 3));
        mPosts.add(new Post("Error5", 0, "Se ha castillo jajajalalba", "prueba,castillo,fallo", 4));
        mPosts.add(new Post("Error6", 1, "Se ha rotoblablalbalablablablalbablablalba", "pruebas,errores,fallo", 5));
        mPosts.add(new Post(6, "Error7", 1, "Se me ha cerrado a tope", true, false, "salto,apagado,pff"));
        mPosts.add(new Post(7, "Error8", 1, "No me puedo pasar la parte x", true, true, "dragon,noche,fuego"));
        mPosts.add(new Post(8, "Error9", 2, "Bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla "
                , true, false, "salto,apagado,pff"));
        mPosts.add(new Post(9, "Error10", 0, "Se me ha cerrado a tope", true, true, "salto,apagado,pff"));
        mPosts.add(new Post(10, "Error11", 0, "Se me ha cerrado a tope", true, false, "salto,apagado,pff"));
        mPosts.add(new Post(11, "Error12", 1, "Se me ha cerrado a tope", true, true, "salto,apagado,pff"));
    }

    private boolean addPost(Post post) {
        boolean allFine = false;
        if (!mPosts.contains(post)) {
            mPosts.add(post);
            mPostsNotPublished.add(post);
            allFine = true;
            sortLists();
        }
        return allFine;
    }

    public boolean toPublicPost(int idPost) {
        boolean allFine = false;
        Post post = new Post(idPost);
        if (mPosts.contains(post)) {
            if (mPostsNotPublished.contains(post)) {
                mPostsNotPublished.remove(mPostsNotPublished.indexOf(post));
                mPostsPublished.add(post);
                allFine = true;
                sortLists();
            }
        }
        return allFine;
    }

    public boolean toFixPost(int idFixedPost) {
        boolean allFine = false;
        Post fixedPost = getPost(idFixedPost);
        if (mPosts.contains(fixedPost)) {
            if (mPostsPublished.contains(fixedPost)) {
                // Put the post like is fixed
                fixedPost.setFixed(true);
                // Add the post at the list of all
                mPosts.add(fixedPost);
                allFine = true;
                // Sort the list to send all posts to his own list
                sortLists();

            }
        }
        return allFine;
    }

    public ArrayList<Post> getPosts() {
        return mPosts;
    }

    public ArrayList<Post> getPostsPublished() {
        return mPostsPublished;
    }

    public ArrayList<Post> getPostsNotPublished() {
        return mPostsNotPublished;
    }

    public ArrayList<Post> getPostsFixed() {
        return mPostsFixed;
    }

    /**
     * Sort all the lists of the class
     */
    private void sortLists() {
        clearLists();
        for (Post post : mPosts) {
            if (post.isFixed()) {
                mPostsFixed.add(post);
            } else if (post.isPublicate() && post.isFixed() == false) {
                mPostsPublished.add(post);
            } else {
                mPostsNotPublished.add(post);
            }

        }
    }

    public boolean deletePost(int idPost) {
        boolean allFine = false;
        Post post = new Post(idPost);
        if (mPosts.contains(post)) {
            mPosts.remove(post);
            allFine = true;
            sortLists();
        }
        return allFine;
    }

    /**
     * get a post by his id
     *
     * @param idPost id of the post you want to get
     * @return a post who coincide with the id intrude
     */
    public Post getPost(int idPost) {
        return mPosts.get(mPosts.indexOf(new Post(idPost)));
    }

    private void clearLists() {
        mPostsFixed.clear();
        mPostsNotPublished.clear();
        mPostsPublished.clear();
    }

    public void createPost(String title, String description, String tags){
        mPosts.add(new Post(title,Users_Singleton.get().getCurrentUser().getIdUser(),description,tags,mPosts.size()));
    }
    public void createPostPublished(String title, String description, String tags){
        mPosts.add(new Post(mPosts.size(),title,Users_Singleton.get().getCurrentUser().getIdUser(),description,true,false,tags));
        sortLists();
    }
}
