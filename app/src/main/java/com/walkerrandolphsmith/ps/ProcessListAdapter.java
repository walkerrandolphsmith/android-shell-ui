package com.walkerrandolphsmith.ps;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProcessListAdapter extends ArrayAdapter<String> {
    private ViewHolder viewHolder;
    private MainActivity activity;
    public JSONArray mList;

    private class ViewHolder {
        TextView processId, processName;
        Button kill_btn;
    }

    public ProcessListAdapter(MainActivity activity) {
        super(activity, R.layout.list_item);
        this.activity = activity;
        this.mList = activity.processes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.processId = (TextView) convertView.findViewById(R.id.process_id);
            viewHolder.processName = (TextView) convertView.findViewById(R.id.process_name);
            viewHolder.kill_btn = (Button) convertView.findViewById(R.id.kill_btn);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ((SwipeListView) parent).recycle(convertView, position);
        try {
            JSONObject process = activity.processes.getJSONObject(position);
            viewHolder.processId.setText(process.getString("processId"));
            viewHolder.processName.setText(process.getString("processName"));
        } catch (JSONException e) {

        }
        viewHolder.kill_btn.setOnClickListener(new killProcessListener(position));

        return convertView;
    }

    @Override
    public int getCount() {
        return mList.length();
    }

    public class killProcessListener implements View.OnClickListener {

        public int position;

        public killProcessListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            try {
                JSONObject process = mList.getJSONObject(position);
                int pid = Integer.parseInt(process.getString("processId"));
                android.os.Process.killProcess(pid);
                android.os.Process.sendSignal(pid, android.os.Process.SIGNAL_KILL);
            } catch (JSONException e) {

            }
        }
    }
}
