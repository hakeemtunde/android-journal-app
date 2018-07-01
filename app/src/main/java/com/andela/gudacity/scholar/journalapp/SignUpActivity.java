package com.andela.gudacity.scholar.journalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.util.ValidateUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private FirebaseAuth mAuth;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSignUpButton;
    private ProgressBar mProgressBar;

    private TextView mErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mEmailEditText = (EditText)findViewById(R.id.et_signup_email);
        mPasswordEditText = (EditText)findViewById(R.id.et_signup_password);
        mErrorTextView = (TextView)findViewById(R.id.tv_signup_error);

        mSignUpButton = (Button)findViewById(R.id.btn_signup_user);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_signup_loader);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signUpUser() {

        mProgressBar.setVisibility(View.VISIBLE);

        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (!ValidateUtil.isValidEmail(email)) {
            mEmailEditText.setError("Invalid email");
            mProgressBar.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            mProgressBar.setVisibility(View.GONE);
            mPasswordEditText.setError("invalid password");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            Intent intent = new Intent(SignUpActivity.this, JournalActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            mErrorTextView.setVisibility(View.VISIBLE);
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            mErrorTextView.setText(task.getException().getMessage());
                            Toast.makeText(SignUpActivity.this,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
        mProgressBar.setVisibility(View.GONE);


    }
}
