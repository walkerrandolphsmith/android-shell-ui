package com.walkerrandolphsmith.ps;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

import org.json.JSONArray;

public class MainActivity extends Activity {
    public ProcessListAdapter adapter;
    public JSONArray processes = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ProcessListAdapter(this);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                CommandExecutor commandExecutor = new CommandExecutor(MainActivity.this);
                commandExecutor.execute("ps");
            }
        });

        final SwipeListView mListView = (SwipeListView) findViewById(R.id.swipelist);
        mListView.setSwipeListViewListener(new BaseSwipeListViewListener(){
            @Override
            public void onOpened(int position, boolean toRight) {

            }

            @Override
            public void onClosed(int position, boolean fromRight) {

            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(final int position, int action, boolean right) {

            }

            @Override
            public void onStartClose(int position, boolean right) {

            }

            @Override
            public void onClickFrontView(int position) {

            }

            @Override
            public void onClickBackView(int position) {

            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) { }
                adapter.notifyDataSetChanged();
            }
        });
        mListView.setAdapter(adapter);

        SettingsManager settings = SettingsManager.getInstance();
        mListView.setSwipeMode(settings.getSwipeMode());
        mListView.setSwipeActionLeft(settings.getSwipeActionLeft());
        mListView.setSwipeActionRight(settings.getSwipeActionRight());
        mListView.setOffsetLeft(convertDpToPixel(settings.getSwipeOffsetLeft()));
        mListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
        mListView.setAnimationTime(settings.getSwipeAnimationTime());
        mListView.setSwipeOpenOnLongPress(settings.isSwipeOpenOnLongPress());
    }

    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


