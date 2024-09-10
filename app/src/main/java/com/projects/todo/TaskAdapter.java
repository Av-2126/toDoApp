package com.projects.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> tasks;

    public TaskAdapter(Context context,ArrayList<String> tasks){
        super(context,0,tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(tasks.get(position));
        return convertView;
    }
}
