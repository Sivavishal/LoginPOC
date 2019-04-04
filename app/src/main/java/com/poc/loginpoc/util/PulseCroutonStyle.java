package com.poc.loginpoc.util;

import android.view.ViewGroup;


import com.poc.loginpoc.R;

import de.keyboardsurfer.android.widget.crouton.Style;

public class PulseCroutonStyle {
    public static final Style ALERT;
    public static final Style INFO;
    public static final Style CONFIRM;

    static {
        ALERT = new Style.Builder()
                .setTextColor(R.color.crouton_text)
                .setBackgroundColor(R.color.bg_alert)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setPaddingDimensionResId(R.dimen.crouton_padding)
                .build();

        INFO = new Style.Builder()
                .setTextColor(R.color.crouton_text)
                .setBackgroundColor(R.color.bg_info)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setPaddingDimensionResId(R.dimen.crouton_padding)
                .build();

        CONFIRM = new Style.Builder()
                .setTextColor(R.color.crouton_text)
                .setBackgroundColor(R.color.bg_confirm)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setPaddingDimensionResId(R.dimen.crouton_padding)
                .build();
    }

    private PulseCroutonStyle() {
    }
}
