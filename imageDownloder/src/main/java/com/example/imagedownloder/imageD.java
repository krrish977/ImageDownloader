package com.example.imagedownloder;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class imageD {
    public static Context c;
    public static File mOriginalFile;

    public static void Downloader(Context context, Bitmap bitmap, String msg) {
        Uri uri = getImageUri(context, bitmap);
        CaptureImage(uri, context, msg);
    }

    public static File CaptureImage(Uri uri, Context context, String msg) {

        c = context;
        Bitmap bitmap = null;
        File f = null;
        try {
            // create bitmap screen capture
            try {
                bitmap = MediaStore.Images.Media.getBitmap(c.getContentResolver(), uri);
            } catch (Exception e) {
                //handle exception
            }
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            //----------------dsestination path--------
            String ParentPath = mOriginalFile.getParentFile().getPath();
//            Log.e("Parent path:",ParentPath + "*");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Uri destination = Uri.fromFile(new File(ParentPath + "/" + timeStamp + ".jpg"));
            String NewImagePath = destination.getPath();
//            Log.e("New path:",NewImagePath + "*");
            //-----------------------------------------
            if (NewImagePath != null) {
                f = new File(NewImagePath);
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //------------insert into media list----
                File newfilee = new File(destination.getPath());
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, destination.getPath());
                values.put(MediaStore.Images.Media.DATE_TAKEN, newfilee.lastModified());
                scanPhoto(newfilee.getPath());
                Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
                Uri uri1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri1 = FileProvider.getUriForFile(c.getApplicationContext(), c.getPackageName() + ".provider", newfilee);
                } else {
                    uri1 = Uri.fromFile(newfilee);
                }
                c.getContentResolver().notifyChange(uri1, null);


                
            }
        } catch (Exception e) {
        }
        return f;

    }

    public static MediaScannerConnection msConn;
    public static void scanPhoto(final String imageFileName) {
        msConn = new MediaScannerConnection(c, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
                msConn.scanFile(imageFileName, null);
            }

            public void onScanCompleted(String path, Uri uri) {
                msConn.disconnect();
            }
        });
        msConn.connect();
    }

    public static Uri getImageUri(Context inContext, Bitmap bitmappp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmappp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmappp, "Title", null);
        return Uri.parse(path);
    }
}
