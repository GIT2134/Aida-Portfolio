package com.example.coen390_poject.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.coen390_poject.Controllers.SharedPreferencesHelper;
import com.example.coen390_poject.Models.Device;
import com.example.coen390_poject.Models.User;
import com.example.coen390_poject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AbsRuntimePermission {
    protected FirebaseUser firebaseUser;
    protected String userId;
    protected TextView scan_qr;
    ImageView imageview;
    private LottieAnimationView lottieAnimationView;
    protected DatabaseReference databaseReference;
    protected Button button;
    protected TextView email;
    protected TextView surname;
    protected TextView name;
    private FirebaseAuth mAuth;
    protected User user;
    protected TextView edit;

    protected Toolbar toolbarProfile;

    private static final int REQUEST_PERMISSION = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        button=findViewById(R.id.button);

        email=findViewById(R.id.currentEmail);
        name=findViewById(R.id.users_name_profile);
        surname=findViewById(R.id.surname_profile);

        imageview = findViewById(R.id.imageView);

        toolbarProfile=findViewById(R.id.profile_toolBar);
        setSupportActionBar(toolbarProfile);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);//displays the back button
        actionBar.setTitle("My Profile" );


        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(ProfileActivity.this);
        String uid = sharedPreferencesHelper.getId("UID");

        if(uid == null)
        {
            uid = getIntent().getStringExtra("UID");
        }
        try {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);

            user = (User) getIntent().getSerializableExtra("User");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot d : snapshot.getChildren()) {

                        user = snapshot.getValue(User.class);
                        name.setText(user.getName());
                        surname.setText(user.getSurname());
                        email.setText(user.getEmail());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception exception)
        {
            Log.d("UserError",
                    exception.getMessage());
        }

   
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
    public void scanQr()
    {
        Intent intent=new Intent (ProfileActivity.this, QrCodeScanner.class).putExtra("User",user);
        startActivity(intent);

    }
  
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }



}
