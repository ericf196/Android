package com.optimussoftware.boohos.data;

import android.content.Context;

import com.optimussoftware.boohos.util.PreferenceManager;

/**
 * Created by guerra on 11/11/16.
 * Status to show tutorial screen.
 */

public class InfoShowCaseView {

    private String SHOWED_SCREEN_TOOLBAR = "showed_screen_toolbar";
    private String SHOWED_SCREEN_FLOATING_BUTTON = "showed_screen_floating_button";
    private String SHOWED_SCREEN_EDIT_BUTTON = "showed_screen_edit_button";
    private String SHOWED_SCREEN_REVIEW_BUTTON = "showed_screen_review_button";

    private PreferenceManager preferenceManager;

    /**
     * @param context
     */
    public InfoShowCaseView(Context context) {
        preferenceManager = new PreferenceManager(context);
    }

    public boolean isShowedScreenToolbar() {
        return preferenceManager.getBoolean(SHOWED_SCREEN_TOOLBAR, false);
    }

    public void setShowedScreenToolbar(boolean showedScreenToolbar) {
        preferenceManager.setBoolean(SHOWED_SCREEN_TOOLBAR, showedScreenToolbar);
    }

    public boolean isShowedScreenFloatingButton() {
        return preferenceManager.getBoolean(SHOWED_SCREEN_FLOATING_BUTTON, false);
    }

    public void setShowedScreenFloatingButton(boolean showedFloatingButton) {
        preferenceManager.setBoolean(SHOWED_SCREEN_FLOATING_BUTTON, showedFloatingButton);
    }

    public boolean isShowedScreenEditButton() {
        return preferenceManager.getBoolean(SHOWED_SCREEN_EDIT_BUTTON, false);
    }

    public void setShowedScreenEditButton(boolean showedEditButton) {
        preferenceManager.setBoolean(SHOWED_SCREEN_EDIT_BUTTON, showedEditButton);
    }

    public boolean isShowedScreenReviewButton() {
        return preferenceManager.getBoolean(SHOWED_SCREEN_REVIEW_BUTTON, false);
    }

    public void setShowedScreenReviewButton(boolean showedReviewButton) {
        preferenceManager.setBoolean(SHOWED_SCREEN_REVIEW_BUTTON, showedReviewButton);
    }

}
