package com.example.ani.noteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.IOException;

public class NoteActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editText = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        editText.setText(MainActivity.arrayList.get(intent.getIntExtra("noteNumber", 0)));
        editText.setSelection(editText.getText().length());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Intent intent = getIntent();
                if(editText.getText().toString().equals(""))
                    MainActivity.arrayList.remove(intent.getIntExtra("noteNumber", 0));
                else
                    MainActivity.arrayList.set(intent.getIntExtra("noteNumber", 0), editText.getText().toString());
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = NoteActivity.this.getSharedPreferences("com.example.ani.noteapp", Context.MODE_PRIVATE);
                try {
                    sharedPreferences.edit().putString("noteList", ObjectSerializer.serialize(MainActivity.arrayList)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
