package com.example.wiuh.nav.memo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wiuh.R;

public class MemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");
        String body = bundle.getString("body");

        TextView bulletinTitle = findViewById(R.id.memoTitle);
        TextView bulletinBody = findViewById(R.id.memoBody);

        bulletinTitle.setText(title);
        bulletinBody.setText(body);

    }
}