
package com.optimussoftware.api.response.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class Links {

    @SerializedName("last")
    @Expose
    private Last last;
    @SerializedName("next")
    @Expose
    private Next next;
    @SerializedName("parent")
    @Expose
    private Parent parent;
    @SerializedName("self")
    @Expose
    private Self self;

    /**
     * 
     * @return
     *     The last
     */
    public Last getLast() {
        return last;
    }

    /**
     * 
     * @param last
     *     The last
     */
    public void setLast(Last last) {
        this.last = last;
    }

    /**
     * 
     * @return
     *     The next
     */
    public Next getNext() {
        return next;
    }

    /**
     * 
     * @param next
     *     The next
     */
    public void setNext(Next next) {
        this.next = next;
    }

    /**
     * 
     * @return
     *     The parent
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * 
     * @param parent
     *     The parent
     */
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    /**
     * 
     * @return
     *     The self
     */
    public Self getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(Self self) {
        this.self = self;
    }

}
