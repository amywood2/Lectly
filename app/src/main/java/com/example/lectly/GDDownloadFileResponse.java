package com.example.lectly;


import com.example.lectly.GDException;

import java.io.File;

public class GDDownloadFileResponse {
    public interface OnDownloadFileCompleteListener {
        void onSuccess(File downloadedFile);

        void onError(GDException exception);
    }
}