package com.example.lectly;

import android.provider.MediaStore;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriveServiceHelper {
    private final Executor nExecutor = Executors.newSingleThreadExecutor();
    private Drive nDriveService;

    public DriveServiceHelper(Drive nDriveService){
        this.nDriveService = nDriveService;
    }

    public Task<String> createFile(String filePath){
        return Tasks.call(nExecutor,() -> {

            File fileMetaData = new File();
            fileMetaData.setName("MyFile");

            java.io.File file = new java.io.File(filePath);

            FileContent mediaContent = new FileContent("Application/pdf", file);

            File myFile = null;

            try {
                myFile = nDriveService.files().create(fileMetaData, mediaContent).execute();
            } catch (Exception e){
                e.printStackTrace();
            }

            if(myFile == null){
                throw new IOException("nULL RESULT WHEN REQUESTING FILE CREATION");
            }

            return myFile.getId();

        });
    }
}
