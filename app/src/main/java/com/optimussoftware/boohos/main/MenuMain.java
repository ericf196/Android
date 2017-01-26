package com.optimussoftware.boohos.main;


import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.optimussoftware.boohos.DeepLinkActivity;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.UserInterestActivity;
import com.optimussoftware.boohos.account.AuthActivity;
import com.optimussoftware.boohos.account.PreferencesActivity;
import com.optimussoftware.boohos.account.UserRegisterActivity;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.invitations.GrowHackingActivity;
import com.optimussoftware.boohos.invitations.InvitationFacebook;
import com.optimussoftware.boohos.invitations.InvitationGooglePlus;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.util.OptimusShowCase;
import com.optimussoftware.boohos.util.PreferenceManager;
import com.optimussoftware.boohos.viewDetail.FragPagerAdapter;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.image.Factory;
import com.optimussoftware.image.SizeImage;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by guerra on 20/10/16.
 * menu
 */

public class MenuMain implements GoogleApiClient.OnConnectionFailedListener {

    private View guillotineMenu;

    private CircleImageView userImg;
    private OptimusTextView user_name;
    private OptimusTextView user_location;

    private TabLayout tabMenuMain;
    private ViewPager viewPagerMenu;

    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton optionAddInterest;
    private FloatingActionButton optionEditInfo;
    private FloatingActionButton optionLogout;
    private FloatingActionButton optionPreferences;

    private String TAG = "MenuMain";

    private Context context;
    private GuillotineAnimation guillotineAnimation;

    private PersonalInfo personalInfo;
    private PreferenceManager preferenceManager;
    private FavoriteFragment favoriteFragment;
    private ProfileFragment profileFragment;

    private CallbackManager callbackManager ;

    public MenuMain(Context context, View guillotineMenu, GuillotineAnimation guillotineAnimation) {
        this.context = context;
        this.guillotineMenu = guillotineMenu;
        this.guillotineAnimation = guillotineAnimation;
        findViews();
        initVar();
        setActions();
        populateInfo();
    }

    private void findViews() {
        userImg = (CircleImageView) guillotineMenu.findViewById(R.id.profile_image);
        user_name = (OptimusTextView) guillotineMenu.findViewById(R.id.user_name);
        user_location = (OptimusTextView) guillotineMenu.findViewById(R.id.user_location);

        tabMenuMain = (TabLayout) guillotineMenu.findViewById(R.id.tab_menu_main);
        viewPagerMenu = (ViewPager) guillotineMenu.findViewById(R.id.viewPagerMenu);

        floatingActionMenu = (FloatingActionMenu) guillotineMenu.findViewById(R.id.main_menu);

        optionAddInterest = (FloatingActionButton) guillotineMenu.findViewById(R.id.option_add_interest);
        optionEditInfo = (FloatingActionButton) guillotineMenu.findViewById(R.id.option_edit_info);
        optionPreferences = (FloatingActionButton) guillotineMenu.findViewById(R.id.option_preferences);
        optionLogout = (FloatingActionButton) guillotineMenu.findViewById(R.id.option_logout);
    }

