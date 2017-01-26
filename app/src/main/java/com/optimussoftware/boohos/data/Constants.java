/**
 * Odoo, Open Source Management Solution
 * Copyright (C) 2012-today Odoo SA (<http:www.odoo.com>)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details
 * <p/>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http:www.gnu.org/licenses/>
 * <p/>
 * Created on 18/12/14 11:28 AM
 */
package com.optimussoftware.boohos.data;

public class Constants {

    /**
     * Constants for behavours
     */

    public static final String KEY_FRESH_LOGIN = "key_fresh_login";
    public static final String KEY_FACEBOOK_LOGIN = "facebook_login";
    public static final String KEY_GOOGLE_LOGIN = "google_login";
    public static final String KEY_SELF_LOGIN = "self_login";
    public static final String SOURCE_FACEBOOK = "facebook";
    public static final String SOURCE_GOOGLE_PLUS = "google+";
    public static final String SOURCE_SELF = "self";
    public static final String SOURCE = "source";
    public static final String TOKEN_API = "token_api";
    public static final String TOKEN_FACEBOOK = "token_facebook";
    public static final String TOKEN_GOOGLE_PLUS = "token_google+";
    public static final String ACCOUNT_NAME = "account_name";
    public static final String USER_ID_FIELD = "user_id";
    public static final String LASTED_SYNC_INTERST = "lasted_sync_interest";

    public static final String GOOGLE_CLIENT_ID = "907089067454-a2q9p0vsrop7v3be73h38bb8sqbm9btb.apps.googleusercontent.com";
    public static final String GOOGLE_CLIENT_SECRET = "nGTNU0xcz3THFsEgvpA0oug9";


    public static final int TYPE_DEVICE_BEACON = 0;
    public static final int TYPE_DEVICE_NERABLE = 1;
    public static final int TYPE_DEVICE_EDDYSTONE = 2;
    public static final int TYPE_DEVICE_CONFIGURABLE = 3;

    /**
     * Auth constants
     */
    public static final int CODE_SLIDER_INTRO = 101;
    public static final int CODE_START_LOGIN = 102;
    public static final int CODE_ACCESS_REQUEST = 103;
    public static final int CODE_ACCESS_REQUEST_SOCIAL = 104;
    public static final int CODE_ACCESS_GRANTED = 105;
    public static final int CODE_ACCESS_DENIED = 106;
    public static final int CODE_START_APP = 107;

    /**
     * Beacons
     */
    public static final String BEACONS_TIME_DELAY = "beacons_time_delay";


    /**
     * Image's Service
     */
    //public static final String URL_WEB = "http://integration.optimus-software.com/boohosimage/";
    public static final String URL_WEB = "http://35.162.49.177:5004/v1/uploads/";
    public static final String URL_WEB_THUMBS = "http://35.162.49.177:5004/v1/uploads/thumbs/";

    /**
     * API Service
     */
    //public static final String API_URI = "http://192.168.0.108:5003/v1/";
    //public static final String API_URI = "http://integration.optimus-software.com:5003/v1/";
    //public static final String API_URI = "http://integration.optimus-software.com:5004/v1/";
    public static final String API_URI = "http://35.162.49.177:5004/v1/";

    /**
     * DBname
     */
    public static final String DBNAME = "com.optimussofware.boohos.sqlite";

    /**
     * Directory
     */
    public static final String DIRECTORY_BASE = "/Boohos";
    private static final String DIRECTORY_MEDIA = "/Media";
    public static final String DIRECTORY_MEDIA_IMAGES = DIRECTORY_MEDIA + "/Boohos Images";

    /**
     * File Name
     */
    public static final String IMAGE_NAME_BASE = "IMG-";

    /**
     * Boohos Token
     */

    public static final String BOOHOS_TOKEN = "eyJhbGciOiJIUzI1NiIsImlhdCI6MTQ2NTkyNDcyMSwiZXhwIjoyMzI5OTI0NzIxfQ.eyJyZWdpc3RlciI6IlNpZ25VcC1Nb3ZpbC1FdmUmQW5kcm9pZCZJT1MifQ.Ll0Gnsl4cLYbhl9ylavQFgdED0NPSj3lD5nml3fc0ns";

    /**
     * Signup
     */
    public static final int SIGN_UP = 1001;

    /**
     * Result successful signup
     */
    public static final int SIGN_UP_OK = 1002;

    /**
     * Signup return action user
     */
    public static final int SIGN_UP_BACK = 1003;

    /**
     * Result successful signup
     */
    public static final int EDIT_PROFILE = 1004;

    /**
     * Result successful signup
     */
    public static final int EDIT_PROFILE_OK = 1005;

    /**
     * Signup with google
     */
    public static final int SIGN_UP_GOOGLE = 2001;

    /**
     * Notification
     */
    public static final int NOTIFICATION_VIEWED = 3000;

    /**
     * Review
     */
    public static final int REVIEW_RESULT = 200;

    /**
     * Date format for json
     */
    public static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    //public static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";

    /**
     * Date format for json
     */
    public static final String DATE_FORMAT_WRITE = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * SyncAdapter
     */

    public static final String ACTION_FINISHED_SYNC = "com.optimussoftware.hermeapp.account.sync.SyncAdapter";
}
