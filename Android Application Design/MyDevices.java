package com.example.coen390_poject.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.coen390_poject.Controllers.DeviceRecyclerViewAdapter;
import com.example.coen390_poject.Controllers.SharedPreferencesHelper;
import com.example.coen390_poject.Models.Device;
import com.example.coen390_poject.Models.User;
import com.example.coen390_poject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class MyDevices extends AbsRuntimePermission {

    protected RecyclerView devicesRecyclerView;
    protected DeviceRecyclerViewAdapter devicesAdapter;
    User user;//to retrieve the list of devices that a user have.
    protected Toolbar myDevicesToolBar;
    DatabaseReference firebaseDatabase;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_devices);
        devicesRecyclerView=findViewById(R.id.recycler_view_devices);
        myDevicesToolBar=findViewById(R.id.mydevices_toolbar);

        setSupportActionBar(myDevicesToolBar);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);//display the back button
        actionBar.setTitle("My Devices" );

        user = (User) getIntent().getSerializableExtra("User");
        SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(this);
        String userId=sharedPreferencesHelper.getId("UID");
        loadDevices(userId);

  }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
    public void loadDevices(String userId)
    {
        firebaseDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(userId);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);//used to display the content of the recyclerview.
        devicesRecyclerView=findViewById(R.id.recycler_view_devices);
        devicesRecyclerView.setLayoutManager(linearLayoutManager);//specifies how are we going to display the list of devices.

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user=snapshot.getValue(User.class);
                List<Device>devices;//to store the list of devices.
                devices=user.getDevices();
                devicesAdapter=new DeviceRecyclerViewAdapter(devices);
                devicesRecyclerView.setAdapter(devicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add_device,menu);

        return super.onCreateOptionsMenu(menu);
    }


    /* Used to go back to the parent activity using the return button-->My Dashboard*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        else if (item.getItemId()==R.id.add_device)
        {
            Intent intent = new Intent(MyDevices.this, QrCodeScanner.class).putExtra("User",user);
            startActivity(intent);
        }
        devicesAdapter.notifyDataSetChanged();

        return super.onOptionsItemSelected(item);
    }
}
