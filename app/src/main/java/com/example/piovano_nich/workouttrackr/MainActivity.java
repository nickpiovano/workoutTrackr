package com.example.piovano_nich.workouttrackr;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;

import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Create homepage buttons
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;

    //Invite a Friend Objects
    Button btnUpdate;
    TextView milePrValue;
    TextView benchPressPrValue;
    EditText milePrInput;
    EditText benchPressPrInput;

    //Intents
    Intent workoutIntent;
    Intent nutritionIntent;
    Intent mapIntent;
    Intent animateIntent;

    //Profile Items
    Button btnProfileUpdate;
    TextView name;
    TextView age;
    TextView height;
    TextView weight;
    TextView sex;
    TextView email;
    EditText nameInput;
    EditText ageInput;
    EditText heightInput;
    EditText weightInput;
    EditText sexInput;
    EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button One: My Profile
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        //Button Two: Workouts
        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        //Button Three: Run
        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        //Button Four: Nutrition
        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(this);

        //Button Five: Stats Tracker
        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

        //Button Six: Invite a Friend
        Button btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(this);

        //Button Seven: Animation & Notification
        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
    }

    //Set on click behevior of home buttons
    public void onClick(View v) {

        switch (v.getId()) {

            //My Profile
            case R.id.btn1:
                //Do something
                setContentView(R.layout.my_profile);
                break;
            //Workouts
            case R.id.btn2:
                //Do something
                //setContentView(R.layout.workouts);
                workoutIntent = new Intent(this, Workouts.class);
                startActivity(workoutIntent);
                break;
            //Run
            case R.id.btn3:
                //Do something
                //setContentView(R.layout.run);
                mapIntent = new Intent(this, Map.class);
                startActivity(mapIntent);
                break;
            //Nutrition
            case R.id.btn4:
                //Do something
                //setContentView(R.layout.nutrition);
                nutritionIntent = new Intent(this, Nutrition.class);
                startActivity(nutritionIntent);
                break;
            //Stats Tracker
            case R.id.btn5:
                setContentView(R.layout.stats_tracker);
                break;
            //Invite a Friend
            case R.id.btn6:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Invite a Friend"));
                break;
            //Animation & Notification
            case R.id.btn7:
                //Animation
                btn7 = findViewById(R.id.btn7);
                Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
                btn7.startAnimation(rotate);

                //Show Notification
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

                    // Configure the notification channel.
                    notificationChannel.setDescription("Channel description");
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);
                }

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

                notificationBuilder.setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setTicker("workoutTrackr")
                        //     .setPriority(Notification.PRIORITY_MAX)
                        .setContentTitle("Motivation")
                        .setContentText("You asked for it. Hit the gym!")
                        .setContentInfo("Info");

                notificationManager.notify(/*notification id*/1, notificationBuilder.build());
            default:
                break;
        }
    }

    //Use the back button to return to the homepage
    //Have to find button Id's and set OnClickListener again
    @Override
    public void onBackPressed()
    {
        setContentView(R.layout.activity_main);

        //Button One: My Profile
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        //Button Two: Workouts
        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        //Button Three: Run
        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        //Button Four: Nutrition
        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(this);

        //Button Five: Stats Tracker
        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

        //Button Six: Invite a Friend
        Button btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(this);

        //Button Seven: Notification
        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
    }

    //Update Stats Tracker
    public void updateStatsTrackerMethod (View v) {
        TextView milePrValue = findViewById(R.id.milePrValue);
        TextView benchPressPrValue = findViewById(R.id.benchPressPrValue);
        EditText milePrInput = findViewById(R.id.milePrInput);
        EditText benchPressPrInput = findViewById(R.id.benchPressPrInput);

        //Update values based on text inputted
        milePrValue.setText(milePrInput.getText().toString() + " min");
        benchPressPrValue.setText(benchPressPrInput.getText().toString() + " lbs");
    }

    //Update My Profile
    public void updateProfileMethod (View v) {
        TextView name = findViewById(R.id.name);
        TextView age = findViewById(R.id.age);
        TextView height = findViewById(R.id.height);
        TextView weight = findViewById(R.id.weight);
        TextView sex = findViewById(R.id.sex);
        TextView email = findViewById(R.id.email);
        EditText nameInput = findViewById(R.id.nameInput);
        EditText ageInput = findViewById(R.id.ageInput);
        EditText heightInput = findViewById(R.id.heightInput);
        EditText weightInput = findViewById(R.id.weightInput);
        EditText sexInput = findViewById(R.id.sexInput);
        EditText emailInput = findViewById(R.id.emailInput);

        //Update values based on text inputted
        name.setText("Name: " + nameInput.getText().toString());
        age.setText("Age: " + ageInput.getText().toString());
        height.setText("Height (in): " + heightInput.getText().toString());
        weight.setText("Weight (lbs): " + weightInput.getText().toString());
        sex.setText("Sex: : " + sexInput.getText().toString());
        email.setText("Email: " + emailInput.getText().toString());
    }
}