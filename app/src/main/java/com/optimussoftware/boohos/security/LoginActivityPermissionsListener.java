package com.optimussoftware.boohos.security;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.optimussoftware.boohos.account.AuthActivity;

import java.util.List;

/**
 * Created by william.castillo@optimus-software.com on 26/06/16.
 */
public class LoginActivityPermissionsListener implements MultiplePermissionsListener {

    private final AuthActivity activity;

    public LoginActivityPermissionsListener(AuthActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
            activity.permissionGranted(response.getPermissionName());
        }

        for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
            activity.permissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                             PermissionToken token) {
        activity.showPermissionRationale(token);
    }

}
