package com.example.AssidApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class scanAdapter extends RecyclerView.Adapter<scanAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<savescan> saveModelList;

    public scanAdapter(Context context, ArrayList<savescan> s){
        this.context = context;
        saveModelList = s;
    }

    public void add(savescan savescan){
        saveModelList.add(savescan);
        notifyDataSetChanged();
    }

    public  void clear(){
        saveModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public scanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.save_scan,parent,false);
        return new scanAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull scanAdapter.MyViewHolder holder, int position) {
        savescan savescan = saveModelList.get(position);
        holder.text.setText(savescan.getSaves());
        holder.text.setText(savescan.getDate());
    }

    @Override
    public int getItemCount() {
        return saveModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView text, date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textview);
            date = itemView.findViewById(R.id.date);
        }
    }
}
