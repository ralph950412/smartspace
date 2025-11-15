package com.google.android.systemui.smartspace.utils;

import android.util.Log;
import android.view.View;
import com.android.wm.shell.R;
import java.util.Arrays;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public abstract class ContentDescriptionUtil {
    public static final void setFormattedContentDescription(String str, View view, CharSequence charSequence, CharSequence charSequence2) {
        CharSequence string = (charSequence == null || charSequence.length() == 0) ? charSequence2 : (charSequence2 == null || charSequence2.length() == 0) ? charSequence : view.getContext().getString(R.string.generic_smartspace_concatenated_desc, charSequence2, charSequence);
        Log.i(str, String.format("setFormattedContentDescription: text=%s, iconDescription=%s, contentDescription=%s", Arrays.copyOf(new Object[]{charSequence, charSequence2, string}, 3)));
        view.setContentDescription(string);
    }
}
