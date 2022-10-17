package com.nikhil.vjitece.homemodules;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nikhil.vjitece.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class pdf extends AppCompatActivity {
    private TextView textView;
    private PDFView pdfView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        pdfView=(PDFView)findViewById(R.id.pdfview);
        textView=(TextView)findViewById(R.id.textView);

        progressBar=(ProgressBar)findViewById(R.id.progressBar) ;

        Intent intent = getIntent();
       final String year = intent.getStringExtra("year");
      final   String section = intent.getStringExtra("section");
        final   String input = intent.getStringExtra("input");
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        assert year != null;
        assert section != null;

        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference mref=database.getReference(input+"/"+year+"/"+section);
        mref.keepSynced(true);
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                progressBar.setVisibility(View.VISIBLE);
                String Value=dataSnapshot.getValue(String.class);
                textView.setText(Value);
                Toast.makeText(pdf.this,"please wait...", Toast.LENGTH_LONG).show();
                String url=textView.getText().toString();
                new RetrivePdfStream().execute(url);
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(pdf.this,"failed to load", Toast.LENGTH_LONG).show();

            }
        });


    }

    class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected void onPostExecute(InputStream inputStream) {

            pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {

                @Override
                public void loadComplete(int nbPages) {
                    progressBar.setVisibility(View.GONE);
                }
            }).load();

        }
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
            try{
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode()==200)
                {

                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e){
                return null;
            }
            return inputStream;


        }
    }

}
