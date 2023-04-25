package com.project.shared_card.model.activity.main_screen.check.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.project.shared_card.R;

public class AdapterForSpinner extends ArrayAdapter<String> {

    String[] list;

    public AdapterForSpinner(@NonNull Context context, @NonNull String[] objects) {
        super(context, 0, objects);
        this.list = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.spinner_dropdown, parent, false);
        TextView textView =  view.findViewById(R.id.spinner_text);
        textView.setText(list[position]);
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.spinner_head, parent, false);
        TextView textView =  view.findViewById(R.id.spinner_text);
        textView.setText(list[position]);
        return view;
    }
}
