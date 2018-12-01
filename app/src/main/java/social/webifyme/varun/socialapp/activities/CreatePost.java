package social.webifyme.varun.socialapp.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.GestureImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import social.webifyme.varun.socialapp.R;
import social.webifyme.varun.socialapp.utils.ImageHandler;
import social.webifyme.varun.socialapp.utils.Utility;

import static android.provider.MediaStore.Images.Media.getBitmap;

public class CreatePost extends AppCompatActivity implements View.OnClickListener {
    private EditText questionEditText;
    private GestureImageView cropViewRight;
    private String userChosenTask;
    private String dirName = "/sopo";
    private Boolean  rightSet = false;
    private Uri imageUri;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);
        context=this;
        cropViewRight = (GestureImageView) findViewById(R.id.create_right_image);
        //cropViewRight.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.testing_image));

        cropViewRight.getController().getSettings()
                .setRotationEnabled(true)
                .setMaxZoom(10f)
                .setFitMethod(Settings.Fit.INSIDE);

        questionEditText = (EditText) findViewById(R.id.create_question_edit_text);
        ImageView rightImageButton = (ImageView) findViewById(R.id.create_right_image_button);
        Button postButton = (Button) findViewById(R.id.postButton);

        postButton.setOnClickListener(this);
        rightImageButton.setOnClickListener(this);
    }


    private void selectImage(final int action) {
        final CharSequence[] items = {"Take Photo", "Pick from Gallery",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CreatePost.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkExternalWritePermission(CreatePost.this);
                if (items[item].equals("Take Photo")) {
                    userChosenTask = "Take Photo";
                    if (result)
                        cameraIntent(action + 5);
                } else if (items[item].equals("Pick from Gallery")) {
                    userChosenTask = "Pick from Gallery";
                    if (result)
                        galleryIntent(action + 7);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void cameraIntent(int action) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  dirName + "/temp.png");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, action);
    }


    private void galleryIntent(int action) {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, action);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Log.i("#Activity result", String.valueOf(requestCode));
            switch (requestCode) {
                case 6: //1+5 left and take photo
                case 7: //2+5 right and take photo
                    onCaptureImageResult(data, requestCode);
                    break;
                case 8: //1+7 left and library
                case 9: //2+7 right and library
                    onSelectFromGalleryResult(data, requestCode);
                    break;

            }
        }
    }


    private void onSelectFromGalleryResult(Intent data, int action) {
        Bitmap image = null;
        if (data != null) {
            try {
                image = getBitmap(getApplicationContext().getContentResolver(), data.getData());
                saveImage(image, (action == 6 || action == 8 || action == 10) ? "left" : "right");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (action) {
            case 9:
                cropViewRight.setImageBitmap(image);
                break;
        }
    }

    private void onCaptureImageResult(Intent data, int action) {
        Uri selectedImage = imageUri;
        getContentResolver().notifyChange(selectedImage, null);
        ContentResolver cr = getContentResolver();
        Bitmap bitmap = null;
        try {
            bitmap = android.provider.MediaStore.Images.Media
                    .getBitmap(cr, selectedImage);
        } catch (Exception e) {
            Log.e("Camera", e.toString());
        }
        bitmap = ImageHandler.rotateBitmap(bitmap, 90);// Rotates default photo captured to portrait mode
        saveImage(bitmap, (action == 6 || action == 8 || action == 10 ) ? "left" : "right");
        switch (action) {
            case 7:
                cropViewRight.setImageBitmap(bitmap);
                break;
        }
    }

    private void saveImage(Bitmap image, String name) {
        if (name.equals("right"))
            rightSet = name.equals("right");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        File folder = new File(Environment.getExternalStorageDirectory() + dirName);
        if (!folder.exists()) folder.mkdir();

        File destination = new File(Environment.getExternalStorageDirectory() + dirName, name + ".png");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.create_right_image_button:
                selectImage(2);
                break;
            case R.id.postButton:
                Log.i("#Button Click", "postButton clicked");

                if (questionEditText.getText().toString().equals("")) {
                    Snackbar.make(findViewById(android.R.id.content), "Please enter a question", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                if (!rightSet) {
                    Snackbar.make(findViewById(android.R.id.content), "Please choose images", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content), "Created", Snackbar.LENGTH_LONG)
                            .show();
                }
                Bitmap croppedBitmapRight = cropViewRight.crop();
                //saveImage(croppedBitmap ,"RightFinal");


                break;
        }

    }
    }
