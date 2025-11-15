package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;

/* compiled from: go/retraceme bc8f312991c214754a2e368df4ed1e9dbe6546937b19609896dfc63dbd122911 */
/* loaded from: classes2.dex */
public interface SmartspaceCard {
    void bindData(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, boolean z);

    BcSmartspaceCardLoggingInfo getLoggingInfo();

    View getView();

    void setDozeAmount$1(float f);

    void setPrimaryTextColor(int i);

    void setScreenOn(boolean z);
}
