package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.content.Intent;
import android.graphics.drawable.Drawable;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class BcNextAlarmData {
    public static final SmartspaceAction SHOW_ALARMS_ACTION = new SmartspaceAction.Builder("nextAlarmId", "Next alarm").setIntent(new Intent("android.intent.action.SHOW_ALARMS")).build();
    public String mDescription;
    public Drawable mImage;
}
