package com.optimussoftware.boohos.data;


import android.app.Activity;
import android.content.Context;
import com.optimussoftware.db.User;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.util.PreferenceManager;


/**
 *
 * Created by Created by william.castillo@optimus-software.com on 10/05/16.
 */
public class PersonalInfo {

    private String uuid;
    private User user;
    private Context context;
    private String gender = null;
    private PreferenceManager preferenceManager = null;

    public PersonalInfo(Activity activity){
        context = activity.getBaseContext();
        preferenceManager = new PreferenceManager(context);
        String uid = preferenceManager.getString("uuid", null);
        DBController controller = DBController.getControler();
        user = controller.getUser(context, uid);
    }

    public PersonalInfo(PreferenceManager preferenceManager){
        this.preferenceManager = preferenceManager;
        String uid = preferenceManager.getString("uuid", null);
        DBController controller = DBController.getControler();
        user = controller.getUser(context, uid);
    }

    public String getEmail() {
        if(user != null)
            return user.getEmail();
        return null;
    }

    private String getSocial_id() {
        if(user != null)
            return user.getSocial_id();
        return null;
    }

    public String getFull_name() {
        if(user != null)
            return user.getFull_name();
        return null;
    }
    public String getFirst_name() {
        if(user != null)
            return user.getFirst_name();
        return null;
    }
    public String getLast_name() {
        if(user != null)
            return user.getLast_name();
        return null;
    }

    public String getPhone() {
        if(user != null)
            return user.getPhone();
        return null;
    }

    public String getSource() {
        if(user != null)
            return user.getSource();
        return null;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;

    }

    public String getUuid() {
        if(user != null)
            return user.get_id();
        return null;
    }

    public String getPicture() {
        if(user != null)
            return user.getProfile_photo();
        return null;
    }

    public String getGender() {
        if(user != null)
            return user.getGender();
        return null;
    }

    public String getLocation() {
        if(user != null)
            return user.getLocation();
        return null;
    }

    public String getBirthday() {
        if(user != null)
            return Commons.dateToString(user.getBirthday(), context);
        return null;
    }

    public void writeInfo(){
        preferenceManager.putString("uuid", uuid);
    }

    public String toString(){
        String text = "";
        text+= "E-mail: " + getEmail() + "\n";
        text+= "social_id: " + getSocial_id() + "\n";
        text+= "Fullname: " + getFull_name() + "\n";
        text+= "Phone: " + getPhone() + "\n";
        text+= "Source: " + getSource() + "\n";
        text+= "UUID: " + getUuid() + "\n";
        text+= "Picture: " + getPicture() + "\n";
        text+= "Gender: " + getGender() + "\n";
        text+= "Location: " + getLocation() + "\n";
        text+= "Birthday: " + getBirthday() + "\n";
        return text;
    }
}
