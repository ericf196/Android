package com.optimussoftware.boohos.account;

/**
 *
 * Created william.castillo@optimus-software.com on 15/06/16.
 */
public class BoohosToken {

    private String _id;
    private String token;

    public BoohosToken(){

    }

    public BoohosToken(String _id, String token){
        this.set_id(_id);
        this.setToken(token);
    }


    public String get_id() {
        return _id;
    }

    @SuppressWarnings("WeakerAccess")
    public void set_id(String _id) {
        this._id = _id;
    }

    public String getToken() {
        return token;
    }

    @SuppressWarnings("WeakerAccess")
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString(){
        return "UID: " + _id +"\nToken: " + token;
    }
}
