package com.example.contactsv3;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    EditText Eusername;
    EditText Epassword;
    EditText fname;
    EditText lname;
    EditText phonenb;

    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        Eusername=findViewById(R.id.usernameet2);
        Epassword=findViewById(R.id.passwordet2);
        b=findViewById(R.id.signupbt2);

        fname=findViewById(R.id.firstnameet);
        lname=findViewById(R.id.lastnameet);
        phonenb=findViewById(R.id.phonenbet);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    boolean checkUserPassword(){
        String Susername=Eusername.getText().toString();
        String Spassword=Epassword.getText().toString();
        String first=fname.getText().toString();
        if(!checkCredentials(first)){
            Toast.makeText(getApplicationContext(), "First name empty.",
                    Toast.LENGTH_SHORT).show();

        }
        String last=lname.getText().toString();
        if(!checkCredentials(last)){
            Toast.makeText(getApplicationContext(), "last name empty.",
                    Toast.LENGTH_SHORT).show();
        }
        String phone=phonenb.getText().toString();
        if(!checkCredentials(phone)){
            Toast.makeText(getApplicationContext(), "Phone number empty.",
                    Toast.LENGTH_SHORT).show();
        }


        //check if email foramt is entered correctly
        String regex = "^[A-Za-z0-9+_.-]+@+[A-Za-z0-9+_.-]+\\.[A-Za-z]{2,}$";
        Pattern patternEmail = Pattern.compile(regex);
        Matcher matcherEmail = patternEmail.matcher(Susername);
        if(!matcherEmail.matches()){
            Toast.makeText(getApplicationContext(), "Email is not in the correct format.", Toast.LENGTH_SHORT).show();
            return false;
        }


        //check if password less than 8 character
        if(Spassword.length()<8){
            Toast.makeText(getApplicationContext(), "password less than 8 characters is not accepted.", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check if password contain at least one number
        Pattern pattern = Pattern.compile(".*\\d+.*");
        Matcher matcher = pattern.matcher(Spassword);
        boolean containsNumber = matcher.matches();
        if(!containsNumber){
            Toast.makeText(getApplicationContext(), "password must contain at least one number.", Toast.LENGTH_SHORT).show();
            return false;
        }
        //che if password contain at least on capital char
        Pattern pattern2 = Pattern.compile("[A-Z]");
        Matcher matcher2 = pattern2.matcher(Spassword);
        boolean containsCapitalLetter = matcher2.find();
        if(!containsCapitalLetter){
            Toast.makeText(getApplicationContext(), "password must contain at least one capital character.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }



    public void signUp(String username,String password){

        mAuth.createUserWithEmailAndPassword(username, password)

                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        creatProfile(user.getUid());

                        Toast.makeText(getApplicationContext(), "User Creation Success.",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "User Creation Failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }
    public void signupBt(View v) throws InterruptedException {
        String username=Eusername.getText().toString();
        String password=Epassword.getText().toString();

        if(checkUserPassword()) {
            signUp(username,password);
            //delay finish() to display Toast.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish(); // Finish the activity after the delay
                }
            }, 1000); // Delay for 1 second (adjust as needed)
        }
    }

    boolean checkCredentials(String x){
        if(x.equals("")) return false;

        return true;
    }

    //create profile in firestore.
    void creatProfile(String userID){
        String first=fname.getText().toString();
        String last=lname.getText().toString();

        String phone=phonenb.getText().toString();


        DocumentReference dr=fstore.collection("users").document(userID);
        Map<String, Object> user =new HashMap<>();
        user.put("first_name",first);
        user.put("last_name",last);
        user.put("phone_number",phone);
        dr.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "creating profile success." );

            }
        });





    }
    public static boolean isValidEmail(String email) {
        // Define a regular expression pattern for a basic email validation
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);

        // Match the input email against the pattern
        Matcher matcher = pattern.matcher(email);

        // Return true if it's a valid email, otherwise false
        return matcher.matches();
    }
}