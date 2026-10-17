package com.example.iot_msiia;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SolutionAdapter extends RecyclerView.Adapter<SolutionAdapter.ViewHolder> {

    private List<Solution> solutionList;

    // إضافة Listener
    private OnSolutionCheckedChangeListener listener;

    public SolutionAdapter(List<Solution> solutionList, OnSolutionCheckedChangeListener listener) {
        this.solutionList = solutionList;
        this.listener = listener;


}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solution, parent, false);
        return new ViewHolder(view);
    }
    public void setOnSolutionCheckedChangeListener(OnSolutionCheckedChangeListener listener) {
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Solution solution = solutionList.get(position);
        holder.solutionName.setText(solution.getName());
        holder.roomNumber.setText("Room " + solution.getRoomNumber());
        Log.d("RecyclerView Data", "Displaying: " + solution.getName() + ", Room: " + solution.getRoomNumber());
        // أيقونة الحالة On/Off
        if (solution.isActive()) {
            holder.statusIcon.setImageResource(R.drawable.baseline_album_24);
        } else {
            holder.statusIcon.setImageResource(R.drawable.baseline_error_24);
        }

        // التشيك بوكس
        holder.checkBox.setChecked(solution.isChecked());

        // إضافة Listener لتغيير الحالة عند التحديد أو الإلغاء
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            solution.setChecked(isChecked); // تغيير حالة العنصر في الـ Solution
            if (listener != null) {
                listener.onSolutionCheckedChange(solution);
            }
        });
    }

    @Override
    public int getItemCount() {
        return solutionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView statusIcon;
        TextView solutionName, roomNumber;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            statusIcon = itemView.findViewById(R.id.statusIcon);
            solutionName = itemView.findViewById(R.id.solutionName);
            roomNumber = itemView.findViewById(R.id.roomNumber);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    // Interface للتعامل مع التغييرات
    public interface OnSolutionCheckedChangeListener {
        void onSolutionCheckedChange(Solution solution);
    }
}