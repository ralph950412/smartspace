package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.os.Handler;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.uitemplate.BaseTemplateCard;
import java.util.List;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public interface CardAdapter {
    SmartspaceCard getCardAtPosition(int i);

    int getCount();

    float getDozeAmount();

    boolean getHasAodLockscreenTransition();

    boolean getHasDifferentTargets();

    BcSmartspaceCard getLegacyCardAtPosition(int i);

    List getLockscreenTargets();

    BcSmartspaceRemoteViewsCard getRemoteViewsCardAtPosition(int i);

    List getSmartspaceTargets();

    SmartspaceTarget getTargetAtPosition(int i);

    BaseTemplateCard getTemplateCardAtPosition(int i);

    String getUiSurface();

    void setBgHandler(Handler handler);

    void setConfigProvider(BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin);

    void setDataProvider(BcSmartspaceDataPlugin bcSmartspaceDataPlugin);

    void setDozeAmount(float f);

    void setKeyguardBypassEnabled(boolean z);

    void setMediaTarget(SmartspaceTarget smartspaceTarget);

    void setNonRemoteViewsHorizontalPadding(Integer num);

    void setPrimaryTextColor(int i);

    void setScreenOn(boolean z);

    void setTargets(List list);

    void setTimeChangedDelegate(BcSmartspaceDataPlugin.TimeChangedDelegate timeChangedDelegate);

    void setUiSurface(String str);
}
