package com.basicloginsystem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView signInBtn = findViewById(R.id.signInBtn);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (googleSignInAccount != null) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                handleSignInTask(task);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);
            }
        });

    }
        private void handleSignInTask(Task<GoogleSignInAccount> task){
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //getting account data
                final String getFullname = account.getDisplayName();
                final String getEmail = account.getEmail();
                final Uri getPhotoUrl = account.getPhotoUrl();

                //Opening Home Activity
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                finish();
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(this,"Failed or Cancelled",Toast.LENGTH_SHORT).show();
            }

        }
}