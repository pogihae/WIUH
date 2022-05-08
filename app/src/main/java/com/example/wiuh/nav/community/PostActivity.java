package com.example.wiuh.nav.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wiuh.R;
import com.example.wiuh.nav.community.PostModify;
import com.example.wiuh.util.FirebaseUtil;

public class PostActivity extends AppCompatActivity {

    private int REQUEST_CODE = 10;
    private int RESULT_OK = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");
        String body = bundle.getString("body");
        String author = bundle.getString("author");
        String uid = bundle.getString("uid");
        String key = bundle.getString("key");

        TextView bulletinTitle = findViewById(R.id.bulletinTitle);
        TextView bulletinBody = findViewById(R.id.bulletinBody);
        TextView bulletinAuth = findViewById(R.id.bulletinAuth);
        TextView bulletinUid = findViewById(R.id.bulletinUid);

        bulletinTitle.setText(title);
        bulletinBody.setText(body);
        bulletinAuth.setText(author);
        bulletinUid.setText(uid);

        Button delButton = findViewById(R.id.btn_delpost);
        Button modButton = findViewById(R.id.btn_modpost);

        if(!isAuthor(uid)) {
            delButton.setVisibility(View.INVISIBLE);
            modButton.setVisibility(View.INVISIBLE);
        }

        delButton.setOnClickListener(view -> {
            FirebaseUtil.getPostRef()
                    .child(key)
                    .removeValue();
            finish();
        });

        modButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PostModify.class);
                Bundle bundle = new Bundle();

                bundle.putString("title", title);
                bundle.putString("body", body);
                bundle.putString("key", key);

                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE);            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Bundle resultBundle = data.getExtras();
                String resultTitle = resultBundle.getString("resultTitle");
                String resultBody = resultBundle.getString("resultBody");

                TextView title = findViewById(R.id.bulletinTitle);
                TextView body = findViewById(R.id.bulletinBody);

                title.setText(resultTitle);
                body.setText(resultBody);

            }
            else {   // RESULT_CANCEL
                Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isAuthor(String uid) {
        String curUserUid = FirebaseUtil.getCurUser().getUid();
        return uid.equals(curUserUid);
    }
}