package social.webifyme.varun.socialapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import social.webifyme.varun.socialapp.R;


/**
 * Created by ketanmalhotra on 09/11/16.
 */

public class ImageHandler {


    public static String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final int maxSize = 600;
        int outWidth;
        int outHeight;
        int inWidth = bmp.getWidth();
        int inHeight = bmp.getHeight();
        if(inWidth > inHeight){
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }
        Bitmap rbmp = Bitmap.createScaledBitmap(bmp, outWidth, outHeight, true);
        rbmp.compress(Bitmap.CompressFormat.PNG, 80, baos);

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int angle) {
        Bitmap bitmap_rotated = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap_rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap_rotated;
    }
}
