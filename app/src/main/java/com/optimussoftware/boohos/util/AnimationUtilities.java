package com.optimussoftware.boohos.util;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.optimussoftware.boohos.widget.OptimusTextView;

public class AnimationUtilities {

    public static ObjectAnimator movimientoY(View view, int duracion, float desdePos, float hastaPos) {
        //Devuelve una animación para mover un view en "Y" o verticalmente, no la reproduce, sólo la devuelve lista para
        //reproducirse.
        ObjectAnimator animacion = new ObjectAnimator();
        animacion.setTarget(view);
        animacion.setPropertyName("translationY");
        animacion.setFloatValues(desdePos, hastaPos);
        animacion.setDuration(duracion);
        return animacion;
    }

    public static ObjectAnimator movimientoY(View view, int duracion, float movimiento) {
        //Devuelve una animación para mover un view en "Y" o verticalmente, no la reproduce, sólo la devuelve lista para
        //reproducirse.
        ObjectAnimator animacion = new ObjectAnimator();
        animacion.setTarget(view);
        animacion.setPropertyName("translationY");
        animacion.setFloatValues(movimiento);
        animacion.setDuration(duracion);
        return animacion;
    }

    public static ObjectAnimator movimientoX(View view, int duracion, float movimiento) {
        //Devuelve una animación para mover un view en "X" u horizontalmente, no la reproduce, sólo la devuelve lista para
        //reproducirse.
        ObjectAnimator animacion = new ObjectAnimator();
        animacion.setTarget(view);
        animacion.setPropertyName("translationX");
        animacion.setFloatValues(movimiento);
        animacion.setDuration(duracion);
        return animacion;
    }

    public static ObjectAnimator movimientoX(View view, int duracion, float desdePos, float hastaPos) {
        //Devuelve una animación para mover un view en "X" u horizontalmente, no la reproduce, sólo la devuelve lista para
        //reproducirse.
        ObjectAnimator animacion = new ObjectAnimator();
        animacion.setTarget(view);
        animacion.setPropertyName("translationX");
        animacion.setFloatValues(desdePos, hastaPos);
        animacion.setDuration(duracion);
        return animacion;
    }

    public static ObjectAnimator animSizeY(View view, float escala, int duracion) {
        //Devuelve una animación para cambiar el tamaño de un view en "Y" o verticalmente, no la reproduce, sólo la devuelve lista para
        //reproducirse.
        return animSizeY(view, 1, escala, duracion);
    }

    public static ObjectAnimator animSizeX(View view, float desdeEscala, float hastaEscala, int duracion) {
        //Devuelve una animación para cambiar el tamaño de un view en "X" u horizontalmente, no la reproduce, sólo la devuelve lista para
        //reproducirse.
        ObjectAnimator animacionX = new ObjectAnimator();
        animacionX.setTarget(view);
        animacionX.setPropertyName("scaleX");
        animacionX.setFloatValues(desdeEscala, hastaEscala);
        animacionX.setDuration(duracion);
        return animacionX;
    }

    public static ObjectAnimator animSizeY(View view, float desdeEscala, float hastaEscala, int duracion) {
        //Devuelve una animación para cambiar el tamaño de un view en "Y" o verticalmente, no la reproduce, sólo la devuelve lista para
        //reproducirse.
        ObjectAnimator animacionY = new ObjectAnimator();
        animacionY.setTarget(view);
        animacionY.setPropertyName("scaleY");
        animacionY.setFloatValues(desdeEscala, hastaEscala);
        animacionY.setDuration(duracion);
        return animacionY;
    }

    public static ObjectAnimator animSizeX(View view, float escala, int duracion) {
        //Devuelve una animación para cambiar el tamaño de un view en "X" u horizontalmente, no la reproduce, sólo la devuelve lista para
        //reproducirse.
        return animSizeX(view, 1, escala, duracion);
    }

    public static ValueAnimator cambiarTamanoCompleto(View view, float escala, int duracion) {
        //Devuelve una animación para cambiar el tamaño tanto vertical como horizontalmente, no la reproduce, sólo la devuelve lista para
        //reproducirse.
        PropertyValuesHolder propX = PropertyValuesHolder.ofFloat(View.SCALE_X, escala);
        PropertyValuesHolder propY = PropertyValuesHolder.ofFloat(View.SCALE_Y, escala);

        ValueAnimator animacionTodo = ObjectAnimator.ofPropertyValuesHolder(view, propX, propY);
        animacionTodo.setDuration(duracion);

        return animacionTodo;
    }

    public static ValueAnimator cambiarTamanoCompleto(View view, float desdeEscala, float hastaEscala, int duracion) {
        //Devuelve una animación para cambiar el tamaño tanto vertical como horizontalmente, no la reproduce, sólo la devuelve lista para
        //reproducirse.
        PropertyValuesHolder propX = PropertyValuesHolder.ofFloat(View.SCALE_X, desdeEscala, hastaEscala);
        PropertyValuesHolder propY = PropertyValuesHolder.ofFloat(View.SCALE_Y, desdeEscala, hastaEscala);

        ValueAnimator animacionTodo = ObjectAnimator.ofPropertyValuesHolder(view, propX, propY);
        animacionTodo.setDuration(duracion);

        return animacionTodo;
    }

    public static void animarAlphaVista(View view, int tiempo, boolean mostrar) {
        animarAlphaVista(view, tiempo, mostrar, true);
    }

