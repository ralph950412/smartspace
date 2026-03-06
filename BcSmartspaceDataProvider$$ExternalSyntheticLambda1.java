package com.google.android.systemui.smartspace;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.function.Consumer;

/* compiled from: go/retraceme b71a7f1f70117f8c58f90def809cf7784fe36a4a686923e2526fc7de282d885a */
/* loaded from: classes2.dex */
public final /* synthetic */ class BcSmartspaceDataProvider$$ExternalSyntheticLambda1 implements Consumer {
    public /* synthetic */ BcSmartspaceDataProvider f$0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BcSmartspaceDataPlugin.SmartspaceTargetListener) obj).onSmartspaceTargetsUpdated(this.f$0.mSmartspaceTargets);
    }
}
