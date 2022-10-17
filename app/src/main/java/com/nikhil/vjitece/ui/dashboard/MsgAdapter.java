package com.nikhil.vjitece.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikhil.vjitece.R;

import java.util.List;


public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    public static  final int MSG_TYPE_RIGHT = 1;

    MsgAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item_contactleft, parent, false);

        return new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int i) {
        Upload uploadCurrent = mUploads.get(i);

        holder.textViewName.setText(uploadCurrent.getMessage());


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;


        ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.show_message);


        }
    }
}
