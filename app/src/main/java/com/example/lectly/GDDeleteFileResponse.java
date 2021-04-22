package com.example.lectly;

public class GDDeleteFileResponse {
    public interface OnDeleteFileListener {
        void onSucess();
        void onError(Exception e);
    }
}
