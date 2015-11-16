package com.example.thainara.personalcontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by thainara on 27/10/15.
 */
public class ExercicioAdapter extends ArrayAdapter<Exercicio> {

    private static final int LAYOUT_ID = android.R.layout.simple_list_item_1;

    public ExercicioAdapter(Context context, List<Exercicio> exercicios) {
        super(context, LAYOUT_ID);
        addAll(exercicios);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null)
            v = LayoutInflater.from(getContext()).inflate(LAYOUT_ID, parent, false);

        TextView tv = (TextView)v;
        tv.setText(getItem(position).nome);
        tv.setBackgroundColor(getItem(position).selected ? getContext().getResources().getColor(R.color.primary) : getContext().getResources().getColor(android.R.color.transparent));

        return tv;
    }

    public Exercicio getSelected() {
        for(int i = 0; i < getCount(); i++)
            if (getItem(i).selected)
                return getItem(i);
        return null;
    }

    public void unselectAll() {
        for(int i = 0; i < getCount(); i++)
            getItem(i).selected = false;
    }
}
