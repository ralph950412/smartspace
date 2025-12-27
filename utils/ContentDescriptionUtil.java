package com.google.android.systemui.smartspace.utils;

import android.util.Log;
import android.view.View;
import com.android.wm.shell.R;
import java.util.Arrays;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public abstract class ContentDescriptionUtil {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static final void setFormattedContentDescription(String str, View view, CharSequence charSequence, CharSequence charSequence2) {
        CharSequence string = (charSequence == null || charSequence.length() == 0) ? charSequence2 : (charSequence2 == null || charSequence2.length() == 0) ? charSequence : view.getContext().getString(R.string.generic_smartspace_concatenated_desc, charSequence2, charSequence);
        Log.i(str, String.format("setFormattedContentDescription: text=%s, iconDescription=%s, contentDescription=%s", Arrays.copyOf(new Object[]{charSequence, charSequence2, string}, 3)));
        view.setContentDescription(string);
    }
}
