package com.nikhil.vjitece.ui.home;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.nikhil.vjitece.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<UploadHome> mUploads;

    public ImageAdapter(Context context, List<UploadHome> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int i) {
        UploadHome uploadCurrent = mUploads.get(i);
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .config(Bitmap.Config.ARGB_4444)
                .centerCrop()
                .fit()
                .noFade()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {


                    }

                    @Override
                    public void onError(Exception e) {

                    }

                });
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.image_view_upload);

        }
    }

}
