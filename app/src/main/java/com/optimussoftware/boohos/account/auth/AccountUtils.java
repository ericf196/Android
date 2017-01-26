package com.optimussoftware.boohos.account.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.optimussoftware.api.Auth;
import com.optimussoftware.db.User;
import com.optimussoftware.boohos.account.BoohosToken;

public class AccountUtils {

    public static final String ACCOUNT_TYPE = "Herme";
    public static final String AUTH_TOKEN_TYPE = "com.optimussoftware.com";

    public static Account getAccount(Context context, String accountName) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        Account accountSave = null;

        for (Account account : accounts) {
            if (account.name.equalsIgnoreCase(accountName)) {
                accountSave = account;
            } else {
                accountManager.removeAccount(account, null, null);
            }
        }
        return accountSave;
    }

    public static BoohosToken signIn(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Auth auth = new Auth();
        return auth.loginSync(user);
    }
}
