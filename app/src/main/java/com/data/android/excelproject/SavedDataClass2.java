package com.data.android.excelproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedDataClass2 extends RecyclerView.Adapter<SavedDataClass2.savedDataAdapter> {
    private List<String> dateList;

    public SavedDataClass2(List<String> dateList, List<String> yrList) {
        this.dateList = dateList;
        this.yrList = yrList;
    }

    private List<String> yrList;
    private Context context;


    @NonNull
    @Override
    public savedDataAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SavedDataClass2.savedDataAdapter(LayoutInflater.from(context).inflate(R.layout.saved_data_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull savedDataAdapter holder, final int position) {
        holder.date.setText(dateList.get(position));
        holder.yr.setText(yrList.get(position));


    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public class savedDataAdapter extends RecyclerView.ViewHolder {
        RelativeLayout clickLauout;
        TextView date, yr;

        public savedDataAdapter(@NonNull View itemView) {
            super(itemView);
            clickLauout = itemView.findViewById(R.id.clickLayout);
            date = itemView.findViewById(R.id.dateText);
            yr = itemView.findViewById(R.id.yrText);

        }
    }
}
