package com.kevin.dev.farmer.Resources;

import android.view.View;

import com.kevin.dev.farmer.model.Fertilizer;

public interface ClickListener {
    default void onClick(View view, int position, Fertilizer subjects){}
}
