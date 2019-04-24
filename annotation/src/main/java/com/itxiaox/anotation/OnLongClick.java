package com.itxiaox.anotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSettter = "setOnLongClickListener",
        listenerType = View.OnLongClickListener.class,
        callBackListener = "onLongClick")
public @interface OnLongClick {
    int[] value();
}
