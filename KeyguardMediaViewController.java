package com.google.android.systemui.smartspace;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class KeyguardMediaViewController {
    public CharSequence artist;
    public Context context;
    public ComponentName mediaComponent;
    public KeyguardMediaViewController$mediaListener$1 mediaListener;
    public NotificationMediaManager mediaManager;
    public LockscreenSmartspaceController smartspaceController;
    public CharSequence title;
    public DelayableExecutor uiExecutor;
    public UserTracker userTracker;
}
