package com.google.android.systemui.smartspace;

import android.app.AlarmManager;
import com.android.systemui.statusbar.policy.NextAlarmController$NextAlarmChangeCallback;
import kotlinx.coroutines.BuildersKt;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final class KeyguardZenAlarmViewController$nextAlarmCallback$1 implements NextAlarmController$NextAlarmChangeCallback {
    public /* synthetic */ KeyguardZenAlarmViewController this$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.statusbar.policy.NextAlarmController$NextAlarmChangeCallback
    public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
        KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
        BuildersKt.launch$default(keyguardZenAlarmViewController.applicationScope, null, null, new KeyguardZenAlarmViewController$updateNextAlarm$1(keyguardZenAlarmViewController, null), 3);
    }
}
