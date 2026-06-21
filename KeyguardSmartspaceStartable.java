package com.google.android.systemui.smartspace;

import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.util.InitializationChecker;
import java.lang.invoke.VarHandle;
import kotlinx.coroutines.BuildersKt;

/* compiled from: go/retraceme 109b9d95419d40ed7f94ba06f2e494aa100aa2b80b21457e78a8af5d54598634 */
/* loaded from: classes3.dex */
public final class KeyguardSmartspaceStartable implements CoreStartable {
    public final InitializationChecker initializationChecker;
    public final KeyguardMediaViewController mediaController;
    public final KeyguardZenAlarmViewController zenController;

    public KeyguardSmartspaceStartable(KeyguardZenAlarmViewController keyguardZenAlarmViewController, KeyguardMediaViewController keyguardMediaViewController, InitializationChecker initializationChecker) {
        this.zenController = keyguardZenAlarmViewController;
        this.mediaController = keyguardMediaViewController;
        this.initializationChecker = initializationChecker;
    }

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
            keyguardMediaViewController.mediaManager.addCallback(keyguardMediaViewController.mediaListener);
        }
    }
}
