package com.nikhil.vjitece;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nikhil.vjitece.R;
import com.nikhil.vjitece.User;

import java.util.ArrayList;
import java.util.Objects;

public class DriverId extends AppCompatActivity {

    ListView myListView;
    ProgressBar progressBar;
    FirebaseUser fuser;
    DatabaseReference reference,referenc;

    Query query= FirebaseDatabase.getInstance().getReference("DriverRoute").limitToLast(300);
    ArrayList<String> myArrayList=new ArrayList<>();
    TextView textView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_id);

        myListView=(ListView)findViewById(R.id.listView);
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(DriverId.this,android.R.layout.simple_list_item_1,myArrayList);
        myListView.setAdapter(myArrayAdapter);



        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;


        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key=dataSnapshot.getKey();
                String Value=dataSnapshot.getValue(String.class) ;
                myArrayList.add("\n"+key+"    :    "+Value+"\n");
                myArrayAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int a=position+1;
                        String b=String.valueOf(a);
                        if (a>0){
                            Intent intent = new Intent(getApplicationContext(),Buses.class);
                            intent.putExtra("id",String.valueOf(a));
                            startActivity(intent);
                            reference = FirebaseDatabase.getInstance().getReference(b);
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        final User user = dataSnapshot.getValue(User.class);
                                        String c= Objects.requireNonNull(user).getId();

                                    }else {
                                        Toast.makeText(getApplicationContext(),"No data !",Toast.LENGTH_LONG).show();

                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myArrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}


