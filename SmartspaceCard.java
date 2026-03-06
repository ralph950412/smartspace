package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.logging.BcSmartspaceCardLoggingInfo;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public interface SmartspaceCard {
    void bindData(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, boolean z);

    BcSmartspaceCardLoggingInfo getLoggingInfo();

    View getView();

    void setDozeAmount(float f);

    void setPrimaryTextColor(int i);

    void setScreenOn(boolean z);
}
