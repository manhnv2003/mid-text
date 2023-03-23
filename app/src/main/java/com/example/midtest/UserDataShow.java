package com.example.midtest;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class UserDataShow extends AppCompatActivity {
    private TextView t;
    database db = new database(this);
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_show);

        t = findViewById(R.id.showUsers);
        t.setMovementMethod(new ScrollingMovementMethod());

        List<User> users_data = db.getAllUsers();

        for (User u : users_data) {
            String log = "Id: " + u.getId() + "\n"
                    + "First name: " + u.getEmail() + "\n"
                    + "Last name: " + u.getUsername() + "\n";
            text = text + log;
        }
        t.setText(text);
    }
}
