package com.chandlerobaker.alcchallenge.android.journalapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chandlerobaker.alcchallenge.android.journalapp.database.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static com.chandlerobaker.alcchallenge.android.journalapp.MainActivity.PREF_APP;


public class SignInActivity extends AppCompatActivity {

    private  GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = SignInActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    private DatabaseReference mDatabaseReferenceUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mDatabaseReferenceUsers = FirebaseDatabase.getInstance().getReference(getResources().getText(R.string.firebase_db_users).toString());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            if(extras.getBoolean("SIGN_OUT", false)){
                signOut();
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            updatePreferences(account);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            updateUI();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void updateUI() {
        
      SignInButton  mSignInButton = (SignInButton) findViewById(R.id.button_signIn);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_signIn:
                        signIn();
                        break;
                }
            }
        });
        mSignInButton.setSize(SignInButton.SIZE_STANDARD);
    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void updatePreferences(GoogleSignInAccount account) {

        if(account != null) {

            User user = new User(account.getId(), account.getDisplayName(), account.getEmail());

            mDatabaseReferenceUsers.child(Objects.requireNonNull(account.getId())).setValue(user);

            SharedPreferences sharedPreferences = getSharedPreferences(PREF_APP, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USER_ACCOUNT_ID", account.getId());
            editor.putString("USER_ACCOUNT_EMAIL", account.getEmail());
            editor.putString("USER_ACCOUNT_NAME", account.getDisplayName());
            editor.apply();
        }


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updatePreferences(account);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, getResources().getText(R.string.success_sign_in_message).toString(), Toast.LENGTH_SHORT).show();
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updatePreferences(null);
        }
    }

    private void signOut(){

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }



}
