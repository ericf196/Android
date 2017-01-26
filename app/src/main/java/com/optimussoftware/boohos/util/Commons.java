package com.optimussoftware.boohos.util;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.optimussoftware.boohos.App;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.widget.OptimusSnackBar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


/**
 * Created by Created by william.castillo@optimus-software.com on 24/03/16.
 */
public class Commons {

    public static final String formatoDefectoFecha = "yyyy-MM-dd HH:mm:ss";

    /***
     * This method is a generic way to get the relation APP-Class for debug app
     *
     * @param context
     * @param clase
     * @return
     */
    public static String getTag(Context context, Class clase) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId) + ":" + clase.getName();
    }

    public static String getLang() {
        /*
        Locale.getDefault().getLanguage()       ---> en
        Locale.getDefault().getISO3Language()   ---> eng
        Locale.getDefault().getCountry()        ---> US
        Locale.getDefault().getISO3Country()    ---> USA
        Locale.getDefault().getDisplayCountry() ---> United States
        Locale.getDefault().getDisplayName()    ---> English (United States)
        Locale.getDefault().toString()          ---> en_US
        Locale.getDefault().getDisplayLanguage()---> English*/
        return Locale.getDefault().getLanguage();
    }

    public static String getNumberPhone(Activity activity) {
        TelephonyManager telemamanger =
                (TelephonyManager) activity.getSystemService(activity.getApplicationContext().TELEPHONY_SERVICE);
        String mPhoneNumber = telemamanger.getLine1Number();
        if (mPhoneNumber == null || mPhoneNumber.compareTo("") == 0) {
            //mPhoneNumber = telemamanger.getSimSerialNumber();
            mPhoneNumber = "";
        }
        return mPhoneNumber;
    }

    public static String getEmail(Context context) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                return account.name;
            }
        }
        return null;
    }

    public static void setTextToTextView(TextView textView, String text) {
        textView.setText(text);
    }

    public static void makeDialogInformation(Activity activity, String msj) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.app_name));
        builder.setMessage(msj);
        if (Build.VERSION.SDK_INT < 21)
            builder.setIcon(activity.getResources().getDrawable(R.drawable.ic_notification));
        else
            builder.setIcon(activity.getDrawable(R.drawable.ic_notification));
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }

    @SuppressLint("SimpleDateFormat")
    public static Date parseDate(String str) {
        return getDateString(str);
    }

    @SuppressLint("SimpleDateFormat")
    public static Date parseDateTimeline(String strFecha) {

        strFecha = strFecha.replaceAll("T", " ");
        SimpleDateFormat formatoDelTextoConHora = new SimpleDateFormat(formatoDefectoFecha);
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = formatoDelTextoConHora.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
            try {
                fecha = formatoDelTexto.parse(strFecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return fecha;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateString(String strFecha) {
        String format = "EEE, dd MMM yyyy HH:mm:ss z";
        String date = strFecha;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        try {
            Date dt = sdf.parse(date);
            System.out.println(dt);
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date parseDateGMT(String str) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);//test idioma español
            //formatter = new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss Z");
            return formatter.parse(str);
        } catch (ParseException e) {
            Log.e("parseDateGMT", "parseDateGMT str " + str);
            Log.e("parseDateGMT", "parseDateGMT Exc " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date parseDateFacebook(String str) {
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            return df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date d, Context context) {
        if (d != null) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            return df.format(d);
        }
        return context.getResources().getString(R.string.undefined);
    }

    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date d, String format, Context context) {
        if (d != null) {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(d);
        }
        return context.getResources().getString(R.string.undefined);
    }

    public static String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss",
                java.util.Locale.getDefault());
        return df.format(c.getTime());
    }

    /*Set app font to any TextView*/
    public static void setTypeFace(TextView tzt) {
        tzt.setTypeface(App.optimusFontDefault);
    }

    /*Set app font to any EditText*/
    public static void setTypeFace(EditText ed) {
        ed.setTypeface(App.optimusFontDefault);
    }

    /*Set app font to any Button*/
    public static void setTypeFace(Button button) {
        button.setTypeface(App.optimusFontDefault);
    }

    /*DPIs to Pixeles*/
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /*Pixeles to DPIs*/
    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /*Width Display pixelex*/
    public static int getWidthDisplay(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /*Height Display pixeles*/
    public static int getHeightDisplay(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /*Copi text clipboard*/
    public static boolean copyClipboard(Context context, String labelToCopy, String textToCopy) {
        try {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (textToCopy != null) {
                ClipData clip = ClipData.newPlainText(labelToCopy, textToCopy.replace("\\n", System.getProperty("line.separator")));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, R.string.text_copied, Toast.LENGTH_LONG).show();
                return true;
            } else {
                Toast.makeText(context, R.string.text_not_copied, Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, R.string.text_not_copied, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /*Check connection*/
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public static String getEfectiveDaysCampaign(List<String> efectiveDays) {
        boolean firts = true;
        String days = "";
        for (String day : efectiveDays) {
            days += firts ? day : "~" + day;

            firts = false;
        }
        return days;
    }

}
