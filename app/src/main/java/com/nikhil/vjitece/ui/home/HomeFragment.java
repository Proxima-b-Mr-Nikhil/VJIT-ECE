package com.nikhil.vjitece.ui.home;

import android.Manifest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nikhil.vjitece.Attendance;
import com.nikhil.vjitece.MessagesActivity;
import com.nikhil.vjitece.R;
import com.nikhil.vjitece.User;
import com.nikhil.vjitece.classgroup;
import com.nikhil.vjitece.feed;
import com.nikhil.vjitece.DriverId;
import com.nikhil.vjitece.homemodules.vjitpage;
import com.nikhil.vjitece.login;
import com.nikhil.vjitece.studymaterial;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends DialogFragment {

    DatabaseReference reference;
    FirebaseUser fuser;
    LinearLayout layout;
    private FirebaseDatabase database=FirebaseDatabase.getInstance();


    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;



    public RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;
    ImageView image_profile,stt;

    private DatabaseReference mDatabaseRef;
    public  TextView quot;
    private List<UploadHome> mUploads;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        image_profile=root.findViewById(R.id.profile);
        quot=root.findViewById(R.id.quot);
        stt=root.findViewById(R.id.st);
        ImageView lgt = (ImageView) root. findViewById (R.id.logout);
        lgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager)
                requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if(wifi.isConnected()){
            image_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {


                    fuser = FirebaseAuth.getInstance().getCurrentUser();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                    reference.keepSynced(true);

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                            User user = dataSnapshot.getValue(User.class);
                            assert user != null;

                            final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

                            ViewGroup viewGroup = root.findViewById(android.R.id.content);
                            final View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.customview, viewGroup, false);
                            ImageView img = (ImageView) dialogView. findViewById (R.id.img);

                            if (user.getImageurl().equals("default")){
                                img.setImageResource(R.drawable.defaultpic);
                            } else {    Picasso.get()
                                        .load(user.getImageurl())
                                        .placeholder(R.drawable.loadingholder)
                                        .into(img, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError(Exception e) {

                                            }

                                        });

                            }

                            builder.setView(dialogView);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }}


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            });

            stt.setImageResource(R.drawable.online);

        }
        else if (mobileNetwork.isConnected()){
            image_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {


                    fuser = FirebaseAuth.getInstance().getCurrentUser();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                    reference.keepSynced(true);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                            User user = dataSnapshot.getValue(User.class);
                            assert user != null;

                            final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                            ViewGroup viewGroup = root.findViewById(android.R.id.content);
                            final View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.customview, viewGroup, false);

                            final   ImageView img = (ImageView) dialogView. findViewById (R.id.img);

                            if (user.getImageurl().equals("default")){
                                img.setImageResource(R.drawable.defaultpic);
                            } else {

                                    Picasso.get()

                                            .load(user.getImageurl())
                                            .placeholder(R.drawable.loadingholder)
                                            .into(img, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError(Exception e) {

                                                }

                                            });

                            }

                            builder.setView(dialogView);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }}


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            });

            stt.setImageResource(R.drawable.online);

        }
        else{

            image_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("You are Offline Please Connect to internet !" +
                                    " You may not have access to all the features");

                    AlertDialog alert = builder.create();
                    Objects.requireNonNull(alert.getWindow()).setGravity(Gravity.CENTER);
                    alert.show();
                }
            });
            stt.setImageResource(R.drawable.offline);


        }

        FirebaseApp.initializeApp(getActivity());

        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                   Intent intent=new Intent(getActivity(),login.class);
                   startActivity(intent);
                }
            }
        };
        reference = FirebaseDatabase.getInstance().getReference("quot");
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                String q = dataSnapshot.child("quot").getValue(String.class);
                quot.setText(q);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    String name = "Hi" + " " + user.getname().toUpperCase();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    if (!prefs.getBoolean("firstTime", false)) {
                        View v = requireActivity().findViewById(android.R.id.content);
                        Snackbar.make(v, name,
                                Snackbar.LENGTH_LONG)
                                .show();
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("firstTime", true);
                        editor.apply();
                    }
                    if (user.getImageurl().equals("default")) {
                        image_profile.setImageResource(R.drawable.defaultpic);
                    } else {
                        Glide.with(image_profile.getContext()).load(user.getImageurl()).into(image_profile);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                User user = dataSnapshot.getValue(User.class);

                assert user != null;
                final  String yea = user.getYear();
                final  String se = user.getSection();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", user.getname());
                editor.putString("year", yea);
                editor.putString("sec", se);
                editor.apply();

            }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        final   String year = preference.getString("year","");
        final   String sec = preference.getString("sec","").toLowerCase();

        LinearLayout app_l = (LinearLayout) root.findViewById (R.id.timetable);
        app_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), com.nikhil.vjitece.homemodules.pdf.class);
                inten.putExtra("year", year);
                inten.putExtra("section", sec);

                inten.putExtra("input", "timetable");
                startActivity(inten);

            }
        });
        LinearLayout app_ = (LinearLayout) root.findViewById (R.id.examdates);
        app_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), com.nikhil.vjitece.homemodules.pdf.class);
                inten.putExtra("year", year);
                inten.putExtra("section", "");
                inten.putExtra("input", "examdates");
                startActivity(inten);
            }
        });
        LinearLayout app = (LinearLayout) root.findViewById (R.id.syllabi);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), com.nikhil.vjitece.homemodules.pdf.class);
                inten.putExtra("year", year);
                inten.putExtra("section", "");
                inten.putExtra("input", "syllabi");
                startActivity(inten);
            }
        });
        LinearLayout c= (LinearLayout) root.findViewById (R.id.examhallallotment);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), com.nikhil.vjitece.homemodules.pdf.class);
                inten.putExtra("year", year);
                inten.putExtra("section", sec);
                inten.putExtra("input", "examhallallotment");
                startActivity(inten);
            }
        });
        LinearLayout app_la = (LinearLayout) root.findViewById (R.id.circular);
        app_la.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), com.nikhil.vjitece.homemodules.pdf.class);
                inten.putExtra("year", "circular");
                inten.putExtra("section", "");
                inten.putExtra("input", "circular");
                startActivity(inten);

            }
        });
        LinearLayout g= (LinearLayout) root.findViewById (R.id.aboutus);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), com.nikhil.vjitece.homemodules.pdf.class);
                inten.putExtra("year", "aboutus");
                inten.putExtra("section", "");
                inten.putExtra("input", "aboutus");
                startActivity(inten);
            }
        });

        LinearLayout app_layer = (LinearLayout) root.findViewById (R.id.vjitpage);
        app_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), vjitpage.class);
                inten.putExtra("message", "https://vjit.ac.in/");
                startActivity(inten);
            }
        });
        LinearLayout app_laye = (LinearLayout) root.findViewById (R.id.vjitlogin);
        app_laye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), vjitpage.class);
                inten.putExtra("message", "http://vjitautonomous.com/Login.aspx?ReturnUrl=%2f");
                startActivity(inten);
            }
        });
        LinearLayout app_lay = (LinearLayout) root.findViewById (R.id.feed);
        app_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), feed.class);
                startActivity(inten);
            }
        });


        LinearLayout ap = (LinearLayout) root.findViewById (R.id.feepayment);
        ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), vjitpage.class);
                inten.putExtra("message", " https://payments.billdesk.com/bdcollect/pay?p1=408&p2=15");
                startActivity(inten);

            }
        });
        LinearLayout a = (LinearLayout) root.findViewById (R.id.studymaterial);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), studymaterial.class);
                startActivity(inten);
            }
        });
        LinearLayout b = (LinearLayout) root.findViewById (R.id.attendance);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inten = new Intent(getActivity(), com.nikhil.vjitece.homemodules.pdf.class);
                inten.putExtra("year", year);
                inten.putExtra("section", sec);
                inten.putExtra("input", "attendance");
                startActivity(inten);

            }
        });


        LinearLayout e= (LinearLayout) root.findViewById (R.id.buseslocation);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(getActivity(), DriverId.class);
                startActivity(inten);
            }
        });

                fuser = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                reference.keepSynced(true);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);

                            assert user != null;
                            String veri = user.getVerification();
                            String imgg = user.getImageurl();
                            String yr = user.getYear();
                            String sec = user.getSection();
                            final String yy = yr + "" + sec;
                            final String rol = user.getRoll();

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("verify", veri);
                            editor.putString("y", yy);
                            editor.putString("roll", rol);
                            editor.putString("img", imgg);
                            editor.apply();


                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        LinearLayout h= (LinearLayout) root.findViewById (R.id.contactus);
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getActivity(), MessagesActivity.class);
               startActivity(intent);
            }
        });
        mRecyclerView =root. findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new CirclePagerIndicatorDecoration());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mProgressCircle =root. findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.keepSynced(true);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadHome upload = postSnapshot.getValue(UploadHome.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(getActivity(), mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
                final int speedScroll = 5000;
                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    int count = 0;
                    boolean flag = true;
                    @Override
                    public void run() {
                        if(count < mAdapter.getItemCount()){
                            if(count==mAdapter.getItemCount()-1){
                                flag = false;
                            }else if(count == 0){
                                flag = true;
                            }
                            if(flag) count++;
                            else count--;
                            mRecyclerView.smoothScrollToPosition(count);
                            handler.postDelayed(this,speedScroll);
                        }
                    }
                };
                handler.postDelayed(runnable,speedScroll);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        int Permission_All = 1;
        String[] Permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,};
        if (!hasPermissions(getActivity(), Permissions)) {
            ActivityCompat.requestPermissions(requireActivity(), Permissions, Permission_All);
        }
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
    public static boolean hasPermissions(FragmentActivity context, String... permissions){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        auth.signOut();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}