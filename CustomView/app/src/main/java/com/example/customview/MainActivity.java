package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private InvalidTextView iv_text;
    private  RectView rv_rect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_text= (InvalidTextView) this.findViewById(R.id.iv_text);
        rv_rect= (RectView) this.findViewById(R.id.rv_rect);
        iv_text.setText("皇家马德里 C罗");
    }
}