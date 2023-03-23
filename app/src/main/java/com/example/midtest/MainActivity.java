package com.example.midtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

private TextView  email, username, password;
     private RadioButton policy;
    private DatePickerDialog dpd;

    database db = new database(this);

    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i)
            {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]*").matcher(String.valueOf(source.charAt(i))).matches())
                {
                    return "";
                }
            }

            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        username = findViewById(R.id.name);
        password = findViewById(R.id.password);
        policy = findViewById(R.id.policy);

        email.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(15)});
        username.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(15)});
        password.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(16)});


        Button b = findViewById(R.id.signUpButtonId);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String datePattern = "^(1[0-2]|0[1-9])/(3[01]" + "|[12][0-9]|0[1-9])/[0-9]{4}$";


                if (email.getText().toString().equals("")) {
                    email.setError("Empty first name");
                }  else if (username.getText().toString().equals("")) {
                    username.setError("Empty last name");
                }  else if (password.getText().toString().equals("")) {
                    password.setError("Empty password");
                } else if(!policy.isChecked()) {
                    policy.setError("Policy must be accepted");
                } else {

                    if (email.getError() == null && username.getError() == null &&
                             password.getError() == null && policy.isChecked()) {

                        addUser(new User(email.getText().toString(), username.getText().toString(),
                                password.getText().toString()));

                        Intent myIntent = new Intent(v.getContext(), UserDataShow.class);
                        startActivity(myIntent);
                    }
                    email.setText("");
                    username.setText("");
                    password.setText("");
                    policy.setChecked(false);
                    policy.setError(null);
                }
            }
        });
    }


    public void addUser(User newUser) {
        boolean insertUser = db.addUser(newUser);
        if(insertUser)
            toastMessage("User added successfully");
        else
            toastMessage("Something went wrong");
    }


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}