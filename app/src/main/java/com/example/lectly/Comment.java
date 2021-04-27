package com.example.lectly;

import java.util.ArrayList;

public class Comment {

    private String mComment;

    public Comment(String comment) {
        mComment = comment;
    }

    public String getComment(){
        //get the comment - textinput
        return mComment;
    }

    private static int lastCommentId = 0;

    public static ArrayList<Comment> createCommentList(int numComment) {
        ArrayList<Comment> comments = new ArrayList<Comment>();

        for (int i = 1; i <= numComment; i++) {
            comments.add(new Comment("comment" + ++lastCommentId));
        }

        return comments;
    }
}
