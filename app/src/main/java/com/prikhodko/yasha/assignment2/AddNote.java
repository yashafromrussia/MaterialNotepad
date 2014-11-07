package com.prikhodko.yasha.assignment2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class AddNote extends ActionBarActivity {

    private EditText text;
    private Boolean editMode = false;
    private int notePos;
    private boolean deleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text = (EditText) findViewById(R.id.noteText);

        try{
            notePos = getIntent().getExtras().getInt("pos");
            Note note = MainActivity.adapter.getItem(notePos);
            text.setText(note.getText());

            editMode = true;
            getSupportActionBar().setTitle("Edit Note");
        } catch(Exception e) {
            // no noteid set.. so its a new note
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {

        if (deleted) {
            super.onDestroy();
            return;
        }

        if (!editMode) {
            if (text.getText().length() > 0) {
                Note note = MainActivity.datasource.createNote(text.getText().toString());
                MainActivity.allNotes.add(0, note);
                MainActivity.adapter.notifyDataSetChanged();
            }
        } else {
            Note note = MainActivity.allNotes.get(notePos);
            note.setText(text.getText().toString());
            note.updateTime();
            MainActivity.datasource.updateNote(note);
            MainActivity.allNotes.remove(note);
            MainActivity.allNotes.add(0, note);
        }

        MainActivity.adapter.notifyDataSetChanged();

        super.onDestroy();
    }

    private void deleteNote() {

        if (editMode) {
            Note note = MainActivity.allNotes.get(notePos);
            MainActivity.allNotes.remove(note);
            MainActivity.datasource.deleteNote(note);
            MainActivity.adapter.notifyDataSetChanged();
        }

        deleted = true;
        finish();
    }
}
