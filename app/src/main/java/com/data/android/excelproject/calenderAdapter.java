package com.data.android.excelproject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class calenderAdapter extends RecyclerView.Adapter<calenderAdapter.calenderVh> {

    private List<model> models;
    private Context context;

    public calenderAdapter(List<model> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public calenderVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new calenderVh(LayoutInflater.from(context).inflate(R.layout.calender_design, null));
    }

    @Override
    public void onBindViewHolder(@NonNull calenderVh holder, int position) {
// adapter class will take data from of internal storage and show in screen...................
        model model = models.get(position);
        Object number = model.getNumber();
        Object present = model.getPresent();

        holder.number.setText(number.toString());
        if (present.equals("P")) {
            holder.number.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else if (present.equals("A")) {
            holder.number.setBackgroundColor(Color.parseColor("#F44336"));
        }


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class calenderVh extends RecyclerView.ViewHolder {
        TextView number;

        public calenderVh(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.info_text);
        }
    }
}