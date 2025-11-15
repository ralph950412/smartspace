package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTargetEvent;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public final class EventNotifierProxy implements BcSmartspaceDataPlugin.SmartspaceEventNotifier {
    public BcSmartspaceDataPlugin.SmartspaceEventDispatcher eventDispatcher;
    public BcSmartspaceDataPlugin.IntentStarter intentStarterRef;

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
    public final BcSmartspaceDataPlugin.IntentStarter getIntentStarter() {
        return this.intentStarterRef;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventDispatcher
    public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
        BcSmartspaceDataPlugin.SmartspaceEventDispatcher smartspaceEventDispatcher = this.eventDispatcher;
        if (smartspaceEventDispatcher != null) {
            smartspaceEventDispatcher.notifySmartspaceEvent(smartspaceTargetEvent);
        }
    }
}
