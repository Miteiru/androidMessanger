package com.example.yuki.messanger;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout newUsernameWrapper;
    TextInputLayout newPasswordWrapper;
    TextInputLayout newPasswordConfirmWrapper;
    LinearLayout registerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        newUsernameWrapper = findViewById(R.id.newUsernameWrapper);
        newPasswordWrapper = findViewById(R.id.newPasswordWrapper);
        newPasswordConfirmWrapper = findViewById(R.id.newPasswordConfirmWrapper);
        registerLayout = findViewById(R.id.registerLinearLayout);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final EditText newUsername = newUsernameWrapper.getEditText();
        final EditText newPassword = newPasswordWrapper.getEditText();
        final EditText newPasswordConfirm = newPasswordConfirmWrapper.getEditText();

        findViewById(R.id.registerLinearLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Utils.hideSoftKeyboard(RegisterActivity.this);
                return false;
            }
        });

        newUsername.addTextChangedListener(new TextValidator(newUsername) {
            @Override
            public void validate(TextView textView, String text) {
                validateNewUsername();
            }
        });

        newPassword.addTextChangedListener(new TextValidator(newPassword) {
            @Override
            public void validate(TextView textView, String text) {
                validateNewPassword();
            }
        });

        newPasswordConfirm.addTextChangedListener(new TextValidator(newPasswordConfirm) {
            @Override
            public void validate(TextView textView, String text) {
                validateNewPasswordConfirm();
            }
        });

    }

    private void validateNewUsername() {
        EditText newUsername = newUsernameWrapper.getEditText();

        if(EditTextUtil.isEmpty(newUsername.getText().toString())) {
            newUsernameWrapper.setError("Please enter Username");
        } else if (!EditTextUtil.isValidEmail(newUsername.getText().toString())){
            newUsernameWrapper.setError("Please enter valid email address");
        } else {
            newUsernameWrapper.setErrorEnabled(false);
        }
    }

    private void validateNewPassword() {
        EditText newPassword = newPasswordWrapper.getEditText();

        if(EditTextUtil.isEmpty(newPassword.getText().toString())){
            newPasswordWrapper.setError("Please enter password");
        } else if (newPassword.getText().length() < 4){
            newPasswordWrapper.setError("Your password must be more then 4 characters");
        } else {
            newPasswordWrapper.setErrorEnabled(false);
        }
    }

    private void validateNewPasswordConfirm() {
        EditText newPasswordConfirm = newPasswordConfirmWrapper.getEditText();
        EditText newPassword = newPasswordWrapper.getEditText();

        if(EditTextUtil.isEmpty(newPasswordConfirm.getText().toString())){
            newPasswordConfirmWrapper.setError("Please enter password confirmation");
        } else if (!newPasswordConfirm.getText().toString().equals(newPassword.getText().toString())) {
            newPasswordConfirmWrapper.setError("Your password does not match");
        } else {
            newPasswordConfirmWrapper.setErrorEnabled(false);
        }
    }


}
