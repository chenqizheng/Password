package com.yonyou.password.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Chen on 2017/1/6.
 */

public class Password {

    public static final String AUTHORITY = "com.yonyou.password.provider";


    public static final class PasswordTable implements BaseColumns {

        public static final String TABLE_NAME = "password";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME + "/");
        // 表数据列
        public static final String NAME = "name";

        public static final String PASSWORD = "password";

    }
}
