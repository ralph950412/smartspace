package com.google.android.systemui.smartspace;

import com.android.systemui.plugins.BcSmartspaceConfigPlugin;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public final class DefaultBcSmartspaceConfigProvider implements BcSmartspaceConfigPlugin {
    @Override // com.android.systemui.plugins.BcSmartspaceConfigPlugin
    public final boolean isDefaultDateWeatherDisabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceConfigPlugin
    public final boolean isSwipeEventLoggingEnabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.BcSmartspaceConfigPlugin
    public final boolean isViewPager2Enabled() {
        return false;
    }
}
