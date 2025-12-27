package com.google.android.systemui.smartspace;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import kotlinx.coroutines.BuildersKt;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class KeyguardZenAlarmViewController$init$1 implements View.OnAttachStateChangeListener {
    public /* synthetic */ KeyguardZenAlarmViewController this$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: android.view.View */
    /* JADX DEBUG: Multi-variable search result rejected for r6v1, resolved type: com.android.systemui.plugins.BcSmartspaceDataPlugin$SmartspaceView */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
        BcSmartspaceDataPlugin.SmartspaceView smartspaceView = (BcSmartspaceDataPlugin.SmartspaceView) view;
        if (!keyguardZenAlarmViewController.smartspaceViews.contains(smartspaceView)) {
            keyguardZenAlarmViewController.smartspaceViews.add(smartspaceView);
            RepeatWhenAttachedKt.repeatWhenAttached$default((View) smartspaceView, null, new KeyguardZenAlarmViewController$addSmartspaceView$1(keyguardZenAlarmViewController, smartspaceView, null), 3);
        }
        if (this.this$0.smartspaceViews.size() == 1) {
            KeyguardZenAlarmViewController keyguardZenAlarmViewController2 = this.this$0;
            keyguardZenAlarmViewController2.nextAlarmController.addCallback(keyguardZenAlarmViewController2.nextAlarmCallback);
        }
        KeyguardZenAlarmViewController keyguardZenAlarmViewController3 = this.this$0;
        BuildersKt.launch$default(keyguardZenAlarmViewController3.applicationScope, null, null, new KeyguardZenAlarmViewController$updateNextAlarm$1(keyguardZenAlarmViewController3, null), 3);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: android.view.View */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.this$0.smartspaceViews.remove((BcSmartspaceDataPlugin.SmartspaceView) view);
        if (this.this$0.smartspaceViews.isEmpty()) {
            KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.this$0;
            keyguardZenAlarmViewController.nextAlarmController.removeCallback(keyguardZenAlarmViewController.nextAlarmCallback);
        }
    }
}
