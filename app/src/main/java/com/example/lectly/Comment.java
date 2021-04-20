package com.example.lectly;

import java.util.ArrayList;

public class Comment {

    private String mPerson;
    private String mComment;

    public Comment(String person, String comment) {
        mPerson = person;
        mComment = comment;
    }


    public String getPerson(){
        //get name of person who commented
        return mPerson;
    }


    public String getComment(){
        //get the comment - textinput
        return mComment;
    }

    private static int lastCommentId = 0;

    public static ArrayList<Comment> createCommentList(int numComment) {
        ArrayList<Comment> comments = new ArrayList<Comment>();

        for (int i = 1; i <= numComment; i++) {
            comments.add(new Comment("person " + ++lastCommentId, "comment" + ++lastCommentId));
        }

        return comments;
    }
}
