package com.example.cameratranslator.ui.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cameratranslator.R;
import com.example.cameratranslator.callback.ItemClickCallback;
import com.example.cameratranslator.model.LocalizedObjectAnnotation;

import java.util.List;

/**
 * Created by Duy M. Nguyen on 5/18/2020.
 */
public class ItemObjectAdapter extends RecyclerView.Adapter<ItemObjectAdapter.ViewHolder> {

    private Context context;
    private List<LocalizedObjectAnnotation> localizedObjectAnnotations;
    @Nullable
    private ItemClickCallback itemClickCallback;

    private int selectPosition = -1;

    public ItemObjectAdapter(Context context, List<LocalizedObjectAnnotation> localizedObjectAnnotations, @Nullable ItemClickCallback itemClickCallback) {
        this.context = context;
        this.localizedObjectAnnotations = localizedObjectAnnotations;
        this.itemClickCallback = itemClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_object_label, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocalizedObjectAnnotation object = localizedObjectAnnotations.get(position);

        holder.setColors(position);
        holder.setData(object);

        holder.itemView.setOnClickListener(v -> {
            setSelectPosition(position);
            if (itemClickCallback != null)
                itemClickCallback.onItemClick(selectPosition);
        });
    }

    @Override
    public int getItemCount() {
        return localizedObjectAnnotations.size();
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvLabel;
        private ProgressBar progressBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLabel = itemView.findViewById(R.id.tv_label);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }

        void setColors(int position) {
            if (position == selectPosition) {
                tvLabel.setBackground(context.getDrawable(R.drawable.button_press_primary_light));
                tvLabel.setTextColor(context.getColor(R.color.colorPrimaryDark));
            } else {
                tvLabel.setBackground(context.getDrawable(R.drawable.button_press_primary_dark));
                tvLabel.setTextColor(context.getColor(R.color.colorWhite));
            }
        }

        void setData(LocalizedObjectAnnotation localizedObjectAnnotation) {
            tvLabel.setText(localizedObjectAnnotation.getTranslation());
        }
    }
}
