package com.prikhodko.yasha.assignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yasha on 14-11-07.
 */
public class NoteAdapter extends ArrayAdapter<Note> {
    private final Context context;
    private final List<Note> objects;

    public NoteAdapter(Context context, List<Note> objects) {
        super(context, R.layout.note_cell, objects);

        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.note_cell, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView time = (TextView) rowView.findViewById(R.id.time);

        String titleText = objects.get(position).getText();
        title.setText(titleText);
        time.setText(objects.get(position).getTime());

        return rowView;
    }
}
