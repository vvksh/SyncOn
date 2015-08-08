//package com.vivek.wifidirect;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Environment;
//import android.os.FileObserver;
//import android.util.Log;
//import android.widget.Toast;
//
///**
// * Created by vivek on 8/7/15.
// */
//public class MyFileObserver extends FileObserver {
//    String TAG = "DEBUXX";
//
//
//    @Override
//    public void onEvent(int event, String path) {
//        final String pathToWatch = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/";
////        Toast.makeText(SessionActivit, "Session Started  watching" + pathToWatch, Toast.LENGTH_SHORT).show();
//        Log.d(TAG, "started service");
//
//        switch(event) {
//            case FileObserver.CREATE:
//                Log.d(TAG, "CREATE:" + pathToWatch + file);
////                        Toast.makeText(PhotoService.this, "CREATED", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
////                        startActivityForResult(intent, CHOOSE_FILE_RESULT_CODE);
//
//                startfileTransfer(pathToWatch+ file);
//                break;
//            case FileObserver.DELETE:
//                Log.d(TAG, "DELETE:" + pathToWatch);
//                break;
//        }
//
//        //}
//    }
//
//    public void startfileTransfer(String filepath ){
//
//        Uri uri = Uri.parse(filepath);
//        //        Log.d("URI", uri);
//
//        Log.d(TAG, "Intent----------- " + uri);
//        Intent serviceIntent = new Intent(this, FileTransferService.class);
//        serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
//        serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString());
//        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
//                DeviceDetailFragment.info.groupOwnerAddress.getHostAddress());
//        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 8988);
//        startService(serviceIntent);
//    }
//
//
//    }
//