    private void initVar() {
        personalInfo = new PersonalInfo((BaseActivity) context);
        preferenceManager = new PreferenceManager(context);

        int color = ContextCompat.getColor(context, R.color.colorPrimary);
        Drawable drawaAdd = Factory.getIcon(context, CommunityMaterial.Icon.cmd_star_outline, 16);
        drawaAdd.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        optionAddInterest.setImageDrawable(drawaAdd);
        Drawable drawEdit = Factory.getIcon(context, CommunityMaterial.Icon.cmd_pencil, 16);
        drawEdit.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        optionEditInfo.setImageDrawable(drawEdit);
        Drawable drawPreferences = Factory.getIcon(context, CommunityMaterial.Icon.cmd_wrench, 16);
        drawPreferences.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        optionPreferences.setImageDrawable(drawPreferences);
        Drawable drawLogout = Factory.getIcon(context, CommunityMaterial.Icon.cmd_logout, 16);
        drawLogout.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        optionLogout.setImageDrawable(drawLogout);

        favoriteFragment = new FavoriteFragment();
        profileFragment = new ProfileFragment();
        FragPagerAdapter menuPagerAdapter = new FragPagerAdapter(((FragmentActivity) context).getSupportFragmentManager());
        menuPagerAdapter.addFragment(favoriteFragment);
        menuPagerAdapter.addFragment(profileFragment);
        tabMenuMain.addTab(tabMenuMain.newTab().setText(context.getString(R.string.tab_favorite)));
        tabMenuMain.addTab(tabMenuMain.newTab().setText(context.getString(R.string.tab_profile)));
        viewPagerMenu.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMenuMain));
        tabMenuMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerMenu.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPagerMenu.setAdapter(menuPagerAdapter);
    }

    private void setActions() {
        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(context, DeepLinkActivity.class);
                //context.startActivity(intent);

                InvitationFacebook.invite(callbackManager, context);
//                Intent intent = new Intent(context, GrowHackingActivity.class);
//                context.startActivity(intent);
                //Intent intent = new Intent(context, InvitationGooglePlus.class);
                //context.startActivity(intent);
            }
        });
        optionAddInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
                guillotineAnimation.close();
                initInterestActivity();
            }
        });
        optionEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
                //guillotineAnimation.close();
                initEditView();
            }
        });
        optionPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionMenu.close(true);
                guillotineAnimation.close();
                initPreferences();
            }
        });
        optionLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void initInterestActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, UserInterestActivity.class);
                intent.putExtra("user_id", personalInfo.getUuid());
                context.startActivity(intent);
            }
        }, 500);
    }

    private void initEditView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(context, UserRegisterActivity.class);
                intent.putExtra("MODE", UserRegisterActivity.MODE_UPDATE);
                intent.putExtra("user_id", personalInfo.getUuid());
                intent.putExtra("email", personalInfo.getEmail());
                intent.putExtra("source", personalInfo.getSource());
                intent.putExtra("gender", personalInfo.getGender());
                intent.putExtra("picture", personalInfo.getPicture());
                intent.putExtra("birthday", personalInfo.getBirthday());
                intent.putExtra("phone", personalInfo.getPhone());

                Pair<View, String> p1 = Pair.create(guillotineMenu.findViewById(R.id.profile_image),
                        context.getString(R.string.transition_name_circle));

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((BaseActivity) context, p1);
                ActivityCompat.startActivityForResult((BaseActivity) context, intent, Constants.EDIT_PROFILE, options.toBundle());
            }
        }, 500);
    }

    private void initPreferences() {
        Intent intent = new Intent(context, PreferencesActivity.class);
        context.startActivity(intent);
    }

    private void logout() {
        if (personalInfo.getSource().compareTo(Constants.SOURCE_FACEBOOK) == 0) {
            ((MainActivity) context).disconnectFromFacebook();
            FirebaseAuth.getInstance().signOut();
        }
        if (personalInfo.getSource().compareTo(Constants.SOURCE_GOOGLE_PLUS) == 0) {
            ((MainActivity) context).signOutGoogle();
//            FirebaseAuth.getInstance().signOut();
        }
        if (personalInfo.getSource().compareTo(Constants.SOURCE_SELF) == 0) {
            disconnectFromThis();
        }
        AccountManager.get(context).invalidateAuthToken(AuthActivity.ARG_ACCOUNT_TYPE, ((MainActivity) context).getAuthToken());
        ((MainActivity) context).setResult(Constants.CODE_START_LOGIN);
        ((MainActivity) context).finish();
    }

    private void disconnectFromThis() {
        preferenceManager.setBoolean(Constants.KEY_SELF_LOGIN, false);
        preferenceManager.putString(Constants.TOKEN_API, "");
    }

    private void populateInfo() {
        SizeImage sizeImage = new SizeImage(context, 120, 120);
        Factory.setImage(userImg, context, personalInfo.getPicture(), sizeImage);
        Commons.setTextToTextView(user_name, setDateNoNull(personalInfo.getFull_name()));
        Commons.setTextToTextView(user_location, personalInfo.getLocation());
    }

    private String setDateNoNull(String s) {
        if (s == null || s.isEmpty()) {
            s = context.getString(R.string.complete_your_profile);
        }
        return s;
    }

    public void reinitFragments() {
        favoriteFragment.reinit();
        profileFragment.reinit();
    }

    public void updateInfo() {
        personalInfo = new PersonalInfo((BaseActivity) context);
        populateInfo();
        profileFragment.reinit();
    }

    public void showTutorialFloating() {
        OptimusShowCase optimusShowCase = new OptimusShowCase(context);
        boolean showed = optimusShowCase.getInfoShow().isShowedScreenFloatingButton();
        Log.d(TAG, "isShowedScreenFloatingButton() " + showed);
        if (!showed) {
            Log.d(TAG, "Mostro");
            final ShowcaseView showcaseView = optimusShowCase.getShowCaseView(R.id.viewFloating,
                    context.getString(R.string.app_name), context.getString(R.string.app_name), "ok",
                    OptimusShowCase.TEXT_POSITION_ABOVE, OptimusShowCase.ALIGN_BUTTON_LEFT);
            showcaseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showcaseView.hide();
                    floatingActionMenu.open(true);
                }
            });
            showcaseView.show();
            optimusShowCase.getInfoShow().setShowedScreenFloatingButton(true);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public void setCallbackManager(CallbackManager callbackManager) {
        this.callbackManager = callbackManager;
    }
}
