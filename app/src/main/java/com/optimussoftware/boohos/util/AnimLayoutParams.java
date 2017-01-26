package com.optimussoftware.boohos.util;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class AnimLayoutParams {
    private boolean listenerSetted = false;
    private OnUpdateLayoutAnim onUpdateLayoutAnim;
    public static final int NORMAL = 1, CENTRO = 2, EXTREMO = 3;

    public void setOnUpdateLayoutAnim(OnUpdateLayoutAnim onUpdateLayoutAnim) {
        listenerSetted = onUpdateLayoutAnim != null;
        this.onUpdateLayoutAnim = onUpdateLayoutAnim;
    }

    public ValueAnimator animarLayoutHeight(final View view, int desiredHeight, int duracion, int orientacion) {
        final int inih = view.getHeight(), dif;
        if (desiredHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            int actH = view.getHeight();
            int actW = view.getWidth();
            view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            desiredHeight = view.getMeasuredHeight();
            view.getLayoutParams().height = actH;
            view.getLayoutParams().width = actW;
        }
        dif = desiredHeight - inih;
        final boolean noNormal = (orientacion != NORMAL);
        final int iniTrasY = (noNormal) ? AnimationUtilities.getTransLayoutY(view) : 0;
        final int divisorMov = (noNormal) ? getDivisorNoNormal(orientacion) : 0;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(inih, desiredHeight);
        valueAnimator.setDuration(duracion);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int pixlsmvd;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pixlsmvd = (int) (valueAnimator.getAnimatedFraction() * dif);
                AnimationUtilities.setLayoutHeight(view, (Integer) valueAnimator.getAnimatedValue());

                if (noNormal) {
                    AnimationUtilities.setTransLayoutY(view, -pixlsmvd / divisorMov, iniTrasY);
                }

                if (listenerSetted) {
                    onUpdateLayoutAnim.onInterpolatedTime(valueAnimator.getAnimatedFraction());
                    onUpdateLayoutAnim.onPixelsMoved(pixlsmvd);
                }
            }
        });
        return valueAnimator;
    }

    public ValueAnimator animarLayoutHeight(final View view, int desiredHeight, int duracion) {
        return animarLayoutHeight(view, desiredHeight, duracion, NORMAL);
    }

    public ValueAnimator animarLayoutScaleY(final View view, float escala, int duracion, int orientacion) {
        int inih = view.getHeight(), desH;
        desH = (int) (inih * escala);
        return animarLayoutHeight(view, desH, duracion, orientacion);
    }

    public ValueAnimator animarLayoutScaleY(final View view, float escala, int duracion) {
        return animarLayoutScaleY(view, escala, duracion, NORMAL);
    }

    public ValueAnimator animarLayoutWidth(final View view, int desiredWidth, int duracion, int orientacion) {
        int iniw = view.getWidth();
        if (desiredWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            int actH = view.getHeight();
            int actW = view.getWidth();
            view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            desiredWidth = view.getMeasuredWidth();
            view.getLayoutParams().height = actH;
            view.getLayoutParams().width = actW;
        }
        final int dif = desiredWidth - iniw;
        final boolean noNormal = (orientacion != NORMAL);
        final int iniTrasX = (noNormal) ? AnimationUtilities.getTransLayoutX(view) : 0;
        final int divisorMov = (noNormal) ? getDivisorNoNormal(orientacion) : 0;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(iniw, desiredWidth);
        valueAnimator.setDuration(duracion);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int pixlsmvd;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimationUtilities.setLayoutWidth(view, (Integer) valueAnimator.getAnimatedValue());
                pixlsmvd = (int) (valueAnimator.getAnimatedFraction() * dif);

                if (noNormal) {
                    AnimationUtilities.setTransLayoutX(view, -pixlsmvd / divisorMov, iniTrasX);
                }

                if (listenerSetted) {
                    onUpdateLayoutAnim.onInterpolatedTime(valueAnimator.getAnimatedFraction());
                    onUpdateLayoutAnim.onPixelsMoved((int) (valueAnimator.getAnimatedFraction() * dif));
                }
            }
        });
        return valueAnimator;
    }

    public ValueAnimator animarLayoutWidth(final View view, int desiredWidth, int duracion) {
        return animarLayoutWidth(view, desiredWidth, duracion, NORMAL);
    }

    public ValueAnimator animarLayoutScaleX(final View view, float escala, int duracion, int orientacion) {
        int iniw = view.getWidth(), desW;
        desW = (int) (iniw * escala);
        return animarLayoutWidth(view, desW, duracion, orientacion);
    }

    public ValueAnimator animarLayoutScaleX(final View view, float escala, int duracion) {
        return animarLayoutScaleX(view, escala, duracion, NORMAL);
    }

    public ValueAnimator animarLayoutMoveY(final View view, int desiredMov, int duracion) {
        final int iniPosY = AnimationUtilities.getTransLayoutY(view), dif;
        dif = desiredMov;
        desiredMov = iniPosY + desiredMov;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(iniPosY, desiredMov);
        valueAnimator.setDuration(duracion);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimationUtilities.setTransLayoutY(view, (Integer) valueAnimator.getAnimatedValue(), 0);
                if (listenerSetted) {
                    onUpdateLayoutAnim.onInterpolatedTime(valueAnimator.getAnimatedFraction());
                    onUpdateLayoutAnim.onPixelsMoved((int) (valueAnimator.getAnimatedFraction() * dif));
                }
            }
        });
        return valueAnimator;
    }

    public ValueAnimator animarLayoutMoveX(final View view, int desiredMov, int duracion) {
        final int iniPosX = AnimationUtilities.getTransLayoutX(view), dif;
        dif = desiredMov;
        desiredMov = iniPosX + desiredMov;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(iniPosX, desiredMov);
        valueAnimator.setDuration(duracion);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimationUtilities.setTransLayoutX(view, (Integer) valueAnimator.getAnimatedValue(), 0);
                if (listenerSetted) {
                    onUpdateLayoutAnim.onInterpolatedTime(valueAnimator.getAnimatedFraction());
                    onUpdateLayoutAnim.onPixelsMoved((int) (valueAnimator.getAnimatedFraction() * dif));
                }
            }
        });
        return valueAnimator;
    }

    public ValueAnimator animarLayoutWH(final View view, int desW, int desH, int duracion) {
        final int iniH, iniW, difW, difH, iniPosY, iniPosX;
        iniPosX = AnimationUtilities.getTransLayoutX(view);
        iniPosY = AnimationUtilities.getTransLayoutY(view);
        iniH = view.getHeight();
        iniW = view.getWidth();
        difW = desW - iniW;
        difH = desH - iniH;

        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.setDuration(duracion);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int pxlX;
            private int pxlY;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pxlX = getPixelMvdW(valueAnimator.getAnimatedFraction());
                pxlY = getPixelMvdH(valueAnimator.getAnimatedFraction());
                AnimationUtilities.setLayoutHeight(view, iniH + pxlY);
                AnimationUtilities.setLayoutWidth(view, iniW + pxlX);
                AnimationUtilities.setTransLayoutY(view, -pxlY / 2, iniPosY);
                AnimationUtilities.setTransLayoutX(view, -pxlX / 2, iniPosX);
            }

            private int getPixelMvdW(float interpolated) {
                return (int) (difW * interpolated);
            }

            private int getPixelMvdH(float interpolated) {
                return (int) (difH * interpolated);
            }
        });
        return valueAnimator;
    }

    public ValueAnimator animarLayoutWH(final View view, float escala, int duracion) {
        final int iniH, iniW, desW, desH;
        iniH = view.getHeight();
        iniW = view.getWidth();
        desH = (int) (escala * iniH);
        desW = (int) (escala * iniW);
        return animarLayoutWH(view, desW, desH, duracion);
    }

    public interface OnUpdateLayoutAnim {
        public void onInterpolatedTime(float interpolatedTime);

        public void onPixelsMoved(int pixels);
    }

    public int getDivisorNoNormal(int orientacion) {
        switch (orientacion) {
            case CENTRO:
                return 2;
            case EXTREMO:
                return 1;
        }
        return 0;
    }
}
