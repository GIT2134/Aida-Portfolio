package com.example.coen390_poject.Views;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coen390_poject.Controllers.SharedPreferencesHelper;
import com.example.coen390_poject.Models.Tests;
import com.example.coen390_poject.Models.User;
import com.example.coen390_poject.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AbsRuntimePermission {

    protected TextView welcome;
    protected TextView signUp;
    protected TextInputEditText emailAddress;
    protected TextInputEditText passworD;
    protected TextInputEditText Name;
    protected TextInputEditText Surname;
    protected Button signUpButton;
   // private static final int REQUEST_PERMISSION = 10;

    protected FirebaseAuth mAuth;

    ProgressDialog progressDialog;
    Tests test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);




        mAuth=FirebaseAuth.getInstance();
        welcome=findViewById(R.id.wel_come);

        signUp=findViewById(R.id.sign_up_to_continue);
        emailAddress=findViewById(R.id.email_address_sing_up);

        passworD=findViewById(R.id.password_sign_up);
        Name=findViewById(R.id.name_sign_up);

        Surname=findViewById(R.id.sign_up_surname);
        signUpButton=findViewById(R.id.next);

        test = new Tests(getApplicationContext());
        setUI();

    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    private void setUI()
    {
       
        progressDialog = new ProgressDialog(SignUp.this);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveUserInfo();
            }
        });

    }
    private void saveUserInfo()
    {

        String name = Name.getText().toString().trim();
        String surname = Surname.getText().toString().trim();
        String emailAdd =emailAddress.getText().toString().trim();
        String password = passworD.getText().toString();

        //  if (!emailAdd.isEmpty())/*A text was entered by the user*/
       if (emailAdd.isEmpty())
        {
            emailAddress.setError("Please enter your email");
            emailAddress.requestFocus();
            return;
        }
            if (!Patterns.EMAIL_ADDRESS.matcher(emailAdd).matches())/*Verify the email address entered*/
            {
              emailAddress.setError("Please enter a valid email address !");
              emailAddress.requestFocus();
              return;
            }

        if (password.isEmpty())
        {
            passworD.setError("Please enter your password");
            passworD.requestFocus();
            return;
        }

            if (password.length()<8)/*Minimum number of characters is 8*/
            {
                passworD.setError("Password length needs to be at least 8 characters.");
                passworD.requestFocus();
                return;
            }

        if (surname.isEmpty())
        {
            Surname.setError("Please enter your surname");
            Surname.requestFocus();
            return;
        }

        if (name.isEmpty())
        {
            Name.setError("Please enter your name");
            Name.requestFocus();
            return;
        }

        signUpWithUserInfo(name,surname,emailAdd,password);
        //scanQr();

    }
    private void signUpWithUserInfo(String name,String surname,String email,String password)
    {
        // Step 01: We need to check the internet connection
        if(!test.isConnected())
        {
            // No connection
            Toast.makeText(getBaseContext(),R.string.internet_connection,Toast.LENGTH_LONG).show();
        }
        else {
            // Step 02: start sing up process

            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.setMessage(getString(R.string.msg_wait));
            // Show waiting progress
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {


                  //  User user = new User(name, surname, email,"0");
                    User user =new User(name,surname,email);
                   // User user=new User(name,surname,email,null);

                    // write in firebase db
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").
                            child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper(SignUp.this);

                    db.setValue(user);
                    // if the create process is done, we need to pass an email here.
                    // Not on the login activity
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    String uid=currentUser.getUid().toString();//get the Id of the current user
                    sharedPreferencesHelper.saveId(uid,"UID"); //save the id in sharedPreferences.

                    currentUser.sendEmailVerification();
                } else {
                    // means the process has failed, thus we need to handle this error.
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException exception) {
                     
                  
                        progressDialog.dismiss();
                        finish();
                    } catch (Exception exception) {
                        // for general exceptions.
                        // as same as catch (...) in C++
                        Toast.makeText(SignUp.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
                progressDialog.dismiss();
            });
        }
    }


}



    /*private void signUpWithUserInfo(String name,String surname,String email,String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(name, surname, email);

                    FirebaseDatabase.getInstance().getReference("Users").
                            child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {   // write in firebase db
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "User has been registered. ", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignUp.this, "Failed , try again", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(SignUp.this, "Failed ", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}*/
