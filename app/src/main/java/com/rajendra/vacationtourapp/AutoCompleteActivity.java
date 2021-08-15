package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class AutoCompleteActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    TextView textView;
    String[] strings = {"one","two","three","four"};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);
        autoCompleteTextView = findViewById(R.id.ac_text_view);
        textView = findViewById(R.id.text_view);
        adapter =new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,strings);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText(adapter.getItem(i));
            }
        });
    }
}