    public static ObjectAnimator animarAlphaVista(View view, int tiempo, boolean mostrar, boolean animar) {
        //Muestra u oculta un view pero con una animación de desvanecimiento, lo anima automáticamente.
        float alpha = (mostrar) ? 1f : 0f;
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", alpha);
        anim.setDuration(tiempo);
        if (mostrar) {
            view.setAlpha(0f);
        } else {
            view.setAlpha(1f);
        }
        view.setVisibility(View.VISIBLE);
        if (animar) {
            anim.start();
        }
        return anim;
    }


    public static Boolean gravityBottom(View v) {
        if (parentLinear(v)) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v.getLayoutParams();
            if (lp != null) {
                return lp.gravity == Gravity.BOTTOM;
            }
        } else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
            if (lp != null) {
                return lp.getRules()[RelativeLayout.ALIGN_PARENT_BOTTOM] == RelativeLayout.TRUE;
            }
        }
        return null;
    }

    public static Boolean gravityRight(View v) {
        if (parentLinear(v)) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v.getLayoutParams();
            if (lp != null) {
                return (lp.gravity == Gravity.RIGHT);
            }
        } else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
            if (lp != null) {
                return lp.getRules()[RelativeLayout.ALIGN_PARENT_RIGHT] == RelativeLayout.TRUE;
            }
        }
        return null;
    }

    public static Integer getMarginTop(View view) {
        if (parentLinear(view)) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            if (lp != null) {
                return lp.topMargin;
            } else {
                return -1;
            }
        } else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
            if (lp != null) {
                return lp.topMargin;
            } else {
                return -1;
            }
        }
    }

    public static Integer getMarginBottom(View view) {
        if (parentLinear(view)) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            if (lp != null) {
                return lp.bottomMargin;
            } else {
                return -1;
            }
        } else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
            if (lp != null) {
                return lp.bottomMargin;
            } else {
                return -1;
            }
        }
    }

    public static Integer getMarginLeft(View view) {
        if (parentLinear(view)) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            if (lp != null) {
                return lp.leftMargin;
            } else {
                return -1;
            }
        } else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
            if (lp != null) {
                return lp.leftMargin;
            } else {
                return -1;
            }
        }
    }

    public static Integer getMarginRight(View view) {
        if (parentLinear(view)) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
            if (lp != null) {
                return lp.rightMargin;
            } else {
                return -1;
            }
        } else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
            if (lp != null) {
                return lp.rightMargin;
            } else {
                return -1;
            }
        }
    }

    public static int getTransLayoutY(View view) {
        if (gravityBottom(view)) {
            return -getMarginBottom(view);
        } else {
            return getMarginTop(view);
        }
    }

    public static int getTransLayoutX(View view) {
        if (gravityRight(view)) {
            return -getMarginRight(view);
        } else {
            return getMarginLeft(view);
        }
    }

    public static void setTransLayoutY(View view, int mov) {
        setTransLayoutY(view, mov, null);
    }

    public static void setTransLayoutY(View view, int mov, Integer desdeEstaPos) {
        int actualPos = (desdeEstaPos != null) ? desdeEstaPos : getTransLayoutY(view);
        if (gravityBottom(view)) {
            actualPos = -actualPos;
            if (parentLinear(view)) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                lp.bottomMargin = actualPos - mov;
                view.setLayoutParams(lp);
            } else {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                lp.bottomMargin = actualPos - mov;
                view.setLayoutParams(lp);
            }
        } else {
            if (parentLinear(view)) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                lp.topMargin = actualPos + mov;
                view.setLayoutParams(lp);
            } else {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                lp.topMargin = actualPos + mov;
                view.setLayoutParams(lp);
            }
        }
    }

    public static void setTransLayoutX(View view, int mov) {
        setTransLayoutX(view, mov, null);
    }

    public static void setTransLayoutX(View view, int mov, Integer desdeEstaPos) {
        int actualPos = (desdeEstaPos != null) ? desdeEstaPos : getTransLayoutX(view);
        if (gravityRight(view)) {
            actualPos = -actualPos;
            if (parentLinear(view)) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                lp.rightMargin = actualPos - mov;
                view.setLayoutParams(lp);
            } else {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                lp.rightMargin = actualPos - mov;
                view.setLayoutParams(lp);
            }
        } else {
            if (parentLinear(view)) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                lp.leftMargin = actualPos + mov;
                view.setLayoutParams(lp);
            } else {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                lp.leftMargin = actualPos + mov;
                view.setLayoutParams(lp);
            }
        }
    }

    public static void setLayoutScaleY(View view, float escala) {
        setLayoutHeight(view, (int) (view.getHeight() * escala));
    }

    public static void setLayoutScaleX(View view, float escala) {
        setLayoutWidth(view, (int) (view.getWidth() * escala));
    }

    public static void setLayoutHeight(View view, int height) {
        view.getLayoutParams().height = height;
        view.requestLayout();
    }

    public static void setLayoutWidth(View view, int width) {
        view.getLayoutParams().width = width;
        view.requestLayout();
    }

    public static Boolean parentLinear(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent instanceof RelativeLayout) {
            return false;
        } else if (parent instanceof LinearLayout) {
            return true;
        }
        throw new IllegalArgumentException();
    }


    public static void girarElemento(final View img, final int tiempo) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(img, "rotation", 0, 360);
        animator.setDuration(tiempo);
        animator.setRepeatCount(Animation.INFINITE);
        animator.start();
    }

    public static ValueAnimator transicionBackgroundColor(final View view, int colorInicio, int colorFin) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorInicio, colorFin);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
        return colorAnimation;
    }

    public static ValueAnimator transicionTextColor(final OptimusTextView text, int colorInicio, int colorFin) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorInicio, colorFin);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                text.setTextColor((Integer) animator.getAnimatedValue());
            }

        });
        return colorAnimation;
    }

}
