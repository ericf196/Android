package com.optimussoftware.api.response;

/**
 *
 * Created by Created by william.castillo@optimus-software.com on 14/06/16.
 */
public class GenericResponse {

    private String _status;
    private String _created;
    private String _etag;
    private String _updated;
    private String _id;

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_created() {
        return _created;
    }

    public void set_created(String _created) {
        this._created = _created;
    }

    public String get_etag() {
        return _etag;
    }

    public void set_etag(String _etag) {
        this._etag = _etag;
    }

    public String get_updated() {
        return _updated;
    }

    public void set_updated(String _updated) {
        this._updated = _updated;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
