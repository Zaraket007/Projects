package com.example.contactsv3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class AddContactActivity extends AppCompatActivity {
    EditText fname;
    EditText lname;
    EditText phonenb;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        fname=findViewById(R.id.fnameet);
        lname=findViewById(R.id.lnameet);
        phonenb=findViewById(R.id.phonenbett);
    }

    public void addBt(View v){
        addContact();
    }
    boolean checkCredentials(String x){
        if(x.equals("")) return false;

        return true;
    }
    void addContact(){
        String first=fname.getText().toString();
        if(!checkCredentials(first)){
            Toast.makeText(getApplicationContext(),"first name is empty!",Toast.LENGTH_SHORT).show();
            return;
        }

        String last=lname.getText().toString();
        if(!checkCredentials(last)){
            Toast.makeText(getApplicationContext(),"last name is empty!",Toast.LENGTH_SHORT).show();
            return;
        }
        String phone=phonenb.getText().toString();
        if(!checkCredentials(phone)){
            Toast.makeText(getApplicationContext(),"phone number is empty!",Toast.LENGTH_SHORT).show();
            return;
        }

// Reference to a specific user's document
        DocumentReference userDocRef = db.collection("users").document(user.getUid());

// Create a new subcollection under the user's document
        CollectionReference subcollectionRef = userDocRef.collection("contacts");

// Add a document to the subcollection with some data
        Map<String, Object> map =new HashMap<>();
        map.put("first_name",first);
        map.put("last_name",last);
        map.put("phone_number",phone);

        subcollectionRef.add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Document added to the subcollection successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occurred during the operation
                    }
                });

        finish();
    }
}
