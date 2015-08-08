package com.vivek.wifidirect;



import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PhotoService extends Service {
    public static FileObserver observer;
    public final String TAG = "DEBUXX";

    public PhotoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        final String pathToWatch = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/";
        Toast.makeText(this, "Session Started  watching" + pathToWatch, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "started service");
        observer = new FileObserver(pathToWatch) { // set up a file observer to watch this directory on sd card

            @Override
            public void onEvent(int event, String file) {
                //if(event == FileObserver.CREATE && !file.equals(".probe")){ // check if its a "create" and not equal to .probe because thats created every time camera is launched
                switch(event) {
                    case FileObserver.CREATE:
                        Log.d(TAG, "CREATE:" + pathToWatch+ file);
//                        Toast.makeText(PhotoService.this, "CREATED", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
//                        startActivityForResult(intent, CHOOSE_FILE_RESULT_CODE);

                        startfileTransfer(pathToWatch+ file);
                        break;
                    case FileObserver.DELETE:
                        Log.d(TAG, "DELETE:" + pathToWatch);
                        break;
                }

                //}
            }
        };
        observer.startWatching(); //START OBSERVING
        return super.onStartCommand(intent, flags, startId);
    }
    public void startfileTransfer(String filepath ){

        Uri uri = Uri.parse(filepath);
    //        Log.d("URI", uri);

        Log.d(TAG, "Intent----------- " + uri);
        Intent serviceIntent = new Intent(this, FileTransferService.class);
        serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
        serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString());
        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                DeviceDetailFragment.info.groupOwnerAddress.getHostAddress());
        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 8988);
        startService(serviceIntent);
    }

}
