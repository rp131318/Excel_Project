package com.data.android.excelproject;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class takeAttendenceAdapter extends RecyclerView.Adapter<takeAttendenceAdapter.calenderVh> {

    private List<model> models;
    private Context context;

    public List<String> presentAbsentList = new ArrayList<>();

    public takeAttendenceAdapter(List<model> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public calenderVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new calenderVh(LayoutInflater.from(context).inflate(R.layout.calender_design, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final calenderVh holder, final int position) {
// adapter class will take data from of internal storage and show in screen...................
        model model = models.get(position);
        final Object number = model.getNumber();
//        Log.e("TAG", "onBindViewHolder: " + number);
//        Object present = model.getPresent();


        holder.tempText.setText(number.toString());
        holder.number.setBackgroundColor(Color.parseColor("#AAAAAA"));
        holder.number.setText("16_" + String.valueOf(position + 1));


        if (number.toString().equals("Present")) {
            holder.tempText.setText("Present");
            presentAbsentList.add("Present");
            holder.number.setBackgroundColor(Color.parseColor("#4CAF50"));
            Log.e("TAG", "onClick: absent");
            Log.e("TAG", "onClick: " + holder.tempText.getText());

        } else if (number.toString().equals("Absent")) {
            holder.tempText.setText("Absent");
            presentAbsentList.add("Absent");
            holder.number.setBackgroundColor(Color.parseColor("#F44336"));
            Log.e("TAG", "onClick: present");
            Log.e("TAG", "onClick: " + holder.tempText.getText());

        }else {
            presentAbsentList.add("No Data");
        }

        holder.number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.tempText.getText().toString()) {

                    case "No Data":
                        holder.tempText.setText("Present");
                        presentAbsentList.set(position, "Present");
                        Log.e("TAG", "onClick: " + holder.tempText.getText());
                        holder.number.setBackgroundColor(Color.parseColor("#4CAF50"));
                        Log.e("TAG", "onClick: no data");
                        break;
                    case "Present":
                        holder.tempText.setText("Absent");
                        presentAbsentList.set(position, "Absent");
                        holder.number.setBackgroundColor(Color.parseColor("#F44336"));
                        Log.e("TAG", "onClick: absent");
                        Log.e("TAG", "onClick: " + holder.tempText.getText());
                        break;
                    case "Absent":
                        holder.tempText.setText("Present");
                        presentAbsentList.set(position, "Present");
                        holder.number.setBackgroundColor(Color.parseColor("#4CAF50"));
                        Log.e("TAG", "onClick: present");
                        Log.e("TAG", "onClick: " + holder.tempText.getText());
                        break;


                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class calenderVh extends RecyclerView.ViewHolder {
        TextView number;
        TextView tempText;

        public calenderVh(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.info_text);
            tempText = itemView.findViewById(R.id.tempText);
        }
    }
}
