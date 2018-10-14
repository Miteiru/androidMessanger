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

import com.example.yuki.messanger.util.EditTextUtil;
import com.example.yuki.messanger.util.TextValidator;
import com.example.yuki.messanger.util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference storeUserDefaultDataReference;

    private TextInputLayout newUsernameWrapper;
    private TextInputLayout newEmailWrapper;
    private TextInputLayout newPasswordWrapper;
    LinearLayout registerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        newUsernameWrapper = findViewById(R.id.newUsernameWrapper);
        newEmailWrapper = findViewById(R.id.newEmailWrapper);
        newPasswordWrapper = findViewById(R.id.newPasswordWrapper);
        registerLayout = findViewById(R.id.registerLinearLayout);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onResume() {
        super.onResume();

        final EditText newUsername = newUsernameWrapper.getEditText();
        final EditText newEmail = newEmailWrapper.getEditText();
        final EditText newPassword = newPasswordWrapper.getEditText();

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

        newEmail.addTextChangedListener(new TextValidator(newEmail) {
            @Override
            public void validate(TextView textView, String text) {
                validateNewEmail();
            }
        });

        newPassword.addTextChangedListener(new TextValidator(newPassword) {
            @Override
            public void validate(TextView textView, String text) {
                validateNewPassword();
            }
        });

        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(newEmail.getText().toString(), newPassword.getText().toString());
            }
        });


    }

    private void register(String email, String password){
        if(!checkValidation()){
            Toast.makeText(RegisterActivity.this, "Something wrong with your validation, try again", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth. createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //TODO: change intent

                    final ArrayList<String> defaultRoom = new ArrayList<String>();
                    defaultRoom.add("home");

                    String currentUserId = mAuth.getCurrentUser().getUid();
                    storeUserDefaultDataReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

                    storeUserDefaultDataReference.child("username").setValue(newUsernameWrapper.getEditText().getText().toString());
                    storeUserDefaultDataReference.child("email").setValue(newEmailWrapper.getEditText().getText().toString());
                    storeUserDefaultDataReference.child("id").setValue(currentUserId);

                    storeUserDefaultDataReference.child("room").setValue(defaultRoom);
                    storeUserDefaultDataReference.child("online").setValue(true)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "New account created", Toast.LENGTH_LONG).show();

                                        //TODO: change intent
                                        goToUsers(findViewById(R.id.registerButton));

                                    }
                                }
                            });

                    Toast.makeText(RegisterActivity.this, "New account created", Toast.LENGTH_LONG).show();
                } else {
                    //TODO: on failure
                    Toast.makeText(RegisterActivity.this, "Something went wrong, try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void goToUsers(View view){
        Intent intent = new Intent(RegisterActivity.this, UsersActivity.class);
        startActivity(intent);
    }

    // Validation

    private void validateNewUsername() {
        EditText newUsername = newUsernameWrapper.getEditText();

        if(EditTextUtil.isEmpty(newUsername.getText().toString())) {
            newUsernameWrapper.setError("Please enter Username");
        } else {
            newUsernameWrapper.setErrorEnabled(false);
        }
    }

    private void validateNewEmail() {
        EditText newEmail = newEmailWrapper.getEditText();

        if(EditTextUtil.isEmpty(newEmail.getText().toString())){
            newEmailWrapper.setError("Please enter your email");
        } else if (!EditTextUtil.isValidEmail(newEmail.getText().toString())) {
            newEmailWrapper.setError("Please enter valid email address");
        } else {
            newEmailWrapper.setErrorEnabled(false);
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

    private boolean checkValidation() {
        boolean isValid = true;

        EditText username = newUsernameWrapper.getEditText();
        EditText email = newEmailWrapper.getEditText();
        EditText password = newPasswordWrapper.getEditText();

        if(EditTextUtil.isEmpty(username.getText().toString()) || EditTextUtil.isEmpty(email.getText().toString())
                || EditTextUtil.isEmpty(password.getText().toString())) {
            isValid = false;
        } else if (!EditTextUtil.isValidEmail(email.getText().toString())){
            isValid = false;
        } else if (password.getText().toString().length() < 4){
            isValid = false;
        }

        return  isValid;
    }

}
