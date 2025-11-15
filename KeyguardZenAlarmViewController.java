package com.google.android.systemui.smartspace;

import android.app.AlarmManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Handler;
import android.util.Log;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.statusbar.policy.domain.model.ZenModeInfo;
import com.android.wm.shell.R;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public final class KeyguardZenAlarmViewController {
    public final Drawable alarmImage;
    public final AlarmManager alarmManager;
    public final CoroutineScope applicationScope;
    public final Context context;
    public final BcSmartspaceDataPlugin datePlugin;
    public final Handler handler;
    public final NextClockAlarmController nextClockAlarmController;
    public final ZenModeInteractor zenModeInteractor;
    public final Set smartspaceViews = new LinkedHashSet();
    public final KeyguardZenAlarmViewController$showNextAlarm$1 showNextAlarm = new AlarmManager.OnAlarmListener() { // from class: com.google.android.systemui.smartspace.KeyguardZenAlarmViewController$showNextAlarm$1
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            KeyguardZenAlarmViewController.this.showAlarm();
        }
    };
    public final KeyguardZenAlarmViewController$nextAlarmCallback$1 nextAlarmCallback = new KeyguardZenAlarmViewController$nextAlarmCallback$1(this);

    /* JADX WARN: Type inference failed for: r2v2, types: [com.google.android.systemui.smartspace.KeyguardZenAlarmViewController$showNextAlarm$1] */
    public KeyguardZenAlarmViewController(Context context, BcSmartspaceDataPlugin bcSmartspaceDataPlugin, ZenModeController zenModeController, ZenModeInteractor zenModeInteractor, AlarmManager alarmManager, NextClockAlarmController nextClockAlarmController, Handler handler, CoroutineScope coroutineScope) {
        this.context = context;
        this.datePlugin = bcSmartspaceDataPlugin;
        this.zenModeInteractor = zenModeInteractor;
        this.alarmManager = alarmManager;
        this.nextClockAlarmController = nextClockAlarmController;
        this.handler = handler;
        this.applicationScope = coroutineScope;
        if (((InsetDrawable) context.getResources().getDrawable(R.drawable.stat_sys_dnd, null)).getDrawable() == null) {
            throw new IllegalStateException("Required value was null.");
        }
        this.alarmImage = context.getResources().getDrawable(R.drawable.ic_access_alarms_big, null);
    }

    public final Job showAlarm() {
        return BuildersKt.launch$default(this.applicationScope, null, null, new KeyguardZenAlarmViewController$showAlarm$1(this, null), 3);
    }

    public final void updateDnd() {
        Log.wtf("KeyguardZenAlarmViewController", "updateDnd should not be called when modes_ui_icons flag is enabled");
    }

    public final void updateModeIcon(BcSmartspaceDataPlugin.SmartspaceView smartspaceView, ZenModeInfo zenModeInfo) {
        BuildersKt.launch$default(this.applicationScope, null, null, new KeyguardZenAlarmViewController$updateModeIcon$1(zenModeInfo, this, smartspaceView, null), 3);
    }

    public static /* synthetic */ void getSmartspaceViews$annotations() {
    }
}
