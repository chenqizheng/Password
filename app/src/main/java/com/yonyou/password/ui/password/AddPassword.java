package com.yonyou.password.ui.password;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yonyou.password.R;
import com.yonyou.password.provider.Password;

public class AddPassword extends AppCompatActivity {

    private Button saveButton;
    private EditText nameEdit;
    private EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.design.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveButton = (Button) findViewById(R.id.save);
        nameEdit = (EditText) findViewById(R.id.edit_name);
        passwordEdit = (EditText) findViewById(R.id.edit_password);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                ContentValues values = new ContentValues();
                values.put(Password.PasswordTable.NAME, name);
                values.put(Password.PasswordTable.PASSWORD, password);
                getContentResolver().insert(Password.PasswordTable.CONTENT_URI, values);
                Toast.makeText(AddPassword.this, "新增成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
