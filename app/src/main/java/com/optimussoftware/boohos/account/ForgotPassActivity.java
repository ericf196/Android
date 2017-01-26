package com.optimussoftware.boohos.account;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.mikepenz.iconics.view.IconicsButton;
import com.optimussoftware.api.RecoveryPassword;
import com.optimussoftware.api.response.GenericResponse;
import com.optimussoftware.db.User;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.OptimusSnackBar;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guerra on 26/09/16.
 */
public class ForgotPassActivity extends BaseActivity {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.edit_email)
    MaterialEditText edit_email;
    @BindView(R.id.button_send)
    IconicsButton button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        ButterKnife.bind(this);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmail()) {
                    User recovery = new User();
                    recovery.setEmail(edit_email.getText().toString().trim());
                    RecoveryPassword recoveryPassword = new RecoveryPassword();
                    recoveryPassword.recovery(recovery, new Callback<GenericResponse>() {
                        @Override
                        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                            errorSnackBar("Correo enviado");
                            //finish();
                        }

                        @Override
                        public void onFailure(Call<GenericResponse> call, Throwable t) {
                            Log.e("lary", "Error " + t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private boolean validateEmail() {
        if (TextUtils.isEmpty(edit_email.getText().toString().trim())) {
            edit_email.requestFocus();
            //emailText.setError(getString(R.string.enter_email));
            edit_email.setError("");
            errorSnackBar(getString(R.string.enter_email));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
                edit_email.getText().toString().trim()).matches()) {
            edit_email.requestFocus();
            //emailText.setError(getString(R.string.enter_email_valid));
            edit_email.setError("");
            errorSnackBar(getString(R.string.enter_email_valid));
            return false;
        }
        return true;
    }

    private void errorSnackBar(String mjs) {
        OptimusSnackBar.snackBarTop(ForgotPassActivity.this, coordinatorLayout,
                mjs, OptimusSnackBar.LENGTH_LONG, true).show();
    }

}
