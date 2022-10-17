package com.nikhil.vjitece.ui.notifications;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.nikhil.vjitece.MainActivity;
import com.nikhil.vjitece.R;
import com.nikhil.vjitece.User;
import com.nikhil.vjitece.register;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class NotificationsFragment extends Fragment {
    CircleImageView image_profile;
    Spinner y,s;
    Button oky,oks;
    LinearLayout lfy,lfs;
    TextView username,year,section,roll,phone;

    DatabaseReference reference,ref;
    FirebaseUser fuser;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        image_profile =root. findViewById(R.id.profile_image);
        username = root.findViewById(R.id.username);
        year =root. findViewById(R.id.year);
        section = root.findViewById(R.id.section);
        roll =root. findViewById(R.id.roll);
        phone = root.findViewById(R.id.phone);
        lfy=root.findViewById(R.id.lfyear);
        lfs=root.findViewById(R.id.lfsec);
        y=root.findViewById(R.id.fyear);
        s=root.findViewById(R.id.fsection);
        oky=root.findViewById(R.id.oky);
        oks=root.findViewById(R.id.oks);
        String[] ye = new String[] {
                "1","2","3","4"

        };

        ArrayAdapter<String> yea = new ArrayAdapter<String>(requireActivity(),
                android.R.layout.simple_spinner_item, ye);
        yea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        y.setAdapter(yea);

        String[] sectio = new String[] {
                "A","B","C","D"

        };

        ArrayAdapter<String> sec = new ArrayAdapter<String>(requireActivity(),
                android.R.layout.simple_spinner_item, sectio);
        sec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(sec);

        oky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a= y.getSelectedItem().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child("facult").child(fuser.getUid());
                final HashMap<String, Object> updates = new HashMap<>();
                updates.put("year",a);
                ref.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(requireActivity(), "uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        oks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String b= s.getSelectedItem().toString();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child("facult").child(fuser.getUid());
                final HashMap<String, Object> updates = new HashMap<>();
                updates.put("section",b);
                ref.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(requireActivity(), "uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                User user = dataSnapshot.getValue(User.class);
                username.setText(Objects.requireNonNull(user).getname());
                year.setText(Objects.requireNonNull(user).getYear());
                roll.setText(Objects.requireNonNull(user).getRoll());
                phone.setText(Objects.requireNonNull(user).getPhone());
                section.setText(Objects.requireNonNull(user).getSection());
                if (user.getImageurl().equals("default")){
                    image_profile.setImageResource(R.drawable.defaultpic);
                } else {

                    if (!requireActivity().isFinishing()) {
                        Glide.with(image_profile.getContext()).load(user.getImageurl()).into(image_profile);
                    }
                }
                }else {

                    fuser = FirebaseAuth.getInstance().getCurrentUser();
                    ref = FirebaseDatabase.getInstance().getReference("Users").child("faculty").child(fuser.getUid());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if (dataSnapshot.exists()){
                               User user=dataSnapshot.getValue(User.class);

                               username.setText(Objects.requireNonNull(user).getname());
                               year.setText(Objects.requireNonNull(user).getYear());
                               roll.setText(Objects.requireNonNull(user).getRoll());
                               phone.setText(Objects.requireNonNull(user).getPhone());
                               section.setText(Objects.requireNonNull(user).getSection());
                               String k= Objects.requireNonNull(user).getImageurl();
                               if (k!=null&& !k.equals("")){
                               if (user.getImageurl().equals("default")){
                                   image_profile.setImageResource(R.drawable.defaultpic);
                               } else {

                                   if (!requireActivity().isFinishing()) {
                                       Glide.with(image_profile.getContext()).load(user.getImageurl()).into(image_profile);
                                   }
                               }
                               }

                               String position=user.getPosition();
                               if (position.equals("faculty")){
                                   lfy.setVisibility(View.VISIBLE);
                                   lfs.setVisibility(View.VISIBLE);
                               }else {
                                   lfy.setVisibility(View.GONE);
                                   lfs.setVisibility(View.GONE);
                               }
                           }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });



        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getActivity(),"Upload in Progress!",Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final  StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return  fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = Objects.requireNonNull(downloadUri).toString();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageurl", ""+mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
