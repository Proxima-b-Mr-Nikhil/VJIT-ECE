package com.nikhil.vjitece;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class classgroup extends AppCompatActivity {



    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chats> mchat;
    private String imageurl;

    RecyclerView recyclerView;

    Intent intent;

    ValueEventListener seenListener;

    String yearsec;
    FirebaseUser fuser;

    boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classgroup);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        Intent intent = getIntent();
        String str= intent.getStringExtra("i");

        assert str != null;
        final  String yearsec=str;

        fuser = FirebaseAuth.getInstance().getCurrentUser();



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                Intent intent = getIntent();
                String roll= intent.getStringExtra("roll");

                String msg = text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(fuser.getUid(), yearsec, msg,roll);
                } else {
                    Toast.makeText(classgroup.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        readMesagges(fuser.getUid(), yearsec);

    }





    private void readMesagges(final String nik, final String yearsec) {

        mchat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mchat.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    final Chats chat = snapshot.getValue(Chats.class);
                    assert chat != null;
                    Intent intent = getIntent();
                    String a= intent.getStringExtra("i");
                    if (chat.getReceiver().equals(a)){
                        mchat.add(chat);
                        }
                    }
                    messageAdapter = new MessageAdapter(classgroup.this, mchat, imageurl);
                    recyclerView.setAdapter(messageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void sendMessage(String sender, final String receiver, String message,String roll){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        String img= intent.getStringExtra("img");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("roll", roll);
        hashMap.put("img", img);
        hashMap.put("message", message);
        reference.child("Chats").push().setValue(hashMap);

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
