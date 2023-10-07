package com.example.contactsv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ContactsViewActivity extends AppCompatActivity {

    TextView title;
    private String userID;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_view);
        title=findViewById(R.id.contactsviewtv);

        Intent i = getIntent();
        userID=i.getStringExtra("userFB");
        if (userID != null) {
            // Use the UID to retrieve the FirebaseUser
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
        }
        String email = user.getEmail();
        int atIndex = email.indexOf('@');
        String username = email.substring(0, atIndex);
        title.setText(username);
    }
}