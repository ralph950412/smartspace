package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;

/* compiled from: go/retraceme 2166bc0b1982ea757f433cb54b93594e68249d3d6a2375aeffa96b8ec4684c84 */
/* loaded from: classes2.dex */
public interface SmartspaceCard {
    void bindData(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, boolean z);

    BcSmartspaceCardLoggingInfo getLoggingInfo();

    View getView();

    void setDozeAmount$1(float f);

    void setPrimaryTextColor(int i);

    void setScreenOn(boolean z);
}
