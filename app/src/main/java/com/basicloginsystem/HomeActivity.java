package com.basicloginsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final TextView email = findViewById(R.id.emailId);
        final TextView fullname = findViewById(R.id.fullName);
        final AppCompatButton signOutBtn = findViewById(R.id.signOutBtn);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        //get details for signed in users
        final String getFullName=googleSignInAccount.getDisplayName();
        final String getEmail = googleSignInAccount.getEmail();

        email.setText("Email : "+getEmail);
        fullname.setText("FullName : "+getFullName);

        signOutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //sign out
                googleSignInClient.signOut();

                // Opening Main Activity to sign in again
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                finish();

            }
        });
    }
}