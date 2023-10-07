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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText Eusername;
    EditText Epassword;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        Eusername=findViewById(R.id.usernameet2);
        Epassword=findViewById(R.id.passwordet2);
        b=findViewById(R.id.signupbt2);
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

        //check if username empty
        if (Susername.contentEquals("")){
            Toast.makeText(getApplicationContext(), "Email Empty.", Toast.LENGTH_SHORT).show();
            return false;
        }

        //check if email foramt is entered correctly
        String regex = "^[A-Za-z0-9+_.-]+@+[A-Za-z0-9+_.-]+[.com](.+)$";
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
}