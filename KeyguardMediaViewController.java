package com.google.android.systemui.smartspace;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class KeyguardMediaViewController {
    public CharSequence artist;
    public Context context;
    public ComponentName mediaComponent;
    public KeyguardMediaViewController$mediaListener$1 mediaListener;
    public NotificationMediaManager mediaManager;
    public BcSmartspaceDataPlugin plugin;
    public BcSmartspaceDataPlugin.SmartspaceView smartspaceView;
    public CharSequence title;
    public DelayableExecutor uiExecutor;
    public UserTracker userTracker;

    public static /* synthetic */ void getSmartspaceView$annotations() {
    }
}
