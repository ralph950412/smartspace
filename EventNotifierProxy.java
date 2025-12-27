package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTargetEvent;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
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
