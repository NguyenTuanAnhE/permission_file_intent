package com.example.administrator.filedemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 4/22/2018.
 */

public class FileActivity extends AppCompatActivity {

    private TextView txtContent;
    private EditText edtContent;
    private Button btnOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        txtContent = findViewById(R.id.txt_content);
        edtContent = findViewById(R.id.edt_content);
        btnOk = findViewById(R.id.btn_ok);

        Intent intent = getIntent();
        int i = intent.getIntExtra("selection", 0);

        if (i > 0) {
            openFile();
            edtContent.setVisibility(View.INVISIBLE);
        } else {
            txtContent.setText("Nhập nội dung muốn thêm vào");
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFile(edtContent.getText().toString());
                finish();
            }
        });
    }

    void openFile() {
        try {
            FileInputStream in = this.openFileInput("new.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            txtContent.setText(buffer.toString());
            Log.d("read-data:", buffer.toString());

        } catch (Exception e) {
            Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void editFile(String content) {
        String fileName = "new.txt";

        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
