package com.google.android.systemui.smartspace;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import kotlinx.coroutines.BuildersKt;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class KeyguardZenAlarmViewController$init$1 implements View.OnAttachStateChangeListener {
    public /* synthetic */ KeyguardZenAlarmViewController this$0;

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
