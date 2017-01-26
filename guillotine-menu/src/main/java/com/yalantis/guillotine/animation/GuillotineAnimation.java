package com.yalantis.guillotine.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.yalantis.guillotine.interfaces.GuillotineListener;
import com.yalantis.guillotine.util.ActionBarInterpolator;
import com.yalantis.guillotine.util.GuillotineInterpolator;

/**
 * Created by Dmytro Denysenko on 5/6/15.
 * Edited: William Castillo
 */
public class GuillotineAnimation {
    private static final String ROTATION = "rotation";
    private static final float GUILLOTINE_CLOSED_ANGLE = -90f;
    private static final float GUILLOTINE_OPENED_ANGLE = 0f;
    private static final int DEFAULT_DURATION = 625;
    private static final float ACTION_BAR_ROTATION_ANGLE = 3f;

    private final View mGuillotineView;
    private final long mDuration;
    private final ObjectAnimator mOpeningAnimation;
    private final ObjectAnimator mClosingAnimation;
    private final GuillotineListener mListener;
    private final boolean mIsRightToLeftLayout;
    private final TimeInterpolator mInterpolator;
    private final View mActionBarView;
    private final long mDelay;
    private boolean menuOpen;
    private boolean isOpening;
    private boolean isClosing;

    private GuillotineAnimation(GuillotineBuilder builder) {
        this.mActionBarView = builder.actionBarView;
        this.mListener = builder.guillotineListener;
        this.mGuillotineView = builder.guillotineView;
        this.mDuration = builder.duration > 0 ? builder.duration : DEFAULT_DURATION;
        this.mDelay = builder.startDelay;
        this.mIsRightToLeftLayout = builder.isRightToLeftLayout;
        this.mInterpolator = builder.interpolator == null ? new GuillotineInterpolator() : builder.interpolator;
        Log.d("barras", builder.openingView + " " + builder.closingView);
        setUpOpeningView(builder.openingView);
        setUpClosingView(builder.closingView);
        this.mOpeningAnimation = buildOpeningAnimation();
        this.mClosingAnimation = buildClosingAnimation();
        if (builder.isClosedOnStart) {
            mGuillotineView.setRotation(GUILLOTINE_CLOSED_ANGLE);
            mGuillotineView.setVisibility(View.INVISIBLE);
        }
        //TODO handle right-to-left layouts
        //TODO handle landscape orientation
    }

    public void open() {
        if (!isOpening) {
            mOpeningAnimation.start();
        }
    }

    public void close() {
        if (!isClosing) {
            mClosingAnimation.start();
        }

    }

    private void setUpOpeningView(final Toolbar openingView) {
        if (mActionBarView != null) {
            mActionBarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mActionBarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mActionBarView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    mActionBarView.setPivotX(calculatePivotX(openingView));
                    mActionBarView.setPivotY(calculatePivotY(openingView));
                }
            });
        }
        openingView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });
    }

    private void setUpClosingView(final Toolbar closingView) {
        mGuillotineView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mGuillotineView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mGuillotineView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                mGuillotineView.setPivotX(calculatePivotX(closingView));
                mGuillotineView.setPivotY(calculatePivotY(closingView));
            }
        });
        closingView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
    }

    private ObjectAnimator buildOpeningAnimation() {
        ObjectAnimator rotationAnimator = initAnimator(ObjectAnimator.ofFloat(mGuillotineView, ROTATION, GUILLOTINE_CLOSED_ANGLE, GUILLOTINE_OPENED_ANGLE));
        rotationAnimator.setInterpolator(mInterpolator);
        rotationAnimator.setDuration(mDuration);
        rotationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mGuillotineView.setVisibility(View.VISIBLE);
                isOpening = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isOpening = false;
                setMenuOpen(true);
                if (mListener != null) {
                    mListener.onGuillotineOpened();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return rotationAnimator;
    }

    private ObjectAnimator buildClosingAnimation() {
        ObjectAnimator rotationAnimator = initAnimator(ObjectAnimator.ofFloat(mGuillotineView, ROTATION, GUILLOTINE_OPENED_ANGLE, GUILLOTINE_CLOSED_ANGLE));
        rotationAnimator.setDuration((long) (mDuration * GuillotineInterpolator.ROTATION_TIME));
        rotationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isClosing = true;
                mGuillotineView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isClosing = false;
                setMenuOpen(false);
                mGuillotineView.setVisibility(View.GONE);
                startActionBarAnimation();

                if (mListener != null) {
                    mListener.onGuillotineClosed();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return rotationAnimator;
    }

    private void startActionBarAnimation() {
        ObjectAnimator actionBarAnimation = ObjectAnimator.ofFloat(mActionBarView, ROTATION, GUILLOTINE_OPENED_ANGLE, ACTION_BAR_ROTATION_ANGLE);
        actionBarAnimation.setDuration((long) (mDuration * (GuillotineInterpolator.FIRST_BOUNCE_TIME + GuillotineInterpolator.SECOND_BOUNCE_TIME)));
        actionBarAnimation.setInterpolator(new ActionBarInterpolator());
        actionBarAnimation.start();
    }

    private ObjectAnimator initAnimator(ObjectAnimator animator) {
        animator.setStartDelay(mDelay);
        return animator;
    }

    private float calculatePivotY(Toolbar burger) {
        return burger.getTop() + burger.getHeight() / 2;
    }

    private float calculatePivotX(Toolbar burger) {
        return burger.getLeft() + burger.getHeight() / 2;
    }

    public static class GuillotineBuilder {
        private final View guillotineView;
        private Toolbar openingView;
        private Toolbar closingView;
        private View actionBarView;
        private GuillotineListener guillotineListener;
        private long duration;
        private long startDelay;
        private boolean isRightToLeftLayout;
        private TimeInterpolator interpolator;
        private boolean isClosedOnStart;

        public GuillotineBuilder(View guillotineView, Toolbar openingView, Toolbar closingView) {
            this.guillotineView = guillotineView;
            this.openingView = openingView;
            this.closingView = closingView;
        }

        public GuillotineBuilder setActionBarViewForAnimation(View view) {
            this.actionBarView = view;
            return this;
        }

        public GuillotineBuilder setGuillotineListener(GuillotineListener guillotineListener) {
            this.guillotineListener = guillotineListener;
            return this;
        }

        public GuillotineBuilder setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        public GuillotineBuilder setStartDelay(long startDelay) {
            this.startDelay = startDelay;
            return this;
        }

        public GuillotineBuilder setRightToLeftLayout(boolean isRightToLeftLayout) {
            this.isRightToLeftLayout = isRightToLeftLayout;
            return this;
        }

        public GuillotineBuilder setInterpolator(TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public GuillotineBuilder setClosedOnStart(boolean isClosedOnStart) {
            this.isClosedOnStart = isClosedOnStart;
            return this;
        }

        public GuillotineAnimation build() {
            return new GuillotineAnimation(this);
        }
    }

    public boolean isMenuOpen() {
        return menuOpen;
    }

    public void setMenuOpen(boolean menuOpen) {
        this.menuOpen = menuOpen;
    }
}
