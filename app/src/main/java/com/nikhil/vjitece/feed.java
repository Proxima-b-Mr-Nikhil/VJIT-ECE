package com.nikhil.vjitece;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class feed extends AppCompatActivity {
    public RecyclerView mRecyclerView;
    private feedAdapter mAdapter;

    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private List<feedUpload> mUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);



        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        //displaying progress dialog while fetching ima
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("feed");
        mDatabaseRef.keepSynced(true);

        //adding an event listener to fetch values
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                //dismissing the progress dialog

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String key=snapshot.getKey();
                    feedUpload upload = postSnapshot.getValue(feedUpload.class);
                    mUploads.add(upload);
                }
                //creating adapter
                mAdapter = new feedAdapter(getApplicationContext(), mUploads);

                //adding adapter to recyclerview
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(feed.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}
