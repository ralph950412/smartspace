package com.google.android.systemui.smartspace;

import android.view.View;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final class KeyguardMediaViewController$init$1 implements View.OnAttachStateChangeListener {
    public /* synthetic */ KeyguardMediaViewController this$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: android.view.View */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        KeyguardMediaViewController keyguardMediaViewController = this.this$0;
        keyguardMediaViewController.smartspaceView = (BcSmartspaceDataPlugin.SmartspaceView) view;
        keyguardMediaViewController.mediaManager.addCallback(keyguardMediaViewController.mediaListener);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        KeyguardMediaViewController keyguardMediaViewController = this.this$0;
        keyguardMediaViewController.smartspaceView = null;
        NotificationMediaManager notificationMediaManager = keyguardMediaViewController.mediaManager;
        notificationMediaManager.mMediaListeners.remove(keyguardMediaViewController.mediaListener);
    }
}
