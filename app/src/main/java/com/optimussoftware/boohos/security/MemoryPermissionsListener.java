package com.optimussoftware.boohos.security;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.optimussoftware.boohos.account.UserRegisterActivity;
import com.optimussoftware.boohos.widget.BaseActivity;

import java.util.List;

/**
 * Created by guerra on 12/12/16.
 */

public class MemoryPermissionsListener implements MultiplePermissionsListener {

    private final BaseActivity activity;

    public MemoryPermissionsListener(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
//        for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
//            activity.permissionGranted(response.getPermissionName());
//        }
//
//        for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
//            activity.permissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
//        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                   PermissionToken token) {
//        activity.showPermissionRationale(token);
    }

}