package com.example.contactsv3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        email=findViewById(R.id.usernameet1);
        password=findViewById(R.id.passwordet1);

        //test
        signIn("ziko@gmail.com","Ziko1234");
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }



    void signIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            goToContactsViewAtivity();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Incorrect email or password.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }



    //method called bay Register Button
    public void registerBt(View v){
        Intent i=new Intent(MainActivity.this,SignupActivity.class);
        startActivityForResult(i,007);
        super.onPause();
    }
    public void signInBt(View v){
        String em=email.getText().toString();
        String ps=password.getText().toString();
        signIn(em,ps);

    }
    public void goToContactsViewAtivity(){
        Intent i=new Intent(getApplicationContext(), ContactsViewActivity.class);
        FirebaseUser user= mAuth.getCurrentUser();
        i.putExtra("userFB",user.getUid());
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //continue current activity after comming from signUp
        if(requestCode==007){
            super.onResume();
        }
    }
}