package com.walkerrandolphsmith.ps;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by walkersmith on 7/19/15.
 */
public class CommandExecutor extends AsyncTask<String, String, String> {

    Context mContext = null;
    ProgressDialog progressDialog;

    public CommandExecutor(Context _context){
        mContext = _context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = ProgressDialog.show(mContext, "Loading", "...");
    }

    @Override
    protected String doInBackground(String... params) {
        Process p;
        StringBuffer output = new StringBuffer();

        try {
            p = Runtime.getRuntime().exec(params[0]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = reader.readLine()) != null){
                output.append(line + "\n");
                p.waitFor();
            }
        }catch(IOException e){

        }catch(InterruptedException e){

        }
        return output.toString();
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        progressDialog.dismiss();
        Log.d("Command result: ", result);
        ((MainActivity)mContext).txtResult.setText(result);
    }
}
