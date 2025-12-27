package com.google.android.systemui.smartspace;

import android.app.AlarmManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.statusbar.policy.domain.model.ZenModeInfo;
import java.util.Set;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class KeyguardZenAlarmViewController {
    public Drawable alarmImage;
    public AlarmManager alarmManager;
    public CoroutineScope applicationScope;
    public CoroutineDispatcher bgDispatcher;
    public Context context;
    public BcSmartspaceDataPlugin datePlugin;
    public Handler handler;
    public KeyguardZenAlarmViewController$nextAlarmCallback$1 nextAlarmCallback;
    public NextAlarmControllerImpl nextAlarmController;
    public KeyguardZenAlarmViewController$showNextAlarm$1 showNextAlarm;
    public Set smartspaceViews;
    public ZenModeController zenModeController;
    public ZenModeInteractor zenModeInteractor;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Job showAlarm(Long l) {
        return BuildersKt.launch$default(this.applicationScope, null, null, new KeyguardZenAlarmViewController$showAlarm$1(l, this, null), 3);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateModeIcon(BcSmartspaceDataPlugin.SmartspaceView smartspaceView, ZenModeInfo zenModeInfo) {
        BuildersKt.launch$default(this.applicationScope, null, null, new KeyguardZenAlarmViewController$updateModeIcon$1(zenModeInfo, this, smartspaceView, null), 3);
    }

    public static /* synthetic */ void getSmartspaceViews$annotations() {
    }
}
