package com.example.android_doctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class prescription_new_report extends AppCompatActivity {

    Button p_date_bt, p_camera, p_save;
    EditText p_title;
    TextView p_date;
    ImageView imageView;
    String currentPhotoPath;

    DBhelper dBhelper;
    SQLiteDatabase sqLiteDatabase;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_new_report);

        p_title = findViewById(R.id.pres_title);
        p_date = findViewById(R.id.pres_date);
        p_date_bt = findViewById(R.id.pres_date_bt);
        imageView = findViewById(R.id.pres_image);
        p_camera = findViewById(R.id.pres_camera);
        p_save = findViewById(R.id.pres_save_bt);

        try{
            dBhelper = new DBhelper(this,"ImageDB",null,1);

            sqLiteDatabase = dBhelper.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE ImageTable(Title TEXT,Date TEXT,Image TEXT)");

        }catch (Exception e){
            e.printStackTrace();
        }

        p_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringfilepath = currentPhotoPath;

                //Bitmap bitmap = BitmapFactory.decodeFile(stringfilepath);
                //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                //bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);

                //byte[] byteImage = byteArrayOutputStream.toByteArray();

                ContentValues contentValues = new ContentValues();
                contentValues.put("Title", p_title.getText().toString());
                contentValues.put("Date", p_date.getText().toString());
                contentValues.put("Image", stringfilepath);

                sqLiteDatabase.insert("ImageTable", null, contentValues);

                Toast.makeText(getApplicationContext(), "record scccesfully added", Toast.LENGTH_SHORT).show();
                Intent back = new Intent(getApplicationContext(), prescription.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
                prescription_new_report.this.finish();
                

            }
        });

        p_date_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat(" d MMM yyyy HH:mm:ss ");
                String time =  format.format(calendar.getTime());

                p_date.setText(time);
            }
        });

        p_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { askCameraPermission(); }
        });

    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }

        else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                imageView.setBackgroundResource(android.R.color.transparent);
                imageView.setImageURI(Uri.fromFile(f));

            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Camare Permission is Required to Use Camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}