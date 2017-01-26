package com.optimussoftware.boohos.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.view.IconicsImageView;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Dislikes;
import com.optimussoftware.db.Favorites;
import com.optimussoftware.db.Likes;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.async.FavoritesAsyncTask;
import com.optimussoftware.boohos.async.OffertAsyncTask;
import com.optimussoftware.boohos.data.DBController;
import com.optimussoftware.boohos.data.PersonalInfo;
import com.optimussoftware.boohos.share.Share;
import com.optimussoftware.image.Factory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by leonardojpr on 9/15/16.
 */
public class AdvertisingActionButtonView extends LinearLayout {
    private static final String TAG = AdvertisingActionButtonView.class.getSimpleName();
    private IconicsImageView likes_icon, dislikes_icon, favorite_icon;
    public IconicsImageView share_icon;
    private OffertAsyncTask likeAndDislike;
    private FavoritesAsyncTask favoriteAsyncTask;

    public AdvertisingActionButtonView(Context context) {
        super(context);
        initView();
    }

    public AdvertisingActionButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AdvertisingActionButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.advertising_action_button, this);
        share_icon = (IconicsImageView) findViewById(R.id.share_advertising);
        likes_icon = (IconicsImageView) findViewById(R.id.like_advertising);
        dislikes_icon = (IconicsImageView) findViewById(R.id.dislike_advertising);
        favorite_icon = (IconicsImageView) findViewById(R.id.favorite_advertising);
    }

    public void getActionListener(final Advertising advertising, final PersonalInfo personalInfo, final boolean isEventBus) {

        likes_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Likes likes = checkIfLikes(getContext(), advertising.get_id(), personalInfo.getUuid());
                    if (likes == null) {
                        likeAndDislike = new OffertAsyncTask(getContext(), personalInfo, likes, null, advertising, true);
                        likeAndDislike.execute();
                        getIconLike(true);
                        getIconDislike(false);
                    } else {
                        likeAndDislike = new OffertAsyncTask(getContext(), personalInfo, likes, null, advertising, true);
                        likeAndDislike.execute();
                        getIconLike(false);
                    }
                    if (isEventBus) {
                        setEventBus(advertising);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        dislikes_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Dislikes dislikes = checkIfDislikes(getContext(), advertising.get_id(), personalInfo.getUuid());
                if (dislikes == null) {
                    likeAndDislike = new OffertAsyncTask(getContext(), personalInfo, null, dislikes, advertising, false);
                    likeAndDislike.execute();
                    getIconDislike(true);
                    getIconLike(false);
                } else {
                    likeAndDislike = new OffertAsyncTask(getContext(), personalInfo, null, dislikes, advertising, false);
                    likeAndDislike.execute();
                    getIconDislike(false);
                }
                if (isEventBus) {
                    setEventBus(advertising);
                }
            }
        });


        favorite_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorites favorites = checkIfFavorite(getContext(), advertising.get_id(), personalInfo.getUuid());
                if (favorites == null) {
                    favoriteAsyncTask = new FavoritesAsyncTask(getContext(), personalInfo, favorites, advertising);
                    favoriteAsyncTask.execute();
                    getIconFavorite(true);
                } else {
                    favoriteAsyncTask = new FavoritesAsyncTask(getContext(), personalInfo, favorites, advertising);
                    favoriteAsyncTask.execute();
                    getIconFavorite(false);
                }
                if (isEventBus) {
                    setEventBus(advertising);
                }
            }
        });

    }


    public void getActionShare(final Share share, final Advertising advertising, final ShareSocialButtonView shareSocialButtonView) {
        share_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "tap share");
                //share_icon.setColor(getResources().getColor(R.color.colorAccent));
                shareSocialButtonView.getActionShareButton(getContext(), share, advertising);
            }
        });
    }

    public static void getColorShare(int color, IconicsImageView iconicsImageView) {
        iconicsImageView.setColor(color);
    }

    private Likes checkIfLikes(Context context, String advertising_id, String user_id) {
        List<Likes> likesList = DBController.getControler().getLikesByUser(context, user_id, advertising_id);
        Likes likes = null;
        for (int i = 0; i < likesList.size(); i++) {
            likes = likesList.get(i);
        }
        return likes;
    }

    private Dislikes checkIfDislikes(Context context, String advertising_id, String user_id) {
        List<Dislikes> dislikesList = DBController.getControler().getDislikesByUser(context, user_id, advertising_id);
        Dislikes dislikes = null;
        for (int i = 0; i < dislikesList.size(); i++) {
            dislikes = dislikesList.get(i);
        }
        return dislikes;
    }

    private Favorites checkIfFavorite(Context context, String advertising_id, String user_id) {
        List<Favorites> favoritesList = DBController.getControler().getFavoritesByAdvertising(context, user_id, advertising_id);
        Favorites favorites = null;
        for (int i = 0; i < favoritesList.size(); i++) {
            favorites = favoritesList.get(i);
        }
        return favorites;
    }

    private void getIconLike(boolean isSelect) {
        if (isSelect) {
            likes_icon.setIcon(Factory.getIcon(getContext(), CommunityMaterial.Icon.cmd_thumb_up, 36));
            likes_icon.setColor(getResources().getColor(R.color.colorAccent));
        } else {
            likes_icon.setIcon(Factory.getIcon(getContext(), CommunityMaterial.Icon.cmd_thumb_up_outline, 36));
            likes_icon.setColor(getResources().getColor(R.color.colorEnabled));
        }
    }

    private void getIconDislike(boolean isSelect) {
        if (isSelect) {
            dislikes_icon.setIcon(Factory.getIcon(getContext(), CommunityMaterial.Icon.cmd_thumb_down, 36));
            dislikes_icon.setColor(getResources().getColor(R.color.colorAccent));
        } else {
            dislikes_icon.setIcon(Factory.getIcon(getContext(), CommunityMaterial.Icon.cmd_thumb_down_outline, 36));
            dislikes_icon.setColor(getResources().getColor(R.color.colorEnabled));
        }
    }

    private void getIconFavorite(boolean isSelect) {
        if (isSelect) {
            favorite_icon.setIcon(Factory.getIcon(getContext(), CommunityMaterial.Icon.cmd_gift, 36));
            favorite_icon.setColor(getResources().getColor(R.color.colorAccent));
        } else {
            favorite_icon.setIcon(Factory.getIcon(getContext(), CommunityMaterial.Icon.cmd_gift, 36));
            favorite_icon.setColor(getResources().getColor(R.color.colorEnabled));
        }
    }

    public void setPaddingIcon(int padding) {
        share_icon.setPaddingDp(padding);
        likes_icon.setPaddingDp(padding);
        dislikes_icon.setPaddingDp(padding);
        favorite_icon.setPaddingDp(padding);
    }

    public void initIconButton(Advertising advertising, PersonalInfo personalInfo) {
        share_icon.setIcon(Factory.getIcon(getContext(), CommunityMaterial.Icon.cmd_share_variant, 36));
        if (checkIfLikes(getContext(), advertising.get_id(), personalInfo.getUuid()) != null) {
            getIconLike(true);
        } else {
            getIconLike(false);
        }

        if (checkIfDislikes(getContext(), advertising.get_id(), personalInfo.getUuid()) != null) {
            getIconDislike(true);
        } else {
            getIconDislike(false);
        }

        if (checkIfFavorite(getContext(), advertising.get_id(), personalInfo.getUuid()) != null) {
            getIconFavorite(true);
        } else {
            getIconFavorite(false);
        }
    }

    public void setEventBus(Advertising advertising) {
        EventBus.getDefault().post(advertising.get_id());
    }

    @Subscribe
    public void onEvent(boolean isTap) {
        if (isTap) {
            share_icon.setColor(getResources().getColor(R.color.colorEnabled));
        }
    }
}
