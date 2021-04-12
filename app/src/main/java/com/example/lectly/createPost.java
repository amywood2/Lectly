package com.example.lectly;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import java.util.ArrayList;

public class createPost extends AppCompatActivity {

    Button savePostButton;
    FloatingActionButton infoButton;
    TextInputEditText textInputTitle;
    TextInputEditText textInputDescription;
    TextInputEditText textInputDemonstration;
    TextInputEditText textInputStudentWork;
    String chosenModuleName;
    private ArrayList<String> allModules;
    private ArrayList<String> moduleNames;
    private JSONArray result;
    private Spinner spinner;
    Button selectFile;


    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        savePostButton = findViewById(R.id.savePostButton);
        infoButton = findViewById(R.id.infoButton);
        textInputTitle = (TextInputEditText) findViewById(R.id.titleValue);
        textInputDescription = (TextInputEditText) findViewById(R.id.descriptionValue);
        textInputDemonstration = (TextInputEditText) findViewById(R.id.demonstrationValue);
        textInputStudentWork = (TextInputEditText) findViewById(R.id.studentWorkValue);
        allModules = new ArrayList<String>();
        moduleNames = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.moduleSpinner);
        selectFile = findViewById(R.id.selectFile);

        //Requesting storage permission
        requestStoragePermission();

        getModules();
        //getModuleId();

        infoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(createPost.this);
                alertDialog.setTitle("Post Info");
                alertDialog.setMessage("This app makes use of the constructivist learning theory. This allows students " +
                        "to learn from a demonstration (presented by a lecturer). Students can then use the demonstration to further " +
                        "there knowledge independently and complete any work set for them" + "\n" + "\n" +
                        "It is important to clearly explain the material through the use of several formats of resources such as notes, recordings, activities. This means that all types of learnings" +
                        "are catered for and student will allow student access to as many resources as possible" + "\n" + "\n" +
                        "Please use these extra resources to aid making posts: " + "\n" +
                        "");
                //add extra resources

                alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        savePostButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View view) {
                String title, description, demonstration, studentWork;

                title = String.valueOf(textInputTitle.getText());
                description = String.valueOf(textInputDescription.getText());
                demonstration = String.valueOf(textInputDemonstration.getText());
                studentWork = String.valueOf(textInputStudentWork.getText());

                //getting name for the image
                String name = String.valueOf(textInputDemonstration.getText());

                //getting the actual path of the image
             //   String path = FilePath.getPath(createPost.this, filePath);

                if (!title.equals("") && !description.equals("") && !demonstration.equals("") && !studentWork.equals("") ) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "title";
                            field[1] = "description";
                            field[2] = "demonstration";
                            field[3] = "studentWork";
                            //field[4] = "path";
                            field[5] = "name";


                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = title;
                            data[1] = description;
                            data[2] = demonstration;
                            data[3] = studentWork;
                            //data[4] = path;
                            data[5] = name;


                            PutData putData = new PutData("http://192.168.1.87:8888/Lectly/savePost.php", "POST", field, data);
                            if (putData.startPut()) {
                                //uploadMultipart();
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Your post has been successfully posted")) {
                                        Toast.makeText(createPost.this, "Your post has been successfully posted", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), lecturerMain.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        private void getModules() {
            StringRequest stringRequest = new StringRequest("http://192.168.1.87:8888/Lectly/getModules.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject allModules = null;
                            try {
                                allModules = new JSONObject(response);
                                result = allModules.getJSONArray(ModuleDetails.JSON_ARRAY);
                                getModuleName(result);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

        private void getModuleName(JSONArray allModules) {
            for (int i = 0; i < allModules.length(); i++) {
                try {
                    JSONObject json = allModules.getJSONObject(i);
                    moduleNames.add(json.getString(ModuleDetails.MODULENAME));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            spinner.setAdapter(new ArrayAdapter<String>(createPost.this, android.R.layout.simple_spinner_dropdown_item, moduleNames));

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    Object item = parent.getItemAtPosition(pos);
                    chosenModuleName = item.toString();
                    //textViewTitle.setText(chosenModuleName);

                }
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getApplicationContext(), "Please select the module you wish to post in", Toast.LENGTH_SHORT).show();

                }
            });
        }

  /*  private void getModuleId() {
        for (int i = 0; i < allModules.size(); i++) {
            if (allModules.get(i) == chosenModuleName) {
                int module_id = i;
                textViewTitle.setText("The module id is    " + module_id);

            }
        }
    }*/

   /* @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void uploadMultipart() {
        //getting name for the image
        String name = textInputDemonstration.getText().toString().trim();

        //getting the actual path of the image
        String path = FilePath.getPath(this, filePath);

        if (path == null) {

            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, "http://192.168.1.87:8888/Lectly/savePost.php")
                        .addFileToUpload(path, "pdf") //Adding file
                        .addParameter("name", name) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }


}


