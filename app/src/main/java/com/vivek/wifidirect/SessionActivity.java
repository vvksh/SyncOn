package com.vivek.wifidirect;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SessionActivity extends Activity {
    FileObserver mObserver;
    String TAG = "DEBUXX";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
    }

    @Override
    protected void onPause() {

        final String pathToWatch = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/";
        Toast.makeText(this, "Session Started  watching" + pathToWatch, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "started service");
        mObserver = new FileObserver(pathToWatch) { // set up a file observer to watch this directory on sd card

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
        mObserver.startWatching(); //START OBSERVING
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_session, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        Log.d(TAG, "going to start transfer");
        startService(serviceIntent);
    }


}
