package com.example.androidengineerexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText repeatPassword;
    private Button register;
    private SharedPreferences shPrefs;
    private TextView criteria1, criteria2, criteria3, criteria4;

    public static final String MyPreferences = "MyPrefs";
    public static final String Email = "EmailKey";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ImageButton imageButton = findViewById(R.id.backToMain);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        email = findViewById(R.id.enter_email_text);
        password = findViewById(R.id.enter_password_text);
        repeatPassword = findViewById(R.id.repeat_password_text);
        register = findViewById(R.id.accountCreationButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
            }
        });
        shPrefs = getSharedPreferences(MyPreferences, MODE_PRIVATE);
        criteria1 = findViewById(R.id.criteria1);
        criteria2 = findViewById(R.id.criteria2);
        criteria3 = findViewById(R.id.criteria3);
        criteria4 = findViewById(R.id.criteria4);

        email.addTextChangedListener(registerTextWatcher);
        password.addTextChangedListener(registerTextWatcher);
        repeatPassword.addTextChangedListener(registerTextWatcher);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String checkPassword = password.getText().toString();
                validatepass(checkPassword);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String e = email.getText().toString();
//
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                editor.putString(Email, String.valueOf(email));
//                editor.apply();
//                Toast.makeText(,"Account created", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private TextWatcher registerTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();
            String repeatPasswordInput = repeatPassword.getText().toString().trim();

            register.setEnabled(!emailInput.isEmpty() && !passwordInput.isEmpty() && !repeatPasswordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void validatepass(String password) {

        // check for pattern
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");

        // if lowercase character is not present
        if (!lowercase.matcher(password).find()) {
            criteria4.setTextColor(Color.RED);
        } else {
            // if lowercase character is  present
            criteria4.setTextColor(Color.GREEN);
        }

        // if uppercase character is not present
        if (!uppercase.matcher(password).find()) {
            criteria3.setTextColor(Color.RED);
        } else {
            // if uppercase character is  present
            criteria3.setTextColor(Color.GREEN);
        }
        // if digit is not present
        if (!digit.matcher(password).find()) {
            criteria2.setTextColor(Color.RED);
        } else {
            // if digit is present
            criteria2.setTextColor(Color.GREEN);
        }
        // if password length is less than 8
        if (password.length() < 8) {
            criteria1.setTextColor(Color.RED);
        } else {
            criteria1.setTextColor(Color.GREEN);
        }
    }



    // Email validation
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean PasswordValidation(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";


        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    boolean isEmty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {

        if (!password.getText().toString().equals(repeatPassword.getText().toString())){

            Toast.makeText(this, "Confirm password is not correct", Toast.LENGTH_SHORT).show();

        }

        if(password.getText().toString().length()<8 &&!PasswordValidation(password.getText().toString())){
            password.setError("Check criteria bellow");
        }else{
            System.out.println("Valid");
        }
        // if email typed with mistake give error
        if (!isEmail(email)) {
            email.setError("Enter valid email");
        }
        // if email is empty give error
        if (isEmty(email)) {
            email.setError("Email is required");
        }
        // if password is empty give error
        if (isEmty(password)) {
            password.setError("Password is required");
        }


    }



}