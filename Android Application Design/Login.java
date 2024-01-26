package com.example.coen390_poject.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coen390_poject.Controllers.SharedPreferencesHelper;
import com.example.coen390_poject.Models.Tests;
import com.example.coen390_poject.Models.User;
import com.example.coen390_poject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class Login extends AbsRuntimePermission {

    protected TextInputEditText emailAddress;
    protected TextInputEditText passwordTxt;
    protected TextView signUp;
    protected Button login;
    private FirebaseAuth mAuth; /*declare an instance of FirebaseAuth*/
    ProgressDialog progressDialog;
    Tests test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        test = new Tests(getBaseContext());
        progressDialog = new ProgressDialog(Login.this);



        setUI();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    private void setUI()
    {
        emailAddress=findViewById(R.id.email_address);
        passwordTxt=findViewById(R.id.txt_password);
        signUp=findViewById(R.id.sign_up);
        login=findViewById(R.id.login_id);
        mAuth=FirebaseAuth.getInstance();/*Initialize the FirebaseAuth instance*/

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToSignUpActivity();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLogin();/*Go to login method*/
            }
        });

    }

   // @Override
  //  public void onStart()/*Initialize the activity*/
  //  {
   //     super.onStart();
   //     FireBaseUser currentUser=mAuth.getCurrentUser();/* Verifies if the user is signed in*/
  //      UpdateUI(currentUser);/*Update the UI*/

 //   }
    private void goToSignUpActivity()
    {

        Intent intent=new Intent (Login.this, SignUp.class);
        startActivity(intent);

    }
    private void setLogin()
    {
        String email=emailAddress.getText().toString().trim();/*trim() removes whitespace from the string*/
        String password=passwordTxt.getText().toString(); /*Space is considered as a character in passwords*/

        if (email.isEmpty())/*No text is entered*/
        {
            /*The email box is empty*/
            /*Display a message to the user to enter the email.*/
            emailAddress.setError("Please enter your email");
            emailAddress.requestFocus();
            return;
        }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())/*Verify the email address entered*/
            {
                emailAddress.setError("Please enter a valid email address !");
                emailAddress.requestFocus();

            }

            if (password.isEmpty())
            {
                passwordTxt.setError("Please enter your password");
                passwordTxt.requestFocus();
               return;
            }
        if (password.length()<8)/*Minimum number of characters is 8*/
        {
            passwordTxt.setError("Password length needs to be at least 8 characters.");
            passwordTxt.requestFocus();
            return;
        }
        if(!test.isConnected())
        {
            // No connection
            Toast.makeText(getBaseContext(),R.string.internet_connection,Toast.LENGTH_LONG).show();
        }
        else {
            loginWithUserInfo(email, password);
        }

    }
    private void loginWithUserInfo(String email1,String password1)
    {

        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setMessage(getString(R.string.msg_wait));
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(Login.this);
        // Show waiting progress
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    FirebaseUser user=mAuth.getCurrentUser();
                    System.out.println("successful login"+ user.isEmailVerified());
//                    if (user.isEmailVerified())
                    {
                       /* the user's email address is
                         linked with his/her account*/
                        progressDialog.dismiss();
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference db = FirebaseDatabase.getInstance().
                                getReference("Users").child(uid);

//                        preferencesEditor.putString("UID", uid);
//                        preferencesEditor.commit();
                        sharedPreferencesHelper.saveId("UID", uid);
                        String UID = sharedPreferencesHelper.getId("UID");


                        Intent intent=new Intent(Login.this,MyDashBoard.class);
                        intent.putExtra("UID",uid);
                        startActivity(intent);

                    }
//                    else {
//                        /*Send an email with the link to verify user's account*/
//                        user.sendEmailVerification();
//                        Toast.makeText(Login.this,"Please check your email to verify your account",Toast.LENGTH_LONG).show();
//                    }
                    // Let's try to check the user on real-time database
                   String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                   DatabaseReference db = FirebaseDatabase.getInstance().
                           getReference("Users").child(uid);

                  ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                       if(!dataSnapshot.exists()) {
                                //create new user
                               User db_user = new User();
                                db_user.setEmail(user.getEmail());
                                db_user.setName(user.getDisplayName());
                                db.setValue(db_user);
                          }
                        }
                      @Override
                     public void onCancelled(DatabaseError error) {
                         Log.d("TAG",error.getMessage()); //Don't ignore potential errors!
                      }
                  };
                    db.addListenerForSingleValueEvent(eventListener);
                }
                else {
                    Toast.makeText(Login.this, "Failed ! Please try again.", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
   /* @Override
    public void onStart()
    {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent=new Intent (Login.this,MyDashBoard.class);
            startActivity(intent);
        }
    }*/


}
