package com.example.a6ld_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView contextMenuTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        registerForContextMenu(textView1);
        registerForContextMenu(textView2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbarMenuItem1:
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        (view, hourOfDay, minute) -> {
                            int timeDiff = Math.abs((mHour * 60 + mMinute) - (hourOfDay * 60 + minute));
                            String text = "Skirtumas tarp dabartinio ir nurodyto laiko yra " + timeDiff + " minutės";
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Skirtumas minutėmis");
                            builder.setMessage(text);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            textView1.setText(text);
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.toolbarMenuItem2:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        contextMenuTextView = findViewById(v.getId());
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contextMenuItem1:
                String text = "Tekste yra " + contextMenuTextView.getText().toString().length() + " simbolių";
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Simbolių skaičius");
                builder.setMessage(text);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                textView2.setText(text);
                break;
            case R.id.contextMenuItem2:
                new Thread(() -> {
                    for (char c : contextMenuTextView.getText().toString().toCharArray()) {
                        textView3.setText(Character.toString(c));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
        return super.onContextItemSelected(item);
    }
}