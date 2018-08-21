package com.example.yuki.messanger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private LinearLayout layout;

    private static final int RC_SIGN_IN = 9001;
   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameWrapper = findViewById(R.id.usernameWrapper);
        passwordWrapper = findViewById(R.id.passwordWrapper);
        layout = findViewById(R.id.linearLayout);
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

    }


    public void goToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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

}
