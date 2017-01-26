package com.optimussoftware.boohos.util;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.TextPaint;
import android.widget.RelativeLayout;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.mikepenz.iconics.view.IconicsButton;
import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.InfoShowCaseView;
import com.optimussoftware.boohos.widget.BaseActivity;
import com.optimussoftware.boohos.widget.FontCache;

/**
 * Created by guerra on 09/11/16.
 * Class manager show tutorials screen
 */

public class OptimusShowCase {

    public static int ALIGN_BUTTON_LEFT = RelativeLayout.ALIGN_PARENT_LEFT;
    public static int ALIGN_BUTTON_RIGHT = RelativeLayout.ALIGN_PARENT_RIGHT;
    public static int TEXT_POSITION_ABOVE = ShowcaseView.ABOVE_SHOWCASE;
    public static final int TEXT_POSITION_BELLOW = ShowcaseView.BELOW_SHOWCASE;

    private Context context;
    private InfoShowCaseView infoShow;

    private TextPaint paintTitle;
    private TextPaint paintContent;
    private IconicsButton iconicsButton;

    private String title = "";
    private String description = "";
    private String button = "";

    private int alignButton = ALIGN_BUTTON_RIGHT;
    private int textPosition = TEXT_POSITION_BELLOW;

    public OptimusShowCase(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        infoShow = new InfoShowCaseView(context);
        setButton(context.getString(R.string.ok));

        paintTitle = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paintTitle.setTextSize(context.getResources().getDimension(R.dimen.font_title_size));
        paintTitle.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        paintTitle.setTypeface(FontCache.getTypeface(context, context.getString(R.string.font_path_bold)));

        paintContent = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paintContent.setTextSize(context.getResources().getDimension(R.dimen.font_body_m_size));
        paintContent.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        paintContent.setTypeface(FontCache.getTypeface(context, context.getString(R.string.font_path_medium)));

        iconicsButton = new IconicsButton(context);
        iconicsButton.setTypeface(FontCache.getTypeface(context, context.getString(R.string.font_path_medium)));
        iconicsButton.setTextColor(ContextCompat.getColor(context, R.color.white));
        iconicsButton.setTextSize(16);
        iconicsButton.setText(getButton());
        iconicsButton.setHeight(Commons.dpToPx(context, 40));
        iconicsButton.setMinWidth(Commons.dpToPx(context, 150));
        iconicsButton.setBackground(context.getResources().getDrawable(R.drawable.button_primary));
    }

    public ShowcaseView getShowCaseView(int idViewTarge, String textTitle, String textDescription) {
        return getShowCaseView(idViewTarge, textTitle, textDescription, getButton(), getAlignButton(), getAlignButton());
    }

    public ShowcaseView getShowCaseView(int idViewTarge, String textTitle, String textDescription, String textButton) {
        return getShowCaseView(idViewTarge, textTitle, textDescription, textButton, getAlignButton(), getAlignButton());
    }

    public ShowcaseView getShowCaseView(int idViewTarge, String textTitle, String textDescription, String textButton, int alignButton, int textPosition) {
        setTitle(textTitle);
        setDescription(textDescription);
        setButton(textButton);
        setAlignButton(alignButton);
        setTextPosition(textPosition);

        iconicsButton.setText(getButton());

        ShowcaseView caseView = new ShowcaseView.Builder((BaseActivity) context)
                .setTarget(new ViewTarget(idViewTarge, (BaseActivity) context))
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme)
                .setContentTitlePaint(paintTitle)
                .setContentTextPaint(paintContent)
                .replaceEndButton(iconicsButton)
                .setContentTitle(getTitle())
                .setContentText(getDescription())
                .hideOnTouchOutside()
                .build();

        int margin = (int) context.getResources().getDimension(com.github.amlcurran.showcaseview.R.dimen.button_margin);
        RelativeLayout.LayoutParams layoutParamsButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParamsButton.addRule(getAlignButton());
        layoutParamsButton.setMargins(margin, margin, margin, margin);
        caseView.setButtonPosition(layoutParamsButton);
        caseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
        caseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);

        if (getTextPosition() == ShowcaseView.BELOW_SHOWCASE) {
            caseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
        } else if (getTextPosition() == ShowcaseView.ABOVE_SHOWCASE) {
            caseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        }

        return caseView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public int getAlignButton() {
        return alignButton;
    }

    public void setAlignButton(int alignButton) {
        this.alignButton = alignButton;
    }

    public int getTextPosition() {
        return textPosition;
    }

    public void setTextPosition(int textPosition) {
        this.textPosition = textPosition;
    }

    public InfoShowCaseView getInfoShow() {
        return infoShow;
    }

}
