package com.google.android.systemui.smartspace;

import android.app.AlarmManager;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class KeyguardZenAlarmViewController$showNextAlarm$1 implements AlarmManager.OnAlarmListener {
    public /* synthetic */ KeyguardZenAlarmViewController this$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.app.AlarmManager.OnAlarmListener
    public final void onAlarm() {
        this.this$0.showAlarm(null);
    }
}
