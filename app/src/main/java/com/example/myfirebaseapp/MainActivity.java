package com.example.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirebaseapp.model.User;
import com.example.myfirebaseapp.model.UserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private StorageReference mStorageRef;

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
       // mRef = mDataBase.getReference("users");


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


        //Firebase storage

        FirebaseStorage firebaseStorage =FirebaseStorage.getInstance();
        mStorageRef= firebaseStorage.getReference("docs/");

        //mDataList= new ArrayList<>();
        //

    }

    public void runCode() {

        Bitmap bitmap=readImage();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            UploadTask uploadTask=mStorageRef.child("images/pic.jpg").putBytes(byteArrayOutputStream.toByteArray());
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    task.getException();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void readData() {
    //read Data
    }

    private Bitmap readImage()
    {       InputStream inputStream= null;
        try {
             inputStream= getAssets().open("pic.jpg");
            BitmapDrawable bitmapDrawable = (BitmapDrawable) Drawable.createFromStream(inputStream, null);
            return bitmapDrawable.getBitmap();
        }catch (IOException e){
            e.printStackTrace();
        }
        if (inputStream != null){
            try{inputStream.close();
    }catch (IOException e){
                e.printStackTrace();
            }
    }
        return null;
    }
        @Override
    protected void onDestroy() {
        super.onDestroy();
        mRef.removeEventListener(mChildListener);

    }
}


