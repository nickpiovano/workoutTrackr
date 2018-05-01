package com.example.piovano_nich.workouttrackr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.piovano_nich.workouttrackr.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;


public class Workouts extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView WBItems;
    private EditText insert;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private static final String FILE_NAME = "list.txt";
    private int lastSelected = 0;
    private TextToSpeech tts;
    private static final String tag = "Widgets";
    private OutputStreamWriter outputStream;
    private String itemclicked;
    private WebView webView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.workouts);

        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec;

        // Initialize a TabSpec for tab1 and add it to the TabHost
        spec = tabs.newTabSpec("tag1");    //create new tab specification
        spec.setContent(R.id.DailyWorkout);    //add tab view content
        spec.setIndicator("Daily Workout");    //put text on tab
        tabs.addTab(spec);             //put tab in TabHost container

        insert = (EditText) findViewById(R.id.insert);
        WBItems = (ListView) findViewById(R.id.WBItems);
        WBItems.setOnItemClickListener(this);
        arrayList = new ArrayList<String>();

        arrayAdapter = new ArrayAdapter<String>(Workouts.this, android.R.layout.simple_expandable_list_item_1, arrayList);
        WBItems.setAdapter(arrayAdapter);
        WBItems.setItemsCanFocus(true);

        getSupportActionBar().setTitle("Workouts"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

//Text to Speech
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });

        //-------------------------------------------------------------------------------------

// Initialize a TabSpec for tab2 and add it to the TabHost
        spec = tabs.newTabSpec("tag2");        //create new tab specification
        spec.setContent(R.id.Tutorials);            //add view tab content
        spec.setIndicator("Web Tutorials");
        tabs.addTab(spec);
        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://gmb.io/bodyweight-basics/");
        //set listeners for web tab

//Text to Speech
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });


        //hide title and icon in action bar
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setDisplayUseLogoEnabled(false);

        //open output stream
        try {
            InputStream in = openFileInput(FILE_NAME);

            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isr);
            String str = null;

            int count = 0;
            while ((str = reader.readLine()) != null) {
                count++; // count number of records read
                arrayList.add(str);
            }
            arrayAdapter.notifyDataSetChanged();
            // toast how many records read
            Toast.makeText(this,
                    count + " records read", Toast.LENGTH_LONG).show();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long l) {
        lastSelected = position;
        itemclicked = insert.getText().toString();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                String result = insert.getText().toString();
                int number = arrayList.size() + 1;
                arrayList.add(number + "." + result);
                arrayAdapter.notifyDataSetChanged();
                tts.speak(result + " added", TextToSpeech.QUEUE_FLUSH, null);
                return true;

            case R.id.delete:
                String delete = insert.getText().toString();
                arrayList.set(lastSelected, delete);
                arrayList.remove(delete);
                for (int i = 0; i < arrayList.size(); i++) {
                    String s = arrayList.get(i);
                    int index = s.indexOf(".");
                    String str = s.substring(index);
                    arrayList.set(i, i + 1 + str);
                }
                arrayAdapter.notifyDataSetChanged();
                tts.speak(delete + " deleted", TextToSpeech.QUEUE_FLUSH, null);
                return true;
            //value of the loop control variable plus one in the beginning . will canatonozie with

            case R.id.update:
                String update = insert.getText().toString();
                String word = arrayList.get(lastSelected);
                int w = word.indexOf(".");
                String s = word.substring(0, w);
                arrayList.set(lastSelected, s + "." + update);
                arrayAdapter.notifyDataSetChanged();
                tts.speak(update + " updated", TextToSpeech.QUEUE_FLUSH, null);
                return true;
            case R.id.save:
                try {
                    outputStream = new OutputStreamWriter(openFileOutput(FILE_NAME, MODE_PRIVATE));
                    for (int i = 0; i < arrayList.size(); i++) {
                        outputStream.write(arrayList.get(i) + " \n");
                    }
                    outputStream.close();
                    Toast.makeText(this, "The file was saved", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}