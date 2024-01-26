package com.example.coen390_poject.Views;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coen390_poject.Controllers.SharedPreferencesHelper;
import com.example.coen390_poject.Models.Device;
import com.example.coen390_poject.Models.User;
import com.example.coen390_poject.R;
import com.example.coen390_poject.Services.NotificationForgroundService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyDashBoard extends AppCompatActivity {

    protected CardView profile;
    protected CardView devices;
    protected CardView settings;
    protected CardView data;
    protected TextView greetings;
    protected TextView date;
    protected DatabaseReference databaseReference;
    protected User user;
    String uid;

    String currentDate;
    String currentTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dash_board);

        profile=findViewById(R.id.cardView_profile);
        devices=findViewById(R.id.cardView_devices);
        settings=findViewById(R.id.cardView_seetings);
        data=findViewById(R.id.cardView_data);

        greetings=findViewById(R.id.greet_user);

        date=findViewById(R.id.current_date);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MM d, yy");

        currentDate=dateFormat.format(calendar.getTime());
        date.setText(currentDate);//set the current date.




        /*Declare a sharedPreferences helper object to retrieve the id of the user to customize the welcoming message */
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(MyDashBoard.this);
        uid = sharedPreferencesHelper.getId("UID");

        if(uid == null)
        {
            uid = getIntent().getStringExtra("UID");
        }

        currentTime=getTime();//get the current time.
        /*Define the path from where we want to read data*/
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);


        databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    /*Retrieve the attributes of the user from the database */
                        user=snapshot.getValue(User.class);

                      
                        if (currentTime.compareTo("12:00") < 0)
                            greetings.setText("Good morning, " + user.getName());

                        else {
                            greetings.setText("Good Evening, " + user.getName());
                        }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MyDashBoard.this,error.getMessage(),Toast.LENGTH_LONG);
                }
            });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfileActivity();
            }
        });

        devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterMyDevice();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSettingsActivity();
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMydevices();
            }
        });
    }

    private void goToProfileActivity()
    {
        //Toast.makeText(MyDashBoard.this,user.getName(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MyDashBoard.this, ProfileActivity.class).putExtra("User",user);
        intent.putExtra("UID",uid);
        startActivity(intent);
    }
    private void goToRegisterMyDevice()
    {
        Intent intent = new Intent(MyDashBoard.this, QrCodeScanner.class).putExtra("User",user);
        startActivity(intent);
    }

    private void goToSettingsActivity()
    {
        Intent intent = new Intent(MyDashBoard.this, Settings.class);
        startActivity(intent);
    }
    private void goToMydevices()
    {

        Intent intent = new Intent(MyDashBoard.this, MyDevices.class).putExtra("User",user);
        startActivity(intent);
    }

    private String getTime()
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date=new Date();
        return dateFormat.format(date);
    }
}
