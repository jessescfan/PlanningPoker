package com.example.simpleplanningpoker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view) {
        Button btn = (Button)view;
        String display = "display_number";
        Intent intent = new Intent(view.getContext(), FullscreenDisplayNumberActivity.class);
        intent.putExtra(display, btn.getText());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
