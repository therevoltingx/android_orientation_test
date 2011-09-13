package com.solrpg.orientation_test;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DefaultActivity extends Activity {
    private static final String TAG = DefaultActivity.class.getName();
    private static DefaultActivity mStaticSelf = null;
    private static final int DIALOG_DOWNLOADING_CONTENT = 1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "onCreate() called");
        mStaticSelf = this;
        
        setContentView(R.layout.main);
        
        Button runButton = (Button) this.findViewById(R.id.main_layout_run_button);
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  doInBackground();     
            }
        });
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
        //mStaticSelf = null;
    }
    
    @Override
    public Dialog onCreateDialog(int id) {
         if (id == DIALOG_DOWNLOADING_CONTENT) {
             ProgressDialog dlg = new ProgressDialog(this);
             dlg.setCancelable(true);
             dlg.setTitle("Please wait");
             dlg.setMessage("ROTATE ME");
             return dlg;
         }
         return null;
    }
    
    public void doInBackground() {
        showDialog(DIALOG_DOWNLOADING_CONTENT);
        Thread bgThread = new Thread() {
            @Override
            public void run() {
                 try {
                     Thread.sleep(10000);
                     mStaticSelf.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mStaticSelf.removeDialog(DIALOG_DOWNLOADING_CONTENT);
                        }
                     });
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
            }
        };
        bgThread.start();
    }
}