package com.example.cameratranslator.ui.fcset;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cameratranslator.R;
import com.example.cameratranslator.callback.StringCallback;
import com.example.cameratranslator.database.fcset.FCSet;

import java.util.List;
import java.util.Random;

import static com.example.cameratranslator.utils.ViewUtils.toast;

/**
 * Created by Duy M. Nguyen on 6/5/2020.
 */
public class FCSetAdapter extends RecyclerView.Adapter<FCSetAdapter.ViewHolder> {

    private Activity activity;
    private List<FCSet> fcSets;
    private StringCallback fcSetNameClickCallback;

    public FCSetAdapter(Activity activity, List<FCSet> fcSets, StringCallback fcSetNameClickCallback) {
        this.activity = activity;
        this.fcSets = fcSets;
        this.fcSetNameClickCallback = fcSetNameClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.item_fc_set, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setRandomColor();
        holder.setNameData(fcSets.get(position));
        holder.itemView.setOnClickListener(v -> fcSetNameClickCallback.execute(fcSets.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return fcSets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView tvSetName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView.findViewById(R.id.view);
            tvSetName = itemView.findViewById(R.id.tv_set_name);
        }

        public void setNameData(FCSet fcSet) {
            tvSetName.setText(fcSet.getName());
        }

        public void setRandomColor() {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            view.setBackgroundColor(color);
        }
    }
}
