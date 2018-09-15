package com.example.yuki.messanger;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private LinearLayout layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameWrapper = findViewById(R.id.usernameWrapper);
        passwordWrapper = findViewById(R.id.passwordWrapper);
        layout = findViewById(R.id.linearLayout);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final EditText username = usernameWrapper.getEditText();
        final EditText password = passwordWrapper.getEditText();

        //hide keyboard on touch
        findViewById(R.id.linearLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Utils.hideSoftKeyboard(LoginActivity.this);
                return false;
            }
        });

        //dynamic username validation
        username.addTextChangedListener(new TextValidator(username) {
            @Override
            public void validate(TextView textView, String text) {
               validateUsername();
            }
        });

        //dynamic password validation
        password.addTextChangedListener(new TextValidator(password) {
            @Override
            public void validate(TextView textView, String text) {
                validatePassword();
            }
        });

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(username.getText().toString(), password.getText().toString());
            }
        });

    }


    public void goToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToUsers(View view){
        Intent intent = new Intent(LoginActivity.this, Users.class);
        startActivity(intent);
    }

    private void validateUsername() {
        EditText username = usernameWrapper.getEditText();
        if(EditTextUtil.isEmpty(username.getText().toString())){
            usernameWrapper.setError("Please enter Username");
        } else if (!EditTextUtil.isValidEmail(username.getText().toString())){
            usernameWrapper.setError("Please enter valid email address");
        } else {
            usernameWrapper.setErrorEnabled(false);
        }
    }

    private void validatePassword() {
        EditText password = passwordWrapper.getEditText();
        if (EditTextUtil.isEmpty(password.getText().toString())) {
            passwordWrapper.setError("Please enter password");
        } else if (password.getText().length() < 4) {
            passwordWrapper.setError("Your password must be more then 4 characters");
        } else {
            passwordWrapper.setErrorEnabled(false);
        }
    }

    private void login(String email, String password){
        if(!checkValidation()){
            Toast.makeText(LoginActivity.this, "Something went wrong, try again", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //TODO: change intent
                    Toast.makeText(LoginActivity.this, "Successful login", Toast.LENGTH_LONG).show();
                } else {
                    //TODO: on failure
                    Toast.makeText(LoginActivity.this, "Something went wrong, try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkValidation() {
        boolean isValid = true;

        EditText username = usernameWrapper.getEditText();
        EditText password = passwordWrapper.getEditText();

        if(EditTextUtil.isEmpty(username.getText().toString()) || EditTextUtil.isEmpty(password.getText().toString())) {
            isValid = false;
        } else if (!EditTextUtil.isValidEmail(username.getText().toString())){
            isValid = false;
        } else if (password.getText().toString().length() < 4){
            isValid = false;
        }

        return  isValid;
    }

}
