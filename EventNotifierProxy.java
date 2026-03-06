package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTargetEvent;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final class EventNotifierProxy implements BcSmartspaceDataPlugin.SmartspaceEventNotifier {
    public BcSmartspaceDataPlugin.SmartspaceEventDispatcher eventDispatcher;
    public BcSmartspaceDataPlugin.IntentStarter intentStarterRef;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
    public final BcSmartspaceDataPlugin.IntentStarter getIntentStarter() {
        return this.intentStarterRef;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventDispatcher
    public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
        BcSmartspaceDataPlugin.SmartspaceEventDispatcher smartspaceEventDispatcher = this.eventDispatcher;
        if (smartspaceEventDispatcher != null) {
            smartspaceEventDispatcher.notifySmartspaceEvent(smartspaceTargetEvent);
        }
    }
}
