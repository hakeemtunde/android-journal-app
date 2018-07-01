package com.andela.gudacity.scholar.journalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.util.ValidateUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 101;

    private SignInButton mGoogleSignInButton;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    private Button mSignInButton;
    private Button mSignUpButton;
    private ProgressBar mProgressBar;

    private TextView mSignInErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailEditText = (EditText) findViewById(R.id.et_email);
        mPasswordEditText = (EditText) findViewById(R.id.et_password);

        mSignInErrorTextView = (TextView) findViewById(R.id.tv_signin_error);

        mGoogleSignInButton = (SignInButton)findViewById(R.id.sign_in_button);
        mSignInButton = (Button) findViewById(R.id.btn_login);
        mSignUpButton = (Button) findViewById(R.id.btn_register);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_loader);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                signIn();
            }
        });

        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            launchJournalActivity();
            return;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                mSignInErrorTextView.setText(e.getMessage());
                mSignInErrorTextView.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.btn_login:
                signInUserWithEmailAndPassword();
                break;

            case R.id.btn_register:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;

        }



    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                                        //launch JournalActivity
                            launchJournalActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_LONG).show();
                            mSignInErrorTextView.setVisibility(View.VISIBLE);

                        }

                        // ...
                    }
                });

        mProgressBar.setVisibility(View.GONE);
    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void launchJournalActivity() {
        Intent intent = new Intent(this, JournalActivity.class);
        startActivity(intent);
    }

    private void signInUserWithEmailAndPassword() {

        mProgressBar.setVisibility(View.VISIBLE);

        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(email) ||
                !ValidateUtil.isValidEmail(email)) {
            mEmailEditText.setError("Invalid email");
            mProgressBar.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            mPasswordEditText.setError("invalid password");
            mProgressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            //launch JournalActivity
                            Log.d(TAG, "signInWithEmail:sucess");
                            launchJournalActivity();

                        } else {
                            mProgressBar.setVisibility(View.GONE);
                            mSignInErrorTextView.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

}
