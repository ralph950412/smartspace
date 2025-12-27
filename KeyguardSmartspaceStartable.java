package com.google.android.systemui.smartspace;

import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.util.InitializationChecker;
import java.lang.invoke.VarHandle;
import kotlinx.coroutines.BuildersKt;

/* compiled from: go/retraceme af8e0b46c0cb0ee2c99e9b6d0c434e5c0b686fd9230eaab7fb9a40e3a9d0cf6f */
/* loaded from: classes2.dex */
public final class KeyguardSmartspaceStartable implements CoreStartable {
    public final InitializationChecker initializationChecker;
    public final KeyguardMediaViewController mediaController;
    public final KeyguardZenAlarmViewController zenController;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public KeyguardSmartspaceStartable(KeyguardZenAlarmViewController keyguardZenAlarmViewController, KeyguardMediaViewController keyguardMediaViewController, InitializationChecker initializationChecker) {
        this.zenController = keyguardZenAlarmViewController;
        this.mediaController = keyguardMediaViewController;
        this.initializationChecker = initializationChecker;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.initializationChecker.initializeComponents()) {
            KeyguardZenAlarmViewController keyguardZenAlarmViewController = this.zenController;
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin = keyguardZenAlarmViewController.datePlugin;
            KeyguardZenAlarmViewController$init$1 keyguardZenAlarmViewController$init$1 = new KeyguardZenAlarmViewController$init$1();
            keyguardZenAlarmViewController$init$1.this$0 = keyguardZenAlarmViewController;
            VarHandle.storeStoreFence();
            bcSmartspaceDataPlugin.addOnAttachStateChangeListener(keyguardZenAlarmViewController$init$1);
            BuildersKt.launch$default(keyguardZenAlarmViewController.applicationScope, null, null, new KeyguardZenAlarmViewController$updateNextAlarm$1(keyguardZenAlarmViewController, null), 3);
            KeyguardMediaViewController keyguardMediaViewController = this.mediaController;
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = keyguardMediaViewController.plugin;
            KeyguardMediaViewController$init$1 keyguardMediaViewController$init$1 = new KeyguardMediaViewController$init$1();
            keyguardMediaViewController$init$1.this$0 = keyguardMediaViewController;
            VarHandle.storeStoreFence();
            bcSmartspaceDataPlugin2.addOnAttachStateChangeListener(keyguardMediaViewController$init$1);
        }
    }
}
