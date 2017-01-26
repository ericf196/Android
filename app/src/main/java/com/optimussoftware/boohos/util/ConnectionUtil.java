package com.optimussoftware.boohos.util;

/**
 * Created by guerra on 01/11/16.
 * ConnectionUtil
 */

public class ConnectionUtil {

    public static OnChangeConnection onChangeConnection;

    public static void setOnChangeConnection(OnChangeConnection onChangeConnection) {
        ConnectionUtil.onChangeConnection = onChangeConnection;
    }

    public static void setOnLine() {
        if (onChangeConnection != null) {
            onChangeConnection.onLine();
        }
    }

    public static void setOffLine() {
        if (onChangeConnection != null) {
            onChangeConnection.offLine();
        }
    }

    public static interface OnChangeConnection {
        void onLine();

        void offLine();
    }
}
