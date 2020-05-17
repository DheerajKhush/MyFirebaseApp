package com.example.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirebaseapp.model.User;
import com.example.myfirebaseapp.model.UserAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";
    private Button mSubmitButton, mReadDataButton;
    private EditText mInputTextName, mInputTextAge,mInputTextCity,mInputTextProfession;
    private TextView mOutputText;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mDataList;

    private FirebaseDatabase mDataBase;
    private DatabaseReference mRef;
    private ChildEventListener mChildListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inintialize views
        mInputTextName = (EditText) findViewById(R.id.EditTextView_Name);
        mInputTextAge = (EditText) findViewById(R.id.EditTextView_Age);
        mInputTextCity=(EditText) findViewById(R.id.EditTextView_City);
        mInputTextProfession=(EditText) findViewById(R.id.EditTextView_Profession);
        mOutputText = (TextView) findViewById(R.id.TextView);
        mSubmitButton = (Button) findViewById(R.id.ButtonSubmit);
        mReadDataButton = (Button) findViewById(R.id.ButtonReadData);

        recyclerView= (RecyclerView)findViewById(R.id.recyclerView);


        //
        mDataBase = FirebaseDatabase.getInstance();
        //mRef here refer to main node(parent node// first node) when getRefernce() is used
        //mRef.getReference(path//name of child)
        mRef = mDataBase.getReference("users");

        //Firebase storage
        FirebaseStorage firebaseStorage =FirebaseStorage.getInstance();

        mDataList= new ArrayList<>();
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter= new UserAdapter(this, mDataList);
        recyclerView.setAdapter(userAdapter);

        mSubmitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                runCode();
            }

        });
        mReadDataButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });

        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                User user = dataSnapshot.getValue(User.class);
                user.setItemId(dataSnapshot.getKey());
               mDataList.add(user);
               userAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                User user=dataSnapshot.getValue(User.class);
                user.setItemId(dataSnapshot.getKey());
                mDataList.remove(user);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mRef.addChildEventListener(mChildListener);

    }

    public void runCode() {
        String name = mInputTextName.getText().toString();
        String city = mInputTextCity.getText().toString();
        String profession = mInputTextProfession.getText().toString();
        int age = Integer.parseInt(mInputTextAge.getText().toString());
        String key = mRef.push().getKey();
        User user = new User(name, age,key,city,profession);


        mRef.child(key).setValue(user);
        Toast.makeText(this, "data Inserted", Toast.LENGTH_LONG).show();

    }

    public void readData() {
    //read Data

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRef.removeEventListener(mChildListener);
        ;
    }
}


