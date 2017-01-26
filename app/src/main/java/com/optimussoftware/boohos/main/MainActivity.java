package com.optimussoftware.boohos.main;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.share.widget.ShareDialog;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.api.LastOfferts;
import com.optimussoftware.api.asynctasks.GetOffertsTask;
import com.optimussoftware.api.response.newAdvertising.AdverstingsList;
import com.optimussoftware.beacons.reciver.BluetoothReceiver;
import com.optimussoftware.beacons.service.OptimusBeaconsManager;
import com.optimussoftware.beacons.service.OptimusService;
import com.optimussoftware.boohos.InitActivity;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.account.auth.AccountUtils;
import com.optimussoftware.boohos.account.sync.SyncAdapter;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.listview.adapters.PagerAdapterTimeLine;
import com.optimussoftware.boohos.listview.holders.OffertViewHolders;
import com.optimussoftware.boohos.listview.model.AdvertisingByInterest;
import com.optimussoftware.boohos.listview.model.AdvertisingContent;
import com.optimussoftware.boohos.notification.NotificationActivity;
import com.optimussoftware.boohos.security.MainActivityPermissionsListener;
import com.optimussoftware.boohos.service.ClearDataUtil;
import com.optimussoftware.boohos.share.Share;
import com.optimussoftware.boohos.util.Commons;
import com.optimussoftware.boohos.util.ConnectionReceiver;
import com.optimussoftware.boohos.util.ConnectionUtil;
import com.optimussoftware.boohos.util.OptimusShowCase;
import com.optimussoftware.boohos.viewDetail.ViewDetailMaterial;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.GenderAndBirthdayView;
import com.optimussoftware.boohos.widget.OptimusTextView;
import com.optimussoftware.boohos.widget.ShareSocialButtonView;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.NotificationAdvertising;
import com.optimussoftware.db.User;
import com.optimussoftware.image.Factory;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.interfaces.GuillotineListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final long RIPPLE_DURATION = 250;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.notify)
    public IconicsImageView notify;
    @BindView(R.id.content_notify)
    RelativeLayout content_notify;
    @BindView(R.id.total_viewed)
    OptimusTextView total_viewed;
    @BindView(R.id.tabs)
    public TabLayout tabLayout;
    @BindView(R.id.view_pager_main)
    ViewPager pager;
    @BindView(R.id.main_content)
    RelativeLayout root;
    @BindView(android.R.id.content)
    ViewGroup rootView;
    @BindView(R.id.share_button)
    public ShareSocialButtonView shareButtonUtil;
    @BindView(R.id.validate_birthdayAndGender)
    public GenderAndBirthdayView genderAndBirthdayView;

    private String TAG = null;

    private PersonalInfo personalInfo;

    //Menu
    private MenuMain menuMain;
    private GuillotineAnimation guillotineAnimation;

    //Share
    public Share share;
    public CallbackManager callbackManager;

    private GoogleApiClient mGoogleApiClient;

    public BroadcastReceiver receiverUpdateData;
    private ConnectionReceiver connectionReceiver = null;
    private BeaconsReceiver beaconsReceiver = null;
    private BluetoothReceiver bluetoothReceiver = null;

    private AdverstingsList adverstingsList;
    public AdvertisingContent advertisingContent;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.EDIT_PROFILE) {
            if (resultCode == Constants.EDIT_PROFILE_OK) {
                menuMain.updateInfo();
                showToast(R.string.edit_profile_successful);
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == Constants.NOTIFICATION_VIEWED) {
            Log.i(TAG, "yes");
            notification();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        personalInfo = new PersonalInfo(this);
        if (verifyDataUser(personalInfo)) {
            init();
            startBase(toolbar);
            initToolbar();
            List<String> l = new ArrayList<>();
            l.add(getBaseContext().getString(R.string.tab_all));
            l.add(getBaseContext().getString(R.string.tab_by));
            iniTabs(tabLayout, l);
            setMenuMain();
            test();
            initTabLayout();
            //callbackManager = CallbackManager.Factory.create();
            //shareButtonUtil = new ShareSocialButtonView(this);
            share = new Share(MainActivity.this, callbackManager, new ShareDialog(this));
            onSynFavorite();
            receiverUpdateData();

            checkBirthdayAndGender();
            notification();
            EventBus.getDefault().register(this);

            getConnection();
        } else {
            deleteAccount();
        }
    }

    private boolean verifyDataUser(PersonalInfo personalInfo) {
        if (DBController.getControler().getUser(this, personalInfo.getUuid()) != null) {
            return true;
        } else {
            return false;
        }
    }

    private void deleteAccount() {
        Log.i(TAG, "Delete Account Manager" + personalInfo.getEmail());
        DBController.getControler().cleanBD(this);
        AccountManager accountManager = AccountManager.get(this);
        // loop through all accounts to remove them
        Account accounts = AccountUtils.getAccount(this, getIntent().getStringExtra("account_name"));
        accountManager.removeAccount(accounts, null, null);
        startActivity(new Intent(this, InitActivity.class));
        finish();
    }

    private void getConnection() {
        ConnectionUtil.setOnChangeConnection(new ConnectionUtil.OnChangeConnection() {
            @Override
            public void onLine() {

            }

            @Override
            public void offLine() {
                showToast(R.string.connection_off);
            }
        });
    }


    private void checkBirthdayAndGender() {
        if (DBController.getControler().getUser(getBaseContext(), personalInfo.getUuid()).getGender() == null && DBController.getControler().getUser(getBaseContext(), personalInfo.getUuid()).getBirthday() == null) {
            User user = new User();
            genderAndBirthdayView.setUser(user, personalInfo);
            genderAndBirthdayView.setGenderMaleListener();
            genderAndBirthdayView.setGenderFemaleListener();
            genderAndBirthdayView.setVisibility(View.VISIBLE);
        } else {
            genderAndBirthdayView.setVisibility(View.GONE);
        }
    }

    private void notification() {
        int total = 0;
        List<NotificationAdvertising> notificationAdvertising;
        content_notify.setVisibility(View.GONE);
        notify.setVisibility(View.GONE);
        notificationAdvertising = DBController.getControler().getNotificationAdvertisingList(getBaseContext());
        if (!notificationAdvertising.isEmpty()) {
            notify.setVisibility(View.VISIBLE);
            total_viewed.setVisibility(View.GONE);
            for (int i = 0; i < notificationAdvertising.size(); i++) {
                if (notificationAdvertising.get(i).getDate().compareTo(new Date()) < 0) {
                    if (!notificationAdvertising.get(i).getViewed()) {
                        total++;
                        total_viewed.setVisibility(View.VISIBLE);
                    }
                }
            }
            total_viewed.setText(String.valueOf(total));
        }
        content_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NotificationActivity.class);
                startActivityForResult(intent, Constants.NOTIFICATION_VIEWED);
            }
        });
    }

    public void test() {
        try {
            LastOfferts lastOfferts = new LastOfferts();

            //user  "AVe3KLJebEyhSJa54Olv"

            //beacon  "26737b4b-c0cb-4d74-a46b-091d0529430d"
            //la mac "[58:EA:BE:2B:18:B7]"

            lastOfferts.getOfferts("29407f30-f5f8-466e-aff9-25556b57fe6d", callbackOffert);

            /*lastOfferts.getOfferts("AVe3KLJebEyhSJa54Olv", Constants.TYPE_DEVICE_BEACON,
                    "12345678", "dfec3f20-db0c-5602-2557-fe0f238f44a2", "all", callbackOffert);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Callback<AdverstingsList> callbackOffert = new Callback<AdverstingsList>() {
        @Override
        public void onResponse(Call<AdverstingsList> call, Response<AdverstingsList> response) {
            sendResponse(response.body());
        }

        @Override
        public void onFailure(Call<AdverstingsList> call, Throwable t) {
            Log.d("Herme: ErrorService", t.toString());
        }
    };

    private void sendResponse(AdverstingsList adverstingsList) {
        try {
            //todo
//        int size = adverstingsList.getMeta().getTotal();
            if (adverstingsList.getItems().size() > 0) {
                String ids = DBController.getControler().storeOffertList(getBaseContext(), adverstingsList);
                this.adverstingsList = adverstingsList;
                setLastOfferts(ids);
            }
        } catch (Exception e) {
            //todo
            Log.e(TAG, "Exception sendResponse: " + e);
        }
    }

    private void init() {
        ButterKnife.bind(this);
        TAG = Commons.getTag(this.getApplicationContext(), MainActivity.class);
        registerReceiver();
        if (personalInfo.getSource().compareTo(Constants.SOURCE_GOOGLE_PLUS) == 0) {
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
        setPermissions();
    }

    private void setPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new MainActivityPermissionsListener(this);
        MultiplePermissionsListener allPermissionsListener = new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                        .with(rootView, R.string.all_permissions_denied_init_activity)
                        .build());
        Dexter.continuePendingRequestsIfPossible(allPermissionsListener);
        if (!Dexter.isRequestOngoing()) {
            Dexter.checkPermissions(allPermissionsListener,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showPermissionRationale(final PermissionToken token) {
        new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .title(R.string.permissions_title)
                .content(R.string.permissions_request)
                .negativeText(android.R.string.cancel)
                .positiveText(android.R.string.ok)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void permissionGranted(String permission) {
        testBluetooth();
        startServices();
    }

    public void permissionDenied(String permission, boolean isPermanentlyDenied) {

    }

    public void initTabLayout() {
        PagerAdapterTimeLine pagerAdapterTimeLine = new PagerAdapterTimeLine(getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(pagerAdapterTimeLine);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 1) {
//                    //linear_share.setVisibility(View.GONE);
//                } else {
//                    // linear_share.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setMenuMain() {
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menu, null);
        root.addView(guillotineMenu);

        Toolbar toolbarMenu = (Toolbar) guillotineMenu.findViewById(R.id.toolbar);
        setToolbarMenu(toolbarMenu);

        guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, toolbar, toolbarMenu)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .setGuillotineListener(new GuillotineListener() {
                    @Override
                    public void onGuillotineOpened() {
                        menuMain.showTutorialFloating();
                        menuMain.reinitFragments();
                    }

                    @Override
                    public void onGuillotineClosed() {

                    }
                })
                .build();

        menuMain = new MenuMain(MainActivity.this, guillotineMenu, guillotineAnimation);

        menuMain.setCallbackManager(callbackManager);
        setShowTutorialToollbar();
    }

    private void setShowTutorialToollbar() {
        final OptimusShowCase optimusShowCase = new OptimusShowCase(this);

        boolean showed = optimusShowCase.getInfoShow().isShowedScreenToolbar();
        Log.d(TAG, "isShowedScreenToolbar() " + showed);
        if (!showed) {
            Log.d(TAG, "Mostro");
            final ShowcaseView showcaseView = optimusShowCase.getShowCaseView(R.id.viewShowToolbar, getString(R.string.app_name), getString(R.string.app_name));
            showcaseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showcaseView.hide();
                    guillotineAnimation.open();
                }
            });
            showcaseView.show();
            optimusShowCase.getInfoShow().setShowedScreenToolbar(true);
        }
    }

    public void setToolbarMenu(Toolbar _toolbar) {
        setSupportActionBar(_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(setColorTextToolbar(getString(R.string.app_name)));
            IconicsDrawable icon = Factory.getIcon(this, CommunityMaterial.Icon.cmd_menu, 20);
            _toolbar.setNavigationIcon(Factory.getRotateDrawable(this, icon.toBitmap(), 90));
        }
    }

    private void startServices() {
        Intent service = new Intent(this, OptimusService.class);
        service.putExtra("user_id", getUser_id());
        startService(service);
    }

    private void registerReceiver() {
        if (connectionReceiver == null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            connectionReceiver = new ConnectionReceiver();
            registerReceiver(connectionReceiver, filter);
        }
        if (bluetoothReceiver == null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED");
            bluetoothReceiver = new BluetoothReceiver();
            registerReceiver(bluetoothReceiver, filter);
        }
        if (beaconsReceiver == null) {
            IntentFilter filterOfferts = new IntentFilter();
            filterOfferts.addAction(OptimusBeaconsManager.ACTION);
            beaconsReceiver = new BeaconsReceiver();
            registerReceiver(beaconsReceiver, filterOfferts);
        }
    }

    public void testBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            showToast(R.string.not_bluetooth);
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            }
        }
    }

    public void unregisterReceivers() {
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
            connectionReceiver = null;
        }
        if (bluetoothReceiver != null) {
            unregisterReceiver(bluetoothReceiver);
            bluetoothReceiver = null;
        }
        if (beaconsReceiver != null) {
            unregisterReceiver(beaconsReceiver);
            beaconsReceiver = null;
        }
    }

    @Override
    public void onBackPressed() {
        //floatingActionMenu.close(true);
        if (shareButtonUtil.isShareOpen()) {
            shareButtonUtil.animarLayoutDown(this);
        } else if (guillotineAnimation.isMenuOpen()) {
            guillotineAnimation.close();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        registerReceiver();
        Intent intent = this.getIntent();
        String items = intent.getStringExtra("_items");
        if (items != null)
            setLastOfferts(items);

        notification();
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiverUpdateData), new IntentFilter(String.valueOf(ClearDataUtil.UPDATEDATA)));
    }

    @Override
    protected void onStop() {
        unregisterReceivers();
        super.onStop();
    }

    @Override
    protected void onPause() {
        unregisterReceivers();
        super.onPause();
    }

    public void receiverUpdateData() {
        receiverUpdateData = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG, "refresh view ");
                initTabLayout();
            }
        };
    }

    public void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return;
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();

                getPreferenceManager().setBoolean(Constants.KEY_FACEBOOK_LOGIN, false);
                getPreferenceManager().putString(Constants.TOKEN_FACEBOOK, "");
                getPreferenceManager().putString(Constants.TOKEN_API, "");
            }
        }).executeAsync();
    }

    public void signOutGoogle() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        getPreferenceManager().setBoolean(Constants.KEY_GOOGLE_LOGIN, false);
                        getPreferenceManager().putString(Constants.TOKEN_GOOGLE_PLUS, "");
                        getPreferenceManager().putString(Constants.TOKEN_API, "");
                    }
                });
    }

    private void initOffertsListView(String definition) {
        AdvertisingContent advertisingContent = new AdvertisingContent(this, definition);
        //     RecyclerView recyclerView = (RecyclerView) findViewById(R.id.offerts_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //       recyclerView.setAdapter(new OffertViewAdapter(this, advertisingContent.getItems(), this, this, this));
    }

    private void setLastOfferts(String s_items) {
        advertisingContent = new AdvertisingContent(getBaseContext(), s_items);
        new GetOffertsTask(this, adverstingsList).execute(advertisingContent.getItems().get(0).getCampaign_id());
    }

    public void initDetailView(final Advertising item, final OffertViewHolders holder) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, ViewDetailMaterial.class);
                intent.putExtra("item", item);
                intent.putExtra("user_id", personalInfo.getUuid());
                //startActivity(intent);

                Pair<View, String> p = new Pair<View, String>(holder.photo,
                        getString(R.string.transition_name_advertising));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this, p);
                ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
            }
        }, 500);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public void closeMenu() {
        guillotineAnimation.close();
    }

    class BeaconsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("_items");
            setLastOfferts(data);
        }
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void onSynFavorite() {
        if (DBController.getControler().getFavoritesByUser(this, personalInfo.getUuid()).size() == 0)
            SyncAdapter.sincronizarAhora(this, personalInfo, false);
    }

    public interface OnlastOffertListener {
        void lastOffertListener(List<Advertising> advertisings);
    }

    public OnlastOffertListener onlastOffertListener;

    public void setOnlastOffertListener(OnlastOffertListener onlastOffertListener) {
        this.onlastOffertListener = onlastOffertListener;
    }

    public interface OnByCategoryListener {
        void byCategoryListener(List<AdvertisingByInterest> advertisingByInterest);
    }

    public OnByCategoryListener onByCategoryListener;

    public void setOnByCategoryListener(OnByCategoryListener onByCategoryListener) {
        this.onByCategoryListener = onByCategoryListener;
    }

    @Subscribe
    public void OnEvent(String datos) {
        Log.i(TAG, "EventBus");
        notification();
    }

}