package com.example.myfirebaseapp.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirebaseapp.DetailActivity;
import com.example.myfirebaseapp.R;
import com.example.myfirebaseapp.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context mContext;
    private List<User> mDataList;

    public static final String USER_KEY="userKey";


    public UserAdapter(Context mContext,List<User> mDataList){
        this.mContext=mContext;
        this.mDataList=mDataList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final User user = mDataList.get(position);
        holder.textView_Name.setText(user.getName() );
        holder.textView_City.setText(user.getCity() );
        holder.textView_Age.setText(""+user.getAge());
        holder.textView_Profession.setText(user.getProfession() );
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemID = user.getItemId();
                Intent intent =new Intent(mContext, DetailActivity.class);
                intent.putExtra(USER_KEY,itemID);
                mContext.startActivity(intent);
            }
        });
        holder.mLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String itemId= user.getItemId();
                Task<Void> voidTask= Utils.removeUser(itemId);
                voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(mContext,"user removed from dataBase",Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView_Name,textView_Age,textView_City,textView_Profession;
        LinearLayout mLayout;
        public MyViewHolder(View itemView){
            super(itemView);
            textView_Name=itemView.findViewById(R.id.ListTextView_Name);
            textView_City=itemView.findViewById(R.id.ListTextView_city);
            textView_Age=itemView.findViewById(R.id.ListTextView_Age);
            textView_Profession=itemView.findViewById(R.id.ListTextView_Profession);
            mLayout= itemView.findViewById(R.id.LinearLayout);


        }
    }
}
