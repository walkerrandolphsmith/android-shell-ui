package com.walkerrandolphsmith.ps;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor extends AsyncTask<String, String, JSONArray> {

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
    protected JSONArray doInBackground(String... params) {
        Process p;
        JSONArray processes = new JSONArray();

        try {
            p = Runtime.getRuntime().exec(params[0]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            int i = 0;
            while((line = reader.readLine()) != null){
                if(i > 0){
                    JSONObject process = new JSONObject();
                    String[] values = line.split("\\s+");

                    process.put("processId", values[1]);
                    process.put("processName",values[8]);
                    process.put("position", i);
                    processes.put(i, process);
                }
                i++;
                p.waitFor();
            }
        }catch(IOException e){

        }catch(InterruptedException e){

        }catch(JSONException e){

        }
        return processes;
    }

    @Override
    protected void onPostExecute(JSONArray processes){
        super.onPostExecute(processes);
        progressDialog.dismiss();

        for(int i = 0; i < processes.length(); i++){
            try {
                JSONObject process = processes.getJSONObject(i);
                ((MainActivity)mContext).adapter.mList.put(i, process);
                ((MainActivity)mContext).adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
