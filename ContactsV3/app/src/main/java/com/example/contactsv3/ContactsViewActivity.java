package com.example.contactsv3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.auth.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ContactsViewActivity extends AppCompatActivity {

    private TextView title;
    private FirebaseAuth mauth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private ContactsInfo currentUser;

    private ArrayList<ContactsInfo> contactsList;
    private ContactsInfo contactsInfo;
    private ArrayList<String> idList,first_nameList,last_nameList,phone_numberList, first_last;
    private ArrayAdapter<String> adapter;
    private ListView lv ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_view);
        title = findViewById(R.id.contactsviewtv);
        contactsList = new ArrayList<>();
        idList=new ArrayList<>();
        first_nameList=new ArrayList<>();
        last_nameList=new ArrayList<>();
        phone_numberList=new ArrayList<>();
        first_last=new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();
        currentUser = new ContactsInfo();

        lv = (ListView) findViewById(R.id.listview);
        retrieveData();
        retrieveContacts();
        longClickLv();
        clickLv();
        editCurrentUser();

    }
    void editCurrentUser(){
        title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i=new Intent(getApplicationContext(),EditContactActivity.class);
                i.putExtra("fname",currentUser.getFirst_name());
                i.putExtra("lname",currentUser.getLast_name());
                i.putExtra("phone",currentUser.getPhone_number());
                i.putExtra("id",user.getUid());

                startActivityForResult(i,7);
                return true;
            }
        });
    }

    public void signOutMethod(View v) {

                // Create an AlertDialog.Builder instance
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactsViewActivity.this);

                // Set the title and message for the dialog
                builder.setTitle("Sign Out");
                builder.setMessage("Are you sure you want to sign out?");

                // Set positive button and its click listener ("Yes" button)
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Perform sign out and finish the activity
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                });

                // Set negative button and its click listener ("No" button)
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Dismiss the dialog (do nothing)
                        dialogInterface.dismiss();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
    }


    public void addContact(View v){
        Intent i=new Intent(getApplicationContext(),AddContactActivity.class);
        startActivityForResult(i,15);

    }
    void setNameAtTop() {
        title.setText(currentUser.getFirst_name()
                + " "  +currentUser.getLast_name()
                + " : "
                + currentUser.getPhone_number());

    }

    void retrieveData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("users");
        DocumentReference docRef = collectionRef.document(user.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    currentUser = documentSnapshot.toObject(ContactsInfo.class);

                    setNameAtTop();

                } else {
                    // Handle the case where the document doesn't exist
                }
            }

        });
    }

    void retrieveContacts(){
        // Get a reference to the Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Reference to the Firestore collection containing your documents
        CollectionReference collectionRef =
                db.collection("users")
                .document(user.getUid())
                .collection("contacts");

        // Query to retrieve all documents in the collection
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Convert each document to a ContactsInfo object
                        contactsInfo=new ContactsInfo();
                        contactsInfo = document.toObject(ContactsInfo.class);
                        contactsInfo.setId(document.getId());
                        //Add the ContactsInfo object to the ArrayList
                        contactsList.add(contactsInfo);
                    }
                    decompose();

                    // Now, 'contactsList' contains all the documents as ContactsInfo objects
                    // You can iterate through the list and work with the data as needed
                } else {
                    // Handle errors
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
    void decompose(){
        if(contactsList!=null) {
            Collections.sort(contactsList, new Comparator<ContactsInfo>() {
                @Override
                public int compare(ContactsInfo contact1, ContactsInfo contact2) {
                    // Use compareToIgnoreCase for case-insensitive sorting
                    return contact1.getFirst_name().compareToIgnoreCase(contact2.getFirst_name());
                }
            });

            for (ContactsInfo c:contactsList){
                String id=c.getId();
                String fname=c.getFirst_name();
                String lname=c.getLast_name();
                String phone=c.getPhone_number();
                idList.add(id);
                first_nameList.add(fname);
                last_nameList.add(lname);
                phone_numberList.add(phone);
                first_last.add(fname+" "+lname);
            }
            setListview();

        }
    }

    void setListview(){
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, first_last);
        // Set the adapter for the ListView
        lv.setAdapter(adapter);
    }

    void longClickLv(){
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                Intent i=new Intent(getApplicationContext(),EditContactActivity.class);
                i.putExtra("fname",first_nameList.get(index));
                i.putExtra("lname",last_nameList.get(index));
                i.putExtra("phone",phone_numberList.get(index));
                i.putExtra("id",idList.get(index));
                startActivityForResult(i,15);
                return true;
            }

        });
    }
    void clickLv(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactsViewActivity.this);

                // Set the title and message for the dialog
                builder.setTitle(first_last.get(position));
                builder.setMessage("Phone number: "+phone_numberList.get(position));

                // Set positive button and its click listener ("Yes" button)
                builder.setPositiveButton("exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Perform sign out and finish the activity
                        dialogInterface.dismiss();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==15){
            idList.clear();
            first_nameList.clear();
            last_nameList.clear();
            phone_numberList.clear();
            contactsList.clear();
            first_last.clear();
            retrieveContacts();
            decompose();
            adapter.notifyDataSetChanged();
        }
        if (requestCode==7){
            retrieveData();
            setNameAtTop();
        }
    }
}



