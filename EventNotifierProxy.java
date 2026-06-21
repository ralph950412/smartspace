package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTargetEvent;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
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
