package com.google.android.systemui.smartspace;

import android.app.AlarmManager;
import com.android.systemui.statusbar.policy.NextAlarmController$NextAlarmChangeCallback;
import kotlinx.coroutines.BuildersKt;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class KeyguardZenAlarmViewController$nextAlarmCallback$1 implements NextAlarmController$NextAlarmChangeCallback {
    public /* synthetic */ KeyguardZenAlarmViewController this$0;

    @Override // com.android.systemui.statusbar.policy.NextAlarmController$NextAlarmChangeCallback
    public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
        KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
        BuildersKt.launch$default(keyguardZenAlarmViewController.applicationScope, null, null, new KeyguardZenAlarmViewController$updateNextAlarm$1(keyguardZenAlarmViewController, null), 3);
    }
}
