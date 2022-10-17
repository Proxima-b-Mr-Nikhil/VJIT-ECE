package com.nikhil.vjitece;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class feedAdapter extends RecyclerView.Adapter<feedAdapter.ImageViewHolder> {

    private List<feedUpload> mUploads;

    public feedAdapter(Context context, List<feedUpload> uploads) {

        mUploads = uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_feeditem, parent, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int i) {
        feedUpload uploadCurrent = mUploads.get(i);

if (uploadCurrent.getName().equals("default")) {
}   else {
    holder.textViewName.setText(uploadCurrent.getName());
}
        holder.imgholder.setVisibility(View.VISIBLE);
        holder.imageView.setVisibility(View.GONE);
        holder.texttime.setText(uploadCurrent.getTime());
        if (uploadCurrent.getImageUrl().equals("default")){

        }else {
            Picasso.get()

                    .load(uploadCurrent.getImageUrl())
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.imageView.setVisibility(View.VISIBLE);
                        holder.imgholder.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }

                    });
        }
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView texttime;

        public ImageView imageView,imgholder;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            texttime = itemView.findViewById(R.id.tim);
            imageView = itemView.findViewById(R.id.image_view_upload);
            imgholder = itemView.findViewById(R.id.holder);

        }
    }
}
