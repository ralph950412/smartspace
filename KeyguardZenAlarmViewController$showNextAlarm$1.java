package com.google.android.systemui.smartspace;

import android.app.AlarmManager;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class KeyguardZenAlarmViewController$showNextAlarm$1 implements AlarmManager.OnAlarmListener {
    public /* synthetic */ KeyguardZenAlarmViewController this$0;

    @Override // android.app.AlarmManager.OnAlarmListener
    public final void onAlarm() {
        this.this$0.showAlarm(null);
    }
}
