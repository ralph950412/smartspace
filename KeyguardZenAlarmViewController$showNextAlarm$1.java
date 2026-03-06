package com.google.android.systemui.smartspace;

import android.app.AlarmManager;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final class KeyguardZenAlarmViewController$showNextAlarm$1 implements AlarmManager.OnAlarmListener {
    public /* synthetic */ KeyguardZenAlarmViewController this$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.app.AlarmManager.OnAlarmListener
    public final void onAlarm() {
        this.this$0.showAlarm(null);
    }
}
