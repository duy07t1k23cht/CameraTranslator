package com.example.cameratranslator.ui.pickimage;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cameratranslator.R;
import com.example.cameratranslator.navigation.Navigation;
import com.example.cameratranslator.utils.BitmapUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Duy M. Nguyen on 5/24/2020.
 */
public class ItemImageAdapter extends RecyclerView.Adapter<ItemImageAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<String> imagePaths;

    public ItemImageAdapter(Activity activity, ArrayList<String> imagePaths) {
        this.activity = activity;
        this.imagePaths = imagePaths;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imagePath = imagePaths.get(position);

        holder.setImageData(imagePath);
        holder.itemView.setOnClickListener(v -> Navigation.toObjectDetectionActivity(activity, imagePath));
    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
        }

        void setImageData(String imagePath) {
            Glide
                    .with(activity)
                    .load(new File(imagePath))
                    .into(imageView);
        }
    }
}
