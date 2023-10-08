package com.example.contactsv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditContactActivity extends AppCompatActivity {

    EditText first_name_ET,last_name_ET,phone_ET;
    String first_name,last_name,phone_number,id;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DocumentReference docRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        db = FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();


        Intent i=getIntent();
        first_name=i.getStringExtra("fname");
        last_name=i.getStringExtra("lname");
        phone_number=i.getStringExtra("phone");
        id=i.getStringExtra("id");

        first_name_ET=findViewById(R.id.editfname);
        last_name_ET=findViewById(R.id.editlname);
        phone_ET=findViewById(R.id.editphone);

        first_name_ET.setText(first_name);
        last_name_ET.setText(last_name);
        phone_ET.setText(phone_number);
        if(id.equals(user.getUid())){
            docRef = db.collection("users")
                    .document(user.getUid());
        }else {
            docRef = db.collection("users")
                    .document(user.getUid())
                    .collection("contacts")
                    .document(id);
        }
    }
    boolean checkCredentials(String x){
        if(x.equals("")) return false;

        return true;
    }

    public void edit(View v){
        Map<String, Object> newValues = new HashMap<>();

        String newfn=first_name_ET.getText().toString();
        if(!checkCredentials(newfn)){
            Toast.makeText(getApplicationContext(),"First name empty!",Toast.LENGTH_SHORT).show();
            return;
        }
        String newln=last_name_ET.getText().toString();
        if(!checkCredentials(newln)){
            Toast.makeText(getApplicationContext(),"last name empty!",Toast.LENGTH_SHORT).show();
            return;
        }
        String newph=phone_ET.getText().toString();
        if(!checkCredentials(newfn)){
            Toast.makeText(getApplicationContext(),"Phone number empty!",Toast.LENGTH_SHORT).show();
            return;
        }


        newValues.put("first_name",newfn );
        newValues.put("last_name",newln );
        newValues.put("phone_number", newph);

        docRef.set(newValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document updated successfully
                        Toast.makeText(getApplicationContext(),"Contact edited successfully",Toast.LENGTH_SHORT).show();
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }
}