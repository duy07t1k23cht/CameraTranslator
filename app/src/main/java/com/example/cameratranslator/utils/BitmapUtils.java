package com.example.cameratranslator.utils;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.callback.VoidCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class BitmapUtils {

    public interface BitmapCallback {
        void execute(Bitmap bitmap);
    }

    public static String getStringBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT).replaceAll("\\s+", "");
    }

    public static ArrayList<String> getImagePathFromStorage(Application application) {
        ArrayList<String> listImages = new ArrayList<>();
        try {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor;
            int columnIndexData;
            String absolutePathOfImage;
            String[] projection = {MediaStore.MediaColumns.DATA};

            cursor = application.getContentResolver().query(uri, projection, null, null, null);

            columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(columnIndexData);
                listImages.add(absolutePathOfImage);
            }
            cursor.close();
            listImages.sort((path1, path2) -> {
                File file1 = new File(path1);
                File file2 = new File(path2);
                return (int) (file2.lastModified() - file1.lastModified());
            });
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        return listImages;
    }

    public static Bitmap modifyOrientation(String imageAbsolutePath) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeFile(imageAbsolutePath);
        ExifInterface ei = new ExifInterface(imageAbsolutePath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static void compressImage(
            Context context,
            String filePath,
            BitmapCallback onPostCompress,
            StringCallback onError
    ) {
        File file = new File(filePath);
        new Compressor(context)
                .compressToBitmapAsFlowable(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onPostCompress::execute, throwable -> onError.execute(throwable.getMessage()))
                .isDisposed();
    }

    private static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
