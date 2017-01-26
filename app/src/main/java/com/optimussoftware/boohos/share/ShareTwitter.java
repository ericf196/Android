package com.optimussoftware.boohos.share;

import android.content.Context;
import android.net.Uri;

import com.optimussoftware.boohos.R;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.URL;

import io.fabric.sdk.android.Fabric;

/**
 * Created by guerra on 29/06/16.
 */
public class ShareTwitter {

    private Context context;

    public ShareTwitter(Context context) {
        this.context = context;
    }

    public void shareText(String text) {
        Fabric.with(context, new TwitterCore(authConfig()), new TweetComposer());
        TweetComposer.Builder builder = new TweetComposer.Builder(context)
                .text(text);
        builder.show();
    }

    public void shareLink(String text, URL url) {
        Fabric.with(context, new TwitterCore(authConfig()), new TweetComposer());
        TweetComposer.Builder builder = new TweetComposer.Builder(context)
                .text(text)
                .url(url);
        builder.show();
    }

    public void sharePhoto(String text, Uri imagen) {
        Fabric.with(context, new TwitterCore(authConfig()), new TweetComposer());
        TweetComposer.Builder builder = new TweetComposer.Builder(context)
                .text(text)
                .image(imagen);
        builder.show();
    }

    public void shareTwiFull(String text, Uri imagen, URL link) {
        Fabric.with(context, new TwitterCore(authConfig()), new TweetComposer());
        TweetComposer.Builder builder = new TweetComposer.Builder(context)
                .text(text)
                .url(link)
                .image(imagen);
        builder.show();
    }

    private TwitterAuthConfig authConfig() {
        return  new TwitterAuthConfig(context.getString(R.string.twitter_consumer_key),
                context.getString(R.string.twitter_secret_key));
    }

}
