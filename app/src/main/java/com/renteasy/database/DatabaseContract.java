package com.renteasy.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by RUPESH on 8/14/2016.
 */
public class DatabaseContract {

    public static final String ADDRESS_TABLE = "address_table";

    public static final class address_table implements BaseColumns {
        public static final String LINE1 = "line1";
        public static final String LINE2="line2";
        public static final String LINE3="line3";
        public static final String LINE4="line4";
        public static final String PRIMARY_ADDRESS_FLAG="primary_address_flag";

        public static final String ADDRESS_PATH = "address";
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ADDRESS_PATH;

        public static Uri buildAddressUri() {
            return BASE_CONTENT_URI.buildUpon().appendPath("address").build();
        }
    }


    public static final String CONTENT_AUTHORITY = "com.renteasy";


   /* static final String URL = "content://" + CONTENT_AUTHORITY + "/"+ADDRESS_PATH;
    public static final Uri ADDRESS_CONTENT_URI = Uri.parse(URL);
*/
    public static Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

}
