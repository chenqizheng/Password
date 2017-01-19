package com.yonyou.password.ui.password;

import android.content.ContentUris;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yonyou.password.R;
import com.yonyou.password.provider.Password;

public class PasswordDetail extends AppCompatActivity {

    public static final String EXTRA_STRING_ID = "id";
    private TextView idText;
    private TextView passwordNameText;
    private TextView passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.design.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        idText = (TextView) findViewById(R.id.text_id);
        passwordNameText = (TextView) findViewById(R.id.text_name);
        passwordText = (TextView) findViewById(R.id.text_password);
        String idStr = getIntent().getStringExtra(EXTRA_STRING_ID);
        long id = Long.parseLong(idStr);

        Cursor cursor = getContentResolver().query(ContentUris.withAppendedId(Password.PasswordTable.CONTENT_URI, id), null
                , null, null, null);
        if (cursor != null) {
            cursor.moveToNext();
            String passwordId = cursor.getString(cursor.getColumnIndex(Password.PasswordTable._ID));
            String passwordName = cursor.getString(cursor.getColumnIndex(Password.PasswordTable.NAME));
            String password = cursor.getString(cursor.getColumnIndex(Password.PasswordTable.PASSWORD));
            PasswordList.Password passwordBean = new PasswordList.Password(passwordId, passwordName, password);
            bindBean(passwordBean);
        }

    }

    private void bindBean(PasswordList.Password passwordBean) {
        if (passwordBean == null) {
            return;
        }
        idText.setText("id : " + passwordBean.id);
        passwordNameText.setText("name : " + passwordBean.name);
        passwordText.setText("password : " + passwordBean.password);
    }
}
