package com.walkerrandolphsmith.ps;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    Button btn, killBtn;
    public TextView txtResult;
    public EditText editTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResult = (TextView) findViewById(R.id.result);
        btn = (Button) findViewById(R.id.btn);
        editTxt = (EditText)findViewById(R.id.kill_input);
        killBtn = (Button)findViewById(R.id.kill_btn);

        btn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                CommandExecutor commandExecutor = new CommandExecutor(MainActivity.this);
                commandExecutor.execute("ps");
            }
        });

        killBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid = editTxt.getText().toString();
                CommandExecutor commandExecutor = new CommandExecutor(MainActivity.this);
                commandExecutor.execute("kill" + pid);
            }
        });
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
